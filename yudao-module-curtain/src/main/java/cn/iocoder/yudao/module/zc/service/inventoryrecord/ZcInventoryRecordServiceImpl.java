package cn.iocoder.yudao.module.zc.service.inventoryrecord;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.inventoryrecord.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.inventoryrecord.ZcInventoryRecordDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.inventoryrecord.ZcInventoryRecordMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;

/**
 * 盘点记录 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class ZcInventoryRecordServiceImpl implements ZcInventoryRecordService {

    @Resource
    private ZcInventoryRecordMapper inventoryRecordMapper;

    @Override
    public Long createInventoryRecord(ZcInventoryRecordSaveReqVO createReqVO) {
        // 插入
        ZcInventoryRecordDO inventoryRecord = BeanUtils.toBean(createReqVO, ZcInventoryRecordDO.class);
        inventoryRecordMapper.insert(inventoryRecord);

        // 返回
        return inventoryRecord.getId();
    }

    @Override
    public void updateInventoryRecord(ZcInventoryRecordSaveReqVO updateReqVO) {
        // 校验存在
        validateInventoryRecordExists(updateReqVO.getId());
        // 更新
        ZcInventoryRecordDO updateObj = BeanUtils.toBean(updateReqVO, ZcInventoryRecordDO.class);
        inventoryRecordMapper.updateById(updateObj);
    }

    @Override
    public void deleteInventoryRecord(Long id) {
        // 校验存在
        validateInventoryRecordExists(id);
        // 删除
        inventoryRecordMapper.deleteById(id);
    }

    @Override
        public void deleteInventoryRecordListByIds(List<Long> ids) {
        // 删除
        inventoryRecordMapper.deleteByIds(ids);
        }


    private void validateInventoryRecordExists(Long id) {
        if (inventoryRecordMapper.selectById(id) == null) {
            throw exception(INVENTORY_RECORD_NOT_EXISTS);
        }
    }

    @Override
    public ZcInventoryRecordDO getInventoryRecord(Long id) {
        return inventoryRecordMapper.selectById(id);
    }

    @Override
    public PageResult<ZcInventoryRecordRespVO> getInventoryRecordPage(ZcInventoryRecordPageReqVO pageReqVO) {
        return inventoryRecordMapper.selectPage(pageReqVO);
    }

}