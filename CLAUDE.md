# CLAUDE.md — ruoyi-vue-pro 项目指南

> **语言要求：永远使用中文回复用户，不得使用其他语言。**

> 本文件为 Claude（AI 编程助手）提供项目上下文、架构说明、开发规范及代码注释要求，
> 帮助 Claude 在参与本项目时给出准确、符合规范的代码与建议。

---

## 项目概览

**芋道（ruoyi-vue-pro）** 是基于 RuoYi-Vue 深度重构的企业级快速开发平台，完全开源（MIT 协议）。

| 维度 | 说明 |
|---|---|
| 后端 | Spring Boot 多模块 + MyBatis Plus + Redis |
| 前端 | Vue 3 (element-plus / vben) + Vue 2 (element-ui) |
| 移动端 | uni-app（支持 APP、小程序、H5） |
| 数据库 | MySQL 5.7/8.0（兼容 Oracle、PostgreSQL、达梦等） |
| 核心特性 | RBAC 权限、SaaS 多租户、Flowable 工作流、支付、商城、CRM、ERP、AI 大模型、IoT |

---

## 模块结构

```
ruoyi-vue-pro/
├── yudao-dependencies/        # Maven 全局依赖版本管理（BOM）
├── yudao-framework/           # 框架公共封装（Security、MVC、Redis、MQ 等）
├── yudao-server/              # 启动入口，聚合所有模块
├── yudao-module-system/       # 系统功能：用户、角色、菜单、租户、字典等
├── yudao-module-member/       # 会员中心：C 端用户、等级、积分、标签
├── yudao-module-infra/        # 基础设施：代码生成、定时任务、文件、消息队列
├── yudao-module-bpm/          # 工作流：Flowable + SIMPLE/BPMN 双设计器
├── yudao-module-pay/          # 支付系统：支付宝、微信支付、退款
├── yudao-module-mall/         # 商城系统：商品、订单、营销、物流
├── yudao-module-erp/          # ERP 系统：采购、销售、库存、财务
├── yudao-module-crm/          # CRM 系统：客户、线索、商机、合同
├── yudao-module-ai/           # AI 大模型：对话、绘图、知识库
├── yudao-module-mp/           # 微信公众号：粉丝、消息、素材、菜单
├── yudao-module-report/       # 数据报表 & 大屏设计器
├── yudao-module-iot/          # IoT 物联网：设备、产品、消息
├── yudao-module-curtain/      # 窗帘业务系统（智仓）：产品、客户、订单、工艺配置
├── sql/                       # 数据库初始化 SQL（MySQL / PostgreSQL 等）
├── script/                    # 运维脚本（Docker、部署等）
└── yudao-ui/                  # 前端子项目引用（外部仓库）
```

### 每个业务模块内部结构（以 `yudao-module-system` 为例）

```
yudao-module-system/
├── yudao-module-system-api/          # 对外暴露的接口定义（DTO、枚举、Feign 接口）
└── yudao-module-system-biz/          # 业务实现
    └── src/main/java/.../system/
        ├── controller/
        │   ├── admin/                # 管理后台 REST 接口（/admin-api/**）
        │   └── app/                  # 用户 APP REST 接口（/app-api/**）
        ├── service/                  # 业务逻辑层
        ├── dal/
        │   ├── dataobject/           # 数据库实体（DO）
        │   └── mysql/                # MyBatis Plus Mapper
        ├── convert/                  # MapStruct 对象转换器
        └── enums/                    # 业务枚举
```

---

## yudao-module-curtain 窗帘业务模块

> **智仓**（`zc`）是针对窗帘制造行业定制的业务模块，包含完整的产品管理、客户管理、窗帘工艺配置、销售订单履约、收支账单和生产工序追踪体系。
> 包名前缀：`cn.iocoder.yudao.module.zc`

### 模块内部结构

```
yudao-module-curtain/
└── src/main/java/cn/iocoder/yudao/module/zc/
    ├── controller/admin/          # 28 个 Controller，路径前缀 /zc/
    ├── service/                   # 28 个 Service 接口 + 实现
    ├── dal/
    │   ├── dataobject/           # 30 个 DO 类
    │   └── mysql/                # 30 个 MyBatis Plus Mapper
    ├── dal/redis/                 # ZcNoGeneratorRedisDAO（Redis INCR 单号生成）
    └── resources/mapper/         # MyBatis XML 映射文件（含复杂 JOIN 查询）
```

### 业务领域划分

