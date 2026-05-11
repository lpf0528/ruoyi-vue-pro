package cn.iocoder.yudao.module.zc.service.base;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.zc.controller.admin.vo.base.ZcCustomerPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.base.ZcCustomerSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.base.ZcCustomerDO;
import cn.iocoder.yudao.module.zc.dal.mysql.base.ZcCustomerMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants;

import javax.annotation.Resource;
import java.math.BigDecimal;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service
@Validated
public class ZcCustomerServiceImpl implements ZcCustomerService {

    @Resource
    private ZcCustomerMapper customerMapper;

    @Override
    public Long createCustomer(ZcCustomerSaveReqVO createReqVO) {
        ZcCustomerDO d = BeanUtils.toBean(createReqVO, ZcCustomerDO.class);
        d.setBalance(BigDecimal.ZERO);
        customerMapper.insert(d);
        return d.getId();
    }

    @Override
    public void updateCustomer(ZcCustomerSaveReqVO updateReqVO) {
        ZcCustomerDO old = customerMapper.selectById(updateReqVO.getId());
        if (old == null) {
            throw exception(ErrorCodeConstants.CUSTOMER_NOT_EXISTS);
        }
        ZcCustomerDO d = BeanUtils.toBean(updateReqVO, ZcCustomerDO.class);
        d.setBalance(old.getBalance());
        customerMapper.updateById(d);
    }

    @Override
    public void deleteCustomer(Long id) {
        if (customerMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.CUSTOMER_NOT_EXISTS);
        }
        customerMapper.deleteById(id);
    }

    @Override
    public ZcCustomerDO getCustomer(Long id) {
        return customerMapper.selectById(id);
    }

    @Override
    public PageResult<ZcCustomerDO> getCustomerPage(ZcCustomerPageReqVO pageReqVO) {
        return customerMapper.selectPage(pageReqVO, new LambdaQueryWrapperX<ZcCustomerDO>()
                .likeIfPresent(ZcCustomerDO::getName, pageReqVO.getName())
                .likeIfPresent(ZcCustomerDO::getMobile, pageReqVO.getMobile())
                .orderByDesc(ZcCustomerDO::getId));
    }

}
