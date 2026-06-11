# ZC 销售订单更新逻辑详解

> 文件来源：`ZcSalesOrderController` + `ZcSalesOrderServiceImpl`
> 生成日期：2026-06-11
> 最后更新：2026-06-11（子表更新策略由"全量删除重建"改为"三路 merge"）

---

## 一、更新相关接口总览

控制器 `ZcSalesOrderController` 中涉及"更新"语义的接口共 6 个，按功能分三大类：

| 接口 | HTTP 方法 & 路径 | 说明 | 权限 |
|------|----------------|------|------|
| 整单更新（成品订单） | `PUT /zc/sales-order/update` | 更新订单主表 + 三路 merge 子表（upsert + delete） | `zc:sales-order:update` |
| 整单更新（面单） | `PUT /zc/sales-order/fabric/update` | 与成品订单逻辑相同，VO 精简版 | `zc:sales-order:update` |
| 确认订单 | `PUT /zc/sales-order/confirm` | `unconfirmed → confirmed`，扣客户余额 | `zc:sales-order:update` |
| 取消确认 | `PUT /zc/sales-order/cancel-confirm` | `confirmed → unconfirmed`，退客户余额 | `zc:sales-order:update` |
| 完成订单 | `PUT /zc/sales-order/complete` | 任意非未确认状态 → `complete` | `zc:sales-order:update` |
| 标记加急 | `PUT /zc/sales-order/expedited` | 将 `isExpedited` 置为 `true` | `zc:sales-order:update` |

---

## 二、整单更新逻辑（成品订单 `updateSalesOrder`）

### 2.1 入口

```
PUT /zc/sales-order/update
请求体：ZcSalesOrderUpdateReqVO（JSON）
```

**ZcSalesOrderUpdateReqVO 字段清单：**

| 字段 | 类型 | 是否必填 | 说明 |
|------|------|---------|------|
| `id` | Long | ✅ | 订单 ID |
| `customerId` | Long | ✅ | 客户 ID |
| `mobile` | String | 否 | 客户手机号 |
| `brandId` | Long | 否 | 品牌 |
| `orderDate` | LocalDate | ✅ | 下单日期 |
| `logisticId` | Long | 否 | 物流 |
| `receiver` | String | 否 | 收货人 |
| `deliveryAddress` | String | ✅ | 送货地址 |
| `freight` | BigDecimal | 否 | 运费 |
| `types` | String | ✅ | 订单类型（但会被 clearProtectedFields 清空，不会写入） |
| `discountAmount` | BigDecimal | 否 | 优惠金额 |
| `totalAmount` | BigDecimal | 否 | 总金额 |
| `amount` | BigDecimal | 否 | 订单金额（优惠后实收） |
| `deliveryDate` | LocalDate | 否 | 交付日期 |
| `note` | String | 否 | 备注 |
| `curtains` | List | ✅ | 窗帘行列表（至少一条），嵌套结构行→用料明细 |

### 2.2 Service 层执行流程

```
updateSalesOrder(ZcSalesOrderUpdateReqVO updateReqVO)
```

#### 第 1 步：前置校验（`prepareOrderUpdate`）

```java
private ZcSalesOrderDO prepareOrderUpdate(Long orderId)
```

1. `validateSalesOrderExists(orderId)`：查询 `zc_sales_order`，不存在则抛 `SALES_ORDER_NOT_EXISTS`。
2. 检查 `confirmTime != null`：已确认订单抛 `SALES_ORDER_CONFIRMED_CANNOT_UPDATE`，**禁止修改**。
3. 返回旧订单 DO（供后续 diff 日志使用）。

> 与旧版本的差别：**不再提前删除子表**，子表的增删改完全交由 `mergeCurtainSubRows` 处理。

#### 第 2 步：构建更新 DO & 清空系统保护字段（`clearProtectedFields`）

将 VO 复制为 DO 后，以下字段强制置 `null`，防止业务接口覆写系统管理列（`updateById` 不更新 null 字段）：

| 字段 | 说明 |
|------|------|
| `orderNo` | 订单号，由系统生成，不可改 |
| `types` | 订单类型，创建后不可改 |
| `payStatus` | 支付状态，由收款流程驱动 |
| `status` | 订单状态，由确认/完成流程驱动 |
| `isExpedited` | 加急标志，由专用接口驱动 |
| `amountReceived` | 已收金额，由收款单驱动 |
| `confirmTime` | 确认时间，由确认接口驱动 |

