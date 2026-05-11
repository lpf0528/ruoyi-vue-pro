package cn.iocoder.yudao.module.zc.service.progress;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.zc.controller.admin.vo.progress.ZcProgressDefinitionPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.progress.ZcProgressDefinitionSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.progress.ZcProgressDefinitionDO;
import cn.iocoder.yudao.module.zc.dal.mysql.progress.ZcProgressDefinitionMapper;
import cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service
@Validated
public class ZcProgressDefinitionServiceImpl implements ZcProgressDefinitionService {

    @Resource
    private ZcProgressDefinitionMapper progressDefinitionMapper;

    @Override
    public Long create(ZcProgressDefinitionSaveReqVO reqVO) {
        assertCodeUnique(null, reqVO.getCode());
        ZcProgressDefinitionDO d = BeanUtils.toBean(reqVO, ZcProgressDefinitionDO.class);
        if (d.getStatus() == null) {
            d.setStatus(0);
        }
        if (d.getIsMilestone() == null) {
            d.setIsMilestone(false);
        }
        if (d.getAllowRepeat() == null) {
            d.setAllowRepeat(true);
        }
        progressDefinitionMapper.insert(d);
        return d.getId();
    }

    @Override
    public void update(ZcProgressDefinitionSaveReqVO reqVO) {
        validate(reqVO.getId());
        assertCodeUnique(reqVO.getId(), reqVO.getCode());
        progressDefinitionMapper.updateById(BeanUtils.toBean(reqVO, ZcProgressDefinitionDO.class));
    }

    @Override
    public void delete(Long id) {
        validate(id);
        progressDefinitionMapper.deleteById(id);
    }

    @Override
    public ZcProgressDefinitionDO get(Long id) {
        return progressDefinitionMapper.selectById(id);
    }

    @Override
    public PageResult<ZcProgressDefinitionDO> getPage(ZcProgressDefinitionPageReqVO pageReqVO) {
        return progressDefinitionMapper.selectPage(pageReqVO, new LambdaQueryWrapperX<ZcProgressDefinitionDO>()
                .likeIfPresent(ZcProgressDefinitionDO::getCode, pageReqVO.getCode())
                .likeIfPresent(ZcProgressDefinitionDO::getName, pageReqVO.getName())
                .eqIfPresent(ZcProgressDefinitionDO::getProgressKind, pageReqVO.getProgressKind())
                .orderByAsc(ZcProgressDefinitionDO::getSort)
                .orderByDesc(ZcProgressDefinitionDO::getId));
    }

    private void validate(Long id) {
        if (id == null || progressDefinitionMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.PROGRESS_DEFINITION_NOT_EXISTS);
        }
    }

    private void assertCodeUnique(Long id, String code) {
        if (code == null) {
            return;
        }
        ZcProgressDefinitionDO exists = progressDefinitionMapper.selectOne(
                new LambdaQueryWrapperX<ZcProgressDefinitionDO>()
                        .eq(ZcProgressDefinitionDO::getCode, code));
        if (exists != null && !exists.getId().equals(id)) {
            throw exception(ErrorCodeConstants.PROGRESS_DEFINITION_CODE_DUPLICATE);
        }
    }

}
