package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import cn.iocoder.yudao.module.zc.controller.admin.processnode.vo.ZcOrderProcessRecordRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 销售订单工序详情 - 结构行分组（含工序记录）
 */
@Schema(description = "销售订单工序详情 - 结构行分组")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ZcSalesOrderStructureProcessRecordRespVO extends ZcSalesOrderStructureRespVO {

    @Schema(description = "结构名称")
    private String structureName;

    @Schema(description = "安装工艺名称")
    private String installProcessName;

    @Schema(description = "结构级工序记录，按创建时间升序")
    private List<ZcOrderProcessRecordRespVO> processRecords = new ArrayList<>();

    @Schema(description = "用料明细列表（含工序记录）")
    private List<ZcSalesOrderMaterialProcessRecordRespVO> materials = new ArrayList<>();

}
