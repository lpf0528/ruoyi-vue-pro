package cn.iocoder.yudao.module.zc.service.salesorder;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZcSalesOrderMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;

/**
 * 销售订单 Service 实现类
 *
 * @author 01Coder
 */
@Service
@Validated
public class ZcSalesOrderServiceImpl implements ZcSalesOrderService {

    @Resource
    private ZcSalesOrderMapper salesOrderMapper;

    @Override
    public Long createSalesOrder(ZcSalesOrderSaveReqVO createReqVO) {
        // 插入
        ZcSalesOrderDO salesOrder = BeanUtils.toBean(createReqVO, ZcSalesOrderDO.class);
        salesOrderMapper.insert(salesOrder);

        // 返回
        return salesOrder.getId();
    }

    @Override
    public void updateSalesOrder(ZcSalesOrderSaveReqVO updateReqVO) {
        // 校验存在
        validateSalesOrderExists(updateReqVO.getId());
        // 更新
        ZcSalesOrderDO updateObj = BeanUtils.toBean(updateReqVO, ZcSalesOrderDO.class);
        salesOrderMapper.updateById(updateObj);
    }

    @Override
    public void deleteSalesOrder(Long id) {
        // 校验存在
        validateSalesOrderExists(id);
        // 删除
        salesOrderMapper.deleteById(id);
    }

    @Override
        public void deleteSalesOrderListByIds(List<Long> ids) {
        // 删除
        salesOrderMapper.deleteByIds(ids);
        }


    private void validateSalesOrderExists(Long id) {
        if (salesOrderMapper.selectById(id) == null) {
            throw exception(SALES_ORDER_NOT_EXISTS);
        }
    }

    @Override
    public ZcSalesOrderDO getSalesOrder(Long id) {
        return salesOrderMapper.selectById(id);
    }

    @Override
    public PageResult<ZcSalesOrderDO> getSalesOrderPage(ZcSalesOrderPageReqVO pageReqVO) {
        return salesOrderMapper.selectPage(pageReqVO);
    }

}