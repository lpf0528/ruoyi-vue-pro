package cn.iocoder.yudao.module.zc.service.product;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcProductPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcProductSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.product.ZcProductDO;
import cn.iocoder.yudao.module.zc.dal.mysql.product.ZcProductMapper;
import cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service
@Validated
public class ZcProductServiceImpl implements ZcProductService {

    @Resource
    private ZcProductMapper productMapper;

    @Override
    public Long create(ZcProductSaveReqVO reqVO) {
        ZcProductDO d = BeanUtils.toBean(reqVO, ZcProductDO.class);
        productMapper.insert(d);
        return d.getId();
    }

    @Override
    public void update(ZcProductSaveReqVO reqVO) {
        validate(reqVO.getId());
        productMapper.updateById(BeanUtils.toBean(reqVO, ZcProductDO.class));
    }

    @Override
    public void delete(Long id) {
        validate(id);
        productMapper.deleteById(id);
    }

    @Override
    public ZcProductDO get(Long id) {
        return productMapper.selectById(id);
    }

    @Override
    public PageResult<ZcProductDO> getPage(ZcProductPageReqVO pageReqVO) {
        return productMapper.selectPage(pageReqVO, new LambdaQueryWrapperX<ZcProductDO>()
                .likeIfPresent(ZcProductDO::getName, pageReqVO.getName())
                .eqIfPresent(ZcProductDO::getVersionId, pageReqVO.getVersionId())
                .eqIfPresent(ZcProductDO::getSupplierId, pageReqVO.getSupplierId())
                .orderByDesc(ZcProductDO::getId));
    }

    private void validate(Long id) {
        if (id == null || productMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.PRODUCT_NOT_EXISTS);
        }
    }

}
