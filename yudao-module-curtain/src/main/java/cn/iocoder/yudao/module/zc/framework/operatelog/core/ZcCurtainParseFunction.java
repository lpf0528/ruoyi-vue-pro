package cn.iocoder.yudao.module.zc.framework.operatelog.core;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtain.ZcCurtainDO;
import cn.iocoder.yudao.module.zc.service.curtain.ZcCurtainService;
import com.mzt.logapi.service.IParseFunction;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * ZC 窗帘款式的 {@link IParseFunction} 实现类
 */
@Component
public class ZcCurtainParseFunction implements IParseFunction {

    public static final String NAME = "getZcCurtainById";

    @Resource
    private ZcCurtainService curtainService;

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
        ZcCurtainDO curtain = curtainService.getCurtain(Long.parseLong(value.toString()));
        return curtain == null ? "" : curtain.getName();
    }

}
