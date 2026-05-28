package cn.iocoder.yudao.module.zc.service.salesorder;

import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.tenant.core.context.TenantContextHolder;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcSalesOrderProductBatchCreateVO;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcSalesOrderProductCreateReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderProductDO;
import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZcSalesOrderMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZcSalesOrderProductMapper;
import cn.iocoder.yudao.module.zc.dal.redis.ZcNoGeneratorRedisDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
        return orderId;
    }

}
