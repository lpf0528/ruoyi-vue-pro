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
    public ZcCurtainTemplateSaveReqVO getCurtainTemplateByCurtainId(Long curtainId) {
        List<ZcCurtainTemplateDO> list = curtainTemplateMapper.selectByCurtainId(curtainId);
        ZcCurtainTemplateSaveReqVO result = new ZcCurtainTemplateSaveReqVO();
        result.setCurtainId(curtainId);
        Map<Long, List<Long>> structureElementMap = list.stream()
                .collect(Collectors.groupingBy(
                        ZcCurtainTemplateDO::getStructureId,
                        Collectors.mapping(ZcCurtainTemplateDO::getElementId, Collectors.toList())
                ));
        List<ZcCurtainTemplateSaveReqVO.StructureItem> structures = structureElementMap.entrySet().stream()
                .map(entry -> {
                    ZcCurtainTemplateSaveReqVO.StructureItem item = new ZcCurtainTemplateSaveReqVO.StructureItem();
                    item.setStructureId(entry.getKey());
                    item.setElementIds(entry.getValue());
                    return item;
                })
                .collect(Collectors.toList());
        result.setStructures(structures);
        return result;
    }

}
