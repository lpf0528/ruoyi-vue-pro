package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 管理后台 - 面单整单更新 Request VO
 *
 * <p>在 {@link ZcSalesOrderFabricCreateReqVO} 基础上增加订单 ID 字段。
 * 订单类型（types）由 Service 层保持不变，不允许通过此接口修改。</p>
 *
 * @author 01Coder
 */
@Schema(description = "管理后台 - 面单整单更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class ZcSalesOrderFabricUpdateReqVO extends ZcSalesOrderFabricCreateReqVO {

    /** 订单 ID，必填 */
    @Schema(description = "订单 ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "19855")
    @NotNull(message = "订单 ID 不能为空")
    private Long id;

}
