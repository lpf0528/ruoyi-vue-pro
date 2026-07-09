package cn.iocoder.yudao.module.zc.framework.operatelog.core;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.module.zc.dal.dataobject.brand.ZcBrandDO;
import cn.iocoder.yudao.module.zc.service.brand.ZcBrandService;
import com.mzt.logapi.service.IParseFunction;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * ZC 品牌的 {@link IParseFunction} 实现类
 */
@Component
public class ZcBrandParseFunction implements IParseFunction {

    public static final String NAME = "getZcBrandById";

    @Resource
    private ZcBrandService brandService;

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
        ZcBrandDO brand = brandService.getBrand(Long.parseLong(value.toString()));
        return brand == null ? "" : brand.getName();
    }

}
