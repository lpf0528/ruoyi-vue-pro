package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import cn.iocoder.yudao.module.zc.controller.admin.processnode.vo.ZcOrderProcessRecordRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理后台 - 销售订单工序记录详情 Response VO
 *
 * <p>继承订单主表字段，以完整窗帘结构（窗帘→结构→用料）为骨架，
 * 在各层级挂载 {@code processRecords}；无工序记录的节点仍会返回。</p>
 */
@Schema(description = "管理后台 - 销售订单工序记录详情 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ZcSalesOrderProcessRecordDetailRespVO extends ZcSalesOrderRespVO {

    @Schema(description = "订单级工序记录（未关联窗帘行），按创建时间升序")
    private List<ZcOrderProcessRecordRespVO> orderRecords = new ArrayList<>();

    @Schema(description = "窗帘行列表（完整订单结构 + 各层工序记录）")
    private List<ZcSalesOrderCurtainProcessRecordRespVO> curtains = new ArrayList<>();

}
