# interface-simple-list

为指定模块生成 `GET /simple-list` 精简列表接口（前端下拉专用，无权限校验）。

**核心规则：** 实体有 `status` 字段 → 过滤启用状态（`CommonStatusEnum.ENABLE`）；无 `status` → 全量返回，不新增该字段。

---

## 执行流程

### Step 1：读 DO + 确认字段

用 Glob 从传入的 Controller 路径推断模块路径，找到对应 DO 文件读取。
排除基础字段（`createTime/updateTime/creator/updater/deleted/tenantId`），列出剩余业务字段，用 `AskUserQuestion`（multiSelect）让用户勾选 simple-list 需要返回的字段。**等待用户确认后再继续。**

### Step 2：并行读取现有文件

同时读取：`Mapper`、`Service` 接口、`ServiceImpl`、`vo/` 目录（Glob），判断 `ListReqVO`、`selectList`、`getXxxList` 是否已存在，按需生成。

### Step 3：生成代码（5处）

**① ListReqVO**（`vo/` 目录，若已存在则检查是否需补 status 字段）

```java
@Schema(description = "管理后台 - {中文名}列表 Request VO")
@Data
@Accessors(chain = true)
public class {Prefix}ListReqVO {
    // DO 有 status 时添加：
    // @Schema(description = "状态", example = "1")
    // private Integer status;
}
```

**② Mapper**（追加到现有 `}` 前）

```java
default List<{Prefix}DO> selectList({Prefix}ListReqVO reqVO) {
    return selectList(new LambdaQueryWrapperX<{Prefix}DO>()
            // DO 有 status 时添加：.eqIfPresent({Prefix}DO::getStatus, reqVO.getStatus())
            .orderByDesc({Prefix}DO::getId));
}
```

**③ Service 接口**（追加到 `}` 前）

```java
List<{Prefix}DO> get{Entity}List({Prefix}ListReqVO listReqVO);
```

**④ ServiceImpl**（追加到最后一个 `@Override` 方法后）

```java
@Override
public List<{Prefix}DO> get{Entity}List({Prefix}ListReqVO listReqVO) {
    return {mapperField}.selectList(listReqVO);
}
```

**⑤ SimpleRespVO**（`vo/` 目录新建）

```java
@Schema(description = "管理后台 - {中文名}精简 Response VO")
@Data
@Accessors(chain = true)
public class {Prefix}SimpleRespVO {
    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;
    // 按用户确认的字段逐一添加（含类型、@Schema）
}
```

**⑥ Controller**（在 `export-excel` 方法前插入；同时补 `convertList` 静态导入）

```java
@GetMapping("/simple-list")
@Operation(summary = "获得{中文名}精简列表", description = "主要用于前端的下拉选项")
public CommonResult<List<{Prefix}SimpleRespVO>> get{Entity}SimpleList() {
    List<{Prefix}DO> list = {serviceField}.get{Entity}List(
            new {Prefix}ListReqVO()/* DO 有 status 时追加：.setStatus(CommonStatusEnum.ENABLE.getStatus()) */);
    return success(convertList(list, item -> new {Prefix}SimpleRespVO()
            .setId(item.getId())
            // 按确认字段链式：.setXxx(item.getXxx())
    ));
}
```

`convertList` 静态导入：`cn.iocoder.yudao.framework.common.util.collection.CollectionUtils`

---

## 禁止事项

- 不加 `@PreAuthorize`
- 禁止复用完整 `RespVO`（未赋值字段会以 `null` 返回）
- 禁止用 `BeanUtils.toBean`，必须用 `convertList` 投影
- 不为 simple-list 新增 `status` 字段
- 树形结构（含 `parentId`）的 SimpleRespVO 必须包含 `parentId`
