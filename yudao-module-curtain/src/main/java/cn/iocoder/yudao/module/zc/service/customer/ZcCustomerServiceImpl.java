package cn.iocoder.yudao.module.zc.service.customer;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.customer.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.customer.ZcCustomerDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.bills.ZcBillsDO;
import cn.iocoder.yudao.module.zc.dal.mysql.bills.ZcBillsMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.customer.ZcCustomerMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZcSalesOrderMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.service.impl.DiffParseFunction;
import com.mzt.logapi.starter.annotation.LogRecord;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.zc.enums.LogRecordConstants.*;

/**
 * 客户资料 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class ZcCustomerServiceImpl implements ZcCustomerService {

    @Resource
    private ZcCustomerMapper customerMapper;

    @Resource
    private ZcSalesOrderMapper salesOrderMapper;

    @Resource
    private ZcBillsMapper billsMapper;

    @Override
    @LogRecord(type = ZC_CUSTOMER_TYPE, subType = ZC_CUSTOMER_CREATE_SUB_TYPE, bizNo = "{{#customer.id}}",
            success = ZC_CUSTOMER_CREATE_SUCCESS)
    public Long createCustomer(ZcCustomerSaveReqVO createReqVO) {
        validateCustomerShortNameUnique(null, createReqVO.getShortName());
        // 插入，余额强制初始化为 0，不允许前端传入
        ZcCustomerDO customer = BeanUtils.toBean(createReqVO, ZcCustomerDO.class);
        customer.setBalance(BigDecimal.ZERO);
        customerMapper.insert(customer);
        // 记录操作日志上下文
        LogRecordContext.putVariable("customer", customer);
        return customer.getId();
    }

    @Override
    @LogRecord(type = ZC_CUSTOMER_TYPE, subType = ZC_CUSTOMER_UPDATE_SUB_TYPE, bizNo = "{{#updateReqVO.id}}",
            success = ZC_CUSTOMER_UPDATE_SUCCESS)
    public void updateCustomer(ZcCustomerSaveReqVO updateReqVO) {
        // 校验存在
        ZcCustomerDO oldCustomer = validateCustomerExists(updateReqVO.getId());
        validateCustomerShortNameUnique(updateReqVO.getId(), updateReqVO.getShortName());
        // 更新
        ZcCustomerDO updateObj = BeanUtils.toBean(updateReqVO, ZcCustomerDO.class);
        customerMapper.updateById(updateObj);
        // 记录操作日志上下文
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, BeanUtils.toBean(oldCustomer, ZcCustomerSaveReqVO.class));
        LogRecordContext.putVariable("customerName", oldCustomer.getShortName());
    }

    @Override
    @LogRecord(type = ZC_CUSTOMER_TYPE, subType = ZC_CUSTOMER_DELETE_SUB_TYPE, bizNo = "{{#id}}",
            success = ZC_CUSTOMER_DELETE_SUCCESS)
    public void deleteCustomer(Long id) {
        // 校验存在
        ZcCustomerDO customer = validateCustomerExists(id);
        // 校验客户下是否存在销售订单，存在则禁止删除
        Long orderCount = salesOrderMapper.selectCount(
                new LambdaQueryWrapper<ZcSalesOrderDO>().eq(ZcSalesOrderDO::getCustomerId, id));
        if (orderCount != null && orderCount > 0) {
            throw exception(CUSTOMER_HAS_ORDERS);
        }
        // 校验客户下是否存在收支账单，存在则禁止删除
        Long billCount = billsMapper.selectCount(
                new LambdaQueryWrapper<ZcBillsDO>().eq(ZcBillsDO::getCustomerId, id));
        if (billCount != null && billCount > 0) {
            throw exception(CUSTOMER_HAS_BILLS);
        }
        // 记录操作日志上下文
        LogRecordContext.putVariable("customerName", customer.getShortName());
        // 删除
        customerMapper.deleteById(id);
    }

    private ZcCustomerDO validateCustomerExists(Long id) {
        ZcCustomerDO customer = customerMapper.selectById(id);
        if (customer == null) {
            throw exception(CUSTOMER_NOT_EXISTS);
        }
        return customer;
    }

    private void validateCustomerShortNameUnique(Long id, String shortName) {
        ZcCustomerDO existing = customerMapper.selectByShortName(shortName);
        if (existing == null || existing.getId().equals(id)) {
            return;
        }
        throw exception(CUSTOMER_SHORT_NAME_EXISTS);
    }

    @Override
    public ZcCustomerDO getCustomer(Long id) {
        return customerMapper.selectById(id);
    }

    @Override
    public PageResult<ZcCustomerRespVO> getCustomerPage(ZcCustomerPageReqVO pageReqVO) {
        return customerMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ZcCustomerDO> getCustomerList(ZcCustomerListReqVO listReqVO) {
        return customerMapper.selectList(listReqVO);
    }

    @Override
    public void adjustBalance(Long customerId, BigDecimal delta) {
        if (customerMapper.selectById(customerId) == null) {
            throw exception(CUSTOMER_NOT_EXISTS);
        }
        // 使用数据库原子加减，避免并发「后写覆盖先写」导致余额计算错误
        customerMapper.update(null, Wrappers.<ZcCustomerDO>lambdaUpdate()
                .setSql("balance = COALESCE(balance, 0) + " + delta.toPlainString())
                .eq(ZcCustomerDO::getId, customerId));
    }

}
