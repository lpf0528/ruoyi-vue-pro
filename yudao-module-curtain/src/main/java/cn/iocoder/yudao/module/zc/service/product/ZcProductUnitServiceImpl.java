package cn.iocoder.yudao.module.zc.service.product;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcProductUnitPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcProductUnitSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.product.ZcProductUnitDO;
import cn.iocoder.yudao.module.zc.dal.mysql.product.ZcProductUnitMapper;
import cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service
@Validated
public class ZcProductUnitServiceImpl implements ZcProductUnitService {

    @Resource
    private ZcProductUnitMapper productUnitMapper;

    @Override
    public Long create(ZcProductUnitSaveReqVO reqVO) {
        ZcProductUnitDO d = BeanUtils.toBean(reqVO, ZcProductUnitDO.class);
        productUnitMapper.insert(d);
        return d.getId();
    }

    @Override
    public void update(ZcProductUnitSaveReqVO reqVO) {
        validate(reqVO.getId());
        productUnitMapper.updateById(BeanUtils.toBean(reqVO, ZcProductUnitDO.class));
    }

    @Override
    public void delete(Long id) {
        validate(id);
        productUnitMapper.deleteById(id);
    }

    @Override
    public ZcProductUnitDO get(Long id) {
        return productUnitMapper.selectById(id);
    }

    @Override
    public PageResult<ZcProductUnitDO> getPage(ZcProductUnitPageReqVO pageReqVO) {
        return productUnitMapper.selectPage(pageReqVO, new LambdaQueryWrapperX<ZcProductUnitDO>()
                .likeIfPresent(ZcProductUnitDO::getValue, pageReqVO.getValue())
                .orderByDesc(ZcProductUnitDO::getId));
    }

    private void validate(Long id) {
        if (id == null || productUnitMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.PRODUCT_UNIT_NOT_EXISTS);
        }
    }

}
