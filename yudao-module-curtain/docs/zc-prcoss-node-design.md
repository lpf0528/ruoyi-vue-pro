# 智仓
## 二、工序追踪功能设计

### 2.1 业务背景

订单确认后进入加工环节，工厂需要对布料进行**裁剪、缝制、定型、质检、包装**等多道工序。目前系统中订单状态 `pending`（待生产）和 `processing`（生产中）没有任何操作入口，客户和管理员都无法了解具体加工进度。

**目标：**
1. 工厂员工可以按工序推进，记录每道工序的完成情况
2. 客户可通过订单号查看实时加工进度（时间线视图）
3. 管理员可灵活配置工序节点，适应不同产品线的生产流程

---

### 2.2 工序流转与订单状态联动

```
unconfirmed（待确认）
    ↓ confirmSalesOrder()
confirmed（已确认）
    ↓ dispatchOrder()  [新增] 派发到加工仓库
pending（待生产）
    ↓ 新增第一条工序记录时，自动触发
processing（生产中）
    ↓ completeOrder()  [新增] 管理员手动完成
completed（已完成）

cancelled（已取消）← 仅允许从 unconfirmed 或 confirmed（且无收款）取消
```

---

### 2.3 数据库设计

#### 2.3.1 工序节点配置表 `zc_process_node`

用于管理员配置加工流程中的工序类型（如"裁剪"、"缝制"等），支持灵活增减。

```sql
CREATE TABLE zc_process_node
(
    id          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    name        VARCHAR(50)  NOT NULL COMMENT '工序名称，如：备料、裁剪、缝制、定型、质检、包装',
    sort        INT          NOT NULL DEFAULT 0 COMMENT '排序号，数字越小越靠前',
    description VARCHAR(200)          COMMENT '工序描述/操作说明',
    creator     VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '创建者',
    create_time DATETIME     NOT NULL COMMENT '创建时间',
    updater     VARCHAR(64)           DEFAULT '' COMMENT '更新者',
    update_time DATETIME     NOT NULL COMMENT '更新时间',
    deleted     BIT(1)       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    tenant_id   BIGINT       NOT NULL DEFAULT 0 COMMENT '租户编号',
    PRIMARY KEY (id)
) COMMENT = '工序节点配置';

-- 初始化默认工序节点（可根据实际需要调整）
INSERT INTO zc_process_node (name, sort, description, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES ('备料',   10, '根据订单物料清单，从仓库领取对应批次的布料和配件', 'admin', NOW(), 'admin', NOW(), 0, 1),
       ('裁剪',   20, '按照订单结构行的高度、宽度规格进行布料裁剪', 'admin', NOW(), 'admin', NOW(), 0, 1),
       ('缝制',   30, '对裁剪好的布料进行主体缝制加工', 'admin', NOW(), 'admin', NOW(), 0, 1),
       ('定型',   40, '对需要定型的窗帘进行熨烫定型处理（isShaping=true 的结构行需此工序）', 'admin', NOW(), 'admin', NOW(), 0, 1),
       ('穿杆装褶', 50, '穿入窗帘杆、安装褶皱和配件（铅块、铁钩等）', 'admin', NOW(), 'admin', NOW(), 0, 1),
       ('质检',   60, '对成品进行质量检验，核对尺寸、工艺是否符合订单要求', 'admin', NOW(), 'admin', NOW(), 0, 1),
       ('包装',   70, '将成品装袋/装箱，标注客户信息和订单号', 'admin', NOW(), 'admin', NOW(), 0, 1),
       ('待发货', 80, '成品已打包，等待安排物流发货', 'admin', NOW(), 'admin', NOW(), 0, 1);
```

#### 2.3.2 订单工序记录表 `zc_order_process_record`

记录每个订单每道工序的执行情况（流水账式记录，不覆盖）。

```sql
CREATE TABLE zc_order_process_record
(
    id               BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键',
    order_id         BIGINT        NOT NULL COMMENT '关联销售订单 ID（zc_sales_order.id）',
    node_id          BIGINT                 COMMENT '工序节点 ID（zc_process_node.id），为 NULL 表示自定义工序',
    node_name        VARCHAR(50)   NOT NULL COMMENT '工序名称快照（冗余存储，防止节点名被修改后历史记录变化）',
    status           TINYINT       NOT NULL DEFAULT 1 COMMENT '状态：1=进行中，2=已完成',
    operator_user_id BIGINT                 COMMENT '操作人员 ID（system_users.id）',
    note             VARCHAR(500)           COMMENT '备注（如质检不通过原因、特殊情况说明等）',
    image_urls       VARCHAR(2000)          COMMENT '现场照片 URL 列表，JSON 字符串，如：["url1","url2"]',
    creator          VARCHAR(64)   NOT NULL DEFAULT '' COMMENT '创建者',
    create_time      DATETIME      NOT NULL COMMENT '创建时间（即工序开始时间）',
    updater          VARCHAR(64)            DEFAULT '' COMMENT '更新者',
    update_time      DATETIME      NOT NULL COMMENT '更新时间',
    deleted          BIT(1)        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    tenant_id        BIGINT        NOT NULL DEFAULT 0 COMMENT '租户编号',
    PRIMARY KEY (id),
    KEY idx_order_id (order_id, deleted) COMMENT '按订单查询工序记录'
) COMMENT = '订单工序记录';
```

