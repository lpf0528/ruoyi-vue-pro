package cn.iocoder.yudao.module.zc.service.base;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.zc.controller.admin.vo.base.ZcPaymentPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.base.ZcPaymentSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.base.ZcPaymentDO;
import cn.iocoder.yudao.module.zc.dal.mysql.base.ZcPaymentMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service
@Validated
public class ZcPaymentServiceImpl implements ZcPaymentService {

    @Resource
    private ZcPaymentMapper paymentMapper;

    @Override
    public Long create(ZcPaymentSaveReqVO reqVO) {
        ZcPaymentDO d = BeanUtils.toBean(reqVO, ZcPaymentDO.class);
        paymentMapper.insert(d);
        return d.getId();
    }

    @Override
    public void update(ZcPaymentSaveReqVO reqVO) {
        validate(reqVO.getId());
        paymentMapper.updateById(BeanUtils.toBean(reqVO, ZcPaymentDO.class));
    }

    @Override
    public void delete(Long id) {
        validate(id);
        paymentMapper.deleteById(id);
    }

    @Override
    public ZcPaymentDO get(Long id) {
        return paymentMapper.selectById(id);
    }

    @Override
    public PageResult<ZcPaymentDO> getPage(ZcPaymentPageReqVO pageReqVO) {
        return paymentMapper.selectPage(pageReqVO, new LambdaQueryWrapperX<ZcPaymentDO>()
                .likeIfPresent(ZcPaymentDO::getName, pageReqVO.getName())
                .orderByDesc(ZcPaymentDO::getId));
    }

    private void validate(Long id) {
        if (id == null || paymentMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.PAYMENT_NOT_EXISTS);
        }
    }

}
