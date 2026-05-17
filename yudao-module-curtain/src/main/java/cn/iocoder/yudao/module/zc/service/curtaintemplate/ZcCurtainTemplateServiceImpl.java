package cn.iocoder.yudao.module.zc.service.curtaintemplate;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.curtaintemplate.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtaintemplate.ZcCurtainTemplateDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.curtaintemplate.ZcCurtainTemplateMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;

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
    public Long createCurtainTemplate(ZcCurtainTemplateSaveReqVO createReqVO) {
        // 插入
        ZcCurtainTemplateDO curtainTemplate = BeanUtils.toBean(createReqVO, ZcCurtainTemplateDO.class);
        curtainTemplateMapper.insert(curtainTemplate);

        // 返回
        return curtainTemplate.getId();
    }

    @Override
    public void updateCurtainTemplate(ZcCurtainTemplateSaveReqVO updateReqVO) {
        // 校验存在
        validateCurtainTemplateExists(updateReqVO.getId());
        // 更新
        ZcCurtainTemplateDO updateObj = BeanUtils.toBean(updateReqVO, ZcCurtainTemplateDO.class);
        curtainTemplateMapper.updateById(updateObj);
    }

    @Override
    public void deleteCurtainTemplate(Long id) {
        // 校验存在
        validateCurtainTemplateExists(id);
        // 删除
        curtainTemplateMapper.deleteById(id);
    }

    @Override
        public void deleteCurtainTemplateListByIds(List<Long> ids) {
        // 删除
        curtainTemplateMapper.deleteByIds(ids);
        }


    private void validateCurtainTemplateExists(Long id) {
        if (curtainTemplateMapper.selectById(id) == null) {
            throw exception(CURTAIN_TEMPLATE_NOT_EXISTS);
        }
    }

    @Override
    public ZcCurtainTemplateDO getCurtainTemplate(Long id) {
        return curtainTemplateMapper.selectById(id);
    }

    @Override
    public PageResult<ZcCurtainTemplateRespVO> getCurtainTemplatePage(ZcCurtainTemplatePageReqVO pageReqVO) {
        return curtainTemplateMapper.selectPage(pageReqVO);
    }

}