#### 2.3.3 销售订单主表新增字段

```sql
ALTER TABLE zc_sales_order
    ADD COLUMN current_node_name VARCHAR(50) COMMENT '当前所处工序名称（冗余，用于列表快速展示）' AFTER status;
```

---

### 2.4 Java 代码设计

#### 2.4.1 DO 类

**`ZcProcessNodeDO`**（工序节点配置）

```java
/**
 * 工序节点配置 DO
 *
 * <p>管理员可灵活增减工序类型，支持按 sort 字段排序展示</p>
 */
@TableName("zc_process_node")
@Data
@EqualsAndHashCode(callSuper = true)
public class ZcProcessNodeDO extends BaseDO {

    /** 主键 */
    private Long id;
    /** 工序名称，如"裁剪"、"缝制" */
    private String name;
    /** 排序号，数字越小越靠前 */
    private Integer sort;
    /** 工序描述/操作说明 */
    private String description;
}
```

**`ZcOrderProcessRecordDO`**（订单工序记录）

```java
/**
 * 订单工序记录 DO
 *
 * <p>以流水账形式记录订单每道工序的执行情况，支持附图和备注。
 * node_name 采用快照存储，防止历史记录因节点名称变更而失真。</p>
 */
@TableName("zc_order_process_record")
@Data
@EqualsAndHashCode(callSuper = true)
public class ZcOrderProcessRecordDO extends BaseDO {

    /** 主键 */
    private Long id;
    /** 关联销售订单 ID */
    private Long orderId;
    /** 工序节点 ID，null 时表示自定义工序 */
    private Long nodeId;
    /** 工序名称快照（冗余，防止节点名被修改后历史记录变化） */
    private String nodeName;
    /**
     * 状态：1=进行中，2=已完成
     *
     * @see ProcessRecordStatusEnum
     */
    private Integer status;
    /** 操作人员 ID */
    private Long operatorUserId;
    /** 备注（质检不通过原因、特殊情况说明等） */
    private String note;
    /** 现场照片 URL 列表，JSON 格式存储，用 JacksonTypeHandler 映射 */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> imageUrls;
}
```

#### 2.4.2 状态枚举

```java
/**
 * 工序记录状态枚举
 */
public enum ProcessRecordStatusEnum {

    IN_PROGRESS(1, "进行中"),
    COMPLETED(2, "已完成");

    private final Integer status;
    private final String name;
}
```

#### 2.4.3 Service 接口设计

**`ZcProcessNodeService`**（工序节点配置管理）

```java
/**
 * 工序节点配置 Service 接口
 *
 * <p>管理员通过此 Service 维护加工流程的工序类型，
 * 工序节点的增删改查均在此处理。</p>
 */
public interface ZcProcessNodeService {

    /**
     * 创建工序节点
     *
     * @param reqVO 创建请求
     * @return 新节点 ID
     */
    Long createProcessNode(ZcProcessNodeSaveReqVO reqVO);

    /**
     * 更新工序节点
     *
     * @param reqVO 更新请求（必须包含 id）
     */
    void updateProcessNode(ZcProcessNodeSaveReqVO reqVO);

    /**
     * 删除工序节点
     *
     * @param id 节点 ID
     */
    void deleteProcessNode(Long id);

    /**
     * 获取工序节点列表（全量，按 sort 排序）
     *
     * @return 工序节点列表
     */
    List<ZcProcessNodeDO> getProcessNodeList();
}
```

**`ZcOrderProcessRecordService`**（订单工序记录操作）

