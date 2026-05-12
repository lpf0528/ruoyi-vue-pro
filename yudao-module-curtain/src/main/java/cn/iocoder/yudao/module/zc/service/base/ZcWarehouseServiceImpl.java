package cn.iocoder.yudao.module.zc.service.base;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.zc.controller.admin.vo.base.ZcWarehousePageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.base.ZcWarehouseSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.base.ZcWarehouseDO;
import cn.iocoder.yudao.module.zc.dal.mysql.base.ZcWarehouseMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service
@Validated
public class ZcWarehouseServiceImpl implements ZcWarehouseService {

    @Resource
    private ZcWarehouseMapper warehouseMapper;

    @Override
    public Long createWarehouse(ZcWarehouseSaveReqVO createReqVO) {
        ZcWarehouseDO d = BeanUtils.toBean(createReqVO, ZcWarehouseDO.class);
        warehouseMapper.insert(d);
        return d.getId();
    }

    @Override
    public void updateWarehouse(ZcWarehouseSaveReqVO updateReqVO) {
        validateWarehouseExists(updateReqVO.getId());
        warehouseMapper.updateById(BeanUtils.toBean(updateReqVO, ZcWarehouseDO.class));
    }

    @Override
    public void deleteWarehouse(Long id) {
        validateWarehouseExists(id);
        warehouseMapper.deleteById(id);
    }

    @Override
    public ZcWarehouseDO getWarehouse(Long id) {
        return warehouseMapper.selectById(id);
    }

    @Override
    public PageResult<ZcWarehouseDO> getWarehousePage(ZcWarehousePageReqVO pageReqVO) {
        return warehouseMapper.selectPage(pageReqVO, new LambdaQueryWrapperX<ZcWarehouseDO>()
                .likeIfPresent(ZcWarehouseDO::getName, pageReqVO.getName())
                .orderByDesc(ZcWarehouseDO::getId));
    }

    private void validateWarehouseExists(Long id) {
        if (id == null || warehouseMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.WAREHOUSE_NOT_EXISTS);
        }
    }

}
