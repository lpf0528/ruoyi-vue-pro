package cn.iocoder.yudao.module.zc.service.salesorder;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderStructureDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZcSalesOrderStructureMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;

/**
 * 成品订单-结构 Service 实现类
 *
 * @author 01Coder
 */
@Service
@Validated
public class ZcSalesOrderStructureServiceImpl implements ZcSalesOrderStructureService {

    @Resource
    private ZcSalesOrderStructureMapper salesOrderStructureMapper;

    @Override
    public Long createSalesOrderStructure(ZcSalesOrderStructureSaveReqVO createReqVO) {
        // 插入
        ZcSalesOrderStructureDO salesOrderStructure = BeanUtils.toBean(createReqVO, ZcSalesOrderStructureDO.class);
        salesOrderStructureMapper.insert(salesOrderStructure);

        // 返回
        return salesOrderStructure.getId();
    }

    @Override
    public void updateSalesOrderStructure(ZcSalesOrderStructureSaveReqVO updateReqVO) {
        // 校验存在
        validateSalesOrderStructureExists(updateReqVO.getId());
        // 更新
        ZcSalesOrderStructureDO updateObj = BeanUtils.toBean(updateReqVO, ZcSalesOrderStructureDO.class);
        salesOrderStructureMapper.updateById(updateObj);
    }

    @Override
    public void deleteSalesOrderStructure(Long id) {
        // 校验存在
        validateSalesOrderStructureExists(id);
        // 删除
        salesOrderStructureMapper.deleteById(id);
    }

    @Override
        public void deleteSalesOrderStructureListByIds(List<Long> ids) {
        // 删除
        salesOrderStructureMapper.deleteByIds(ids);
        }


    private void validateSalesOrderStructureExists(Long id) {
        if (salesOrderStructureMapper.selectById(id) == null) {
            throw exception(SALES_ORDER_STRUCTURE_NOT_EXISTS);
        }
    }

    @Override
    public ZcSalesOrderStructureDO getSalesOrderStructure(Long id) {
        return salesOrderStructureMapper.selectById(id);
    }

    @Override
    public PageResult<ZcSalesOrderStructureDO> getSalesOrderStructurePage(ZcSalesOrderStructurePageReqVO pageReqVO) {
        return salesOrderStructureMapper.selectPage(pageReqVO);
    }

}