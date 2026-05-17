package cn.iocoder.yudao.module.zc.service.curtaintemplate;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
    public ZcCurtainTemplateDO getCurtainTemplate(Long id) {
        return curtainTemplateMapper.selectById(id);
    }

}
