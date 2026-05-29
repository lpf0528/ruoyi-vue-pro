package cn.iocoder.yudao.module.zc.service.warehouse;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.warehouse.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.warehouse.ZcWarehouseDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.warehouse.ZcWarehouseMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;

/**
 * 仓库 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class ZcWarehouseServiceImpl implements ZcWarehouseService {

    @Resource
    private ZcWarehouseMapper warehouseMapper;

    @Override
    public Long createWarehouse(ZcWarehouseSaveReqVO createReqVO) {
        validateWarehouseNameUnique(null, createReqVO.getName());
        // 插入
        ZcWarehouseDO warehouse = BeanUtils.toBean(createReqVO, ZcWarehouseDO.class);
        warehouseMapper.insert(warehouse);
        return warehouse.getId();
    }

    @Override
    public void updateWarehouse(ZcWarehouseSaveReqVO updateReqVO) {
        // 校验存在
        validateWarehouseExists(updateReqVO.getId());
        validateWarehouseNameUnique(updateReqVO.getId(), updateReqVO.getName());
        // 更新
        ZcWarehouseDO updateObj = BeanUtils.toBean(updateReqVO, ZcWarehouseDO.class);
        warehouseMapper.updateById(updateObj);
    }

    @Override
    public void deleteWarehouse(Long id) {
        // 校验存在
        validateWarehouseExists(id);
        // 删除
        warehouseMapper.deleteById(id);
    }

    @Override
        public void deleteWarehouseListByIds(List<Long> ids) {
        // 删除
        warehouseMapper.deleteByIds(ids);
        }


    private void validateWarehouseExists(Long id) {
        if (warehouseMapper.selectById(id) == null) {
            throw exception(WAREHOUSE_NOT_EXISTS);
        }
    }

    private void validateWarehouseNameUnique(Long id, String name) {
        ZcWarehouseDO existing = warehouseMapper.selectByName(name);
        if (existing == null || existing.getId().equals(id)) {
            return;
        }
        throw exception(WAREHOUSE_NAME_EXISTS);
    }

    @Override
    public ZcWarehouseDO getWarehouse(Long id) {
        return warehouseMapper.selectById(id);
    }

    @Override
    public ZcWarehouseRespVO getWarehouseVO(Long id) {
        return warehouseMapper.selectByIdWithVO(id);
    }

    @Override
    public PageResult<ZcWarehouseDO> getWarehousePage(ZcWarehousePageReqVO pageReqVO) {
        return warehouseMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<ZcWarehouseRespVO> getWarehousePageVO(ZcWarehousePageReqVO pageReqVO) {
        return warehouseMapper.selectPageVO(pageReqVO);
    }

    @Override
    public List<ZcWarehouseDO> getWarehouseList(ZcWarehouseListReqVO listReqVO) {
        return warehouseMapper.selectList(listReqVO);
    }

}