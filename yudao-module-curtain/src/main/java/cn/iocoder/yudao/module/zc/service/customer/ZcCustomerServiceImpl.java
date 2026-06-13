package cn.iocoder.yudao.module.zc.service.customer;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import cn.iocoder.yudao.module.zc.controller.admin.customer.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.customer.ZcCustomerDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.controller.admin.brand.vo.ZcBrandListReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.logistics.vo.ZcLogisticsListReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.brand.ZcBrandDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.logistics.ZcLogisticsDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.bills.ZcBillsDO;
import cn.iocoder.yudao.module.zc.dal.mysql.bills.ZcBillsMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.brand.ZcBrandMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.customer.ZcCustomerMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.logistics.ZcLogisticsMapper;
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

    @Resource
    private ZcLogisticsMapper logisticsMapper;

    @Resource
    private ZcBrandMapper brandMapper;

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
    @Transactional(rollbackFor = Exception.class)
    public ZcCustomerImportRespVO importCustomerList(List<ZcCustomerImportExcelVO> importCustomers, boolean isUpdateSupport) {
        if (CollUtil.isEmpty(importCustomers)) {
            throw exception(CUSTOMER_IMPORT_LIST_IS_EMPTY);
        }

        // 预加载所有物流名称 -> ID 映射，避免逐行 N+1 查询；使用 HashMap 以便后续动态插入
        Map<String, Long> logisticNameIdMap = new HashMap<>(logisticsMapper.selectList(new ZcLogisticsListReqVO())
                .stream().collect(Collectors.toMap(ZcLogisticsDO::getName, ZcLogisticsDO::getId, (a, b) -> a)));
        // 预加载所有品牌名称 -> ID 映射
        Map<String, Long> brandNameIdMap = brandMapper.selectList(new ZcBrandListReqVO())
                .stream().collect(Collectors.toMap(ZcBrandDO::getName, ZcBrandDO::getId, (a, b) -> a));

        ZcCustomerImportRespVO respVO = ZcCustomerImportRespVO.builder()
                .createShortNames(new ArrayList<>())
                .updateShortNames(new ArrayList<>())
                .failureShortNames(new LinkedHashMap<>())
                .build();

        for (int i = 0; i < importCustomers.size(); i++) {
            ZcCustomerImportExcelVO importCustomer = importCustomers.get(i);
            String shortName = importCustomer.getShortName();
            // 简称为空时用行号标记，方便用户定位
            String rowKey = StrUtil.isNotBlank(shortName) ? shortName : "第 " + (i + 1) + " 行";

            if (StrUtil.isBlank(shortName)) {
                respVO.getFailureShortNames().put(rowKey, "简称不能为空");
                continue;
            }

            // 解析物流 ID（填写了名称才处理，空则不关联；不存在时自动创建，code 与 name 相同）
            Long logisticId = null;
            if (StrUtil.isNotBlank(importCustomer.getLogisticName())) {
                String logisticName = importCustomer.getLogisticName();
                logisticId = logisticNameIdMap.get(logisticName);
                if (logisticId == null) {
                    ZcLogisticsDO newLogistic = ZcLogisticsDO.builder()
                            .code(logisticName)
                            .name(logisticName)
                            .build();
                    logisticsMapper.insert(newLogistic);
                    logisticId = newLogistic.getId();
                    // 放入 map，避免同一批次重复创建相同物流
                    logisticNameIdMap.put(logisticName, logisticId);
                }
            }

            // 解析品牌 ID（填写了名称才校验，空则不关联）
            Long brandId = null;
            if (StrUtil.isNotBlank(importCustomer.getBrandName())) {
                brandId = brandNameIdMap.get(importCustomer.getBrandName());
                if (brandId == null) {
                    respVO.getFailureShortNames().put(rowKey, "品牌【" + importCustomer.getBrandName() + "】不存在");
                    continue;
                }
            }

            ZcCustomerDO existCustomer = customerMapper.selectByShortName(shortName);
            try {
                if (existCustomer == null) {
                    // 新增：余额强制初始化为 0
                    ZcCustomerDO customer = BeanUtils.toBean(importCustomer, ZcCustomerDO.class);
                    customer.setLogisticId(logisticId);
                    customer.setBrandId(brandId);
                    customer.setBalance(BigDecimal.ZERO);
                    customerMapper.insert(customer);
                    respVO.getCreateShortNames().add(shortName);
                } else if (isUpdateSupport) {
                    // 更新：不覆盖余额
                    ZcCustomerDO updateObj = BeanUtils.toBean(importCustomer, ZcCustomerDO.class);
                    updateObj.setId(existCustomer.getId());
                    updateObj.setLogisticId(logisticId);
                    updateObj.setBrandId(brandId);
                    customerMapper.updateById(updateObj);
                    respVO.getUpdateShortNames().add(shortName);
                } else {
                    respVO.getFailureShortNames().put(rowKey, "客户简称已存在");
                }
            } catch (DataIntegrityViolationException e) {
                // 数据超出字段长度限制（如手机号含多个号码）或其他数据完整性问题，单行失败不影响整体导入
                respVO.getFailureShortNames().put(rowKey, "数据格式有误，请检查各字段长度是否超限");
            }
        }

        return respVO;
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
