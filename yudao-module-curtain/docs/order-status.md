# 成品订单四层状态定义与联动规则

> 适用订单类型：`types = CURTAIN`（成品单）及同结构的 `FABRIC` 面单。  
> 字典类型：`zc_order_status`（订单/窗帘行）；用料明细使用 `ZcSalesOrderMaterialStatusEnum`。  
> 聚合计算器：`ZcSalesOrderStatusCalculator`

## 一、四层结构

```
zc_sales_order（L1 订单）
└── zc_sales_order_curtain（L2 窗帘行）
    └── zc_sales_order_structure（L3 结构行，无 status 字段）
        └── zc_sales_order_material（L4 用料明细）
```

| 层级 | 表 | 是否有 status | 更新方式 |
|------|-----|:---:|----------|
| L1 | `zc_sales_order` | ✅ | 手动确认/完成 + 由 L2 窗帘行聚合 |
| L2 | `zc_sales_order_curtain` | ✅ | 手动确认/打包/发货 + 由 L4 用料聚合配料状态 |
| L3 | `zc_sales_order_structure` | ❌ | — |
| L4 | `zc_sales_order_material` | ✅ | 裁剪 / 撤销裁剪（手动） |

---

## 二、各层状态枚举

### 2.1 L1 订单主表（`zc_sales_order.status`）

枚举类：`ZcSalesOrderStatusEnum`，字典：`zc_order_status`

| 枚举值 | 中文 | 触发方式 |
|--------|------|----------|
| `UNCONFIRMED` | 未确认 | 创建订单时默认 |
| `CONFIRMED` | 已确认 | **手动** `confirmSalesOrder`；或所有窗帘均未进入配料阶段时聚合回退 |
| `BUFEN_PEILIAO` | 部分配料 | **自动**：存在已配料进度（`BUFEN_PEILIAO` / `HAVE_PEILIAO`）的窗帘，但未全部 `HAVE_PEILIAO` |
| `HAVE_PEILIAO` | 已配料 | **自动**：所有窗帘行均为 `HAVE_PEILIAO` |
| `BUFEN_DABAO` | 部分打包 | **自动**：部分窗帘已 `DABAO`，未全部打包且未进入发货阶段 |
| `DABAO` | 已打包 | **自动**：所有窗帘行均为 `DABAO` |
| `BUFEN_FAHUO` | 部分发货 | **自动**：部分窗帘已 `FAHUO` |
| `FAHUO` | 已发货 | **自动**：所有窗帘行均为 `FAHUO` |
| `COMPLETE` | 完成 | **手动** `completeSalesOrder` |

> 注：`NOT_PEILIAO` 用于 L2 窗帘行初始状态，**不**作为 L1 订单主表聚合结果（订单在未配料阶段展示为 `CONFIRMED`）。

### 2.2 L2 窗帘行（`zc_sales_order_curtain.status`）

与订单共用 `ZcSalesOrderStatusEnum`（字典 `zc_order_status`），窗帘行实际使用的取值：

| 枚举值 | 中文 | 触发方式 |
|--------|------|----------|
| `UNCONFIRMED` | 未确认 | 创建订单时；`cancelConfirmSalesOrder` 回退 |
| `NOT_PEILIAO` | 未配料 | **手动** `confirmSalesOrder` 批量写入 |
| `BUFEN_PEILIAO` | 部分配料 | **自动**：该窗帘下部分用料已 `HAVE_PEILIAO` |
| `HAVE_PEILIAO` | 已配料 | **自动**：该窗帘下全部用料已 `HAVE_PEILIAO` |
| `DABAO` | 打包 | **手动** `packCurtain` |
| `FAHUO` | 发货 | **手动** `shipCurtain` |

### 2.3 L3 结构行（`zc_sales_order_structure`）

无 `status` 字段，不参与状态计算，仅作为 L2 与 L4 的关联桥梁（`order_curtain_id` → `order_structure_id`）。

### 2.4 L4 用料明细（`zc_sales_order_material.status`）

枚举类：`ZcSalesOrderMaterialStatusEnum`

| 枚举值 | 中文 | 触发方式 |
|--------|------|----------|
| `NOT_PEILIAO` | 未配料 | 默认；`cancelCutMaterial` 回退 |
| `HAVE_PEILIAO` | 已配料 | **手动** `cutMaterial`（裁剪出库） |

关联字段：`batch_id`、`cut_quantity`；裁剪时若批次为整匹（`status=1`）自动调整为余料（`status=-1`）。

---

## 三、聚合规则

实现类：`ZcSalesOrderStatusCalculator`

### 3.1 L4 → L2（用料驱动窗帘配料状态）

统计某窗帘行下所有结构行的用料明细（经 `order_curtain_id` → `order_structure_id` 关联）：

```
全部 HAVE_PEILIAO  →  窗帘 HAVE_PEILIAO
部分 HAVE_PEILIAO  →  窗帘 BUFEN_PEILIAO
均未配料           →  窗帘 NOT_PEILIAO
```

**保护规则**：窗帘行已为 `DABAO` / `FAHUO` 时，裁剪不再覆盖其状态（配料阶段与履约阶段隔离）。