| 领域 | 涉及实体 | 说明 |
|------|---------|------|
| **产品域** | `ZcProductDO`、`ZcProductVersionDO`、`ZcProductCategoryDO`、`ZcProductSpecDO`、`ZcProductBatchDO` | 产品定义、版本、分类、规格、库存批次管理 |
| **客户域** | `ZcCustomerDO`、`ZcCustomerProductPriceDO` | 客户资料、送货地址、余额、客户专项定价 |
| **窗帘工艺域** | `ZcCurtainDO`、`ZcCurtainStructureDO`、`ZcCurtainStructureElementDO`、`ZcCurtainPleatRatioDO`、`ZcCurtainInstallProcessDO`、`ZcCurtainTemplateDO` | 窗帘款式库、结构定义、组件库、褶倍配置、安装工艺、工艺模板 |
| **订单履约域** | `ZcSalesOrderDO`、`ZcSalesOrderCurtainDO`、`ZcSalesOrderStructureDO`、`ZCSalesOrderMaterialDO`、`ZcSalesOrderProductDO` | **核心域**：成品订单（三层嵌套）+ 产品类订单（面料单，两层扁平）|
| **收支账单域** | `ZcBillsDO`、`ZcBillOrderItemsDO`、`ZcBillMethodsDO`、`ZcBillAttachmentsDO` | 客户收款单、按订单分摊金额、收款方式配置、附件凭证 |
| **生产工序域** | `ZcProcessNodeDO`、`ZcOrderProcessRecordDO`、`ZcUserProcessNodeDO` | 工序节点配置（备料/裁剪/缝制/定型/质检/包装等）、订单工序流水记录、员工工序授权 |
| **基础配置域** | `ZcBrandDO`、`ZcSupplierDO`、`ZcWarehouseDO`、`ZcLogisticsDO`、`ZcInventoryRecordDO` | 品牌、供应商、仓库、物流、库存变动记录（含盘点/入库/裁剪/撤销裁剪） |

### 核心数据模型（DO）

| DO 类 | 表名 | 关键字段说明 |
|-------|------|------------|
| `ZcSalesOrderDO` | zc_sales_order | orderNo（自动生成）、customerId、payStatus（unpaid/partial/paid）、status（unconfirmed/pending/processing/completed/cancelled）、confirmTime（非空即已确认）、totalAmount、amount、amountReceived、freight、isExpedited、currentNodeName（当前工序名称快照）、sets（套数：成品单=curtains数量，面料单=batchs数量） |
| `ZcSalesOrderCurtainDO` | zc_sales_order_curtain | orderId、curtainId（款式）、room（房间）、pleatRatioValue（褶倍快照）、mountings（配件 JSON）、discountRate、amount |
| `ZcSalesOrderStructureDO` | zc_sales_order_structure | orderId、orderCurtainId、structureId、height、width、leftCorner、rightCorner、pasteDirection、installProcessId、openMethod、processType、isShaping、pleatsNum、pleatsDistance、skirtHeight |
| `ZCSalesOrderMaterialDO` | zc_sales_order_material | orderId、orderStructureId、elementId（组件类型）、productId、batchId、price、quantity、unitValue、discountRate、amount、status（配料状态：NOT_PEILIAO/HAVE_PEILIAO，见 `ZcSalesOrderMaterialStatusEnum`）、cutQuantity（裁剪数量） |
| `ZcSalesOrderProductDO` | zc_sales_order_product | orderId、productId、batchId、quantity、price、amount、note（产品类订单/面料单的产品批次行） |
| `ZcBillsDO` | zc_bills | billNo（自动生成）、billDate、billUserId（财务人员）、customerId、discountAmount（优惠）、actualAmount（实收）、billMethodId |
| `ZcBillOrderItemsDO` | zc_bill_order_items | billId、orderId、allocatedAmount（本次分摊金额） |
| `ZcProcessNodeDO` | zc_process_node | name（工序名称）、sort（排序号）、description |
| `ZcOrderProcessRecordDO` | zc_order_process_record | orderId、nodeId、nodeName（快照）、status（1=进行中/2=已完成）、operatorUserId、note、imageUrls（JSON 图片列表） |
| `ZcUserProcessNodeDO` | zc_user_process_node | userId、nodeId（员工授权的工序节点） |
| `ZcProductDO` | zc_product | name、versionId、inboundPrice、specId、onePrice（一级售价）、supplierId |
| `ZcProductBatchDO` | zc_product_batch | batchNo、inboundDate、productId、inboundPrice、inboundQuantity、quantity（剩余）、warehouseId、supplierId |
| `ZcCustomerDO` | zc_customer | shortName、contactName、province/city/district、address、deliveryAddress、mobile、logisticId、brandId、balance |
| `ZcCustomerProductPriceDO` | zc_customer_product_price | customerId、productId、authorizedPrice（客户专项授权价） |
| `ZcCurtainDO` | zc_curtain | name（款式名称）、pleatRatioValue（默认褶倍）、pleatsDistance（褶距） |
| `ZcCurtainStructureDO` | zc_curtain_structure | name、attributes（`List<String>` 存 JSON，动态属性如长/宽/高） |
| `ZcInventoryRecordDO` | zc_inventory_record | productId、batchId、oldQuantity、newQuantity、changeQuantity（变化量，正增负减）、operate（操作类型：PANDIAN/RUKU/CAIJIAN/CANCEL_CAIJIAN，见 `ZcInventoryRecordOperateEnum`）、orderId（裁剪/撤销裁剪时关联来源订单，盘点/入库为 null） |

