package cn.iocoder.yudao.module.zc.service.product;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcCustomerProductPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcCustomerProductSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.product.ZcCustomerProductDO;
import cn.iocoder.yudao.module.zc.dal.mysql.product.ZcCustomerProductMapper;
import cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service
@Validated
public class ZcCustomerProductServiceImpl implements ZcCustomerProductService {

    @Resource
    private ZcCustomerProductMapper customerProductMapper;

    @Override
    public Long create(ZcCustomerProductSaveReqVO reqVO) {
        ZcCustomerProductDO d = BeanUtils.toBean(reqVO, ZcCustomerProductDO.class);
        customerProductMapper.insert(d);
        return d.getId();
    }

    @Override
    public void update(ZcCustomerProductSaveReqVO reqVO) {
        validate(reqVO.getId());
        customerProductMapper.updateById(BeanUtils.toBean(reqVO, ZcCustomerProductDO.class));
    }

    @Override
    public void delete(Long id) {
        validate(id);
        customerProductMapper.deleteById(id);
    }

    @Override
    public ZcCustomerProductDO get(Long id) {
        return customerProductMapper.selectById(id);
    }

    @Override
    public PageResult<ZcCustomerProductDO> getPage(ZcCustomerProductPageReqVO pageReqVO) {
        return customerProductMapper.selectPage(pageReqVO, new LambdaQueryWrapperX<ZcCustomerProductDO>()
                .eqIfPresent(ZcCustomerProductDO::getCustomerId, pageReqVO.getCustomerId())
                .eqIfPresent(ZcCustomerProductDO::getProductId, pageReqVO.getProductId())
                .orderByDesc(ZcCustomerProductDO::getId));
    }

    private void validate(Long id) {
        if (id == null || customerProductMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.CUSTOMER_PRODUCT_NOT_EXISTS);
        }
    }

}
