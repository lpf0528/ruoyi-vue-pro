package cn.iocoder.yudao.module.zc.framework.operatelog.core;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.module.zc.dal.dataobject.warehouse.ZcWarehouseDO;
import cn.iocoder.yudao.module.zc.service.warehouse.ZcWarehouseService;
import com.mzt.logapi.service.IParseFunction;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

/**
 * ZC 仓库的 {@link IParseFunction} 实现类
 */
@Component
public class ZcWarehouseParseFunction implements IParseFunction {

    public static final String NAME = "getZcWarehouseById";

    @Resource
    private ZcWarehouseService warehouseService;

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
        ZcWarehouseDO warehouse = warehouseService.getWarehouse(Long.parseLong(value.toString()));
        return warehouse == null ? "" : warehouse.getName();
    }

}