### 销售订单类型说明

ZC 模块存在两类销售订单，共用 `zc_sales_order` 主表，但子行结构不同：

**成品订单（窗帘订单）**：四表三层嵌套，通过 `/zc/sales-order` 接口操作
```
ZcSalesOrder（订单主表）
└── ZcSalesOrderCurtain（窗帘行，L2）      ← 按款式/房间分行
    └── ZcSalesOrderStructure（结构行，L3） ← 含尺寸、工艺、褶数等加工参数
        └── ZCSalesOrderMaterial（用料明细，L4） ← 具体物料、批次、用量、单价
```

**产品类订单（面料单）**：两层扁平结构，通过 `/zc/sales-order-product` 接口操作
```
ZcSalesOrder（订单主表）
└── ZcSalesOrderProduct（产品批次行）  ← 直接购买产品批次，无工艺配置
```

**成品订单整单创建流程**（`ZcSalesOrderServiceImpl.createSalesOrder`）：
1. 生成订单号：`ZC{租户ID}{yyyyMMdd}{5位序号}`，如 `ZC120260519000001`（由 `ZcNoGeneratorRedisDAO.nextOrderSeq()` Redis INCR 保证并发唯一）
2. 保存订单主记录，初始状态 `payStatus=unpaid`、`status=unconfirmed`
3. 遍历窗帘行 → 保存，配件列表（mountings）序列化为 JSON 字符串
4. 遍历结构行 → 保存，关联 orderId + orderCurtainId
5. 遍历用料明细 → 保存，关联 orderId + orderStructureId
6. 全程在同一个 `@Transactional` 事务内，失败整体回滚

**订单详情查询**（`getSalesOrderDetail`）的 N+1 优化：
- 一次性查出所有窗帘行、结构行、用料行（按 orderId），不循环查询
- 批量查询款式名称、结构名称、工艺名称、组件名称、产品名称、批次号
- 在内存中组装嵌套 VO，冗余名称字段，前端无需二次请求

**裁剪出库流程**（`ZCSalesOrderMaterialServiceImpl.cutMaterial`）：
1. 校验用料明细存在，取出 orderId 供库存记录关联
2. 校验批次存在且剩余库存 ≥ 裁剪数量（不足时抛 `PRODUCT_BATCH_INSUFFICIENT_QUANTITY`）
3. 更新用料明细：绑定 batchId、记录 cutQuantity、状态变更为 `HAVE_PEILIAO`
4. 原子扣减批次剩余数量（`productBatchMapper.decreaseQuantity`，防并发超卖）
5. 写入 `zc_inventory_record`，operate=`CAIJIAN`，orderId=来源订单

**撤销裁剪流程**（`ZCSalesOrderMaterialServiceImpl.cancelCutMaterial`）：
1. 校验用料明细存在，状态必须为 `HAVE_PEILIAO`（否则抛 `SALES_ORDER_MATERIAL_NOT_PEILIAO`）
2. 原子回退批次库存（`productBatchMapper.increaseQuantity`）
3. 用 `LambdaUpdateWrapper` 显式将 `cutQuantity` 置为 null、状态回退为 `NOT_PEILIAO`（不能用 `updateById`，会忽略 null 字段）
4. 写入 `zc_inventory_record`，operate=`CANCEL_CAIJIAN`，orderId=来源订单

**入库自动记流水**（`ZcProductBatchServiceImpl.createProductBatch`）：
- 批次创建成功后立即写入 `zc_inventory_record`，operate=`RUKU`，old=0，new=入库数量

