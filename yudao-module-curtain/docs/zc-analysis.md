# 智仓（ZC）模块 —— 系统分析与工序追踪功能设计

> 文档日期：2026-05-22  
> 作者：01Coder  
> 范围：`yudao-module-curtain`（包名前缀 `cn.iocoder.yudao.module.zc`）

---

## 一、现有系统问题分析

### 1.1 财务逻辑隐患（中高优先级）

#### 1.1.1 客户余额语义模糊

**当前逻辑：**
- 确认订单时：`balance -= order.amount`（扣减）
- 收款时：`balance += actualAmount + discountAmount`（增加）

**语义推导：** 余额为负数时代表欠款，正数时代表预存/超额付款。这个模型本身是合理的，但**缺乏文档说明**，容易在后续维护中被误改。

**建议：** 在 `ZcCustomerDO.balance` 字段注释中明确说明：`余额为正表示客户预存款（或已多付），为负表示欠款，订单确认时扣减，收款时增加`。

#### 1.1.2 收款金额与分摊金额缺乏一致性校验

**问题代码（`ZcBillsServiceImpl.createBills`）：**
```java
// 5. 更新客户余额：balance += actualAmount + discountAmount
updateCustomer.setBalance(currentBalance.add(createReqVO.getActualAmount()).add(discount));
```

`actualAmount`（加到余额）和 `orderItems[].allocatedAmount`（分摊到各订单的已收金额）是两套独立的数字，代码没有校验两者合计是否一致。如果分摊金额合计 ≠ actualAmount + discountAmount，会导致：
- 订单 `amountReceived` 累计值与客户账目不一致
- 对账时出现差额

**修复建议：** 在 `createBills` 开头加一步校验：
```java
BigDecimal totalAllocated = orderItems.stream()
    .map(ZcBillOrderItemReqVO::getAllocatedAmount)
    .reduce(BigDecimal.ZERO, BigDecimal::add);
BigDecimal totalSettled = actualAmount.add(discountAmount);
if (totalAllocated.compareTo(totalSettled) != 0) {
    throw exception(BILL_ALLOCATED_AMOUNT_NOT_MATCH); // 分摊金额与实收+优惠不一致
}
```

#### 1.1.3 删除收款单未回滚余额与订单状态（严重）

**问题代码（`ZcBillsServiceImpl.deleteBills`）：**
```java
public void deleteBills(Long id) {
    validateBillsExists(id);
    billsMapper.deleteById(id);  // 只删主表，没有任何回滚
}
```

删除收款单时**没有执行**：
- 客户余额回滚（减掉当时加上的 `actualAmount + discountAmount`）
- 各关联订单 `amountReceived` 回滚，支付状态重新计算
- 级联删除附件（`zc_bill_attachments`）和分摊明细（`zc_bill_order_items`）

**修复建议：** 参照创建逻辑的逆操作，在删除前先查询出账单及关联数据，逐步回滚。

#### 1.1.4 更新收款单逻辑不完整

**问题代码（`ZcBillsServiceImpl.updateBills`）：**
```java
public void updateBills(ZcBillsSaveReqVO updateReqVO) {
    validateBillsExists(updateReqVO.getId());
    BeanUtils.toBean(updateReqVO, ZcBillsDO.class);
    billsMapper.updateById(updateObj);  // 只更新主表
}
```

更新时没有处理：附件的增删、订单分摊的调整、客户余额的差额计算。  
**建议：** 更新操作实现为「先删后建」（delete + re-create），保持逻辑简单一致。

#### 1.1.5 取消确认订单应禁止有已收款记录的情况

**问题场景：**
1. 客户初始余额 0，确认 100 元订单 → 余额变 -100
2. 收款 60 元 → 余额变 -40，订单 `amountReceived = 60`
3. 此时取消确认 → 余额退回 +100 - 40 = **+60**（客户不欠款反而多了 60）

**修复建议：** 在 `cancelConfirmSalesOrder` 中增加校验：
```java
if (order.getAmountReceived() != null && order.getAmountReceived().compareTo(BigDecimal.ZERO) > 0) {
    throw exception(SALES_ORDER_HAS_RECEIVED_AMOUNT); // 已有收款记录，不能取消确认
}
```

---

### 1.2 并发安全问题（高优先级）

#### 1.2.1 订单号/账单号生成存在并发冲突

**问题代码：**
```java
// 订单号
long orderCount = salesOrderMapper.selectCount(Wrappers.emptyWrapper());
String orderNo = String.format("ZC%d%s-%05d", tenantId, date, orderCount + 1);

// 账单号
long billCount = billsMapper.selectCount(Wrappers.emptyWrapper());
String billNo = String.format("SK%s-%06d", date, billCount + 1);
```