```java
/**
 * 订单工序记录 Service 接口
 *
 * <p>工厂员工通过此 Service 推进订单工序进度，支持上传现场照片。
 * 第一条工序记录创建时，系统自动将订单状态从 pending → processing。</p>
 */
public interface ZcOrderProcessRecordService {

    /**
     * 新增工序记录（开始某道工序）
     *
     * <p>若订单当前状态为 pending，自动变更为 processing 并记录当前工序名称。</p>
     *
     * @param reqVO 创建请求，必须包含 orderId 和 nodeName
     * @return 新记录 ID
     */
    Long createProcessRecord(ZcOrderProcessRecordSaveReqVO reqVO);

    /**
     * 标记某工序已完成
     *
     * @param id     工序记录 ID
     * @param note   完成备注（可选）
     */
    void completeProcessRecord(Long id, String note);

    /**
     * 删除工序记录（仅允许删除状态为"进行中"的记录）
     *
     * @param id 记录 ID
     */
    void deleteProcessRecord(Long id);

    /**
     * 获取订单的全部工序记录，按创建时间升序排列
     *
     * @param orderId 订单 ID
     * @return 工序记录列表（含操作人姓名）
     */
    List<ZcOrderProcessRecordRespVO> getProcessRecordList(Long orderId);
}
```

**`ZcSalesOrderService` 新增方法：**

```java
/**
 * 派发订单到加工仓库（confirmed → pending）
 *
 * <p>仅允许已确认（confirmed）状态的订单执行派发操作。
 * 派发后订单进入待生产队列，工厂端可见该订单。</p>
 *
 * @param id 订单 ID
 */
void dispatchOrder(Long id);

/**
 * 完成订单（processing → completed）
 *
 * <p>所有工序均已完成后，管理员调用此接口标记订单为已完成。</p>
 *
 * @param id 订单 ID
 */
void completeOrder(Long id);

/**
 * 取消订单（仅允许 unconfirmed 或未收款的 confirmed 状态）
 *
 * @param id   订单 ID
 * @param note 取消原因
 */
void cancelOrder(Long id, String note);
```

#### 2.4.4 Service 实现关键逻辑

**`ZcOrderProcessRecordServiceImpl.createProcessRecord` 核心逻辑：**

```java
@Override
@Transactional(rollbackFor = Exception.class)
public Long createProcessRecord(ZcOrderProcessRecordSaveReqVO reqVO) {
    // 1. 校验订单存在，且状态为 pending 或 processing
    ZcSalesOrderDO order = salesOrderMapper.selectById(reqVO.getOrderId());
    if (order == null) {
        throw exception(SALES_ORDER_NOT_EXISTS);
    }
    if (!"pending".equals(order.getStatus()) && !"processing".equals(order.getStatus())) {
        throw exception(SALES_ORDER_STATUS_NOT_IN_PRODUCTION); // 订单不在生产流程中
    }

    // 2. 若节点ID存在，从节点配置中读取名称快照
    String nodeName = reqVO.getNodeName();
    if (reqVO.getNodeId() != null) {
        ZcProcessNodeDO node = processNodeMapper.selectById(reqVO.getNodeId());
        if (node != null) {
            nodeName = node.getName();
        }
    }

    // 3. 保存工序记录
    ZcOrderProcessRecordDO record = BeanUtils.toBean(reqVO, ZcOrderProcessRecordDO.class);
    record.setNodeName(nodeName);
    record.setStatus(ProcessRecordStatusEnum.IN_PROGRESS.getStatus());
    record.setOperatorUserId(SecurityFrameworkUtils.getLoginUserId());
    processRecordMapper.insert(record);

    // 4. 若订单当前为 pending，自动变更为 processing
    if ("pending".equals(order.getStatus())) {
        salesOrderMapper.update(null, Wrappers.<ZcSalesOrderDO>lambdaUpdate()
                .set(ZcSalesOrderDO::getStatus, "processing")
                .set(ZcSalesOrderDO::getCurrentNodeName, nodeName)
                .eq(ZcSalesOrderDO::getId, reqVO.getOrderId()));
    } else {
        // 仅更新当前工序名称，方便列表展示
        salesOrderMapper.update(null, Wrappers.<ZcSalesOrderDO>lambdaUpdate()
                .set(ZcSalesOrderDO::getCurrentNodeName, nodeName)
                .eq(ZcSalesOrderDO::getId, reqVO.getOrderId()));
    }

    return record.getId();
}
```

---

### 2.5 REST API 设计

#### 2.5.1 工序节点配置（管理员）

| 方法   | 路径                          | 功能           | 权限标识                     |
|------|-------------------------------|--------------|--------------------------|
| POST | /zc/process-node/create       | 创建工序节点      | `zc:process-node:create` |
| PUT  | /zc/process-node/update       | 更新工序节点      | `zc:process-node:update` |
| DELETE | /zc/process-node/delete     | 删除工序节点      | `zc:process-node:delete` |
| GET  | /zc/process-node/simple-list  | 获取全部工序节点列表  | `zc:process-node:query`  |

#### 2.5.2 订单生产状态操作

