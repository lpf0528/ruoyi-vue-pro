package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import cn.iocoder.yudao.module.zc.controller.admin.processnode.vo.ZcOrderProcessRecordRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 销售订单工序详情 - 用料明细分组（含工序记录）
 */
@Schema(description = "销售订单工序详情 - 用料明细分组")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ZcSalesOrderMaterialProcessRecordRespVO extends ZCSalesOrderMaterialDetailRespVO {

    @Schema(description = "用料级工序记录，按创建时间升序")
    private List<ZcOrderProcessRecordRespVO> processRecords = new ArrayList<>();

}
