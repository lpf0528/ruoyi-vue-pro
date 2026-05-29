package cn.iocoder.yudao.module.zc.service.customer;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.customer.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.customer.ZcCustomerDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.bills.ZcBillsDO;
import cn.iocoder.yudao.module.zc.dal.mysql.bills.ZcBillsMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.customer.ZcCustomerMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZcSalesOrderMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;

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
    public Long createCustomer(ZcCustomerSaveReqVO createReqVO) {
        validateCustomerShortNameUnique(null, createReqVO.getShortName());
        // 插入，余额强制初始化为 0，不允许前端传入
        ZcCustomerDO customer = BeanUtils.toBean(createReqVO, ZcCustomerDO.class);
        customer.setBalance(BigDecimal.ZERO);
        customerMapper.insert(customer);
        return customer.getId();
    }

    @Override
    public void updateCustomer(ZcCustomerSaveReqVO updateReqVO) {
        // 校验存在
        validateCustomerExists(updateReqVO.getId());
        validateCustomerShortNameUnique(updateReqVO.getId(), updateReqVO.getShortName());
        // 更新
        ZcCustomerDO updateObj = BeanUtils.toBean(updateReqVO, ZcCustomerDO.class);
        customerMapper.updateById(updateObj);
    }

    @Override
    public void deleteCustomer(Long id) {
        // 校验存在
        validateCustomerExists(id);
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
        // 删除
        customerMapper.deleteById(id);
    }

    private void validateCustomerExists(Long id) {
        if (customerMapper.selectById(id) == null) {
            throw exception(CUSTOMER_NOT_EXISTS);
        }
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