| 方法   | 路径                               | 功能                          | 权限标识                      |
|------|-----------------------------------|-----------------------------|-----------------------------|
| PUT  | /zc/sales-order/dispatch          | 派发订单到加工仓库（confirmed→pending）| `zc:sales-order:update`     |
| PUT  | /zc/sales-order/complete          | 完成订单（processing→completed） | `zc:sales-order:update`     |
| PUT  | /zc/sales-order/cancel            | 取消订单                        | `zc:sales-order:update`     |

#### 2.5.3 订单工序记录

| 方法   | 路径                                    | 功能              | 权限标识                              |
|------|----------------------------------------|-----------------|-------------------------------------|
| POST | /zc/order-process/create              | 新增工序记录（开始工序）    | `zc:order-process:create`           |
| PUT  | /zc/order-process/complete            | 标记工序完成          | `zc:order-process:update`           |
| DELETE | /zc/order-process/delete            | 删除工序记录          | `zc:order-process:delete`           |
| GET  | /zc/order-process/list?orderId={id}   | 获取订单工序时间线       | `zc:order-process:query`            |

---

### 2.6 前端展示设计（工序时间线）

建议在订单详情页新增「加工进度」标签页，以时间线组件展示：

```
订单状态：生产中   当前工序：缝制

工序进度时间线：
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
✅  备料          已完成   2026-05-21 09:15   操作人：张工
✅  裁剪          已完成   2026-05-21 11:30   操作人：李工  [查看照片]
🔄  缝制          进行中   2026-05-22 08:00   操作人：王工
─────────────────────────────
⏸  定型          待开始
⏸  穿杆装褶       待开始
⏸  质检          待开始
⏸  包装          待开始
⏸  待发货        待开始
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
```

**展示逻辑：**
1. 调用 `GET /zc/process-node/simple-list` 获取全部工序节点（按 sort 排序）
2. 调用 `GET /zc/order-process/list?orderId=xxx` 获取该订单的工序记录
3. 将两个列表合并展示：有记录的工序显示实际操作时间和状态，无记录的工序显示为"待开始"

---

### 2.7 开发任务拆解

| 优先级 | 任务 | 估算 |
|------|------|------|
| P0 | 新建 `zc_process_node` 和 `zc_order_process_record` 表，主表加 `current_node_name` 字段 | 0.5天 |
| P0 | 实现 `ZcProcessNodeService` 及 Controller（CRUD + simple-list）| 1天 |
| P0 | 实现 `ZcOrderProcessRecordService` 及 Controller（含订单状态联动）| 1天 |
| P0 | `ZcSalesOrderService` 新增 `dispatchOrder`、`completeOrder`、`cancelOrder` | 0.5天 |
| P1 | 前端订单详情页「加工进度」时间线组件 | 1.5天 |
| P1 | 工序记录支持上传现场照片（复用现有 OSS/文件上传）| 0.5天 |
| P2 | 修复账单删除回滚问题（参考 1.1.3）| 1天 |
| P2 | 修复订单号并发冲突问题（参考 1.2.1）| 0.5天 |
| P2 | 确认订单时扣减库存批次数量（参考 1.3.2）| 1天 |

---

## 三、优化建议汇总

| 编号 | 问题描述 | 优先级 | 影响范围 |
|-----|---------|-------|--------|
| B1 | 删除收款单未回滚客户余额和订单收款状态 | 高 | 财务数据一致性 |
| B2 | 取消确认订单应禁止有收款记录的情况 | 高 | 财务数据一致性 |
| B3 | 删除销售订单未级联删除子表数据（窗帘行/结构行/用料明细） | 高 | 数据一致性 |
| B4 | 库存批次数量从未扣减，库存数据不准确 | 高 | 库存管理 |
| O1 | 订单号/账单号生成存在并发冲突，改用 Redis incr | 中 | 系统稳定性 |
| O2 | 客户余额调整改为数据库原子操作，防止并发写入丢失 | 中 | 财务数据一致性 |
| O3 | 收款金额与分摊金额加一致性校验 | 中 | 财务准确性 |
| O4 | 更新收款单需处理附件/分摊/余额的差额，改为「先删后建」 | 中 | 功能完整性 |
| O5 | 库存盘点创建时同步更新批次数量 | 中 | 库存管理 |
| O6 | payStatus "partialpaid" 与 PDF 展示 "partial" 不一致 | 低 | 显示问题 |
| O7 | 引入订单状态/支付状态枚举类，替换分散的字符串常量 | 低 | 代码质量 |
| N1 | **新功能：工序追踪**（见第二章完整设计） | 新功能 | 生产管理 |
| N2 | 考虑增加订单统计报表（按客户、按时间段的营收汇总）| 新功能（低优先级）| 经营分析 |
