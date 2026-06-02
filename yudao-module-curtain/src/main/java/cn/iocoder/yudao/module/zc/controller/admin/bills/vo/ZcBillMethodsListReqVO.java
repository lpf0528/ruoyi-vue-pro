package cn.iocoder.yudao.module.zc.controller.admin.bills.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 管理后台 - 收款方式列表 Request VO
 *
 * <p>用于 simple-list 下拉接口，无分页、无状态过滤（DO 无 status 字段）</p>
 */
@Schema(description = "管理后台 - 收款方式列表 Request VO")
@Data
@Accessors(chain = true)
public class ZcBillMethodsListReqVO {

}