两个并发请求拿到相同 `count` 值，会生成**重复单号**，违反唯一性。此外：
- 删除记录后再新建，序号可能与已删除记录重复
- 日期变更后序号不重置，序号会无限增长直至超出位数

**修复建议（推荐 Redis incr 方案）：**
```java
// 使用 Redis INCR 保证原子性，key 包含日期确保跨日重置
String key = String.format("zc:order_seq:%s:%d", date, tenantId);
long seq = redisTemplate.opsForValue().increment(key);
redisTemplate.expire(key, Duration.ofDays(2));
String orderNo = String.format("ZC%d%s%05d", tenantId, date, seq);
```

#### 1.2.2 客户余额调整缺乏并发保护

**问题代码（`ZcCustomerServiceImpl.adjustBalance`）：**
```java
ZcCustomerDO customer = customerMapper.selectById(customerId); // 先读
BigDecimal newBalance = current.add(delta);
update.setBalance(newBalance);
customerMapper.updateById(update);  // 后写（无版本号/乐观锁）
```

高并发下（如多个收款单同时提交）会出现「后写覆盖先写」问题，导致余额错误。

**修复建议（使用数据库原子更新）：**
```sql
UPDATE zc_customer SET balance = balance + #{delta}
WHERE id = #{customerId} AND tenant_id = #{tenantId}
```

在 Mapper 中新增此原子操作方法，替换先读后写模式。

---

### 1.3 数据完整性问题（中优先级）

#### 1.3.1 删除订单未级联删除子表数据

**问题代码（`ZcSalesOrderServiceImpl.deleteSalesOrder`）：**
```java
public void deleteSalesOrder(Long id) {
    validateSalesOrderExists(id);
    salesOrderMapper.deleteById(id);  // 只删主表
}
```

删除订单后，`zc_sales_order_curtain`、`zc_sales_order_structure`、`zc_sales_order_material` 中的关联数据成为**孤立记录**。

**修复建议：** 改为级联删除：
```java
salesOrderCurtainMapper.deleteByOrderId(id);
salesOrderStructureMapper.deleteByOrderId(id);
salesOrderMaterialMapper.deleteByOrderId(id);
salesOrderMapper.deleteById(id);
```

#### 1.3.2 库存批次数量从未扣减

下单或确认订单时，均未从 `ZcProductBatchDO.quantity`（剩余数量）中扣减 `ZCSalesOrderMaterialDO.quantity`（用料数量）。批次的剩余数量永远等于入库数量，除非手动盘点。

**建议处理时机：** 订单确认时扣减库存（`confirmSalesOrder` 中）；取消确认时归还库存。这是生产订单的标准做法，可防止超卖（多个订单使用同一批次）。

#### 1.3.3 库存盘点未更新批次数量

`ZcInventoryRecordDO` 记录了盘点前后的数量，但代码中没有在创建盘点记录时同步更新对应 `ZcProductBatchDO.quantity`，盘点记录形同虚设。

**修复建议：** 在 `ZcInventoryRecordServiceImpl.createInventoryRecord` 中，创建记录的同时更新批次数量。

---

### 1.4 状态流转不完整（中优先级）

当前成品订单状态已按四层结构实现（详见 `docs/order-status.md`）：

```
UNCONFIRMED → CONFIRMED → BUFEN_PEILIAO / HAVE_PEILIAO → BUFEN_DABAO / DABAO → BUFEN_FAHUO / FAHUO → COMPLETE
```

手动操作：确认、取消确认、完成；自动聚合：配料（裁剪）、打包、发货。

工序记录（`zc_order_process_record`）与订单 `status` 独立维护，通过 `current_node_name` 快照展示当前工序。

---

### 1.5 payStatus 字符串不一致（低优先级）

收款时写入的 `payStatus` 值为 `"partialpaid"`，但 PDF 生成的 `pdfPayStatus` 方法中的判断逻辑为：
```java
case "partial": return "部分结算";  // 与实际写入值不一致
```
导致「部分收款」状态在 PDF 中显示为原始字符串 `"partialpaid"` 而非中文。

**修复建议：** 统一改为 `"partialpaid"`，或引入枚举类管理所有状态码。

---

### 1.6 批次号生成存在并发问题（低优先级）

`ZcProductBatchServiceImpl.createProductBatch` 使用 `countTodayBatchSeqByProductId` 查询当日该产品的批次数再加一，同样存在并发冲突问题，建议同样改用 Redis incr 方案。

---