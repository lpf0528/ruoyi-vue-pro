# 修复更新接口 null 字段不生效问题

修复 ruoyi-vue-pro 中 `updateById` 默认跳过 null 字段，导致可选外键（如 `categoryId`、`supplierId`）无法被清空的问题。

## 问题根因

MyBatis-Plus 的 `updateById` 默认字段策略是 `NOT_NULL`：字段值为 null 时不生成对应的 `SET` SQL，导致"清空某字段"的操作静默失败。

## 使用方式

告诉我：
1. 目标模块或 Controller 名称（如 `ZcProductVersion`）
2. 哪些字段在更新时需要支持置为 null（通常是可选的外键字段，如 `categoryId`、`supplierId`）

## 执行步骤

### 第 1 步 — 定位 DO 文件

根据模块名找到对应的 DataObject，路径规律：

```
dal/dataobject/{module}/{XxxDO}.java
```

### 第 2 步 — 读取 DO，确认目标字段

并行读取：
- `XxxDO.java` — 确认字段名和类型
- `XxxSaveReqVO.java` — 确认哪些字段是可选的（无 `@NotNull` / `@NotEmpty`）

可选字段（VO 中无校验注解）且为引用类型（`Long`、`String`、`BigDecimal` 等）的，都是潜在需要修复的对象。

### 第 3 步 — 在 DO 目标字段上添加注解

```java
// 修复前
private Long categoryId;

// 修复后
@TableField(updateStrategy = FieldStrategy.ALWAYS)
private Long categoryId;
```

> **注意**：本项目使用 MyBatis-Plus 3.5.16，该版本中 `IGNORED` 已不存在，必须使用 `FieldStrategy.ALWAYS`。
> `FieldStrategy` 已由 `com.baomidou.mybatisplus.annotation.*` 覆盖，无需单独 import。

对所有需要支持置 null 的字段重复此操作。

### 第 4 步 — 确认 Service 无需改动

`ServiceImpl` 中的标准更新写法无需修改：

```java
ZcXxxDO updateObj = BeanUtils.toBean(updateReqVO, ZcXxxDO.class);
xxxMapper.updateById(updateObj);
```

`FieldStrategy.ALWAYS` 在 DO 层生效，Service 层无感知。

## 涉及文件清单

```
dal/dataobject/{module}/XxxDO.java    ← 在目标字段上添加 @TableField(updateStrategy = FieldStrategy.ALWAYS)
```

## 验证方法

修复后，调用更新接口时将 `categoryId` 或 `supplierId` 设为 null，观察数据库中对应字段是否被清空为 NULL。
