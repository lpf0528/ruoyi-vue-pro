# 分页查询接口改造为 JOIN 多表查询

将一个已有的 ruoyi-vue-pro 分页接口，从单表 LambdaQueryWrapperX 查询改造为 MyBatis XML 多表 JOIN 查询，并将关联字段输出到 RespVO。

## 使用方式

告诉我：
1. 目标 Controller 路径或模块名（如 `ZcProductVersion`）
2. 需要 JOIN 的表及要输出的字段，例如：
   ```sql
   LEFT JOIN zc_product_category t2 ON t1.category_id = t2.id  -- 输出 t2.value AS category_value
   LEFT JOIN zc_supplier t3 ON t1.supplier_id = t3.id          -- 输出 t3.name AS supplier_name
   ```

## 执行步骤

### 第 1 步 — 读取现有文件，理解结构

先并行读取以下 5 个文件，再动手修改：

| 文件 | 目的 |
|------|------|
| `RespVO.java` | 确认现有字段，决定在哪里追加新字段 |
| `Mapper.java` | 确认现有 `selectPage` 的过滤条件，后续要在 XML 里复现 |
| `Mapper.xml` | 确认是否为空模板 |
| `Service.java`（接口） | 确认 `getXxxPage` 的返回类型 |
| `ServiceImpl.java` | 确认 `getXxxPage` 的实现 |
| `Controller.java` | 确认 page/export-excel 端点如何调用 Service 和转换 VO |

### 第 2 步 — 修改 RespVO

在 `note` 字段之后、`createTime` 字段之前追加关联字段：

```java
@Schema(description = "类别名称")
@ExcelProperty("类别名称")
private String categoryValue;

@Schema(description = "供应商名称")
@ExcelProperty("供应商名称")
private String supplierName;
```

> 字段名用驼峰（`categoryValue`、`supplierName`），MyBatis 的 `map-underscore-to-camel-case=true` 会自动将 SQL 别名 `category_value`、`supplier_name` 映射过来。

### 第 3 步 — 改造 Mapper.java

用以下结构替换原来的 `default selectPage` 方法：

```java
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

// XML 绑定方法（供 MyBatis 调用）
IPage<XxxRespVO> selectPageWithVO(IPage<?> page, @Param("reqVO") XxxPageReqVO reqVO);

// 对外统一入口，封装 IPage → PageResult 的转换
default PageResult<XxxRespVO> selectPage(XxxPageReqVO reqVO) {
    IPage<XxxRespVO> result = selectPageWithVO(
            new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), reqVO);
    return new PageResult<>(result.getRecords(), result.getTotal());
}
```

> **关键**：`IPage<XxxRespVO>` 作为第一个参数时，MyBatis Plus 分页插件会自动注入 `LIMIT/OFFSET` 并执行 COUNT 查询。

### 第 4 步 — 编写 Mapper.xml

```xml
<select id="selectPageWithVO"
        resultType="cn.iocoder.yudao.module.zc.controller.admin.xxx.vo.XxxRespVO">
    SELECT
        t2.value  AS category_value,
        t3.name   AS supplier_name,
        t1.id, t1.name, t1.unit_value, t1.category_id, t1.selling_price_type,
        t1.inbound_price, t1.one_price, t1.classify, t1.supplier_id, t1.note,
        t1.create_time, t1.update_time, t1.creator, t1.updater
    FROM zc_xxx t1
    LEFT JOIN zc_product_category t2 ON t1.category_id = t2.id AND t2.deleted = 0
    LEFT JOIN zc_supplier t3        ON t1.supplier_id  = t3.id AND t3.deleted = 0
    WHERE t1.deleted = 0
    <if test="reqVO.name != null and reqVO.name != ''">
        AND t1.name LIKE CONCAT('%', #{reqVO.name}, '%')
    </if>
    <if test="reqVO.unitValue != null and reqVO.unitValue != ''">
        AND t1.unit_value = #{reqVO.unitValue}
    </if>
    <if test="reqVO.categoryId != null">
        AND t1.category_id = #{reqVO.categoryId}
    </if>
    <if test="reqVO.sellingPriceType != null and reqVO.sellingPriceType != ''">
        AND t1.selling_price_type = #{reqVO.sellingPriceType}
    </if>
    <if test="reqVO.classify != null">
        AND t1.classify = #{reqVO.classify}
    </if>
    <if test="reqVO.supplierId != null">
        AND t1.supplier_id = #{reqVO.supplierId}
    </if>
    <if test="reqVO.createTime != null and reqVO.createTime.length == 2">
        AND t1.create_time BETWEEN #{reqVO.createTime[0]} AND #{reqVO.createTime[1]}
    </if>
    ORDER BY t1.id DESC
</select>
```

**注意事项：**
- `t1.deleted = 0` 需要显式写出（XML 自定义查询中框架的逻辑删除拦截器对多表别名支持有限）
- `tenant_id` 由 MyBatis Plus 租户插件自动注入主表，无需手动添加
- JOIN 的从表加 `AND t2.deleted = 0` 过滤已删除数据

### 第 5 步 — 更新 Service 接口

```java
// 改为返回 RespVO，不再返回 DO
PageResult<XxxRespVO> getXxxPage(XxxPageReqVO pageReqVO);
```

### 第 6 步 — 更新 ServiceImpl

```java
@Override
public PageResult<XxxRespVO> getXxxPage(XxxPageReqVO pageReqVO) {
    return xxxMapper.selectPage(pageReqVO);
}
```

### 第 7 步 — 更新 Controller

**page 端点** — 去掉 `BeanUtils.toBean`：
```java
// 改前
PageResult<XxxDO> pageResult = xxxService.getXxxPage(pageReqVO);
return success(BeanUtils.toBean(pageResult, XxxRespVO.class));

// 改后
return success(xxxService.getXxxPage(pageReqVO));
```

**export-excel 端点** — 直接使用 RespVO list：
```java
// 改前
List<XxxDO> list = xxxService.getXxxPage(pageReqVO).getList();
ExcelUtils.write(response, "xxx.xls", "数据", XxxRespVO.class,
        BeanUtils.toBean(list, XxxRespVO.class));

// 改后
List<XxxRespVO> list = xxxService.getXxxPage(pageReqVO).getList();
ExcelUtils.write(response, "xxx.xls", "数据", XxxRespVO.class, list);
```

> `Controller` 中 `/get` 单条查询端点仍返回 DO 再 toBean，无需改动，`ZcXxxDO` 的 import 可以保留。

## 涉及文件清单

```
controller/admin/xxx/vo/XxxRespVO.java          ← 追加关联字段
dal/mysql/xxx/XxxMapper.java                    ← 替换为 XML 方法 + 封装 default
resources/mapper/xxx/XxxMapper.xml              ← 编写 JOIN SQL
service/xxx/XxxService.java                     ← 返回类型改为 RespVO
service/xxx/XxxServiceImpl.java                 ← 返回类型改为 RespVO
controller/admin/xxx/XxxController.java         ← 去掉 BeanUtils.toBean 转换
```
