package cn.iocoder.yudao.module.zc.service.product;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcProductCategoryPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcProductCategorySaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.product.ZcProductCategoryDO;
import cn.iocoder.yudao.module.zc.dal.mysql.product.ZcProductCategoryMapper;
import cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service
@Validated
public class ZcProductCategoryServiceImpl implements ZcProductCategoryService {

    @Resource
    private ZcProductCategoryMapper productCategoryMapper;

    @Override
    public Long create(ZcProductCategorySaveReqVO reqVO) {
        ZcProductCategoryDO d = BeanUtils.toBean(reqVO, ZcProductCategoryDO.class);
        productCategoryMapper.insert(d);
        return d.getId();
    }

    @Override
    public void update(ZcProductCategorySaveReqVO reqVO) {
        validate(reqVO.getId());
        productCategoryMapper.updateById(BeanUtils.toBean(reqVO, ZcProductCategoryDO.class));
    }

    @Override
    public void delete(Long id) {
        validate(id);
        productCategoryMapper.deleteById(id);
    }

    @Override
    public ZcProductCategoryDO get(Long id) {
        return productCategoryMapper.selectById(id);
    }

    @Override
    public PageResult<ZcProductCategoryDO> getPage(ZcProductCategoryPageReqVO pageReqVO) {
        return productCategoryMapper.selectPage(pageReqVO, new LambdaQueryWrapperX<ZcProductCategoryDO>()
                .likeIfPresent(ZcProductCategoryDO::getValue, pageReqVO.getValue())
                .orderByDesc(ZcProductCategoryDO::getId));
    }

    private void validate(Long id) {
        if (id == null || productCategoryMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.PRODUCT_CATEGORY_NOT_EXISTS);
        }
    }

}