**触发入口**：`cutMaterial` / `cancelCutMaterial` → `syncAfterMaterialChange(orderId, orderStructureId)`

### 3.2 L2 → L1（窗帘驱动订单状态）

统计订单下所有窗帘行，**优先级由高到低**：

```
1. 发货：全部 FAHUO → FAHUO；部分 → BUFEN_FAHUO
2. 打包：全部 DABAO → DABAO；部分 → BUFEN_DABAO
3. 配料：全部 HAVE_PEILIAO → HAVE_PEILIAO；存在配料进度 → BUFEN_PEILIAO
4. 其余 → CONFIRMED
```

**触发入口**：
- 裁剪/撤销裁剪（经 L4→L2 后再聚合）
- `packCurtain` / `shipCurtain`
- `cancelPackCurtain` / `cancelShipCurtain`

### 3.3 撤销操作的回退规则

| 操作 | 窗帘行回退 | 订单聚合 |
|------|-----------|----------|
| 撤销裁剪 | 按下属用料重算配料状态 | 由窗帘行重新聚合 |
| 撤销打包 | 按下属用料重算配料状态（非 `CONFIRMED`） | 由窗帘行重新聚合 |
| 撤销发货 | 有 `pack_time` → `DABAO`；否则按用料重算配料状态 | 由窗帘行重新聚合 |
| 取消确认 | 全部窗帘 → `UNCONFIRMED`（须先撤销全部裁剪） | `UNCONFIRMED` |

---

## 四、状态流转总览

```
                    ┌── 手动 confirm ──────────────────────────────┐
                    ▼                                              │
            UNCONFIRMED ── cancelConfirm ──► UNCONFIRMED          │
                    │                                              │
                    │ confirm                                      │
                    ▼                                              │
              CONFIRMED ◄── 聚合：所有窗帘均未配料 ────────────────┘
                    │
        ┌───────────┼─────────── 裁剪（L4→L2→L1）───────────┐
        ▼           ▼                                         │
  BUFEN_PEILIAO  HAVE_PEILIAO                                 │
        │           │                                         │
        └───────────┘                                         │
                    │                                         │
        ┌───────────┼─────────── 打包（手动 L2→L1）───────────┤
        ▼           ▼                                         │
  BUFEN_DABAO    DABAO                                        │
        │           │                                         │
        └───────────┘                                         │
                    │                                         │
        ┌───────────┼─────────── 发货（手动 L2→L1）───────────┤
        ▼           ▼                                         │
  BUFEN_FAHUO    FAHUO                                        │
                    │                                         │
                    │ 手动 complete                           │
                    ▼                                         │
               COMPLETE
```

---

## 五、手动操作与状态约束

| API | 权限 | 订单前置状态 | 说明 |
|-----|------|-------------|------|
| `PUT /zc/sales-order/confirm` | `zc:sales-order:update` | `UNCONFIRMED` | 订单→`CONFIRMED`，窗帘→`NOT_PEILIAO`，扣减客户余额 |
| `PUT /zc/sales-order/cancel-confirm` | 同上 | `CONFIRMED` / `BUFEN_PEILIAO` / `HAVE_PEILIAO` | 须无收款、无已裁剪用料 |
| `PUT /zc/sales-order/cut` | 同上 | — | L4→L2→L1 联动 |
| `PUT /zc/sales-order/cancel-cut` | 同上 | 用料须 `HAVE_PEILIAO` | L4→L2→L1 联动 |
| `PUT /zc/sales-order/complete` | 同上 | 非 `UNCONFIRMED` / `COMPLETE` | 手动完成 |
| 窗帘打包/发货/撤销 | 窗帘接口 | 见 `ZcSalesOrderCurtainServiceImpl` | L2 手动 + L1 聚合 |

---

## 六、字典 SQL

初始化脚本：`sql/mysql/curtain.sql`，字典类型 id=`2301`，数据 id=`3700~3745`。

| id | label | value |
|----|-------|-------|
| 3700 | 未确认 | UNCONFIRMED |
| 3701 | 已确认 | CONFIRMED |
| 3702 | 未配料 | NOT_PEILIAO |
| 3739 | 部分配料 | BUFEN_PEILIAO |
| 3740 | 已配料 | HAVE_PEILIAO |
| 3741 | 部分打包 | BUFEN_DABAO |
| 3742 | 已打包 | DABAO |
| 3743 | 部分发货 | BUFEN_FAHUO |
| 3744 | 已发货 | FAHUO |
| 3745 | 完成 | COMPLETE |

---

## 七、相关源码

| 文件 | 职责 |
|------|------|
| `ZcSalesOrderStatusEnum` | 订单/窗帘行状态枚举 |
| `ZcSalesOrderMaterialStatusEnum` | 用料明细配料状态 |
| `ZcSalesOrderStatusCalculator` | 四层聚合计算 |
| `ZCSalesOrderMaterialServiceImpl` | 裁剪/撤销裁剪 + L4→L2→L1 |
| `ZcSalesOrderCurtainServiceImpl` | 打包/发货/撤销 + L1 聚合 |
| `ZcSalesOrderServiceImpl` | 确认/取消确认/完成（手动） |
