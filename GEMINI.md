# Project Overview: ruoyi-vue-pro (芋道)

> **语言要求：永远使用中文回复用户，不得使用其他语言。 (Language Requirement: Always reply in Chinese, never use other languages.)**

This workspace contains the backend codebase for **ruoyi-vue-pro (芋道)**, a rapid development platform completely open source (MIT license) based on a deep refactoring of RuoYi-Vue.

## Architecture & Technologies
- **Backend:** Java (JDK 8 on `master`, JDK 17/21 on `master-jdk17`), Spring Boot (2.7.x / 3.2.x), MyBatis Plus 3.5.x, Redis (Redisson), Spring Security + Token.
- **Frontend:** Vue 3 (element-plus / vben) & Vue 2 (element-ui) & uni-app (APP/H5/Mini-program).
- **Build Tool:** Maven.
- **Features:** RBAC, SaaS Multi-tenant, Flowable Workflow, Mall, ERP, CRM, AI, IoT, etc.

## Module Structure

- `yudao-dependencies`: Centralized dependency version management (BOM).
- `yudao-framework`: Core framework encapsulations (Security, MVC, Redis, MQ).
- `yudao-server`: Main application launcher.
- `yudao-module-system`: System features (Users, Roles, Menus, Tenants, Dictionaries).
- `yudao-module-infra`: Infrastructure (Code generation, Jobs, Files, MQ).
- `yudao-module-curtain` (ZC - 智仓): Custom business module for curtain manufacturing.
- `sql/` & `script/`: DB initialization and DevOps scripts.

### Internal Module Structure (e.g., `yudao-module-system`)
```text
yudao-module-system/
├── yudao-module-system-api/          # DTOs, Enums, Feign interfaces
└── yudao-module-system-biz/          # Business implementation
    └── src/main/java/.../
        ├── controller/admin/         # Admin REST (/admin-api/**)
        ├── controller/app/           # User APP REST (/app-api/**)
        ├── service/                  # Business logic
        ├── dal/dataobject/           # DO (Database Objects)
        ├── dal/mysql/                # MyBatis Plus Mappers
        ├── convert/                  # MapStruct converters
        └── enums/                    # Business Enums
```

## yudao-module-curtain (ZC) Module Specifications

The **ZC** (智仓) module is a core business domain for curtain manufacturing.
**Package Prefix:** `cn.iocoder.yudao.module.zc`

**Core Domains:** Product, Customer, Curtain Process (structures, pleats), Order Fulfillment, Bills, Production Processes, Basic Config (warehouse, logistics).

**Sales Order Types:**
1. **Curtain Orders (成品订单):** 4 tables, 3 layers (`ZcSalesOrder` -> `Curtain` -> `Structure` -> `Material`). Endpoint: `/zc/sales-order`. Types: `CURTAIN`.
2. **Fabric Orders nested (面单):** 3 layers, simplified VO. Endpoints: `/zc/sales-order/fabric/create`. Types: `FABRIC`.
3. **Fabric Product Orders (产品类订单):** 2 layers flat (`ZcSalesOrder` -> `ZcSalesOrderProduct`). Endpoint: `/zc/sales-order-product`. Types: `FABRIC`.

**Key Technical Details:**
- **JSON Fields:** `mountings`, `attributes`, `imageUrls` are stored as JSON using `JacksonTypeHandler`.
- **Order No Gen:** Use `ZcNoGeneratorRedisDAO` (Redis INCR) for `nextOrderSeq()`, `nextBillSeq()`, `nextBatchSeq()`.
- **Inventory/Stock:** Use atomic operations like `decreaseQuantity` / `increaseQuantity`. All inventory changes (PANDIAN, RUKU, CAIJIAN, CANCEL_CAIJIAN) must be logged to `zc_inventory_record`.

## Building and Running

> **Maven 执行方式（Windows）：** 本项目使用 IntelliJ IDEA 内置的 Maven，请用以下完整路径替代裸命令 `mvn`：
> ```
> & 'D:\Program Files\JetBrains\IntelliJ IDEA 2026.1.2\plugins\maven\lib\maven3\bin\mvn.cmd'
> ```
> **注意**：PowerShell 中路径含空格时，`&` 操作符后必须使用**单引号** `'...'`，而非双引号。