**盘点记流水**（`ZcInventoryRecordServiceImpl.createInventoryRecord`）：
- 盘点接口写入 operate=`PANDIAN` 的变动记录，并同步更新批次剩余数量和备注（覆盖上次盘点行）

**App 端订单分页接口**（`GET /zc/sales-order/app/page`）：
- 固定过滤 `status=unconfirmed` 的未确认订单，前端无需传参，用于车间 App 生产工单场景

### 关键技术特点

**JSON 字段存储**
- `ZcSalesOrderCurtainDO.mountings`：配件多选列表，存 JSON 字符串
- `ZcCurtainStructureDO.attributes`：动态属性，用 `@TableField(typeHandler = JacksonTypeHandler.class)` 映射为 `List<String>`

**复杂 JOIN 查询（Mapper XML）**
- `ZcSalesOrderMapper`：LEFT JOIN `zc_customer`、`zc_logistics`、`system_users`，分页返回含客户名/物流名/创建人名
- `ZcProductMapper`：LEFT JOIN `zc_product_version`、`zc_product_spec`、`zc_supplier`、`system_users`

**窗帘行业专有概念**

| 概念 | 字段 | 说明 |
|------|------|------|
| 褶倍（Pleat Ratio） | `pleatRatioValue` | 布料用量系数，窗口宽 × 褶倍 = 所需布宽 |
| 褶距（Pleat Distance） | `pleatsDistance` | 褶与褶的间距（cm） |
| 褶数 | `pleatsNum` | 总宽度 / 褶距 计算得出 |
| 裙摆高度 | `skirtHeight` | 窗帘底部装饰高度 |
| 安装工艺 | `installProcessId` | 墙装、顶装、罗马杆等方案 |
| 配件（Mountings） | `mountings` | 铅块、磁条、铁钩等附件（多选 JSON） |

**关键枚举**

| 枚举类 | 取值 | 说明 |
|--------|------|------|
| `ZcSalesOrderMaterialStatusEnum` | `NOT_PEILIAO` / `HAVE_PEILIAO` | 用料明细配料状态（未配料/已配料） |
| `ZcInventoryRecordOperateEnum` | `PANDIAN` / `RUKU` / `CAIJIAN` / `CANCEL_CAIJIAN` | 库存变动操作类型（盘点/入库/裁剪/撤销裁剪） |

**权限标识规范**：`zc:{资源}:{操作}`，如 `zc:product:create`、`zc:sales-order:query`

**错误码范围**：`100001 ~ 100061`，定义于 `ErrorCodeConstants`

### 模块依赖
- 依赖 `yudao-module-system`：Mapper XML 中 JOIN `system_users` 表获取创建人名称
- 依赖 `yudao-spring-boot-starter-biz-tenant`：订单号生成使用 `TenantContextHolder.getRequiredTenantId()`
- 无对其他业务模块（如 ERP、CRM）的 Service 调用，业务域相对独立

### 新增 ZC 模块功能时的注意事项

1. **整单 API**：涉及订单创建/更新，优先考虑整单接口（一次请求处理多层嵌套），避免前端多次调用
2. **N+1 防范**：查询含关联名称时，先批量查出 ID 集合，再 `selectBatchIds()` 一次性加载，不要在循环里查数据库
3. **JSON 字段**：多选类字段（如 mountings、attributes、imageUrls）用 JSON 存储，DO 层用 `JacksonTypeHandler`，Service 层负责序列化/反序列化
4. **订单号唯一性**：统一使用 `ZcNoGeneratorRedisDAO`（Redis INCR）生成序号，订单用 `nextOrderSeq()`，收款单用 `nextBillSeq()`，批次用 `nextBatchSeq()`，不要再用 `selectCount + 1`
5. **级联删除**：删除订单前必须校验 `confirmTime == null`（已确认订单禁止删除）；成品订单级联删窗帘行/结构行/用料明细，产品类订单级联删产品行
6. **工序权限**：员工操作工序记录前需校验 `ZcUserProcessNodeDO` 授权，参考 `ZcOrderProcessRecordServiceImpl`
7. **收款分摊**：创建/更新收款单时，需校验分摊金额合计 = 实收 + 优惠，参考错误码 `BILL_ALLOCATED_AMOUNT_NOT_MATCH`
8. **裁剪操作**：`cutMaterial` 扣库存用原子方法 `decreaseQuantity`；`cancelCutMaterial` 回退库存后必须用 `LambdaUpdateWrapper` 将 `cutQuantity` 显式置 null（`updateById` 不会更新 null 字段）；两个操作均需写入 `ZcInventoryRecordDO`
9. **库存变动记录**：`zc_inventory_record` 已不只是盘点表，所有库存变化（入库/盘点/裁剪/撤销裁剪）都写入，operate 字段区分类型，勿遗漏
10. **字典/状态枚举**：DO 中凡是注释含"枚举"或"字典"的 String 字段（如 `status`、`payStatus`），必须有对应的枚举类（位于 `enums/` 包）及 `curtain.sql` 中的字典数据（`system_dict_type` + `system_dict_data`）。Service 层一律使用 `枚举.name()` 写入，禁止硬编码字符串字面量。**若字段尚无枚举，Claude 应主动询问用户提供所有取值及其中文名称，确认后再创建枚举类和字典 SQL；若枚举已存在但需新增取值，同步更新枚举类、字典 SQL，并 grep 所有引用该字段的 Service/Mapper，确保没有遗漏的硬编码字面量。**

