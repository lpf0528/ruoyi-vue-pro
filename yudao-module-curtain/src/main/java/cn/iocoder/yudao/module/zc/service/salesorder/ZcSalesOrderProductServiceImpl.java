package cn.iocoder.yudao.module.zc.service.salesorder;

import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.tenant.core.context.TenantContextHolder;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcSalesOrderProductBatchCreateVO;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcSalesOrderProductCreateReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcSalesOrderProductDetailRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcSalesOrderProductLineRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcSalesOrderProductUpdateReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcSalesOrderRespVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderProductDO;
import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZcSalesOrderMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZcSalesOrderProductMapper;
import cn.iocoder.yudao.module.zc.dal.redis.ZcNoGeneratorRedisDAO;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.service.impl.DiffParseFunction;
import com.mzt.logapi.starter.annotation.LogRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.SALES_ORDER_CONFIRMED_CANNOT_DELETE;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.SALES_ORDER_NOT_EXISTS;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.SALES_ORDER_CONFIRMED_CANNOT_UPDATE;
import static cn.iocoder.yudao.module.zc.enums.LogRecordConstants.*;

/**
 * 产品类销售订单 Service 实现类
 *
 * @author 01Coder
 */
@Service
@Validated
public class ZcSalesOrderProductServiceImpl implements ZcSalesOrderProductService {

