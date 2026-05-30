package cn.iocoder.yudao.module.zc.service.salesorder;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderCurtainDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZcSalesOrderCurtainMapper;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.service.impl.DiffParseFunction;
import com.mzt.logapi.starter.annotation.LogRecord;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.zc.enums.LogRecordConstants.*;

/**
 * 成品订单-窗帘行 Service 实现类
 *
 * @author o1Coder
 */
@Service
@Validated
public class ZcSalesOrderCurtainServiceImpl implements ZcSalesOrderCurtainService {

    @Resource
    private ZcSalesOrderCurtainMapper salesOrderCurtainMapper;

    @Override
    @LogRecord(type = ZC_SALES_ORDER_CURTAIN_TYPE, subType = ZC_SALES_ORDER_CURTAIN_CREATE_SUB_TYPE, bizNo = "{{#orderCurtain.id}}",
            success = ZC_SALES_ORDER_CURTAIN_CREATE_SUCCESS)
    public Long createSalesOrderCurtain(ZcSalesOrderCurtainSaveReqVO createReqVO) {
        // 插入
        ZcSalesOrderCurtainDO orderCurtain = BeanUtils.toBean(createReqVO, ZcSalesOrderCurtainDO.class);
        salesOrderCurtainMapper.insert(orderCurtain);
        // 记录操作日志上下文
        LogRecordContext.putVariable("orderCurtain", orderCurtain);
        return orderCurtain.getId();
    }

    @Override
    @LogRecord(type = ZC_SALES_ORDER_CURTAIN_TYPE, subType = ZC_SALES_ORDER_CURTAIN_UPDATE_SUB_TYPE, bizNo = "{{#updateReqVO.id}}",
            success = ZC_SALES_ORDER_CURTAIN_UPDATE_SUCCESS)
    public void updateSalesOrderCurtain(ZcSalesOrderCurtainSaveReqVO updateReqVO) {
        // 校验存在
        ZcSalesOrderCurtainDO oldOrderCurtain = validateSalesOrderCurtainExists(updateReqVO.getId());
        // 更新
        ZcSalesOrderCurtainDO updateObj = BeanUtils.toBean(updateReqVO, ZcSalesOrderCurtainDO.class);
        salesOrderCurtainMapper.updateById(updateObj);
        // 记录操作日志上下文
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, BeanUtils.toBean(oldOrderCurtain, ZcSalesOrderCurtainSaveReqVO.class));
        LogRecordContext.putVariable("orderCurtainId", oldOrderCurtain.getId());
    }

    @Override
    @LogRecord(type = ZC_SALES_ORDER_CURTAIN_TYPE, subType = ZC_SALES_ORDER_CURTAIN_DELETE_SUB_TYPE, bizNo = "{{#id}}",
            success = ZC_SALES_ORDER_CURTAIN_DELETE_SUCCESS)
    public void deleteSalesOrderCurtain(Long id) {
        // 校验存在
        ZcSalesOrderCurtainDO orderCurtain = validateSalesOrderCurtainExists(id);
        // 记录操作日志上下文
        LogRecordContext.putVariable("orderCurtainId", orderCurtain.getId());
        // 删除
        salesOrderCurtainMapper.deleteById(id);
    }

    @Override
    public void deleteSalesOrderCurtainListByIds(List<Long> ids) {
        // 删除
        salesOrderCurtainMapper.deleteByIds(ids);
    }

    private ZcSalesOrderCurtainDO validateSalesOrderCurtainExists(Long id) {
        ZcSalesOrderCurtainDO orderCurtain = salesOrderCurtainMapper.selectById(id);
        if (orderCurtain == null) {
            throw exception(SALES_ORDER_CURTAIN_NOT_EXISTS);
        }
        return orderCurtain;
    }

    @Override
    public ZcSalesOrderCurtainDO getSalesOrderCurtain(Long id) {
        return salesOrderCurtainMapper.selectById(id);
    }

    @Override
    public PageResult<ZcSalesOrderCurtainDO> getSalesOrderCurtainPage(ZcSalesOrderCurtainPageReqVO pageReqVO) {
        return salesOrderCurtainMapper.selectPage(pageReqVO);
    }

}