#### 第 3 步：更新套数 & 写入主表

```java
updateDO.setSets(CollUtil.size(updateReqVO.getCurtains()));  // 套数 = 窗帘行数量
salesOrderMapper.updateById(updateDO);                       // 更新 zc_sales_order
```

#### 第 4 步：写入操作日志上下文

```java
LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT,
    BeanUtils.toBean(existing, ZcSalesOrderUpdateReqVO.class));  // 旧值供 diff 比较
LogRecordContext.putVariable("orderNo", existing.getOrderNo());
```

日志注解模板：`"更新了销售订单【{{#orderNo}}】: {_DIFF{#updateReqVO}}"`，自动输出字段变更差异。

#### 第 5 步：三路 merge 子表（`mergeCurtainSubRows`）

```java
mergeCurtainSubRows(updateReqVO.getId(), updateReqVO.getCurtains());
```

详见 [第二节：mergeCurtainSubRows 详解](#三mergecurtainsubrows-三路-merge-详解)。

### 2.3 事务保证

整个方法被 `@Transactional(rollbackFor = Exception.class)` 包裹，主表更新 + 子表三路 merge（含删除/插入/更新）同属一个事务，任意步骤异常均整体回滚。

---

## 三、整单更新逻辑（面单 `updateFabricSalesOrder`）

### 3.1 入口

```
PUT /zc/sales-order/fabric/update
请求体：ZcSalesOrderFabricUpdateReqVO（JSON）
```

`ZcSalesOrderFabricUpdateReqVO` 继承自 `ZcSalesOrderFabricCreateReqVO`，仅额外增加：
- `id`（Long，必填）：订单 ID

**ZcSalesOrderFabricCreateReqVO 字段清单（面单比成品订单更精简）：**

| 字段 | 类型 | 是否必填 | 说明 |
|------|------|---------|------|
| `customerId` | Long | ✅ | 客户 ID |
| `mobile` | String | 否 | 手机 |
| `brandId` | Long | 否 | 品牌 |
| `orderDate` | LocalDate | ✅ | 下单日期 |
| `logisticId` | Long | 否 | 物流 |
| `receiver` | String | 否 | 收货人 |
| `deliveryAddress` | String | 否 | 送货地址（面单非必填） |
| `amount` | BigDecimal | 否 | 订单金额 |
| `curtains` | List\<ZcSalesOrderFabricCurtainCreateVO\> | 否 | 精简窗帘行 |

**面单窗帘行（ZcSalesOrderFabricCurtainCreateVO）字段更精简：**

| 字段 | 说明 |
|------|------|
| `amount` | 应收金额 |
| `note` | 备注 |
| `structures` | 结构列表（仅含用料明细，无其他工艺字段） |

**面单结构行（ZcSalesOrderFabricStructureCreateVO）字段极简：**

| 字段 | 说明 |
|------|------|
| `materials` | 用料明细列表 |

### 3.2 Service 层执行流程

`updateFabricSalesOrder` 与 `updateSalesOrder` 步骤完全对称，仅存在以下两处差异：

| 差异点 | 成品订单 `updateSalesOrder` | 面单 `updateFabricSalesOrder` |
|--------|--------------------------|------------------------------|
| VO 类型 | `ZcSalesOrderUpdateReqVO` | `ZcSalesOrderFabricUpdateReqVO` |
| 子表 merge | 直接调 `mergeCurtainSubRows(id, curtains)` | 先调 `toStandardCurtainVOs(curtains)` 转换后再调 `mergeCurtainSubRows` |

**`toStandardCurtainVOs` 转换逻辑：**

面单精简 VO → 标准 VO 的适配，**同时透传各层 `id`**，确保 merge 逻辑能正确识别已有行：

```
ZcSalesOrderFabricCurtainCreateVO  →  ZcSalesOrderCurtainCreateVO
  .id                              →    .id        ← 透传，merge 用
  .amount                          →    .amount
  .note                            →    .note
  .structures[].id                 →    .structures[].id  ← 透传
  .structures[].materials          →    .structures[].materials
  （curtainId、room 等工艺字段为 null，面单无款式概念）
```

目的是**复用同一套 `mergeCurtainSubRows` 逻辑**，不重复实现。

---

## 四、`mergeCurtainSubRows` 三路 merge 详解

整单更新的子表处理核心，被 `updateSalesOrder` 和 `updateFabricSalesOrder` 共同调用。

### 4.1 前提：VO 中各层新增 `id` 字段

三个嵌套 VO 均新增可选的 `id` 字段，前端更新时回传：

| VO | 新增字段 | 语义 |
|----|---------|------|
| `ZcSalesOrderCurtainCreateVO` | `id`（可选） | 有值 → 更新已有行；无值 → 新增行 |
| `ZcSalesOrderStructureCreateVO` | `id`（可选） | 同上 |
| `ZCSalesOrderMaterialCreateVO` | `id`（可选） | 同上 |
| `ZcSalesOrderFabricCurtainCreateVO` | `id`（可选） | 同上（面单） |
| `ZcSalesOrderFabricStructureCreateVO` | `id`（可选） | 同上（面单） |

### 4.2 执行流程（五步）

#### 步骤 ①：一次性加载当前三层子表

```java
List<ZcSalesOrderCurtainDO>  existingCurtains   = salesOrderCurtainMapper.selectListByOrderId(orderId);
List<ZcSalesOrderStructureDO> existingStructures = salesOrderStructureMapper.selectListByOrderId(orderId);
List<ZCSalesOrderMaterialDO>  existingMaterials  = salesOrderMaterialMapper.selectListByOrderId(orderId);
```

分别构建 `Map<Long, DO>` 以 O(1) 查找，避免后续循环中重复查库。

#### 步骤 ②：收集请求中所有 ID

遍历入参的三层嵌套结构，把所有非 null 的 id 归入三个 Set：

```
requestCurtainIds   = { id | curtainVO.id != null }
requestStructureIds = { id | structureVO.id != null }
requestMaterialIds  = { id | materialVO.id != null }
```

**待删集合** = 数据库中存在但不在对应 Set 里的行。

#### 步骤 ③：前置校验——禁止删除已裁剪用料明细

```java
existingMaterials.stream()
    .filter(m -> !requestMaterialIds.contains(m.getId()))   // 待删行
    .filter(m -> HAVE_PEILIAO.name().equals(m.getStatus())) // 且已裁剪
    .findFirst()
    .ifPresent(m -> { throw exception(SALES_ORDER_MATERIAL_CANNOT_DELETE_WHEN_CUT); });
```

该行的库存已通过裁剪操作扣减，若直接删除会导致库存账目与实物不符。必须先逐条撤销裁剪归还库存，才能从订单中移除。

#### 步骤 ④：逐层 upsert

对每个请求行按"有 id 且在库里 → UPDATE，否则 → INSERT"的规则处理：

**窗帘行 upsert：**

| 分支 | 操作 | 保护字段 |
|------|------|---------|
| id 匹配到已有行 | `updateById` | `status`、`packTime`、`shipTime` 置 null（不覆盖） |
| 无 id 或 id 不存在 | `insert` | 强制 `id=null`、`status=UNCONFIRMED`、`index=当前序号` |

- `mountings`：`ZcSalesOrderCurtainDO.mountings` 标注了 `@TableField(updateStrategy = ALWAYS)`，因此即使值为 null（用户清空配件）也会被 `updateById` 写入数据库。
- `index`：无论新增还是更新，均按请求中的列表顺序从 1 开始重新赋值，保持展示顺序一致。

**结构行 upsert：**

| 分支 | 操作 | 特殊处理 |
|------|------|---------|
| id 匹配到已有行 | `updateById` | `orderCurtainId` 更新为当前父级（支持跨窗帘行挪动） |
| 无 id 或 id 不存在 | `insert` | `id=null`、填入 `orderId`、`orderCurtainId` |

**用料明细 upsert：**

| 分支 | 操作 | 保护字段 |
|------|------|---------|
| id 匹配到已有行（`NOT_PEILIAO`） | `updateById` | `status`、`cutQuantity` 置 null（不覆盖）；`batchId` 可修改 |
| id 匹配到已有行（`HAVE_PEILIAO`） | `updateById` | 在上述基础上，`batchId` 也置 null（不覆盖，与库存扣减绑定） |
| 无 id 或 id 不存在 | `insert` | `id=null`、填入 `orderId`、`orderStructureId` |

#### 步骤 ⑤：删除不在请求中的行（由内向外）

```java
// 先删最内层，避免父子 FK 约束问题
if (materialsToDelete  非空) salesOrderMaterialMapper.deleteBatchIds(materialsToDelete);
if (structuresToDelete 非空) salesOrderStructureMapper.deleteBatchIds(structuresToDelete);
if (curtainsToDelete   非空) salesOrderCurtainMapper.deleteBatchIds(curtainsToDelete);
```

### 4.3 与旧"全量删除重建"策略的对比

| 维度 | 旧策略（全量删除重建） | 新策略（三路 merge） |
|------|-------------------|-----------------|
| 子表 ID | 每次更新全部重新生成 | 已有行 ID 不变 |
| 裁剪状态 | 丢失（cutQuantity/batchId/status 被删） | 保留，且禁止删除已裁剪行 |
| 关联安全 | 外部引用旧子表 ID 失效 | ID 稳定，关联不断 |
| 前端要求 | 无需传子表 id | 更新时需回传原行 id |
| 代码复杂度 | 低 | 中（多三次 selectList + 逐行判断） |

---

## 五、确认订单（`confirmSalesOrder`）

### 4.1 入口

```
PUT /zc/sales-order/confirm?id={orderId}
```

### 4.2 执行流程

**步骤 1：校验**
- `validateSalesOrderExists(id)`：订单必须存在
- `order.getStatus() != UNCONFIRMED`：只有"未确认"状态才能确认，否则抛 `SALES_ORDER_STATUS_NOT_UNCONFIRMED`

**步骤 2：更新主表状态**

```sql
UPDATE zc_sales_order
SET status = 'confirmed', confirm_time = NOW()
WHERE id = #{id}
```

**步骤 3：同步更新窗帘行状态**

```java
salesOrderCurtainMapper.updateStatusByOrderId(id, ZcSalesOrderStatusEnum.NOT_PEILIAO.name());
// 将所有窗帘行状态改为 NOT_PEILIAO（待配料）
```

**步骤 4：扣减客户账户余额（仅当 customerId 和 amount 均非空）**

```
delta = -order.amount（负数，扣款）
客户余额 = 客户余额 + delta（即减少）
```

同时写入余额变动流水：

| 字段 | 值 |
|------|---|
| `customerId` | 订单客户 ID |
| `changeAmount` | `-order.amount`（负数） |
| `balanceBefore` | 操作前余额 |
| `balanceAfter` | 操作后余额 |
| `bizType` | `ORDER_CONFIRM` |
| `refType` | `SALES_ORDER` |
| `refId` | 订单 ID |
| `refNo` | 订单号 |

**步骤 5：记录操作日志上下文**

```java
LogRecordContext.putVariable("orderNo", order.getOrderNo());
// 日志模板："确认了销售订单【{{#orderNo}}】"
```

---

## 六、取消确认（`cancelConfirmSalesOrder`）

### 6.1 入口

```
PUT /zc/sales-order/cancel-confirm?id={orderId}
```

### 6.2 执行流程

**步骤 1：校验 3 项前置条件**

| 检查项 | 失败错误码 | 说明 |
|--------|----------|------|
| 订单存在 | `SALES_ORDER_NOT_EXISTS` | 基础校验 |
| `status == CONFIRMED` | `SALES_ORDER_STATUS_NOT_CONFIRMED` | 只有已确认才能取消确认 |
| `amountReceived == 0` | `SALES_ORDER_HAS_RECEIVED_AMOUNT` | 已有收款则禁止，防余额虚增 |
| 用料明细无 HAVE_PEILIAO 行 | `SALES_ORDER_HAS_CUT_MATERIAL` | 已裁剪物料须先逐条撤销，防库存错乱 |

**步骤 2：更新主表状态**

```sql
UPDATE zc_sales_order
SET status = 'unconfirmed', confirm_time = NULL
WHERE id = #{id}
```

**步骤 3：同步更新窗帘行状态**

```java
salesOrderCurtainMapper.updateStatusByOrderId(id, ZcSalesOrderStatusEnum.UNCONFIRMED.name());
// 将所有窗帘行状态改回 UNCONFIRMED
```

**步骤 4：退回客户账户余额（仅当 customerId 和 amount 均非空）**

```
delta = +order.amount（正数，退款）
客户余额 = 客户余额 + delta（即增加）
```

写入余额变动流水：

| 字段 | 值 |
|------|---|
| `changeAmount` | `+order.amount`（正数） |
| `bizType` | `ORDER_UNCONFIRM` |

---

## 七、完成订单（`completeSalesOrder`）

### 7.1 入口

```
PUT /zc/sales-order/complete?id={orderId}
```

### 7.2 执行流程

**步骤 1：校验**
- 订单存在
- `status == UNCONFIRMED` → 抛 `SALES_ORDER_UNCONFIRMED_CANNOT_COMPLETE`（未确认不能直接完成）
- `status == COMPLETE` → 抛 `SALES_ORDER_ALREADY_COMPLETE`（防重复完成）

**步骤 2：更新主表状态**

```sql
UPDATE zc_sales_order SET status = 'complete' WHERE id = #{id}
```

> 日志模板："完成了销售订单【{{#orderNo}}】"

---

## 八、标记加急（`markExpedited`）

### 8.1 入口

```
PUT /zc/sales-order/expedited?orderId={orderId}
```

### 8.2 执行流程

1. 校验订单存在
2. `UPDATE zc_sales_order SET is_expedited = true WHERE id = #{orderId}`

> 注意：该接口是**单向操作**，只能标记加急，无取消加急接口。

---

## 九、状态流转图

```
创建
  │
  ▼
UNCONFIRMED（未确认）
  │  ← 整单更新（update / fabric/update）：此状态下才可修改主表及子表
  │  ← 删除（delete）：此状态下才可删除
  │
  │ [confirm] 扣客户余额
  ▼
CONFIRMED（已确认）
  │  ← 取消确认（cancel-confirm）：有条件退回（无收款、无裁剪）
  │
  │ [complete] 无额外约束
  ▼
COMPLETE（已完成）
```

---

## 十、关键设计要点汇总

| 要点 | 描述 |
|------|------|
| **子表三路 merge** | 更新时按请求中各行的 `id` 分为三路：有 id 且在库 → UPDATE，无 id 或不在库 → INSERT，在库但不在请求 → DELETE；ID 稳定，状态不丢失 |
| **主表受保护字段** | `orderNo/types/payStatus/status/isExpedited/amountReceived/confirmTime` 通过 `clearProtectedFields` 置 null，利用 `updateById` 不更新 null 字段的特性加以保护 |
| **子表受保护字段** | 窗帘行：`status/packTime/shipTime` 不覆盖；用料明细：`status/cutQuantity` 不覆盖，已裁剪行的 `batchId` 也不覆盖 |
| **mountings 置空能力** | `ZcSalesOrderCurtainDO.mountings` 加 `@TableField(updateStrategy = ALWAYS)`，保证用户清空配件时 `updateById` 能将该字段写为 null |
| **禁止删除已裁剪用料行** | merge 前置校验：若待删行中存在 `HAVE_PEILIAO` 状态的用料明细，整个更新操作直接拒绝（错误码 100077），需先逐条撤销裁剪归还库存 |
| **事务一致性** | 主表更新 + 子表 upsert + 子表删除同属一个 `@Transactional` 事务，任意步骤异常整体回滚 |
| **面单 VO 适配** | 面单精简 VO 通过 `toStandardCurtainVOs` 转为标准格式（透传 id），复用同一套 `mergeCurtainSubRows`，不重复实现 |
| **确认与余额联动** | 确认时扣减客户余额 + 写流水；取消确认时退回余额 + 写流水；两步完全对称 |
| **取消确认前置防护** | 防止已收款后取消确认导致余额虚增；防止有裁剪记录时取消确认导致库存不一致 |
| **操作日志 diff** | 更新操作通过 `DiffParseFunction.OLD_OBJECT` + `@LogRecord` 自动输出字段变更差异日志 |
| **行序号（index）** | 无论新增还是更新窗帘行，均按请求列表顺序从 1 开始重新赋值，保持展示顺序与提交顺序一致 |
