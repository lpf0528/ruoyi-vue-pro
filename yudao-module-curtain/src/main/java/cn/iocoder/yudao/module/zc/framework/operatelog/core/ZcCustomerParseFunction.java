package cn.iocoder.yudao.module.zc.framework.operatelog.core;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.module.zc.dal.dataobject.customer.ZcCustomerDO;
import cn.iocoder.yudao.module.zc.service.customer.ZcCustomerService;
import com.mzt.logapi.service.IParseFunction;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * ZC 客户的 {@link IParseFunction} 实现类
 */
@Component
public class ZcCustomerParseFunction implements IParseFunction {

    public static final String NAME = "getZcCustomerById";

    @Resource
    private ZcCustomerService customerService;

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
        ZcCustomerDO customer = customerService.getCustomer(Long.parseLong(value.toString()));
        return customer == null ? "" : customer.getShortName();
    }

}