---

## 技术栈速查

### 后端核心依赖

| 框架 / 组件 | 用途 | 版本 |
|---|---|---|
| Spring Boot | 应用框架 | 2.7.x（master）/ 3.2.x（master-jdk17） |
| MyBatis Plus | ORM 框架 | 3.5.x |
| Redisson | Redis 客户端（分布式锁/缓存/MQ） | 3.32.x |
| Spring Security + Token | 认证鉴权 | 5.7.x |
| Flowable | 工作流引擎 | 6.8.x |
| Quartz | 定时任务 | 2.3.x |
| Springdoc | Swagger 接口文档 | 1.7.x |
| MapStruct | Java Bean 转换 | 1.6.x |
| Lombok | 减少样板代码 | 1.18.x |
| JUnit 5 + Mockito | 单元测试 | 5.8.x / 4.8.x |

### 分支说明

| 分支 | JDK | Spring Boot |
|---|---|---|
| `master` | JDK 8 | 2.7.x |
| `master-jdk17` | JDK 17 / 21 | 3.2.x |

---

## 开发规范

### 命名规范

遵循《阿里巴巴 Java 开发手册》，以下为项目特有约定：

| 类型 | 规范 | 示例 |
|---|---|---|
| 数据对象 (DO) | `XxxDO` | `UserDO` |
| 数据传输对象 | `XxxReqVO` / `XxxRespVO` | `UserCreateReqVO` |
| 分页请求 | `XxxPageReqVO` | `UserPageReqVO` |
| 对象转换 | `XxxConvert` | `UserConvert` |
| 业务接口 | `XxxService` | `AdminUserService` |
| 业务实现 | `XxxServiceImpl` | `AdminUserServiceImpl` |
| Mapper | `XxxMapper` | `UserMapper` |
| 控制器 | `XxxController` | `UserController` |
| 枚举 | `XxxEnum` | `UserStatusEnum` |

### 接口路径规范

```
管理后台：/admin-api/{模块}/{资源}     # 如 /admin-api/system/user
用户 APP ：/app-api/{模块}/{资源}      # 如 /app-api/member/user/profile
```

### 错误码规范

错误码格式：`{模块编号}-{错误编号}`，统一在 `ErrorCodeConstants` 中定义：

```java
// 系统模块错误码从 1-001-000 开始
public interface ErrorCodeConstants {
    // 用户相关
    ErrorCode USER_NOT_EXISTS = new ErrorCode(1_001_000_000, "用户不存在");
    ErrorCode USER_PASSWORD_FAILED = new ErrorCode(1_001_000_001, "账号密码不正确");
}
```

---

## 代码注释规范（重要）

**本项目要求所有新增与修改的代码必须包含必要的注释**，以下为具体规范：

### 1. 类级注释

每个类（包括接口、枚举）都需要 Javadoc 注释，说明类的职责、使用场景。

```java
/**
 * 管理后台 - 用户管理 Controller
 *
 * <p>提供用户的增删改查、密码重置、状态变更等接口，
 * 仅供管理后台调用，权限标识为 {@code system:user:*}</p>
 *
 * @author 芋道源码
 */
@Tag(name = "管理后台 - 用户")
@RestController
@RequestMapping("/system/user")
public class UserController {
    // ...
}
```

### 2. 方法级注释

Service 接口的所有方法都必须有 Javadoc，实现类中复杂逻辑方法也需要注释：

```java
/**
 * 创建用户
 *
 * @param reqVO 创建请求 VO，包含用户名、密码、部门等信息
 * @return 新用户的 ID
 * @throws ServiceException 若用户名已存在，抛出 {@link UserErrorCodeConstants#USER_USERNAME_EXISTS}
 */
Long createUser(UserCreateReqVO reqVO);
```

