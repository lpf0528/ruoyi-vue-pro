package cn.iocoder.yudao.module.zc.service.curtain;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.zc.dal.mysql.curtain.ZcCurtainSeriesMapper;
import cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainSeriesPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainSeriesSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtain.ZcCurtainSeriesDO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service
@Validated
public class ZcCurtainSeriesServiceImpl implements ZcCurtainSeriesService {

    @Resource
    private ZcCurtainSeriesMapper curtainSeriesMapper;

    @Override
    public Long create(ZcCurtainSeriesSaveReqVO reqVO) {
        ZcCurtainSeriesDO d = BeanUtils.toBean(reqVO, ZcCurtainSeriesDO.class);
        curtainSeriesMapper.insert(d);
        return d.getId();
    }

    @Override
    public void update(ZcCurtainSeriesSaveReqVO reqVO) {
        validate(reqVO.getId());
        curtainSeriesMapper.updateById(BeanUtils.toBean(reqVO, ZcCurtainSeriesDO.class));
    }

    @Override
    public void delete(Long id) {
        validate(id);
        curtainSeriesMapper.deleteById(id);
    }

    @Override
    public ZcCurtainSeriesDO get(Long id) {
        return curtainSeriesMapper.selectById(id);
    }

    @Override
    public PageResult<ZcCurtainSeriesDO> getPage(ZcCurtainSeriesPageReqVO pageReqVO) {
        return curtainSeriesMapper.selectPage(pageReqVO, new LambdaQueryWrapperX<ZcCurtainSeriesDO>()
                .likeIfPresent(ZcCurtainSeriesDO::getName, pageReqVO.getName())
                .eqIfPresent(ZcCurtainSeriesDO::getCategory, pageReqVO.getCategory())
                .orderByDesc(ZcCurtainSeriesDO::getId));
    }

    private void validate(Long id) {
        if (id == null || curtainSeriesMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.CURTAIN_SERIES_NOT_EXISTS);
        }
    }

}
