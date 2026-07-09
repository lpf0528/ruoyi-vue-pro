package cn.iocoder.yudao.module.zc.controller.admin.inventoryrecord.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 盘点记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ZcInventoryRecordRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "8627")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "产品", requiredMode = Schema.RequiredMode.REQUIRED, example = "9127")
    @ExcelProperty("产品")
    private Long productId;

    @Schema(description = "批次", requiredMode = Schema.RequiredMode.REQUIRED, example = "8051")
    @ExcelProperty("批次")
    private Long batchId;

    @Schema(description = "盘点前数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("盘点前数量")
    private BigDecimal oldQuantity;

    @Schema(description = "盘点后数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("盘点后数量")
    private BigDecimal newQuantity;

    @Schema(description = "盘点差值")
    @ExcelProperty("盘点差值")
    private BigDecimal diffQuantity;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String note;

    @Schema(description = "产品名称")
    @ExcelProperty("产品名称")
    private String productName;

    @Schema(description = "批次号")
    @ExcelProperty("批次号")
    private String batchNo;

    @Schema(description = "仓库名称")
    @ExcelProperty("仓库名称")
    private String warehouseName;

    @Schema(description = "仓库ID")
    @ExcelProperty("仓库ID")
    private Long warehouseId;

    @Schema(description = "版本名称")
    @ExcelProperty("版本名称")
    private String versionName;

    @Schema(description = "版本ID")
    @ExcelProperty("版本ID")
    private Long versionId;

    @Schema(description = "操作人昵称")
    @ExcelProperty("操作人昵称")
    private String nickname;

    @Schema(description = "创建者")
    @ExcelProperty("创建者")
    private String creator;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createTime;

    @Schema(description = "变化数量（old_quantity - new_quantity），正数表示减少，负数表示增加")
    @ExcelProperty("变化数量")
    private BigDecimal changeQuantity;

    @Schema(description = "操作类型：PANDIAN/盘点、RUKU/入库、CAIJIAN/裁剪、CANCEL_CAIJIAN/撤销裁剪")
    @ExcelProperty("操作类型")
    private String operate;

    @Schema(description = "关联订单ID，裁剪/撤销裁剪时记录来源订单")
    private Long orderId;

}