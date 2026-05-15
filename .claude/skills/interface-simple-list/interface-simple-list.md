# interface-simple-list

为指定模块快速生成 `GET /simple-list` 精简列表接口，专为前端下拉筛选场景服务。

---

## 触发方式

用户说"给 XXX 模块生成 simple-list 接口"或"仿照 XXX 生成精简列表"时使用本 skill。

---

## 接口定位

`/simple-list` 是**无权限校验**的精简查询接口，专为前端下拉选项/筛选器服务：
- 只返回下拉所需的**最少字段**（id + 名称字段，如有 status 也返回）
- 无分页，返回全量 List
- 不加 `@PreAuthorize`（登录即可访问）
- 若实体有 `status` 字段，则固定只返回启用状态（`CommonStatusEnum.ENABLE`）数据；**若实体无 status 字段，不新增，返回全量数据**

### 与 `/list` 接口的区别

| 维度 | `/list` | `/simple-list` |
|------|---------|----------------|
| 权限 | 需要 query 权限 | 无需权限 |
| 返回字段 | 完整 RespVO | 仅下拉所需关键字段 |
| 状态过滤 | 支持自定义条件 | 有 status 则过滤启用；无则全量 |
| 用途 | 列表页展示 | 下拉选择器 / 筛选器 |

---

## 生成前的字段确认规则（必须执行）

**每次生成前都必须执行以下步骤，不得跳过：**

1. 读取实体 DO 文件，列出所有字段
2. 排除基础字段（`createTime`、`updateTime`、`creator`、`updater`、`deleted`、`tenantId`）
3. 将剩余业务字段全部列出，向用户提问：

> 当前实体业务字段如下，请确认 simple-list 接口需要返回哪些字段？
>
> | 字段名 | 类型 | 说明 |
> |--------|------|------|
> | id | Long | 主键 |
> | xxx | String | ... |
> | ...（列出所有业务字段）|
>
> 典型组合参考：
> - 最简：`id` + 名称字段（`name` / `value` / `title` 等）
> - 含状态：`id` + 名称 + `status`（仅当 DO 本身有 status 时）
> - 树形结构：`id` + 名称 + `parentId`

4. 等待用户确认字段列表后，再生成代码

---

## 代码生成步骤

### 第一步：确认 ListReqVO 是否存在

若已有 `ListReqVO`，确认其是否有 `status` 字段（实体有 status 时需要）。
若不存在，新建 `{模块}ListReqVO`，加 `@Accessors(chain = true)` 支持链式调用：

```java
@Schema(description = "管理后台 - {中文名称}列表 Request VO")
@Data
@Accessors(chain = true)
public class {模块}ListReqVO {

    @Schema(description = "名称字段")
    private String {nameField};

    // 仅当 DO 有 status 字段时添加
    @Schema(description = "开启状态", example = "1")
    private Integer status;
}
```

### 第二步：确认 Mapper 有 selectList 方法

若已有 `selectList(ListReqVO)`，检查是否有 `eqIfPresent(status)` 条件（DO 有 status 时需要）。
若不存在，新增：

```java
default List<{模块}DO> selectList({模块}ListReqVO reqVO) {
    return selectList(new LambdaQueryWrapperX<{模块}DO>()
            .eqIfPresent({模块}DO::{nameField}, reqVO.get{NameField}())
            // 仅当 DO 有 status 时加此行：
            .eqIfPresent({模块}DO::getStatus, reqVO.getStatus())
            .orderByDesc({模块}DO::getId));
}
```

### 第三步：Service 接口 + 实现（通常需新增）

Service 接口添加：
```java
List<{模块}DO> get{Entity}List({模块}ListReqVO listReqVO);
```

ServiceImpl 实现：
```java
@Override
public List<{模块}DO> get{Entity}List({模块}ListReqVO listReqVO) {
    return {mapper}.selectList(listReqVO);
}
```

### 第四步：新建专用 SimpleRespVO

**必须**为 simple-list 创建独立的精简 VO，不复用完整 RespVO（复用会导致未赋值字段以 `null` 出现在响应中）。

文件命名：`{模块}SimpleRespVO.java`，加 `@Accessors(chain = true)` 支持链式 set：

```java
@Schema(description = "管理后台 - {中文名称}精简 Response VO")
@Data
@Accessors(chain = true)
public class {模块}SimpleRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "{名称字段说明}", requiredMode = Schema.RequiredMode.REQUIRED)
    private {Type} {nameField};

    // 按用户确认的字段继续添加...
}
```

### 第五步：Controller 层新增 simple-list 方法

```java
@GetMapping("/simple-list")
@Operation(summary = "获得{中文名称}精简列表", description = "主要用于前端的下拉选项")
public CommonResult<List<{模块}SimpleRespVO>> get{Entity}SimpleList() {
    // 有 status 时：
    List<{模块}DO> list = {service}.get{Entity}List(
            new {模块}ListReqVO().setStatus(CommonStatusEnum.ENABLE.getStatus()));
    // 无 status 时：
    // List<{模块}DO> list = {service}.get{Entity}List(new {模块}ListReqVO());

    return success(convertList(list, item -> new {模块}SimpleRespVO()
            .setId(item.getId())
            .set{NameField}(item.get{NameField}())
            // 按确认的字段继续链式 set...
    ));
}
```

**关键点：**
- 无 `@PreAuthorize`
- 返回类型用 `{模块}SimpleRespVO`，不用完整 `RespVO`
- 用 `convertList` 做字段投影，不用 `BeanUtils.toBean`
- 需静态导入 `cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList`

---

## 完整示例参考（ErpProductCategory）

```java
// DO 有 status + parentId（树形结构）的典型写法
@GetMapping("/simple-list")
@Operation(summary = "获得产品分类精简列表", description = "只包含被开启的分类，主要用于前端的下拉选项")
public CommonResult<List<ErpProductCategoryRespVO>> getProductCategorySimpleList() {
    List<ErpProductCategoryDO> list = productCategoryService.getProductCategoryList(
            new ErpProductCategoryListReqVO().setStatus(CommonStatusEnum.ENABLE.getStatus()));
    return success(convertList(list, category -> new ErpProductCategoryRespVO()
            .setId(category.getId()).setName(category.getName()).setParentId(category.getParentId())));
}
```

参考文件：
- `yudao-module-erp/.../ErpProductCategoryController.java`（第 80-87 行）
- `yudao-module-erp/.../ErpProductCategoryMapper.java`
- `yudao-module-erp/.../ErpProductCategoryService.java`

---

## 注意事项

- **不要为了 simple-list 给实体新增 status 字段**，status 是业务属性，应在需求层面决定
- `convertList` 静态导入：`cn.iocoder.yudao.framework.common.util.collection.CollectionUtils`
- `CommonStatusEnum.ENABLE.getStatus()` 返回 `1`（整型）
- simple-list 不新增 Service/Mapper 方法以外的业务逻辑
- 若实体是树形结构（有 `parentId`），精简 VO 中必须包含 `parentId`，前端才能构建树
