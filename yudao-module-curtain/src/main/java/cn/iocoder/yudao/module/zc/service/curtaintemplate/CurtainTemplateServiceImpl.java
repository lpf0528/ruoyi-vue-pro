package cn.iocoder.yudao.module.zc.service.curtaintemplate;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.curtaintemplate.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtaintemplate.CurtainTemplateDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.curtaintemplate.CurtainTemplateMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;

/**
 * 窗帘模板 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class CurtainTemplateServiceImpl implements CurtainTemplateService {

    @Resource
    private CurtainTemplateMapper curtainTemplateMapper;

    @Override
    public Long createCurtainTemplate(CurtainTemplateSaveReqVO createReqVO) {
        // 插入
        CurtainTemplateDO curtainTemplate = BeanUtils.toBean(createReqVO, CurtainTemplateDO.class);
        curtainTemplateMapper.insert(curtainTemplate);

        // 返回
        return curtainTemplate.getId();
    }

    @Override
    public void updateCurtainTemplate(CurtainTemplateSaveReqVO updateReqVO) {
        // 校验存在
        validateCurtainTemplateExists(updateReqVO.getId());
        // 更新
        CurtainTemplateDO updateObj = BeanUtils.toBean(updateReqVO, CurtainTemplateDO.class);
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
    public CurtainTemplateDO getCurtainTemplate(Long id) {
        return curtainTemplateMapper.selectById(id);
    }

    @Override
    public PageResult<CurtainTemplateDO> getCurtainTemplatePage(CurtainTemplatePageReqVO pageReqVO) {
        return curtainTemplateMapper.selectPage(pageReqVO);
    }

}