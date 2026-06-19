# 智仓（ZC）订单与账单核心接口逻辑梳理

> 模块路径：`yudao-module-curtain/src/main/java/cn/iocoder/yudao/module/zc`  
> 包名前缀：`cn.iocoder.yudao.module.zc`  
> **订单四层状态专项文档**：[order-status.md](./order-status.md)

---

## 目录

1. [数据模型总览](#一数据模型总览)
2. [销售订单接口](#二销售订单接口)
3. [收支账单接口](#三收支账单接口)
4. [订单工序记录接口](#四订单工序记录接口)
5. [核心联动关系图](#五核心联动关系图)
6. [单号生成机制](#六单号生成机制)
7. [客户余额联动](#七客户余额联动)
8. [关键枚举值说明](#八关键枚举值说明)

**附录**：[成品订单四层状态定义与联动规则](./order-status.md)

---

## 一、数据模型总览

### 1.1 销售订单四层嵌套结构

```
zc_sales_order（订单主表，L1）
└── zc_sales_order_curtain（窗帘行，L2，按款式/房间分行）
    └── zc_sales_order_structure（结构行，L3，含尺寸、工艺、褶数等加工参数）
        └── zc_sales_order_material（用料明细，L4，具体物料、批次、用量、单价）
```

### 1.2 销售订单主表（`zc_sales_order`）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | Long | 主键 |
| order_no | String | 订单号，格式：`ZC{租户ID}{yyyyMMdd}{5位序号}`，如 `ZC120260519000001` |
| customer_id | Long | 客户 ID，关联 `zc_customer` |
| mobile | String | 客户手机号 |
| brand_id | Long | 品牌 ID |
| order_date | LocalDate | 下单日期 |
| logistic_id | Long | 物流 ID，关联 `zc_logistics` |
| receiver | String | 收货人姓名 |
| delivery_address | String | 送货地址 |
| freight | BigDecimal | 运费（默认 0） |
| types | String | 订单类型（字典：`zc_order_type`，如 chengpin、面料单） |
| discount_amount | BigDecimal | 优惠金额 |
| total_amount | BigDecimal | 总金额（含运费等，默认 0） |
| amount | BigDecimal | 订单金额（优惠后实收） |
| amount_received | BigDecimal | 已收金额（账单结算时累加） |
| delivery_date | LocalDate | 交付日期 |
| pay_status | String | 结算状态：`UNPAID` / `PARTIALPAID` / `PAID`（`ZcOrderPayStatusEnum`） |
| status | String | 订单状态，参见 `ZcSalesOrderStatusEnum` / 字典 `zc_order_status`；**详细定义见 [order-status.md](./order-status.md)** |
| confirm_time | LocalDateTime | 确认时间（确认时写入，取消确认时清空） |
| is_expedited | Boolean | 是否加急（默认 false） |
| current_node_name | String | 当前所处工序名称（工序记录创建时同步更新） |
| note | String | 备注 |

### 1.3 窗帘行（`zc_sales_order_curtain`）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | Long | 主键 |
| order_id | Long | 关联销售订单 ID |
| curtain_id | Long | 窗帘款式 ID，关联 `zc_curtain` |
| room | String | 房间名称（如：主卧、客厅） |
| pleat_ratio_value | BigDecimal | 褶倍快照（下单时从款式表取值冻结） |
| pleat_distance | BigDecimal | 褶距 |
| discount_rate | BigDecimal | 折扣率 |
| amount | BigDecimal | 本行应收金额 |
| image1 / image2 | String | 现场照片 |
| mountings | String | **配件多选（JSON 字符串存储）**，如 `["加铅块","加磁条"]` |
| note | String | 备注 |
| status | String | 窗帘行状态，参见 `ZcSalesOrderStatusEnum`；**详细定义见 [order-status.md](./order-status.md)** |
| pack_time | LocalDateTime | 打包时间 |
| ship_time | LocalDateTime | 发货时间 |

### 1.4 结构行（`zc_sales_order_structure`）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | Long | 主键 |
| order_id | Long | 关联销售订单 ID |
| order_curtain_id | Long | 关联窗帘行 ID |
| structure_id | Long | 结构款式 ID，关联 `zc_curtain_structure` |
| height / width | BigDecimal | 高度/宽度（cm） |
| left_corner / right_corner | String | 左/右转角 |
| paste_direction | String | 粘贴方向 |
| install_process_id | Long | 安装工艺 ID，关联 `zc_curtain_install_process`（如：墙装、顶装） |
| open_method | String | 打开方式（如：左开、右开、对开） |
| process_type | String | 加工类型（如 DKMG） |
| is_shaping | Boolean | 是否定型 |
| pleats_num | Integer | 总褶数 |
| pleats_distance | BigDecimal | 褶距（cm） |
| skirt_height | BigDecimal | 裙摆高度（cm） |
| note | String | 备注 |

### 1.5 用料明细（`zc_sales_order_material`）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | Long | 主键 |
| order_id | Long | 关联销售订单 ID |
| order_structure_id | Long | 关联结构行 ID |
| element_id | Long | 组件类型 ID，关联 `zc_curtain_structure_element`（如：主帘布、辅料） |
| product_id | Long | 产品 ID，关联 `zc_product` |
| batch_id | Long | 产品批次 ID，关联 `zc_product_batch` |
| price | BigDecimal | 单价 |
| quantity | BigDecimal | 用量 |
| unit_value | String | 单位 |
| discount_rate | BigDecimal | 折扣率 |
| amount | BigDecimal | 小计（price × quantity × discountRate） |
| note | String | 备注 |
| status | String | 配料状态：`NOT_PEILIAO` / `HAVE_PEILIAO`（`ZcSalesOrderMaterialStatusEnum`） |
| cut_quantity | BigDecimal | 裁剪数量（裁剪后写入，撤销裁剪置 null） |

### 1.6 收支账单相关表

#### `zc_bills`（账单主表）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | Long | 主键 |
| bill_no | String | 单号，格式：`SK{yyyyMMdd}-{6位序号}`，如 `SK20260519-000001` |
| bill_date | LocalDate | 收款日期 |
| bill_user_id | Long | 财务人员（创建时取登录用户，不允许修改） |
| customer_id | Long | 客户 ID |
| discount_amount | BigDecimal | 本次优惠金额（默认 0） |
| actual_amount | BigDecimal | 本次实收金额 |
| bill_method_id | Long | 收支方式 ID，关联 `zc_bill_methods` |
| note | String | 备注 |

#### `zc_bill_order_items`（订单分摊明细）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | Long | 主键 |
| bill_id | Long | 关联账单 ID |
| order_id | Long | 关联销售订单 ID |
| allocated_amount | BigDecimal | 本次分摊到该订单的金额 |
| note | String | 备注 |

#### `zc_bill_attachments`（账单附件）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | Long | 主键 |
| bill_id | Long | 关联账单 ID |
| url | String | 附件 URL |
| type | Integer | 附件类型：`1`=图片，`2`=文件（根据 URL 后缀自动判断） |

**附件类型判断逻辑**：URL 后缀为 `jpg/jpeg/png/gif/webp` 时为图片（type=1），其余为文件（type=2）。

### 1.7 工序记录（`zc_order_process_record`）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | Long | 主键 |
| order_id | Long | 关联销售订单 ID |
| node_id | Long | 工序节点 ID，关联 `zc_process_node` |
| node_name | String | **工序名称快照**（冗余存储，防止节点改名后历史记录失真） |
| status | Integer | `1`=进行中，`2`=已完成 |
| operator_user_id | Long | 操作人员 ID，关联 `system_users` |
| note | String | 备注（如质检不通过原因） |
| image_urls | List\<String\> | 现场照片 URL 列表（JSON 存储，JacksonTypeHandler 自动映射） |

---

## 二、销售订单接口

**Controller**：`ZcSalesOrderController`  
**路径前缀**：`/zc/sales-order`  
**Service 实现**：`ZcSalesOrderServiceImpl`

---

### 2.1 整单创建销售订单

```
POST /zc/sales-order/create
权限：zc:sales-order:create
```

**请求体**（`ZcSalesOrderCreateReqVO`）：

```json
{
  "customerId": 29746,          // 客户 ID（必填）
  "mobile": "13800138000",      // 手机号
  "brandId": 8302,              // 品牌 ID
  "orderDate": "2026-05-19",   // 下单日期（必填）
  "logisticId": 27080,          // 物流 ID
  "receiver": "张三",            // 收货人
  "deliveryAddress": "上海市...", // 送货地址（必填）
  "freight": 50.00,             // 运费（默认 0）
  "types": "chengpin",          // 订单类型（必填）
  "discountAmount": 100.00,     // 优惠金额
  "totalAmount": 5000.00,       // 总金额
  "amount": 4900.00,            // 订单金额（优惠后）
  "deliveryDate": "2026-05-30", // 交付日期
  "note": "备注",
  "curtains": [                 // 窗帘行列表（必填，至少 1 条）
    {
      "curtainId": 26707,       // 款式 ID（必填）
      "room": "主卧",
      "pleatRatioValue": 2.0,   // 褶倍快照
      "pleatsDistance": 15.0,   // 褶距
      "discountRate": 0.9,      // 折扣率
      "amount": 3000.00,        // 应收金额
      "image1": "https://...",
      "mountings": ["加铅块", "加磁条"],  // 配件多选（存为 JSON 字符串）
      "note": "备注",
      "structures": [           // 结构行列表（必填，至少 1 条）
        {
          "structureId": 17209, // 结构款式 ID（必填）
          "height": 240,        // 高（cm）
          "width": 360,         // 宽（cm）
          "leftCorner": "L型",
          "rightCorner": "直角",
          "pasteDirection": "正面",
          "installProcessId": 5095,
          "openMethod": "对开",
          "processType": "DKMG",
          "isShaping": true,
          "pleatsNum": 24,
          "pleatsDistance": 15.0,
          "skirtHeight": 10.0,
          "note": "备注",
          "materials": [        // 用料明细（可选）
            {
              "elementId": 1,   // 组件类型 ID
              "productId": 100, // 产品 ID
              "batchId": 200,   // 批次 ID
              "price": 120.00,  // 单价
              "quantity": 25.0, // 用量
              "unitValue": "米",
              "discountRate": 0.9,
              "amount": 2700.00
            }
          ]
        }
      ]
    }
  ]
}
```

**返回**：新订单 ID（`CommonResult<Long>`）

**Service 层处理步骤**（`ZcSalesOrderServiceImpl.createSalesOrder`）：

```
1. 生成订单号
   └── 取当前租户ID + 当日日期
   └── Redis INCR 原子获取当日序号（ZcNoGeneratorRedisDAO.nextOrderSeq）
   └── 拼接：ZC{tenantId}{yyyyMMdd}{seq:05d}

2. 保存订单主记录（设置默认值）
   └── payStatus = "unpaid"（未结算）
   └── status = "unconfirmed"（待确认）
   └── isExpedited = false（非加急）
   └── freight/totalAmount 为 null 时默认设 0

3. 遍历窗帘行 → 保存
   └── orderId 自动填充
   └── mountings 列表 → JSONUtil.toJsonStr() → 存字符串

4. 遍历结构行 → 保存
   └── orderId + orderCurtainId 自动填充

5. 遍历用料明细 → 保存
   └── orderId + orderStructureId 自动填充

6. 全程 @Transactional，失败整体回滚
```

---

### 2.2 获取订单简要信息

```
GET /zc/sales-order/get?id={id}
权限：zc:sales-order:query
```

**返回**（`ZcSalesOrderRespVO`）：订单主表所有字段 + 关联冗余字段（`customerName`、`logisticName`、`creatorName`）

**底层查询**：`ZcSalesOrderMapper.selectVOById`，LEFT JOIN 三张关联表一次性取回名称字段：

```sql
SELECT t1.*, t2.name AS customer_name, t3.name AS logistic_name, t4.nickname AS creator_name
FROM zc_sales_order t1
LEFT JOIN zc_customer t2 ON t1.customer_id = t2.id AND t2.deleted = 0
LEFT JOIN zc_logistics t3 ON t1.logistic_id = t3.id AND t3.deleted = 0
LEFT JOIN system_users t4 ON t1.creator = t4.id AND t4.deleted = 0
WHERE t1.deleted = 0 AND t1.id = #{id}
```

---

### 2.3 获取订单全量明细（三层嵌套）

```
GET /zc/sales-order/detail?orderId={orderId}
权限：zc:sales-order:query
```

**返回**：`List<ZcSalesOrderCurtainDetailRespVO>`

```
ZcSalesOrderCurtainDetailRespVO
├── curtainName（款式名称）
├── 窗帘行所有字段
└── structures: List<ZcSalesOrderStructureDetailRespVO>
    ├── structureName（结构名称）
    ├── installProcessName（安装工艺名称）
    ├── 结构行所有字段
    └── materials: List<ZCSalesOrderMaterialDetailRespVO>
        ├── elementName（组件类型名称）
        ├── productName（产品名称）
        ├── batchNo（批次号）
        └── 用料明细所有字段
```

**N+1 优化策略**（`getSalesOrderDetail` 完整流程）：

```
1. 一次查出该订单所有窗帘行（按 orderId）
2. 一次查出该订单所有结构行（按 orderId）
3. 一次查出该订单所有用料明细（按 orderId）

4. 批量构建各 Map（一次性 selectBatchIds，不循环查库）：
   ├── curtainNameMap    → 款式ID → 款式名称
   ├── structureNameMap  → 结构ID → 结构名称
   ├── installProcessNameMap → 安装工艺ID → 安装工艺名称
   ├── elementNameMap    → 组件类型ID → 组件类型名称
   ├── productNameMap    → 产品ID → 产品名称
   └── batchNoMap        → 批次ID → 批次号

5. 按 orderStructureId 分组用料明细（内存 groupingBy）
6. 按 orderCurtainId 分组结构行（内存 groupingBy）
7. 组装最终嵌套 VO 列表返回

总数据库查询次数：3（四层数据）+ 6（名称批量查询）= 9 次，与数据量无关
```

---

### 2.4 订单分页查询

```
GET /zc/sales-order/page
权限：zc:sales-order:query
```

**查询参数**（`ZcSalesOrderPageReqVO`）：

| 参数 | 类型 | 说明 |
|------|------|------|
| orderNo | String | 订单号（模糊匹配，LIKE） |
| customerId | Long | 客户 ID（精确匹配） |
| brandId | Long | 品牌 ID（精确匹配） |
| orderDate | LocalDate[2] | 下单日期区间（BETWEEN） |
| logisticId | Long | 物流 ID（精确匹配） |
| types | String | 订单类型（精确匹配） |
| deliveryDate | LocalDate[2] | 交付日期区间（BETWEEN） |
| payStatus | List\<String\> | 结算状态多选（IN 查询，如 `["paid","partialpaid"]`） |
| status | String | 订单状态（精确匹配） |
| isConfirm | Boolean | 是否已确认（true → confirm_time IS NOT NULL） |
| isExpedited | Boolean | 是否加急（精确匹配） |

**返回**：`PageResult<ZcSalesOrderRespVO>`（含关联名称字段）

**底层实现**：动态 SQL（`ZcSalesOrderMapper.xml`），LEFT JOIN 三张表，ORDER BY id DESC

---

### 2.5 确认订单

```
PUT /zc/sales-order/confirm?id={id}
权限：zc:sales-order:update
```

**业务逻辑**（`confirmSalesOrder`）：

```
前置校验：
├── 订单必须存在（否则抛 SALES_ORDER_NOT_EXISTS）
└── 订单 status 必须为 "unconfirmed"（否则抛 SALES_ORDER_STATUS_NOT_UNCONFIRMED）

执行操作：
1. 更新 status = "confirmed"，confirmTime = 当前时间
2. 若 customerId 和 amount 均不为 null：
   └── 调用 customerService.adjustBalance(customerId, amount.negate())
       即：客户余额 -= 订单金额（表示客户欠款增加）
```

> **设计说明**：确认订单时扣减客户余额，表示客户产生了欠款；后续账单收款时再把余额加回来，形成负债-还款的完整闭环。

---

### 2.6 取消确认订单

```
PUT /zc/sales-order/cancel-confirm?id={id}
权限：zc:sales-order:update
```

**业务逻辑**（`cancelConfirmSalesOrder`）：

```
前置校验：
├── 订单必须存在
├── 订单 status 必须为 "confirmed"（否则抛 SALES_ORDER_STATUS_NOT_CONFIRMED）
└── 禁止有收款记录时取消：
    amountReceived > 0 → 抛 SALES_ORDER_HAS_RECEIVED_AMOUNT
    （防止取消后客户余额虚增，与已收款不符）

执行操作：
1. 更新 status = "unconfirmed"，confirmTime = null（清空）
2. 若 customerId 和 amount 均不为 null：
   └── 调用 customerService.adjustBalance(customerId, amount)
       即：客户余额 += 订单金额（退回之前扣减的欠款）
```

---

### 2.7 标记加急

```
PUT /zc/sales-order/expedited?orderId={orderId}
权限：zc:sales-order:update
```

**业务逻辑**：校验订单存在，将 `isExpedited` 更新为 `true`，**不验证当前订单状态**（任何状态均可标记加急）。

---

### 2.8 导出 Excel

```
GET /zc/sales-order/export-excel
权限：zc:sales-order:export
```

**业务逻辑**：接收与分页查询相同的查询参数，将 `pageSize` 强制设为 `PAGE_SIZE_NONE`（不分页全量查询），通过 `ExcelUtils.write` 写出 `销售订单.xls`。

---

### 2.9 导出 PDF

```
GET /zc/sales-order/export-pdf?id={id}
权限：zc:sales-order:export
```

**业务逻辑**（`generateSalesOrderPdf` → `buildSalesOrderPdf`）：

```
1. 查询订单主信息（selectVOById，含关联名称）
2. 查询三层嵌套明细（getSalesOrderDetail）
3. 使用 OpenPDF 构建 A4 横向 PDF：
   ├── 标题区：订单号 + 打印日期
   ├── 基本信息区：6 列网格（客户/下单日期/交付日期/手机/物流/收货人/状态/金额/运费/备注）
   └── 窗帘明细区（逐层展开）：
       ├── 区块标题栏（蓝色背景）
       ├── 每条窗帘行：藏青蓝标题（房间/款式/褶倍/折扣/金额/配件）
       │   ├── 结构行表格（灰色表头）：结构/高/宽/安装工艺/打开方式/加工类型/定型/褶数/褶距/备注
       │   └── 用料明细表格（浅灰表头）：组件类型/产品名称/批次号/单价/用量/单位/折扣率/小计
       └── 合计行（右对齐）：总金额/订单金额/已收款/运费
4. 返回 PDF 字节数组，以 Content-Disposition: attachment 响应
```

**中文字体**：使用 STSong-Light 标准 CJK 字体（无需嵌入字体文件）。

---

## 三、收支账单接口

**Controller**：`ZcBillsController`  
**路径前缀**：`/zc/bills`  
**Service 实现**：`ZcBillsServiceImpl`

---

### 3.1 创建收支账单

```
POST /zc/bills/create
权限：zc:bills:create
```

**请求体**（`ZcBillsSaveReqVO`）：

```json
{
  "billDate": "2026-05-19",    // 收款日期（必填）
  "customerId": 1212,           // 客户 ID
  "discountAmount": 50.00,      // 优惠金额
  "actualAmount": 2000.00,     // 实收金额（必填）
  "billMethodId": 23,           // 收支方式 ID（必填）
  "note": "备注",
  "attachments": [              // 附件 URL 列表（可选）
    "https://xxx.jpg",
    "https://xxx.pdf"
  ],
  "orderItems": [               // 订单分摊明细（必填）
    {
      "orderId": 1024,
      "allocatedAmount": 1500.00
    },
    {
      "orderId": 1025,
      "allocatedAmount": 550.00
    }
  ]
}
```

> **核心约束**：`sum(orderItems[].allocatedAmount)` 必须 == `actualAmount + discountAmount`，不相等则抛 `BILL_ALLOCATED_AMOUNT_NOT_MATCH`

**返回**：新账单 ID（`CommonResult<Long>`）

**Service 层处理步骤**（`ZcBillsServiceImpl.createBills`）：

```
1. 校验分摊金额一致性
   └── totalSettled = actualAmount + discountAmount
   └── totalAllocated = sum(orderItems[].allocatedAmount)
   └── totalAllocated != totalSettled → 抛异常

2. 生成账单号
   └── Redis INCR 获取当日序号（ZcNoGeneratorRedisDAO.nextBillSeq）
   └── 格式：SK{yyyyMMdd}-{seq:06d}

3. 保存账单主记录
   └── billNo = 生成的单号
   └── billUserId = 当前登录用户 ID（SecurityFrameworkUtils.getLoginUserId()）
   └── discountAmount 为 null 时默认 0

4. 保存附件记录（遍历 attachments URL 列表）
   └── 根据 URL 后缀判断 type：jpg/jpeg/png/gif/webp → 1（图片），其他 → 2（文件）

5. 处理订单分摊（遍历 orderItems）
   └── 查出各订单当前 amountReceived
   └── newReceived = currentReceived + allocatedAmount
   └── 判断结算状态：
       ├── newReceived >= orderAmount → payStatus = "paid"
       └── newReceived < orderAmount  → payStatus = "partialpaid"
   └── 更新订单 amountReceived 和 payStatus
   └── 保存 zc_bill_order_items 分摊明细记录

6. 更新客户余额
   └── customerService.adjustBalance(customerId, actualAmount + discountAmount)
       即：客户余额 += 实收 + 优惠（表示还款，抵消确认订单时的欠款）
```

---

### 3.2 更新收支账单

```
PUT /zc/bills/update
权限：zc:bills:update
```

**请求体**：同 `ZcBillsSaveReqVO`，需包含 `id` 字段。

**业务逻辑**（`updateBills`，完整回滚-再写流程）：

```
1. 校验账单存在，取旧快照（existingBill）

2. 校验新分摊金额一致性（与创建相同的逻辑）

3. 回滚旧订单分摊
   └── 查出旧的 zc_bill_order_items
   └── 遍历，对每个订单：
       newReceived = currentReceived - oldAllocatedAmount（不低于 0）
       重算 payStatus：
         ├── newReceived == 0 → "unpaid"
         ├── newReceived >= orderAmount → "paid"
         └── 其他 → "partialpaid"

4. 回滚旧客户余额
   └── adjustBalance(existingBill.customerId, -(oldActualAmount + oldDiscountAmount))

5. 删除旧附件（deleteByBillId）
   删除旧分摊明细（deleteByBillId）

6. 更新账单主记录
   └── billNo 和 billUserId 保持旧值不变（不允许修改）

7. 保存新附件

8. 保存新分摊明细，更新各订单 amountReceived 和 payStatus（逻辑同创建步骤 5）

9. 更新新客户余额
   └── adjustBalance(newCustomerId, newActualAmount + newDiscountAmount)
```

---

### 3.3 删除收支账单

```
DELETE /zc/bills/delete?id={id}
权限：zc:bills:delete
```

**业务逻辑**（`deleteBills`，完整回滚流程）：

```
1. 校验账单存在

2. 回滚订单分摊
   └── 查出该账单的所有 zc_bill_order_items
   └── 遍历，对每个订单：
       newReceived = currentReceived - allocatedAmount（不低于 0）
       重算 payStatus（同更新逻辑）

3. 回滚客户余额
   └── adjustBalance(customerId, -(actualAmount + discountAmount))

4. 级联删除
   └── 删除 zc_bill_attachments（deleteByBillId）
   └── 删除 zc_bill_order_items（deleteByBillId）
   └── 删除 zc_bills 主记录
```

---

### 3.4 批量删除收支账单

```
DELETE /zc/bills/delete-list?ids={ids}
权限：zc:bills:delete
```

> **注意**：当前实现直接批量删除主表记录，**未回滚关联的订单分摊和客户余额**，属于简化实现，高业务场景慎用。

---

### 3.5 获取收支账单

```
GET /zc/bills/get?id={id}
权限：zc:bills:query
```

**返回**（`ZcBillsRespVO`）：账单主表字段（id, billNo, billDate, billUserId, customerId, discountAmount, actualAmount, billMethodId, note）

---

### 3.6 账单分页查询

```
GET /zc/bills/page
权限：zc:bills:query
```

**返回**：`PageResult<ZcBillsRespVO>`

---

### 3.7 导出账单 Excel

```
GET /zc/bills/export-excel
权限：zc:bills:export
```

---

## 四、订单工序记录接口

**Controller**：`ZcOrderProcessRecordController`  
**路径前缀**：`/zc/order-process-record`  
**Service 实现**：`ZcOrderProcessRecordServiceImpl`

---

### 4.1 新增工序记录（开始某道工序）

```
POST /zc/order-process-record/create
权限：zc:order-process-record:create
```

**请求体**（`ZcOrderProcessRecordSaveReqVO`）：

```json
{
  "orderId": 1024,      // 销售订单 ID（必填）
  "nodeId": 3,          // 工序节点 ID（必填，必须是当前员工已绑定的节点）
  "note": "开始裁布",   // 备注
  "imageUrls": ["https://..."] // 现场照片 URL 列表
}
```

**返回**：新工序记录 ID

**业务逻辑**（`createProcessRecord`）：

```
1. 校验订单存在
   └── 订单 status 必须为 "pending" 或 "processing"
       否则抛 SALES_ORDER_STATUS_CANNOT_PROCESS

2. 权限校验
   └── 调用 userProcessNodeService.validateCurrentUserCanOperateNode(nodeId)
       当前登录员工必须已绑定该工序节点（在其绑定列表内）

3. 读取工序节点信息
   └── processNodeMapper.selectById(nodeId)
   └── node 不存在 → 抛 PROCESS_NODE_NOT_EXISTS

4. 保存工序记录
   └── nodeName = node.getName()（快照存储）
   └── status = 1（进行中）
   └── operatorUserId = 当前登录用户 ID

5. 联动更新订单状态
   └── 若 order.status == "pending"（待生产）：
       newStatus = "processing"（推进至生产中）
   └── 否则保持原状态
   └── 更新 status = newStatus 并同步 currentNodeName = node.getName()
```

---

### 4.2 标记工序完成

```
PUT /zc/order-process-record/complete
权限：zc:order-process-record:update
```

**请求体**（`ZcOrderProcessRecordCompleteReqVO`）：

```json
{
  "id": 10,           // 工序记录 ID（必填）
  "note": "裁布完成"  // 完成备注
}
```

**业务逻辑**：

```
1. 校验工序记录存在（否则抛 ORDER_PROCESS_RECORD_NOT_EXISTS）
2. 更新 status = 2（已完成），写入 note
```

---

### 4.3 删除工序记录

```
DELETE /zc/order-process-record/delete?id={id}
权限：zc:order-process-record:delete
```

**业务逻辑**：

```
1. 校验工序记录存在
2. 若 status == 2（已完成） → 抛 ORDER_PROCESS_RECORD_ALREADY_COMPLETED
   （已完成的记录不允许删除，防止篡改历史）
3. 删除记录（仅允许删除"进行中"的记录）
```

---

### 4.4 查询订单工序时间线

```
GET /zc/order-process-record/list?orderId={orderId}
权限：zc:order-process-record:query
```

**返回**（`List<ZcOrderProcessRecordRespVO>`）：按 `create_time` 升序排列

**响应字段**：
- id, orderId, nodeId, nodeName（快照）, status, operatorUserId
- **operatorUserName**（操作员昵称，LEFT JOIN system_users 获取）
- note, imageUrls, createTime（工序开始时间）, updateTime（完成时更新）

**底层 SQL**（`ZcOrderProcessRecordMapper.xml`）：

```sql
SELECT t1.*, t2.nickname AS operator_user_name
FROM zc_order_process_record t1
LEFT JOIN system_users t2 ON t1.operator_user_id = t2.id AND t2.deleted = 0
WHERE t1.deleted = 0 AND t1.order_id = #{orderId}
ORDER BY t1.create_time ASC
```

---

## 五、核心联动关系图

### 5.1 订单状态流转

> **完整四层状态定义、聚合规则与 API 约束**见专项文档：[order-status.md](./order-status.md)

```
UNCONFIRMED（未确认）
    │ confirmSalesOrder（手动，扣减客户余额；窗帘行→NOT_PEILIAO）
    ▼
CONFIRMED（已确认）
    │ cutMaterial（L4 用料→L2 窗帘→L1 订单联动）
    ├─► BUFEN_PEILIAO（部分配料）
    └─► HAVE_PEILIAO（已配料）
    │ packCurtain（手动，L2→L1 聚合）
    ├─► BUFEN_DABAO（部分打包）
    └─► DABAO（已打包）
    │ shipCurtain（手动，L2→L1 聚合）
    ├─► BUFEN_FAHUO（部分发货）
    └─► FAHUO（已发货）
    │ completeSalesOrder（手动）
    ▼
COMPLETE（完成）

cancelConfirmSalesOrder：CONFIRMED / BUFEN_PEILIAO / HAVE_PEILIAO → UNCONFIRMED
  （须无收款、无已裁剪用料，退回客户余额）
```

### 5.2 结算状态流转

```
unpaid（未结算）
    │
    │ createBills()：分摊金额达到订单金额
    ▼
paid（已结算）

unpaid → partialpaid（部分结算）→ paid（全额结算）

deleteBills/updateBills 可逆推回前一状态
```

### 5.3 账单与订单余额联动

```
确认订单时：   客户余额 -= 订单 amount（欠款增加）
账单收款时：   客户余额 += actualAmount + discountAmount（还款）
取消确认时：   客户余额 += 订单 amount（退回欠款，要求无收款记录）
删除账单时：   客户余额 -= actualAmount + discountAmount（撤销还款）
更新账单时：   先回滚旧账单影响，再应用新账单影响
```

---

## 六、单号生成机制

**组件**：`ZcNoGeneratorRedisDAO`  
**方式**：Redis INCR 原子自增，每日每租户独立计数

### 订单号

```
格式：ZC{tenantId}{yyyyMMdd}{seq:05d}
示例：ZC120260519000001

Key：zc:order:seq:{tenantId}:{date}
TTL：48 小时（每次调用刷新，自动清理跨日旧Key）
```

### 账单号

```
格式：SK{yyyyMMdd}-{seq:06d}
示例：SK20260519-000001

Key：zc:bill:seq:{tenantId}:{date}
TTL：48 小时
```

**并发安全**：Redis INCR 是原子操作，天然保证高并发下序号不重复，解决了原有 `COUNT(*)+1` 在并发场景下的竞争问题。

---

## 七、客户余额联动

**实现**：`ZcCustomerServiceImpl.adjustBalance`

```java
// 使用数据库原子加减，避免并发「后写覆盖先写」导致余额计算错误
customerMapper.update(null, Wrappers.<ZcCustomerDO>lambdaUpdate()
    .setSql("balance = COALESCE(balance, 0) + " + delta.toPlainString())
    .eq(ZcCustomerDO::getId, customerId));
```

**关键设计**：
- 使用 `UPDATE ... SET balance = balance + delta` 而非先查后写，避免并发写覆盖
- `COALESCE(balance, 0)` 防止 balance 为 NULL 时计算错误
- delta 为负数时表示扣减，为正数时表示增加

### 余额变动汇总

| 操作 | delta | 说明 |
|------|-------|------|
| 确认订单 | `-amount` | 产生欠款（负数 = 余额减少） |
| 取消确认 | `+amount` | 退回欠款（正数 = 余额恢复） |
| 创建账单 | `+actualAmount + discountAmount` | 还款 |
| 删除账单 | `-(actualAmount + discountAmount)` | 撤销还款 |
| 更新账单 | 先减旧值，再加新值 | 差额调整 |

---

## 八、关键枚举值说明

### 订单状态（`status`，字典 `zc_order_status`）

> 枚举类 `ZcSalesOrderStatusEnum`；四层联动规则见 [order-status.md](./order-status.md)

| 值 | 中文 | 适用层级 | 说明 |
|----|------|----------|------|
| `UNCONFIRMED` | 未确认 | L1 订单、L2 窗帘 | 创建时默认 |
| `CONFIRMED` | 已确认 | L1 订单 | 手动确认；或未进入配料阶段时聚合 |
| `NOT_PEILIAO` | 未配料 | L2 窗帘 | 确认订单时写入 |
| `BUFEN_PEILIAO` | 部分配料 | L1 订单、L2 窗帘 | 自动聚合 |
| `HAVE_PEILIAO` | 已配料 | L1 订单、L2 窗帘 | 自动聚合 / 用料 `HAVE_PEILIAO` |
| `BUFEN_DABAO` | 部分打包 | L1 订单 | 自动聚合 |
| `DABAO` | 已打包 | L1 订单、L2 窗帘 | 手动打包 / 聚合 |
| `BUFEN_FAHUO` | 部分发货 | L1 订单 | 自动聚合 |
| `FAHUO` | 已发货 | L1 订单、L2 窗帘 | 手动发货 / 聚合 |
| `COMPLETE` | 完成 | L1 订单 | 手动完成 |

### 用料配料状态（`zc_sales_order_material.status`）

| 值 | 中文 | 说明 |
|----|------|------|
| `NOT_PEILIAO` | 未配料 | 默认；撤销裁剪回退 |
| `HAVE_PEILIAO` | 已配料 | 裁剪出库后写入 |

### 结算状态（`payStatus`）

| 值 | 中文 | 说明 |
|----|------|------|
| `unpaid` | 未结算 | 初始状态，无任何收款 |
| `partialpaid` | 部分结算 | 已收款但未全额 |
| `paid` | 已结算 | 收款总额 >= 订单金额 |

### 工序记录状态（`status`）

| 值 | 中文 | 说明 |
|----|------|------|
| `1` | 进行中 | 创建时设置，可删除 |
| `2` | 已完成 | 标记完成后不可删除 |

### 账单附件类型（`type`）

| 值 | 说明 | 判断条件 |
|----|------|---------|
| `1` | 图片 | URL 后缀为 jpg/jpeg/png/gif/webp |
| `2` | 文件 | 其他后缀 |

---

## 九、权限标识汇总

| 接口 | 权限标识 |
|------|---------|
| 创建销售订单 | `zc:sales-order:create` |
| 查询销售订单 | `zc:sales-order:query` |
| 更新销售订单 | `zc:sales-order:update` |
| 导出销售订单 | `zc:sales-order:export` |
| 创建收支账单 | `zc:bills:create` |
| 更新收支账单 | `zc:bills:update` |
| 删除收支账单 | `zc:bills:delete` |
| 查询收支账单 | `zc:bills:query` |
| 导出收支账单 | `zc:bills:export` |
| 新增工序记录 | `zc:order-process-record:create` |
| 更新工序记录 | `zc:order-process-record:update` |
| 删除工序记录 | `zc:order-process-record:delete` |
| 查询工序记录 | `zc:order-process-record:query` |

---

## 十、涉及文件索引

### 销售订单

| 层级 | 文件路径 |
|------|---------|
| Controller | `controller/admin/salesorder/ZcSalesOrderController.java` |
| Service 接口 | `service/salesorder/ZcSalesOrderService.java` |
| Service 实现 | `service/salesorder/ZcSalesOrderServiceImpl.java` |
| DO | `dal/dataobject/salesorder/ZcSalesOrderDO.java` |
| DO（窗帘行） | `dal/dataobject/salesorder/ZcSalesOrderCurtainDO.java` |
| DO（结构行） | `dal/dataobject/salesorder/ZcSalesOrderStructureDO.java` |
| DO（用料明细） | `dal/dataobject/salesorder/ZCSalesOrderMaterialDO.java` |
| Mapper XML | `resources/mapper/salesorder/ZcSalesOrderMapper.xml` |

### 收支账单

| 层级 | 文件路径 |
|------|---------|
| Controller | `controller/admin/bills/ZcBillsController.java` |
| Service 实现 | `service/bills/ZcBillsServiceImpl.java` |
| DO（主表） | `dal/dataobject/bills/ZcBillsDO.java` |
| DO（分摊明细） | `dal/dataobject/bills/ZcBillOrderItemsDO.java` |
| DO（附件） | `dal/dataobject/bills/ZcBillAttachmentsDO.java` |

### 工序记录

| 层级 | 文件路径 |
|------|---------|
| Controller | `controller/admin/processnode/ZcOrderProcessRecordController.java` |
| Service 实现 | `service/processnode/ZcOrderProcessRecordServiceImpl.java` |
| DO | `dal/dataobject/processnode/ZcOrderProcessRecordDO.java` |
| Mapper XML | `resources/mapper/processnode/ZcOrderProcessRecordMapper.xml` |

### 公共组件

| 组件 | 文件路径 | 说明 |
|------|---------|------|
| 单号生成 | `dal/redis/ZcNoGeneratorRedisDAO.java` | Redis INCR 原子序号生成 |
| 余额调整 | `service/customer/ZcCustomerServiceImpl.java` | 数据库原子加减 |
