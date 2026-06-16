package cn.iocoder.yudao.module.zc.controller.admin.productversion.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 产品版本规格分页 Request VO")
@Data
public class ZcProductVersionSpcPageReqVO extends PageParam {

    @Schema(description = "版本编号，不传则查全部", example = "1024")
    private Long versionId;

    @Schema(description = "规格名称")
    private String spec;

}
