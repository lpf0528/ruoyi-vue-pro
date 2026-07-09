package cn.iocoder.yudao.module.zc.framework.operatelog.core;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.module.zc.dal.dataobject.logistics.ZcLogisticsDO;
import cn.iocoder.yudao.module.zc.service.logistics.ZcLogisticsService;
import com.mzt.logapi.service.IParseFunction;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * ZC 物流公司的 {@link IParseFunction} 实现类
 */
@Component
public class ZcLogisticsParseFunction implements IParseFunction {

    public static final String NAME = "getZcLogisticsById";

    @Resource
    private ZcLogisticsService logisticsService;

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
        ZcLogisticsDO logistics = logisticsService.getLogistics(Long.parseLong(value.toString()));
        return logistics == null ? "" : logistics.getName();
    }

}
