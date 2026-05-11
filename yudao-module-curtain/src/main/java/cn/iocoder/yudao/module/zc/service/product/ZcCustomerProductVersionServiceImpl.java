package cn.iocoder.yudao.module.zc.service.product;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcCustomerProductVersionPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcCustomerProductVersionSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.product.ZcCustomerProductVersionDO;
import cn.iocoder.yudao.module.zc.dal.mysql.product.ZcCustomerProductVersionMapper;
import cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service
@Validated
public class ZcCustomerProductVersionServiceImpl implements ZcCustomerProductVersionService {

    @Resource
    private ZcCustomerProductVersionMapper customerProductVersionMapper;

    @Override
    public Long create(ZcCustomerProductVersionSaveReqVO reqVO) {
        ZcCustomerProductVersionDO d = BeanUtils.toBean(reqVO, ZcCustomerProductVersionDO.class);
        customerProductVersionMapper.insert(d);
        return d.getId();
    }

    @Override
    public void update(ZcCustomerProductVersionSaveReqVO reqVO) {
        validate(reqVO.getId());
        customerProductVersionMapper.updateById(BeanUtils.toBean(reqVO, ZcCustomerProductVersionDO.class));
    }

    @Override
    public void delete(Long id) {
        validate(id);
        customerProductVersionMapper.deleteById(id);
    }

    @Override
    public ZcCustomerProductVersionDO get(Long id) {
        return customerProductVersionMapper.selectById(id);
    }

    @Override
    public PageResult<ZcCustomerProductVersionDO> getPage(ZcCustomerProductVersionPageReqVO pageReqVO) {
        return customerProductVersionMapper.selectPage(pageReqVO, new LambdaQueryWrapperX<ZcCustomerProductVersionDO>()
                .eqIfPresent(ZcCustomerProductVersionDO::getCustomerId, pageReqVO.getCustomerId())
                .eqIfPresent(ZcCustomerProductVersionDO::getProductVersionId, pageReqVO.getProductVersionId())
                .orderByDesc(ZcCustomerProductVersionDO::getId));
    }

    private void validate(Long id) {
        if (id == null || customerProductVersionMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.CUSTOMER_PRODUCT_VERSION_NOT_EXISTS);
        }
    }

}