    @Resource
    private ZcSalesOrderMapper salesOrderMapper;
    @Resource
    private ZcSalesOrderProductMapper salesOrderProductMapper;
    @Resource
    private ZcNoGeneratorRedisDAO noGeneratorRedisDAO;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = ZC_SALES_ORDER_PRODUCT_TYPE, subType = ZC_SALES_ORDER_PRODUCT_CREATE_SUB_TYPE, bizNo = "{{#salesOrder.id}}",
            success = ZC_SALES_ORDER_PRODUCT_CREATE_SUCCESS)
    public Long createSalesOrderProduct(ZcSalesOrderProductCreateReqVO createReqVO) {
        // 1. 生成订单号：ZC{租户ID}{yyyyMMdd}{5位序号}，Redis INCR 保证并发唯一
        Long tenantId = TenantContextHolder.getRequiredTenantId();
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long seq = noGeneratorRedisDAO.nextOrderSeq(tenantId, date);
        String orderNo = String.format("ZC%d%s%05d", tenantId, date, seq);

        // 2. 保存订单主记录，设置自动生成/默认字段
        ZcSalesOrderDO salesOrder = BeanUtils.toBean(createReqVO, ZcSalesOrderDO.class);
        salesOrder.setOrderNo(orderNo);
        salesOrder.setPayStatus("unpaid");   // 默认：未结算
        salesOrder.setStatus("unconfirmed"); // 默认：待确认
        salesOrder.setIsExpedited(false);    // 默认：非加急
        if (salesOrder.getFreight() == null) {
            salesOrder.setFreight(java.math.BigDecimal.ZERO);
        }
        salesOrderMapper.insert(salesOrder);
        Long orderId = salesOrder.getId();

        // 3. 级联保存产品批次行
        for (ZcSalesOrderProductBatchCreateVO batchVO : createReqVO.getBatchs()) {
            ZcSalesOrderProductDO productDO = BeanUtils.toBean(batchVO, ZcSalesOrderProductDO.class);
            productDO.setOrderId(orderId);
            salesOrderProductMapper.insert(productDO);
        }
        // 记录操作日志上下文
        LogRecordContext.putVariable("salesOrder", salesOrder);
        return orderId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = ZC_SALES_ORDER_PRODUCT_TYPE, subType = ZC_SALES_ORDER_PRODUCT_DELETE_SUB_TYPE, bizNo = "{{#id}}",
            success = ZC_SALES_ORDER_PRODUCT_DELETE_SUCCESS)
    public void deleteSalesOrderProduct(Long id) {
        // 1. 校验订单存在
        ZcSalesOrderDO order = validateSalesOrderExists(id);
        // 2. 已确认（confirm_time 不为空）的订单禁止删除
        if (order.getConfirmTime() != null) {
            throw exception(SALES_ORDER_CONFIRMED_CANNOT_DELETE);
        }
        // 记录操作日志上下文
        LogRecordContext.putVariable("orderNo", order.getOrderNo());
        // 3. 先删产品行，再删主记录，防止孤立数据
        salesOrderProductMapper.deleteByOrderId(id);
        salesOrderMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = ZC_SALES_ORDER_PRODUCT_TYPE, subType = ZC_SALES_ORDER_PRODUCT_UPDATE_SUB_TYPE, bizNo = "{{#updateReqVO.id}}",
            success = ZC_SALES_ORDER_PRODUCT_UPDATE_SUCCESS)
    public void updateSalesOrderProduct(ZcSalesOrderProductUpdateReqVO updateReqVO) {
        Long orderId = updateReqVO.getId();

        // 1. 校验订单存在
        ZcSalesOrderDO existing = validateSalesOrderExists(orderId);

        // 2. confirm_time 不为空表示已确认，禁止修改任何信息
        if (existing.getConfirmTime() != null) {
            throw exception(SALES_ORDER_CONFIRMED_CANNOT_UPDATE);
        }

        // 3. 更新订单主记录，保留系统字段（订单号、状态、结算状态、是否加急不允许覆盖）
        ZcSalesOrderDO updateOrder = BeanUtils.toBean(updateReqVO, ZcSalesOrderDO.class);
        updateOrder.setOrderNo(existing.getOrderNo());
        updateOrder.setStatus(existing.getStatus());
        updateOrder.setPayStatus(existing.getPayStatus());
        updateOrder.setIsExpedited(existing.getIsExpedited());
        updateOrder.setConfirmTime(existing.getConfirmTime());
        if (updateOrder.getFreight() == null) {
            updateOrder.setFreight(java.math.BigDecimal.ZERO);
        }
        salesOrderMapper.updateById(updateOrder);

        // 4. 整单替换产品行：先全量删除旧行，再重新插入新行
        salesOrderProductMapper.deleteByOrderId(orderId);
        for (ZcSalesOrderProductBatchCreateVO batchVO : updateReqVO.getBatchs()) {
            ZcSalesOrderProductDO productDO = BeanUtils.toBean(batchVO, ZcSalesOrderProductDO.class);
            productDO.setOrderId(orderId);
            salesOrderProductMapper.insert(productDO);
        }
        // 记录操作日志上下文（仅主表字段参与 diff）
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, BeanUtils.toBean(existing, ZcSalesOrderProductUpdateReqVO.class));
        LogRecordContext.putVariable("orderNo", existing.getOrderNo());
    }

    @Override
    public ZcSalesOrderProductDetailRespVO getSalesOrderProductDetail(Long id) {
        // 1. SQL JOIN 查订单主记录（含客户名称、物流名称），不存在则抛异常
        ZcSalesOrderRespVO orderVO = salesOrderMapper.selectVOById(id);
        if (orderVO == null) {
            throw exception(SALES_ORDER_NOT_EXISTS);
        }

        // 2. SQL JOIN 查产品行（含产品名称、批次号），结果直接映射到 VO
        List<ZcSalesOrderProductLineRespVO> lines = salesOrderProductMapper.selectProductLinesWithVOByOrderId(id);

        // 3. 组装返回 VO
        ZcSalesOrderProductDetailRespVO respVO = BeanUtils.toBean(orderVO, ZcSalesOrderProductDetailRespVO.class);
        respVO.setBatchs(lines);
        return respVO;
    }

    private ZcSalesOrderDO validateSalesOrderExists(Long id) {
        ZcSalesOrderDO order = salesOrderMapper.selectById(id);
        if (order == null) {
            throw exception(SALES_ORDER_NOT_EXISTS);
        }
        return order;
    }

}
