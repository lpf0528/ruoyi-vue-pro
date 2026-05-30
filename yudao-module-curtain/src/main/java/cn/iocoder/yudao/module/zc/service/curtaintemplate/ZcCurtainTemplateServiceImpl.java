package cn.iocoder.yudao.module.zc.service.curtaintemplate;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import cn.iocoder.yudao.module.zc.controller.admin.curtaintemplate.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtaintemplate.ZcCurtainTemplateDO;
import cn.iocoder.yudao.module.zc.dal.mysql.curtaintemplate.ZcCurtainTemplateMapper;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.starter.annotation.LogRecord;

import static cn.iocoder.yudao.module.zc.enums.LogRecordConstants.*;

/**
 * 窗帘模板 Service 实现类
 *
 * @author 01Coder
 */
@Service
@Validated
public class ZcCurtainTemplateServiceImpl implements ZcCurtainTemplateService {

    @Resource
    private ZcCurtainTemplateMapper curtainTemplateMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = ZC_CURTAIN_TEMPLATE_TYPE, subType = ZC_CURTAIN_TEMPLATE_SAVE_SUB_TYPE, bizNo = "{{#curtainId}}",
            success = ZC_CURTAIN_TEMPLATE_SAVE_SUCCESS)
    public void saveCurtainTemplate(ZcCurtainTemplateSaveReqVO saveReqVO) {
        // 全量替换：先按 curtainId 清空旧记录，再批量插入新记录
        // productId 允许为空，模板未指定产品时直接落库 null
        curtainTemplateMapper.deleteByCurtainId(saveReqVO.getCurtainId());
        List<ZcCurtainTemplateDO> list = new ArrayList<>();
        for (ZcCurtainTemplateSaveReqVO.StructureItem structure : saveReqVO.getStructures()) {
            for (ZcCurtainTemplateSaveReqVO.ElementItem element : structure.getElements()) {
                list.add(ZcCurtainTemplateDO.builder()
                        .curtainId(saveReqVO.getCurtainId())
                        .structureId(structure.getStructureId())
                        .elementId(element.getElementId())
                        .productId(element.getProductId())
                        .build());
            }
        }
        curtainTemplateMapper.insertBatch(list);
        // 记录操作日志上下文
        LogRecordContext.putVariable("curtainId", saveReqVO.getCurtainId());
    }

    @Override
    public ZcCurtainTemplateGetRespVO getCurtainTemplateByCurtainId(Long curtainId) {
        // JOIN 查询一次拿到模板行及产品名称
        List<ZcCurtainTemplateDO> list = curtainTemplateMapper.selectByCurtainIdWithProductName(curtainId);

        Map<Long, List<ZcCurtainTemplateDO>> structureMap = list.stream()
                .collect(Collectors.groupingBy(ZcCurtainTemplateDO::getStructureId));
        List<ZcCurtainTemplateGetRespVO.StructureItem> structures = structureMap.entrySet().stream()
                .map(entry -> {
                    ZcCurtainTemplateGetRespVO.StructureItem item = new ZcCurtainTemplateGetRespVO.StructureItem();
                    item.setStructureId(entry.getKey());
                    item.setElements(entry.getValue().stream()
                            .map(t -> {
                                ZcCurtainTemplateGetRespVO.ElementItem ei = new ZcCurtainTemplateGetRespVO.ElementItem();
                                ei.setElementId(t.getElementId());
                                ei.setProductId(t.getProductId());
                                ei.setProductName(t.getProductName());
                                ei.setOnePrice(t.getOnePrice());
                                return ei;
                            })
                            .collect(Collectors.toList()));
                    return item;
                })
                .collect(Collectors.toList());

        ZcCurtainTemplateGetRespVO result = new ZcCurtainTemplateGetRespVO();
        result.setCurtainId(curtainId);
        result.setStructures(structures);
        return result;
    }

}
