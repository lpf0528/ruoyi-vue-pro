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
import cn.iocoder.yudao.module.zc.dal.mysql.curtainstructureelement.ZcCurtainStructureElementMapper;


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
    @Resource
    private ZcCurtainStructureElementMapper curtainStructureElementMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCurtainTemplate(ZcCurtainTemplateSaveReqVO saveReqVO) {
        curtainTemplateMapper.deleteByCurtainId(saveReqVO.getCurtainId());
        List<ZcCurtainTemplateDO> list = new ArrayList<>();
        for (ZcCurtainTemplateSaveReqVO.StructureItem structure : saveReqVO.getStructures()) {
            for (Long elementId : structure.getElementIds()) {
                list.add(ZcCurtainTemplateDO.builder()
                        .curtainId(saveReqVO.getCurtainId())
                        .structureId(structure.getStructureId())
                        .elementId(elementId)
                        .build());
            }
        }
        curtainTemplateMapper.insertBatch(list);
    }

    @Override
    public ZcCurtainTemplateGetRespVO getCurtainTemplateByCurtainId(Long curtainId) {
        List<ZcCurtainTemplateDO> list = curtainTemplateMapper.selectByCurtainId(curtainId);

        List<Long> elementIds = list.stream()
                .map(ZcCurtainTemplateDO::getElementId)
                .distinct()
                .collect(Collectors.toList());
        Map<Long, Long> elementVersionMap = new HashMap<>();
        if (!elementIds.isEmpty()) {
            curtainStructureElementMapper.selectBatchIds(elementIds)
                    .forEach(e -> elementVersionMap.put(e.getId(), e.getVersionId()));
        }

        Map<Long, List<ZcCurtainTemplateDO>> structureMap = list.stream()
                .collect(Collectors.groupingBy(ZcCurtainTemplateDO::getStructureId));
        List<ZcCurtainTemplateGetRespVO.StructureItem> structures = structureMap.entrySet().stream()
                .map(entry -> {
                    ZcCurtainTemplateGetRespVO.StructureItem item = new ZcCurtainTemplateGetRespVO.StructureItem();
                    item.setStructureId(entry.getKey());
                    item.setElementIds(entry.getValue().stream()
                            .map(t -> {
                                ZcCurtainTemplateGetRespVO.ElementItem ei = new ZcCurtainTemplateGetRespVO.ElementItem();
                                ei.setElementId(t.getElementId());
                                ei.setVersionId(elementVersionMap.get(t.getElementId()));
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
