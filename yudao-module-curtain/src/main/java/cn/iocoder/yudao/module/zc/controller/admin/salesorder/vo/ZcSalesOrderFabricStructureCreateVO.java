package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import javax.validation.Valid;

/**
 * 管理后台 - 面单结构行（嵌套创建）VO
 */
@Schema(description = "管理后台 - 面单结构行（嵌套创建）VO")
@Data
public class ZcSalesOrderFabricStructureCreateVO {

    /** 用料明细列表 */
    @Schema(description = "用料明细列表")
    @Valid
    private List<ZCSalesOrderMaterialCreateVO> materials;

}
