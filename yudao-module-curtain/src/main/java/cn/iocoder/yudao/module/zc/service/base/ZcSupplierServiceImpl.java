package cn.iocoder.yudao.module.zc.service.base;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.zc.controller.admin.vo.base.ZcSupplierPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.base.ZcSupplierSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.base.ZcSupplierDO;
import cn.iocoder.yudao.module.zc.dal.mysql.base.ZcSupplierMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service
@Validated
public class ZcSupplierServiceImpl implements ZcSupplierService {

    @Resource
    private ZcSupplierMapper supplierMapper;

    @Override
    public Long create(ZcSupplierSaveReqVO reqVO) {
        ZcSupplierDO d = BeanUtils.toBean(reqVO, ZcSupplierDO.class);
        supplierMapper.insert(d);
        return d.getId();
    }

    @Override
    public void update(ZcSupplierSaveReqVO reqVO) {
        validate(reqVO.getId());
        supplierMapper.updateById(BeanUtils.toBean(reqVO, ZcSupplierDO.class));
    }

    @Override
    public void delete(Long id) {
        validate(id);
        supplierMapper.deleteById(id);
    }

    @Override
    public ZcSupplierDO get(Long id) {
        return supplierMapper.selectById(id);
    }

    @Override
    public PageResult<ZcSupplierDO> getPage(ZcSupplierPageReqVO pageReqVO) {
        return supplierMapper.selectPage(pageReqVO, new LambdaQueryWrapperX<ZcSupplierDO>()
                .likeIfPresent(ZcSupplierDO::getName, pageReqVO.getName())
                .likeIfPresent(ZcSupplierDO::getShortName, pageReqVO.getShortName())
                .orderByDesc(ZcSupplierDO::getId));
    }

    private void validate(Long id) {
        if (id == null || supplierMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.SUPPLIER_NOT_EXISTS);
        }
    }

}
