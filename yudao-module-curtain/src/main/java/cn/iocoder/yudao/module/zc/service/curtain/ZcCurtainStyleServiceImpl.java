package cn.iocoder.yudao.module.zc.service.curtain;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.zc.dal.mysql.curtain.ZcCurtainStyleMapper;
import cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainStylePageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainStyleSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtain.ZcCurtainStyleDO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service
@Validated
public class ZcCurtainStyleServiceImpl implements ZcCurtainStyleService {

    @Resource
    private ZcCurtainStyleMapper curtainStyleMapper;

    @Override
    public Long create(ZcCurtainStyleSaveReqVO reqVO) {
        ZcCurtainStyleDO d = BeanUtils.toBean(reqVO, ZcCurtainStyleDO.class);
        curtainStyleMapper.insert(d);
        return d.getId();
    }

    @Override
    public void update(ZcCurtainStyleSaveReqVO reqVO) {
        validate(reqVO.getId());
        curtainStyleMapper.updateById(BeanUtils.toBean(reqVO, ZcCurtainStyleDO.class));
    }

    @Override
    public void delete(Long id) {
        validate(id);
        curtainStyleMapper.deleteById(id);
    }

    @Override
    public ZcCurtainStyleDO get(Long id) {
        return curtainStyleMapper.selectById(id);
    }

    @Override
    public PageResult<ZcCurtainStyleDO> getPage(ZcCurtainStylePageReqVO pageReqVO) {
        return curtainStyleMapper.selectPage(pageReqVO, new LambdaQueryWrapperX<ZcCurtainStyleDO>()
                .likeIfPresent(ZcCurtainStyleDO::getName, pageReqVO.getName())
                .eqIfPresent(ZcCurtainStyleDO::getSeriesId, pageReqVO.getSeriesId())
                .orderByDesc(ZcCurtainStyleDO::getId));
    }

    private void validate(Long id) {
        if (id == null || curtainStyleMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.CURTAIN_STYLE_NOT_EXISTS);
        }
    }

}