### 3. 字段注释

DO、VO、枚举等数据类的每个字段必须有单行注释，说明字段含义、取值约束：

```java
public class UserDO extends BaseDO {

    /** 用户账号，唯一，最长 30 字符 */
    private String username;

    /** 加密后的登录密码（BCrypt） */
    private String password;

    /** 用户状态，参见 {@link CommonStatusEnum} */
    private Integer status;

    /** 所属部门 ID，关联 DeptDO */
    private Long deptId;

    /** 最后登录 IP */
    private String loginIp;

    /** 最后登录时间 */
    private LocalDateTime loginDate;
}
```

### 4. 关键逻辑内联注释

对于复杂的业务逻辑、算法、非直觉性的处理，必须添加行内或块注释，解释"为什么"而非"是什么"：

```java
public void processOrder(Long orderId) {
    OrderDO order = orderMapper.selectById(orderId);

    // 校验订单状态：只有【待支付】的订单才允许继续处理
    if (!OrderStatusEnum.UNPAID.getStatus().equals(order.getStatus())) {
        throw exception(ORDER_STATUS_NOT_UNPAID);
    }

    // 锁定库存：在支付完成前提前锁库，防止超卖
    // 注意：此处使用分布式锁，key = "stock:lock:{skuId}"
    skuService.lockStock(order.getSkuId(), order.getCount());

    // 发送延迟消息：30 分钟未支付自动取消
    // 使用 RocketMQ 延迟消息而非 Quartz，避免大量定时任务扫描带来的数据库压力
    delayMessageProducer.sendOrderExpireMessage(orderId, Duration.ofMinutes(30));
}
```

### 5. TODO / FIXME 注释

临时方案或已知问题需标注，格式为 `// TODO [作者] 说明` 或 `// FIXME [作者] 说明`：

```java
// TODO [芋艿] 后续支持按用户维度的限流，当前仅做全局限流
rateLimiter.acquire();

// FIXME [芋艿] 当租户数量超过 1000 时，此查询存在性能问题，需要改为分页或缓存方案
List<TenantDO> tenants = tenantMapper.selectList();
```

### 6. 注释的禁忌

- ❌ 不写无意义的重复注释：`// 获取用户` 对应 `getUser()` 毫无价值
- ❌ 不注释掉大段代码提交到仓库，应直接删除
- ❌ 不用中英混杂的拼音注释
- ✅ 注释说明"为什么"（设计决策、限制原因），而非"是什么"（代码本身已表达）

---

## 常用开发模式

### 新增一个 CRUD 模块的标准流程

1. **定义数据库表** → 编写 `sql/` 下的 DDL
2. **创建 DO** → 继承 `BaseDO`，字段加注释
3. **创建 Mapper** → 继承 `BaseMapperX<XxxDO>`
4. **创建 VO** → `XxxCreateReqVO`、`XxxUpdateReqVO`、`XxxRespVO`、`XxxPageReqVO`
5. **创建 Convert** → 使用 `@Mapper`（MapStruct），在接口中定义转换方法
6. **创建 Service 接口 + 实现** → 接口方法必须有完整 Javadoc
7. **创建 Controller** → 使用 `@Tag`、`@Operation` 注解完善 Swagger 文档
8. **编写单元测试** → 基于 JUnit 5 + Mockito，覆盖核心业务逻辑

### Controller 标准写法

```java
/**
 * 管理后台 - 示例模块 Controller
 */
@Tag(name = "管理后台 - 示例")
@RestController
@RequestMapping("/demo/example")
@Validated
public class ExampleController {

    @Resource
    private ExampleService exampleService;

    /**
     * 创建示例
     */
    @PostMapping("/create")
    @Operation(summary = "创建示例")
    @PreAuthorize("@ss.hasPermission('demo:example:create')")
    public CommonResult<Long> createExample(@RequestBody @Valid ExampleCreateReqVO reqVO) {
        return success(exampleService.createExample(reqVO));
    }

    /**
     * 分页查询示例列表
     */
    @GetMapping("/page")
    @Operation(summary = "获得示例分页")
    @PreAuthorize("@ss.hasPermission('demo:example:query')")
    public CommonResult<PageResult<ExampleRespVO>> getExamplePage(
            @Valid ExamplePageReqVO pageVO) {
        PageResult<ExampleDO> pageResult = exampleService.getExamplePage(pageVO);
        return success(ExampleConvert.INSTANCE.convertPage(pageResult));
    }
}
```

### Service 标准写法

