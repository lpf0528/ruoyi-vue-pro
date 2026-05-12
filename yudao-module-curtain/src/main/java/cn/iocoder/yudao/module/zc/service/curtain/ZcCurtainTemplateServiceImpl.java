package cn.iocoder.yudao.module.zc.service.curtain;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.zc.dal.mysql.curtain.ZcCurtainTemplateMapper;
import cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainTemplatePageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainTemplateSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtain.ZcCurtainTemplateDO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service
@Validated
public class ZcCurtainTemplateServiceImpl implements ZcCurtainTemplateService {

    @Resource
    private ZcCurtainTemplateMapper curtainTemplateMapper;

    @Override
    public Long create(ZcCurtainTemplateSaveReqVO reqVO) {
        ZcCurtainTemplateDO d = BeanUtils.toBean(reqVO, ZcCurtainTemplateDO.class);
        curtainTemplateMapper.insert(d);
        return d.getId();
    }

    @Override
    public void update(ZcCurtainTemplateSaveReqVO reqVO) {
        validate(reqVO.getId());
        curtainTemplateMapper.updateById(BeanUtils.toBean(reqVO, ZcCurtainTemplateDO.class));
    }

    @Override
    public void delete(Long id) {
        validate(id);
        curtainTemplateMapper.deleteById(id);
    }

    @Override
    public ZcCurtainTemplateDO get(Long id) {
        return curtainTemplateMapper.selectById(id);
    }

    @Override
    public PageResult<ZcCurtainTemplateDO> getPage(ZcCurtainTemplatePageReqVO pageReqVO) {
        return curtainTemplateMapper.selectPage(pageReqVO, new LambdaQueryWrapperX<ZcCurtainTemplateDO>()
                .eqIfPresent(ZcCurtainTemplateDO::getCurtainId, pageReqVO.getCurtainId())
                .eqIfPresent(ZcCurtainTemplateDO::getStructureId, pageReqVO.getStructureId())
                .orderByDesc(ZcCurtainTemplateDO::getId));
    }

    private void validate(Long id) {
        if (id == null || curtainTemplateMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.CURTAIN_TEMPLATE_NOT_EXISTS);
        }
    }

}
