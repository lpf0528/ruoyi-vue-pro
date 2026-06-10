package cn.iocoder.yudao.module.zc.service.barcoderegistry;

import cn.hutool.core.util.IdUtil;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.zc.controller.admin.barcoderegistry.vo.ZcBarcodeRegistryCreateReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.barcoderegistry.ZcBarcodeRegistryDO;
import cn.iocoder.yudao.module.zc.dal.mysql.barcoderegistry.ZcBarcodeRegistryMapper;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.starter.annotation.LogRecord;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.iocoder.yudao.module.zc.enums.LogRecordConstants.*;

/**
 * 码注册表 Service 实现类
 *
 * @author 智仓
 */
@Service
@Validated
public class ZcBarcodeRegistryServiceImpl implements ZcBarcodeRegistryService {

    @Resource
    private ZcBarcodeRegistryMapper barcodeRegistryMapper;

    @Override
    @LogRecord(type = ZC_BARCODE_REGISTRY_TYPE, subType = ZC_BARCODE_REGISTRY_CREATE_SUB_TYPE,
            bizNo = "{{#registry.id}}", success = ZC_BARCODE_REGISTRY_CREATE_SUCCESS)
    public String createBarcodeRegistry(ZcBarcodeRegistryCreateReqVO createReqVO) {
        ZcBarcodeRegistryDO registry = BeanUtils.toBean(createReqVO, ZcBarcodeRegistryDO.class);
        // 服务端生成全局唯一的 UUID 作为 codeId，确保二维码不重复
        registry.setCodeId(IdUtil.fastSimpleUUID());
        barcodeRegistryMapper.insert(registry);
        // 记录操作日志上下文
        LogRecordContext.putVariable("registry", registry);
        return registry.getCodeId();
    }

    @Override
    public ZcBarcodeRegistryDO getBarcodeRegistry(String codeId) {
        return barcodeRegistryMapper.selectByCodeId(codeId);
    }

}
