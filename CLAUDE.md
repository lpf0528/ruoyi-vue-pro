# CLAUDE.md — ruoyi-vue-pro 项目指南

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