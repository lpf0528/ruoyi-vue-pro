package cn.iocoder.yudao.module.zc.service.barcoderegistry;

import cn.hutool.core.util.IdUtil;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.zc.controller.admin.barcoderegistry.vo.ZcBarcodeRegistryCreateReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.barcoderegistry.ZcBarcodeRegistryDO;
import cn.iocoder.yudao.module.zc.dal.mysql.barcoderegistry.ZcBarcodeRegistryMapper;
import cn.iocoder.yudao.module.zc.framework.util.ContentHashUtil;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.starter.annotation.LogRecord;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;

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
        // 1. 计算 code_content 规范化指纹，用于去重查询
        String contentHash = ContentHashUtil.hash(createReqVO.getCodeContent());

        // 2. 先查询是否已存在相同记录（同一租户下 codeType + targetRoute + contentHash 联合唯一）
        ZcBarcodeRegistryDO existing = barcodeRegistryMapper.selectByDedup(
                createReqVO.getCodeType(), createReqVO.getTargetRoute(), contentHash);
        if (existing != null) {
            LogRecordContext.putVariable("registry", existing);
            return existing.getCodeId();
        }

        // 3. 不存在则新建记录
        ZcBarcodeRegistryDO registry = BeanUtils.toBean(createReqVO, ZcBarcodeRegistryDO.class);
        registry.setCodeId(IdUtil.fastSimpleUUID());
        registry.setContentHash(contentHash);

        try {
            barcodeRegistryMapper.insert(registry);
        } catch (DuplicateKeyException e) {
            // 并发场景下唯一索引冲突，重新查询返回已有记录
            ZcBarcodeRegistryDO concurrent = barcodeRegistryMapper.selectByDedup(
                    createReqVO.getCodeType(), createReqVO.getTargetRoute(), contentHash);
            LogRecordContext.putVariable("registry", concurrent);
            return concurrent.getCodeId();
        }

        LogRecordContext.putVariable("registry", registry);
        return registry.getCodeId();
    }

    @Override
    public ZcBarcodeRegistryDO getBarcodeRegistry(String codeId) {
        return barcodeRegistryMapper.selectByCodeId(codeId);
    }

}
