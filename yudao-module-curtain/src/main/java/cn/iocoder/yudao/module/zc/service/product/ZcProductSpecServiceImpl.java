package cn.iocoder.yudao.module.zc.service.product;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcProductSpecPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcProductSpecSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.product.ZcProductSpecDO;
import cn.iocoder.yudao.module.zc.dal.mysql.product.ZcProductSpecMapper;
import cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service
@Validated
public class ZcProductSpecServiceImpl implements ZcProductSpecService {

    @Resource
    private ZcProductSpecMapper productSpecMapper;

    @Override
    public Long create(ZcProductSpecSaveReqVO reqVO) {
        ZcProductSpecDO d = BeanUtils.toBean(reqVO, ZcProductSpecDO.class);
        productSpecMapper.insert(d);
        return d.getId();
    }

    @Override
    public void update(ZcProductSpecSaveReqVO reqVO) {
        validate(reqVO.getId());
        productSpecMapper.updateById(BeanUtils.toBean(reqVO, ZcProductSpecDO.class));
    }

    @Override
    public void delete(Long id) {
        validate(id);
        productSpecMapper.deleteById(id);
    }

    @Override
    public ZcProductSpecDO get(Long id) {
        return productSpecMapper.selectById(id);
    }

    @Override
    public PageResult<ZcProductSpecDO> getPage(ZcProductSpecPageReqVO pageReqVO) {
        return productSpecMapper.selectPage(pageReqVO, new LambdaQueryWrapperX<ZcProductSpecDO>()
                .likeIfPresent(ZcProductSpecDO::getValue, pageReqVO.getValue())
                .orderByDesc(ZcProductSpecDO::getId));
    }

    private void validate(Long id) {
        if (id == null || productSpecMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.PRODUCT_SPEC_NOT_EXISTS);
        }
    }

}
