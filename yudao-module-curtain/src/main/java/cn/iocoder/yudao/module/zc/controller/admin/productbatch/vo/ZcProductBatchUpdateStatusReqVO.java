package cn.iocoder.yudao.module.zc.controller.admin.productbatch.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Schema(description = "管理后台 - 产品批次更新状态 Request VO")
@Data
public class ZcProductBatchUpdateStatusReqVO {

    @Schema(description = "批次编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "批次编号不能为空")
    private Long id;

    @Schema(description = "状态: 1:整匹、0:零裁、-1:余料", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态不能为空")
    private Integer status;

}
