package cn.iocoder.yudao.module.zc.framework.operatelog.core;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.module.zc.dal.dataobject.processnode.ZcProcessNodeDO;
import cn.iocoder.yudao.module.zc.service.processnode.ZcProcessNodeService;
import com.mzt.logapi.service.IParseFunction;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * ZC 工序节点的 {@link IParseFunction} 实现类
 */
@Component
public class ZcProcessNodeParseFunction implements IParseFunction {

    public static final String NAME = "getZcProcessNodeById";

    @Resource
    private ZcProcessNodeService processNodeService;

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
        ZcProcessNodeDO processNode = processNodeService.getProcessNode(Long.parseLong(value.toString()));
        return processNode == null ? "" : processNode.getName();
    }

}