```java
/**
 * 示例 Service 实现类
 *
 * <p>包含完整的参数校验、异常抛出规范，可作为新模块开发参考</p>
 */
@Service
@Validated
public class ExampleServiceImpl implements ExampleService {

    @Resource
    private ExampleMapper exampleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createExample(ExampleCreateReqVO reqVO) {
        // 1. 校验名称是否已存在（唯一性校验）
        validateExampleNameUnique(null, reqVO.getName());

        // 2. 将 VO 转换为 DO 并插入数据库
        ExampleDO example = ExampleConvert.INSTANCE.convert(reqVO);
        exampleMapper.insert(example);

        // 3. 返回新记录的主键 ID
        return example.getId();
    }

    /**
     * 校验示例名称的唯一性
     *
     * @param id   待排除的记录 ID（更新时传入，新增时传 null）
     * @param name 待校验的名称
     */
    private void validateExampleNameUnique(Long id, String name) {
        ExampleDO example = exampleMapper.selectByName(name);
        if (example == null) {
            return; // 名称不存在，校验通过
        }
        // 更新场景：若查到的记录就是自身，则允许
        if (example.getId().equals(id)) {
            return;
        }
        throw exception(EXAMPLE_NAME_EXISTS);
    }
}
```

---

## 多租户开发注意事项

- 所有 Mapper 查询会被 `TenantLineInnerInterceptor` 自动拼接 `tenant_id` 条件，**无需手动过滤**
- 若某个表不需要租户隔离，在 `TenantProperties#ignoreTables` 中配置白名单
- 系统级别（跨租户）操作，使用 `TenantUtils.executeIgnore()` 包裹：

```java
// 忽略租户隔离，查询所有租户的数据（仅超级管理员可调用）
TenantUtils.executeIgnore(() -> {
    List<TenantDO> allTenants = tenantMapper.selectList();
    // ... 处理逻辑
});
```

---

## 权限控制规范

- 接口权限通过 `@PreAuthorize("@ss.hasPermission('模块:资源:操作')")` 声明
- 数据权限通过 `@DataPermission` 注解自动过滤，支持本人/部门/全部等维度
- 操作日志通过 `@OperateLog` 注解自动记录，指定 `type` 区分增删改查

```java
// 权限 + 数据权限 + 操作日志的组合示例
@DeleteMapping("/delete")
@Operation(summary = "删除用户")
@Parameter(name = "id", description = "用户 ID", required = true)
@PreAuthorize("@ss.hasPermission('system:user:delete')")
@OperateLog(type = DELETE)
public CommonResult<Boolean> deleteUser(@RequestParam("id") Long id) {
    userService.deleteUser(id);
    return success(true);
}
```

---

## 单元测试规范

所有核心 Service 方法必须覆盖单元测试，使用 `BaseDbAndRedisUnitTest` 或 `BaseDbUnitTest` 基类：

```java
/**
 * {@link ExampleServiceImpl} 的单元测试
 *
 * <p>测试目标：验证创建、更新、查询、删除等核心方法的正确性</p>
 */
@Import(ExampleServiceImpl.class)
public class ExampleServiceImplTest extends BaseDbUnitTest {

    @Autowired
    private ExampleService exampleService;

    @Autowired
    private ExampleMapper exampleMapper;

    @Test
    public void testCreateExample_success() {
        // ① 准备请求参数
        ExampleCreateReqVO reqVO = randomPojo(ExampleCreateReqVO.class);

        // ② 调用被测方法
        Long exampleId = exampleService.createExample(reqVO);

        // ③ 断言数据库记录与入参一致
        ExampleDO example = exampleMapper.selectById(exampleId);
        assertPojoEquals(reqVO, example);
    }

    @Test
    public void testCreateExample_nameDuplicate() {
        // ① 先插入一条已存在的记录
        ExampleDO existExample = randomPojo(ExampleDO.class);
        exampleMapper.insert(existExample);

        // ② 用相同名称再次创建，期望抛出异常
        ExampleCreateReqVO reqVO = randomPojo(ExampleCreateReqVO.class,
                o -> o.setName(existExample.getName())); // 重复名称
        assertServiceException(
                () -> exampleService.createExample(reqVO),
                EXAMPLE_NAME_EXISTS
        );
    }
}
```

---

## 常见命令

```bash
# 后端编译（跳过测试）
mvn clean package -DskipTests

# 后端运行（开发环境配置文件）
java -jar yudao-server/target/yudao-server.jar --spring.profiles.active=dev

# 前端安装依赖（Vue3 版本）
cd yudao-ui-admin-vue3 && pnpm install

# 前端启动开发服务器
pnpm dev

# 运行单元测试
mvn test -pl yudao-module-system/yudao-module-system-biz
```

