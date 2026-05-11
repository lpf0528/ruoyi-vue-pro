package cn.iocoder.yudao.module.zc.service.product;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcProductVersionPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcProductVersionSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.product.ZcProductVersionDO;
import cn.iocoder.yudao.module.zc.dal.mysql.product.ZcProductVersionMapper;
import cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service
@Validated
public class ZcProductVersionServiceImpl implements ZcProductVersionService {

    @Resource
    private ZcProductVersionMapper productVersionMapper;

    @Override
    public Long create(ZcProductVersionSaveReqVO reqVO) {
        ZcProductVersionDO d = BeanUtils.toBean(reqVO, ZcProductVersionDO.class);
        productVersionMapper.insert(d);
        return d.getId();
    }

    @Override
    public void update(ZcProductVersionSaveReqVO reqVO) {
        validate(reqVO.getId());
        productVersionMapper.updateById(BeanUtils.toBean(reqVO, ZcProductVersionDO.class));
    }

    @Override
    public void delete(Long id) {
        validate(id);
        productVersionMapper.deleteById(id);
    }

    @Override
    public ZcProductVersionDO get(Long id) {
        return productVersionMapper.selectById(id);
    }

    @Override
    public PageResult<ZcProductVersionDO> getPage(ZcProductVersionPageReqVO pageReqVO) {
        return productVersionMapper.selectPage(pageReqVO, new LambdaQueryWrapperX<ZcProductVersionDO>()
                .likeIfPresent(ZcProductVersionDO::getName, pageReqVO.getName())
                .eqIfPresent(ZcProductVersionDO::getSupplierId, pageReqVO.getSupplierId())
                .orderByDesc(ZcProductVersionDO::getId));
    }

    private void validate(Long id) {
        if (id == null || productVersionMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.PRODUCT_VERSION_NOT_EXISTS);
        }
    }

}
