package cn.iocoder.yudao.module.zc.framework.operatelog.core;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.module.zc.dal.dataobject.product.ZcProductDO;
import cn.iocoder.yudao.module.zc.service.product.ZcProductService;
import com.mzt.logapi.service.IParseFunction;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

/**
 * ZC 产品的 {@link IParseFunction} 实现类
 */
@Component
public class ZcProductParseFunction implements IParseFunction {

    public static final String NAME = "getZcProductById";

    @Resource
    private ZcProductService productService;

    @Override
    public boolean executeBefore() {
        return true;
    }

    @Override
    public String functionName() {
        return NAME;
    }

    @Override
    public String apply(Object value) {
        if (StrUtil.isEmptyIfStr(value)) {
            return "";
        }
        ZcProductDO product = productService.getProduct(Long.parseLong(value.toString()));
        return product == null ? "" : product.getName();
    }

}
