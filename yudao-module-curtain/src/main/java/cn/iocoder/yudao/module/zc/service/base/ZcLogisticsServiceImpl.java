package cn.iocoder.yudao.module.zc.service.base;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.zc.controller.admin.vo.base.ZcLogisticsPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.base.ZcLogisticsSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.base.ZcLogisticsDO;
import cn.iocoder.yudao.module.zc.dal.mysql.base.ZcLogisticsMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service
@Validated
public class ZcLogisticsServiceImpl implements ZcLogisticsService {

    @Resource
    private ZcLogisticsMapper logisticsMapper;

    @Override
    public Long create(ZcLogisticsSaveReqVO reqVO) {
        ZcLogisticsDO d = BeanUtils.toBean(reqVO, ZcLogisticsDO.class);
        logisticsMapper.insert(d);
        return d.getId();
    }

    @Override
    public void update(ZcLogisticsSaveReqVO reqVO) {
        validate(reqVO.getId());
        logisticsMapper.updateById(BeanUtils.toBean(reqVO, ZcLogisticsDO.class));
    }

    @Override
    public void delete(Long id) {
        validate(id);
        logisticsMapper.deleteById(id);
    }

    @Override
    public ZcLogisticsDO get(Long id) {
        return logisticsMapper.selectById(id);
    }

    @Override
    public PageResult<ZcLogisticsDO> getPage(ZcLogisticsPageReqVO pageReqVO) {
        return logisticsMapper.selectPage(pageReqVO, new LambdaQueryWrapperX<ZcLogisticsDO>()
                .likeIfPresent(ZcLogisticsDO::getName, pageReqVO.getName())
                .likeIfPresent(ZcLogisticsDO::getCode, pageReqVO.getCode())
                .orderByDesc(ZcLogisticsDO::getId));
    }

    private void validate(Long id) {
        if (id == null || logisticsMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.LOGISTICS_NOT_EXISTS);
        }
    }

}