```powershell
# 编译项目并跳过测试
& 'D:\Program Files\JetBrains\IntelliJ IDEA 2026.1.2\plugins\maven\lib\maven3\bin\mvn.cmd' clean package -DskipTests

# 安装单个模块到本地仓库（解决未解析依赖问题），-am 同时安装其上游依赖
& 'D:\Program Files\JetBrains\IntelliJ IDEA 2026.1.2\plugins\maven\lib\maven3\bin\mvn.cmd' install -pl yudao-module-pay -am -DskipTests

# 安装整个项目到本地仓库（彻底解决所有 SNAPSHOT 依赖问题）
& 'D:\Program Files\JetBrains\IntelliJ IDEA 2026.1.2\plugins\maven\lib\maven3\bin\mvn.cmd' install -DskipTests

# 运行后端（Dev 环境）
java -jar yudao-server/target/yudao-server.jar --spring.profiles.active=dev

# 运行单元测试
& 'D:\Program Files\JetBrains\IntelliJ IDEA 2026.1.2\plugins\maven\lib\maven3\bin\mvn.cmd' test -pl yudao-module-system/yudao-module-system-biz
```

## Development Conventions

- **Naming Conventions:**
  - DO: `XxxDO`
  - VOs: `XxxReqVO`, `XxxRespVO`, `XxxPageReqVO`
  - Converter: `XxxConvert`
  - Mapper: `XxxMapper`
- **REST Endpoints:** `/admin-api/{module}/{resource}` and `/app-api/{module}/{resource}`.
- **Error Codes:** Defined in `ErrorCodeConstants` (format: `{module_code}-{error_id}`). Throw using `ServiceExceptionUtil.exception(errorCode)`.
- **Responses:** Always return `CommonResult<T>` via `success()` or `error()`.

### Code Comments (CRITICAL)
- **Class/Method/Field Comments:** All new code (Classes, DOs, VOs, Service interfaces) MUST have detailed Javadoc comments. Include `@author`.
- **Inline Comments:** Add block/inline comments for complex business logic, explaining *why* something is done.
- **TODO/FIXME:** Mark incomplete or sub-optimal code using `// TODO [Author] Description`.

### Multi-tenant & Permissions
- **Multi-tenant:** `TenantLineInnerInterceptor` automatically handles `tenant_id`. Use `TenantUtils.executeIgnore()` for cross-tenant operations.
- **Permissions:** Use `@PreAuthorize("@ss.hasPermission('module:resource:action')")` on controllers.

## Gemini (AI) Agent Instructions

1. **Comments Mandatory:** Generate comprehensive Javadocs and inline comments for all new/modified code.
2. **Follow Layered Architecture:** Strictly adhere to the `controller -> service -> dal -> convert -> enums` structure.
3. **Reuse Framework Utils:** Prioritize `yudao-framework` utilities (`RedisUtils`, `NumberUtils`, `ArrayUtils`) over external libraries.
4. **Exception Handling:** Never use `throw new RuntimeException()`. Always use `exception(errorCode)` with predefined codes.
5. **Enums & Dictionaries:** If user asks to handle a status/dict field:
   - If values are unknown, **ask the user** for all values/Chinese names first.
   - Create Enum (`enums/XxxEnum.java`), add Dict SQL (`system_dict_type` & `system_dict_data` usually in `curtain.sql`), and update DO Javadoc.
   - Replace any hardcoded strings with `Enum.name()` or `Enum.valueOf(code).getLabel()`.
6. **Operation Logs (`@LogRecord`):** Mandatory for all write operations in ZC module.
   - Define constants in `LogRecordConstants.java` (`_SUB_TYPE`, `_SUCCESS`).
   - Add `@LogRecord` to Service implementations (not controllers).
   - Use `LogRecordContext.putVariable` to inject non-parameter variables into SpEL templates.
