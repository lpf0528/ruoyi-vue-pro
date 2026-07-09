package cn.iocoder.yudao.module.zc.framework.operatelog.core;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.module.zc.dal.dataobject.supplier.ZcSupplierDO;
import cn.iocoder.yudao.module.zc.service.supplier.ZcSupplierService;
import com.mzt.logapi.service.IParseFunction;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

/**
 * ZC 供应商的 {@link IParseFunction} 实现类
 */
@Component
public class ZcSupplierParseFunction implements IParseFunction {

    public static final String NAME = "getZcSupplierById";

    @Resource
    private ZcSupplierService supplierService;

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
        ZcSupplierDO supplier = supplierService.getSupplier(Long.parseLong(value.toString()));
        return supplier == null ? "" : supplier.getName();
    }

}
