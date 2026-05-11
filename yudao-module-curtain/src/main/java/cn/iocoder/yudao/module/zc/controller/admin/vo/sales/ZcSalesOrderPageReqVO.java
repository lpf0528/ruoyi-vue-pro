package cn.iocoder.yudao.module.zc.controller.admin.vo.sales;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "管理后台 - 智仓销售订单分页")
@Data
@EqualsAndHashCode(callSuper = true)
public class ZcSalesOrderPageReqVO extends PageParam {

    private String orderNo;
    private Long customerId;
    private String types;
    private String payStatus;
    private String confirmStatus;

}