---

## 参考资料

- **官方文档**：https://doc.iocoder.cn/
- **快速启动**：https://doc.iocoder.cn/quick-start/
- **视频教程**：https://doc.iocoder.cn/video/
- **演示环境（Vue3）**：http://dashboard-vue3.yudao.iocoder.cn
- **Gitee 仓库**：https://gitee.com/zhijiantianya/ruoyi-vue-pro

---

## 给 Claude 的附加说明

1. **代码生成时默认加注释**：Claude 在生成任何 Java 代码时，必须为类、方法、复杂逻辑添加必要注释，不得省略
2. **遵循现有包结构**：新增文件时严格遵循 `controller → service → dal → convert → enums` 的分层结构
3. **使用项目已有工具类**：优先使用 `yudao-framework` 中封装的工具（如 `RedisUtils`、`NumberUtils`、`ArrayUtils` 等），不要引入外部库
4. **异常统一用 `exception()`**：不要直接 `throw new RuntimeException()`，一律调用 `ServiceExceptionUtil.exception(errorCode)` 抛出业务异常
5. **返回值统一用 `CommonResult`**：Controller 层所有接口返回类型必须是 `CommonResult<T>`，用 `success()` 或 `error()` 包装
6. **涉及多租户改动时提醒确认**：若修改涉及 `tenant_id` 相关逻辑，需明确提示开发者确认是否影响租户隔离
7. **字典/状态枚举的主动处理**：当用户要求处理状态或字典类型字段时，按以下步骤执行：① 若用户未提供枚举值，**先询问所有取值及中文名称再动手**；② 确认后同时创建枚举类（`enums/XxxEnum.java`）、追加字典 SQL（`curtain.sql` 末尾）、更新 DO 字段 Javadoc；③ grep 全项目中该字段的所有硬编码字符串，逐一替换为 `枚举.name()`，并为对应 Service 文件补充 import；④ 如有 `switch/case` 或 `if-else` 做 label 映射，改为 `枚举.valueOf(code).getLabel()` + try-catch 兜底。枚举类模板：`@Getter @AllArgsConstructor public enum XxxEnum { VALUE("中文"); private final String label; }`；字典 SQL 使用 `system_dict_type`（id ≥ 2301）和 `system_dict_data`（id ≥ 3700），颜色参考：默认/初始=info、进行中/主要=primary、警告/中间态=warning、完成/成功=success。
8. **操作日志（@LogRecord）规范**：ZC 模块所有写操作（增删改、状态变更、库存操作等）必须加操作日志。步骤如下：
   - **① 在 `LogRecordConstants` 定义常量**（位于 `yudao-module-curtain/.../enums/LogRecordConstants.java`），每个操作需定义 `_SUB_TYPE`（子类型中文名）和 `_SUCCESS`（成功模板，支持 SpEL 表达式）两个常量，挂在对应资源的分组注释下。
   - **② 在 Service 实现方法上加 `@LogRecord` 注解**，参数：`type`（资源大类，如 `ZC_SALES_ORDER_MATERIAL_TYPE`）、`subType`（操作子类型常量）、`bizNo`（业务主键，用 SpEL 取方法参数，如 `"{{#reqVO.id}}"`）、`success`（成功模板常量）。
   - **③ 将模板中用到的非方法参数变量放入 `LogRecordContext`**：在方法执行成功后（通常在方法末尾）调用 `LogRecordContext.putVariable("变量名", 值)`，确保异常回滚时不会产生虚假日志。方法入参可直接在模板用 `{{#参数名.字段}}`，无需放入 Context。
   - **常量命名模板**：`ZC_{资源}_TYPE`、`ZC_{资源}_{操作}_SUB_TYPE`、`ZC_{资源}_{操作}_SUCCESS`，操作词：`CREATE/UPDATE/DELETE/CONFIRM/CANCEL_CONFIRM/MARK_EXPEDITED/CUT/CANCEL_CUT` 等。
   - **成功模板示例**：`"裁剪了用料明细【{{#reqVO.id}}】，批次【{{#batchNo}}】，裁剪数量 {{#reqVO.cutQuantity}}"`；diff 场景用 `{_DIFF{#updateReqVO}}`，需配合 `LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, oldObj)`。
   - **注意**：`@LogRecord` 加在 Service 实现类方法上，不加在 Controller 上；`@Transactional` 与 `@LogRecord` 可同时存在，顺序不限。