package cn.iocoder.yudao.module.zc.controller.admin.vo.finance;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ZcCollectionPageReqVO extends PageParam {

    @Schema(description = "客户编号")
    private Long customerId;

}
