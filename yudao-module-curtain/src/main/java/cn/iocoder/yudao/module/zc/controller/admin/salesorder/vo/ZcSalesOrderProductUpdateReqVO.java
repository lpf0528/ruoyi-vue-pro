package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 管理后台 - 产品类销售订单整单更新 Request VO
 *
 * <p>在创建请求（{@link ZcSalesOrderProductCreateReqVO}）的基础上增加订单 ID，
 * 整单更新：订单主记录覆盖写入，产品行先全量删除再重新插入，保持与创建接口相同的整单风格。</p>
 *
 * @author 01Coder
 */
@Schema(description = "管理后台 - 产品类销售订单整单更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class ZcSalesOrderProductUpdateReqVO extends ZcSalesOrderProductCreateReqVO {

    /** 订单 ID，必填 */
    @Schema(description = "订单 ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "订单 ID 不能为空")
    private Long id;

}
