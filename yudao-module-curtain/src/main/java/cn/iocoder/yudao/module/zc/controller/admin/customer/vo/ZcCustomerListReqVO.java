package cn.iocoder.yudao.module.zc.controller.admin.customer.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "管理后台 - 客户资料列表 Request VO")
@Data
@Accessors(chain = true)
public class ZcCustomerListReqVO {

    @Schema(description = "简称", example = "张三")
    private String shortName;

}
