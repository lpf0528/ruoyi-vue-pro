package cn.iocoder.yudao.module.zc.service.curtain;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.zc.dal.mysql.curtain.ZcCurtainPleatRatioMapper;
import cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainPleatRatioPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainPleatRatioSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtain.ZcCurtainPleatRatioDO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service
@Validated
public class ZcCurtainPleatRatioServiceImpl implements ZcCurtainPleatRatioService {

    @Resource
    private ZcCurtainPleatRatioMapper curtainPleatRatioMapper;

    @Override
    public Long create(ZcCurtainPleatRatioSaveReqVO reqVO) {
        ZcCurtainPleatRatioDO d = BeanUtils.toBean(reqVO, ZcCurtainPleatRatioDO.class);
        curtainPleatRatioMapper.insert(d);
        return d.getId();
    }

    @Override
    public void update(ZcCurtainPleatRatioSaveReqVO reqVO) {
        validate(reqVO.getId());
        curtainPleatRatioMapper.updateById(BeanUtils.toBean(reqVO, ZcCurtainPleatRatioDO.class));
    }

    @Override
    public void delete(Long id) {
        validate(id);
        curtainPleatRatioMapper.deleteById(id);
    }

    @Override
    public ZcCurtainPleatRatioDO get(Long id) {
        return curtainPleatRatioMapper.selectById(id);
    }

    @Override
    public PageResult<ZcCurtainPleatRatioDO> getPage(ZcCurtainPleatRatioPageReqVO pageReqVO) {
        return curtainPleatRatioMapper.selectPage(pageReqVO, new LambdaQueryWrapperX<ZcCurtainPleatRatioDO>()
                .orderByAsc(ZcCurtainPleatRatioDO::getRank)
                .orderByDesc(ZcCurtainPleatRatioDO::getId));
    }

    private void validate(Long id) {
        if (id == null || curtainPleatRatioMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.CURTAIN_PLEAT_RATIO_NOT_EXISTS);
        }
    }

}
