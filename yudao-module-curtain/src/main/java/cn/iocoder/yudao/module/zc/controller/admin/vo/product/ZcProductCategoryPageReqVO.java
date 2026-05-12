package cn.iocoder.yudao.module.zc.controller.admin.vo.product;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ZcProductCategoryPageReqVO extends PageParam {

    private String value;

}
