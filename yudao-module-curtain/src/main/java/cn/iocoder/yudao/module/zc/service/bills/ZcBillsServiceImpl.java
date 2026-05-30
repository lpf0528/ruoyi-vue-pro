package cn.iocoder.yudao.module.zc.service.bills;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.bills.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.bills.ZcBillAttachmentsDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.bills.ZcBillOrderItemsDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.bills.ZcBillsDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.bills.ZcBillAttachmentsMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.bills.ZcBillOrderItemsMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.bills.ZcBillsMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZcSalesOrderMapper;
import cn.iocoder.yudao.module.zc.dal.redis.ZcNoGeneratorRedisDAO;
import cn.iocoder.yudao.module.zc.service.customer.ZcCustomerService;
import cn.iocoder.yudao.framework.tenant.core.context.TenantContextHolder;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;

/**
 * 收支账单 Service 实现类
 *
 * @author 01Coder
 */
@Service
@Validated
public class ZcBillsServiceImpl implements ZcBillsService {

    @Resource
    private ZcBillsMapper billsMapper;
    @Resource
    private ZcBillAttachmentsMapper billAttachmentsMapper;
    @Resource
    private ZcBillOrderItemsMapper billOrderItemsMapper;
    @Resource
    private ZcSalesOrderMapper salesOrderMapper;
    @Resource
    private ZcCustomerService customerService;
    @Resource
    private ZcNoGeneratorRedisDAO noGeneratorRedisDAO;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createBills(ZcBillsSaveReqVO createReqVO) {
        // 1. 校验分摊金额合计必须等于实收+优惠，否则订单账目与客户余额会不一致
        BigDecimal discount = createReqVO.getDiscountAmount() == null ? BigDecimal.ZERO : createReqVO.getDiscountAmount();
        BigDecimal totalSettled = createReqVO.getActualAmount().add(discount);
        BigDecimal totalAllocated = createReqVO.getOrderItems().stream()
                .map(ZcBillOrderItemReqVO::getAllocatedAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (totalAllocated.compareTo(totalSettled) != 0) {
            throw exception(BILL_ALLOCATED_AMOUNT_NOT_MATCH);
        }

        // 2. 自动生成单号：SK{yyyyMMdd}-{6位序号}，Redis INCR 保证并发唯一
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long seq = noGeneratorRedisDAO.nextBillSeq(TenantContextHolder.getRequiredTenantId(), date);
        String billNo = String.format("SK%s-%06d", date, seq);

        // 3. 保存账单主记录，billUserId 取当前登录用户
        ZcBillsDO bills = BeanUtils.toBean(createReqVO, ZcBillsDO.class);
        bills.setBillNo(billNo);
        bills.setBillUserId(SecurityFrameworkUtils.getLoginUserId());
        if (bills.getDiscountAmount() == null) {
            bills.setDiscountAmount(BigDecimal.ZERO);
        }
        billsMapper.insert(bills);
        Long billId = bills.getId();

        // 4. 保存附件记录（根据扩展名判断类型：jpg/jpeg/png/gif/webp 为图片，其余为文件）
        // tenant_id 由 MyBatis Plus 多租户拦截器自动注入，无需手动设置
        if (CollUtil.isNotEmpty(createReqVO.getAttachments())) {
            for (String url : createReqVO.getAttachments()) {
                int type = isImageUrl(url) ? 1 : 2;
                ZcBillAttachmentsDO attachment = ZcBillAttachmentsDO.builder()
                        .billId(billId)
                        .url(url)
                        .type(type)
                        .build();
                billAttachmentsMapper.insert(attachment);
            }
        }

        // 5. 处理订单分摊明细：更新各订单已收金额与结算状态
        for (ZcBillOrderItemReqVO item : createReqVO.getOrderItems()) {
            ZcSalesOrderDO order = salesOrderMapper.selectById(item.getOrderId());
            if (order == null) {
                throw exception(SALES_ORDER_NOT_EXISTS);
            }
            BigDecimal currentReceived = order.getAmountReceived() == null
                    ? BigDecimal.ZERO : order.getAmountReceived();
            BigDecimal newReceived = currentReceived.add(item.getAllocatedAmount());
            BigDecimal orderAmount = order.getAmount() == null ? BigDecimal.ZERO : order.getAmount();
            // 判断结算状态：全额到账则已支付，否则部分收款
            String payStatus = newReceived.compareTo(orderAmount) >= 0 ? "paid" : "partialpaid";
            ZcSalesOrderDO updateOrder = new ZcSalesOrderDO();
            updateOrder.setId(order.getId());
            updateOrder.setAmountReceived(newReceived);
            updateOrder.setPayStatus(payStatus);
            salesOrderMapper.updateById(updateOrder);
            // 保存分摊明细记录
            ZcBillOrderItemsDO itemDO = ZcBillOrderItemsDO.builder()
                    .billId(billId)
                    .orderId(item.getOrderId())
                    .allocatedAmount(item.getAllocatedAmount())
                    .build();
            billOrderItemsMapper.insert(itemDO);
        }

        // 6. 更新客户余额：balance += actualAmount + discountAmount
        // actualAmount 为本次实收，discountAmount 为本次优惠，两者合计为本次结算总价值
        if (createReqVO.getCustomerId() != null) {
            customerService.adjustBalance(createReqVO.getCustomerId(), totalSettled);
        }

        return billId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBills(ZcBillsSaveReqVO updateReqVO) {
        // 1. 校验收款单存在，获取当前数据快照
        ZcBillsDO existingBill = billsMapper.selectById(updateReqVO.getId());
        if (existingBill == null) {
            throw exception(BILLS_NOT_EXISTS);
        }

        // 2. 校验新分摊金额合计一致性
        BigDecimal newDiscount = updateReqVO.getDiscountAmount() == null ? BigDecimal.ZERO : updateReqVO.getDiscountAmount();
        BigDecimal newTotalSettled = updateReqVO.getActualAmount().add(newDiscount);
        BigDecimal newTotalAllocated = updateReqVO.getOrderItems().stream()
                .map(ZcBillOrderItemReqVO::getAllocatedAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (newTotalAllocated.compareTo(newTotalSettled) != 0) {
            throw exception(BILL_ALLOCATED_AMOUNT_NOT_MATCH);
        }

        // 3. 回滚旧的订单分摊明细（逆操作）
        List<ZcBillOrderItemsDO> oldItems = billOrderItemsMapper.selectByBillId(updateReqVO.getId());
        for (ZcBillOrderItemsDO item : oldItems) {
            ZcSalesOrderDO order = salesOrderMapper.selectById(item.getOrderId());
            if (order == null) {
                continue;
            }
            BigDecimal currentReceived = order.getAmountReceived() == null ? BigDecimal.ZERO : order.getAmountReceived();
            // 减去旧分摊额，不允许为负
            BigDecimal newReceived = currentReceived.subtract(item.getAllocatedAmount()).max(BigDecimal.ZERO);
            BigDecimal orderAmount = order.getAmount() == null ? BigDecimal.ZERO : order.getAmount();
            String payStatus = newReceived.compareTo(BigDecimal.ZERO) == 0 ? "unpaid"
                    : newReceived.compareTo(orderAmount) >= 0 ? "paid" : "partialpaid";
            ZcSalesOrderDO updateOrder = new ZcSalesOrderDO();
            updateOrder.setId(order.getId());
            updateOrder.setAmountReceived(newReceived);
            updateOrder.setPayStatus(payStatus);
            salesOrderMapper.updateById(updateOrder);
        }

        // 4. 回滚旧的客户余额
        if (existingBill.getCustomerId() != null) {
            BigDecimal oldDiscount = existingBill.getDiscountAmount() == null ? BigDecimal.ZERO : existingBill.getDiscountAmount();
            BigDecimal oldSettled = existingBill.getActualAmount().add(oldDiscount);
            customerService.adjustBalance(existingBill.getCustomerId(), oldSettled.negate());
        }

        // 5. 删除旧附件和分摊明细
        billAttachmentsMapper.deleteByBillId(updateReqVO.getId());
        billOrderItemsMapper.deleteByBillId(updateReqVO.getId());

        // 6. 更新主记录（billNo 和 billUserId 不允许修改）
        ZcBillsDO updateObj = BeanUtils.toBean(updateReqVO, ZcBillsDO.class);
        updateObj.setBillNo(existingBill.getBillNo());
        updateObj.setBillUserId(existingBill.getBillUserId());
        billsMapper.updateById(updateObj);

        // 7. 保存新附件
        if (CollUtil.isNotEmpty(updateReqVO.getAttachments())) {
            for (String url : updateReqVO.getAttachments()) {
                int type = isImageUrl(url) ? 1 : 2;
                ZcBillAttachmentsDO attachment = ZcBillAttachmentsDO.builder()
                        .billId(updateReqVO.getId())
                        .url(url)
                        .type(type)
                        .build();
                billAttachmentsMapper.insert(attachment);
            }
        }

        // 8. 保存新的订单分摊明细并更新订单已收金额
        for (ZcBillOrderItemReqVO item : updateReqVO.getOrderItems()) {
            ZcSalesOrderDO order = salesOrderMapper.selectById(item.getOrderId());
            if (order == null) {
                throw exception(SALES_ORDER_NOT_EXISTS);
            }
            BigDecimal currentReceived = order.getAmountReceived() == null ? BigDecimal.ZERO : order.getAmountReceived();
            BigDecimal newReceived = currentReceived.add(item.getAllocatedAmount());
            BigDecimal orderAmount = order.getAmount() == null ? BigDecimal.ZERO : order.getAmount();
            String payStatus = newReceived.compareTo(orderAmount) >= 0 ? "paid" : "partialpaid";
            ZcSalesOrderDO updateOrder = new ZcSalesOrderDO();
            updateOrder.setId(order.getId());
            updateOrder.setAmountReceived(newReceived);
            updateOrder.setPayStatus(payStatus);
            salesOrderMapper.updateById(updateOrder);
            ZcBillOrderItemsDO itemDO = ZcBillOrderItemsDO.builder()
                    .billId(updateReqVO.getId())
                    .orderId(item.getOrderId())
                    .allocatedAmount(item.getAllocatedAmount())
                    .build();
            billOrderItemsMapper.insert(itemDO);
        }

        // 9. 更新新的客户余额
        Long newCustomerId = updateReqVO.getCustomerId() != null ? updateReqVO.getCustomerId() : existingBill.getCustomerId();
        if (newCustomerId != null) {
            customerService.adjustBalance(newCustomerId, newTotalSettled);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBills(Long id) {
        // 1. 校验收款单存在
        ZcBillsDO bill = billsMapper.selectById(id);
        if (bill == null) {
            throw exception(BILLS_NOT_EXISTS);
        }

        // 2. 回滚各订单的已收金额与支付状态
        List<ZcBillOrderItemsDO> orderItems = billOrderItemsMapper.selectByBillId(id);
        for (ZcBillOrderItemsDO item : orderItems) {
            ZcSalesOrderDO order = salesOrderMapper.selectById(item.getOrderId());
            if (order == null) {
                continue;
            }
            BigDecimal currentReceived = order.getAmountReceived() == null
                    ? BigDecimal.ZERO : order.getAmountReceived();
            // 减去本张账单的分摊额，不允许变为负数
            BigDecimal newReceived = currentReceived.subtract(item.getAllocatedAmount()).max(BigDecimal.ZERO);
            BigDecimal orderAmount = order.getAmount() == null ? BigDecimal.ZERO : order.getAmount();
            String payStatus = newReceived.compareTo(BigDecimal.ZERO) == 0 ? "unpaid"
                    : newReceived.compareTo(orderAmount) >= 0 ? "paid" : "partialpaid";
            ZcSalesOrderDO updateOrder = new ZcSalesOrderDO();
            updateOrder.setId(order.getId());
            updateOrder.setAmountReceived(newReceived);
            updateOrder.setPayStatus(payStatus);
            salesOrderMapper.updateById(updateOrder);
        }

        // 3. 回滚客户余额（撤销当时增加的 actualAmount + discountAmount）
        if (bill.getCustomerId() != null && bill.getActualAmount() != null) {
            BigDecimal discount = bill.getDiscountAmount() == null ? BigDecimal.ZERO : bill.getDiscountAmount();
            customerService.adjustBalance(bill.getCustomerId(), bill.getActualAmount().add(discount).negate());
        }

        // 4. 级联删除附件和分摊明细，再删主记录
        billAttachmentsMapper.deleteByBillId(id);
        billOrderItemsMapper.deleteByBillId(id);
        billsMapper.deleteById(id);
    }

    @Override
    public void deleteBillsListByIds(List<Long> ids) {
        billsMapper.deleteByIds(ids);
    }

    private void validateBillsExists(Long id) {
        if (billsMapper.selectById(id) == null) {
            throw exception(BILLS_NOT_EXISTS);
        }
    }

    @Override
    public ZcBillsDO getBills(Long id) {
        return billsMapper.selectById(id);
    }

    @Override
    public PageResult<ZcBillsRespVO> getBillsPage(ZcBillsPageReqVO pageReqVO) {
        return billsMapper.selectPage(pageReqVO);
    }

    /**
     * 根据 URL 后缀判断是否为图片类型
     */
    private boolean isImageUrl(String url) {
        if (url == null) {
            return false;
        }
        String lower = url.toLowerCase();
        return lower.endsWith(".jpg") || lower.endsWith(".jpeg")
                || lower.endsWith(".png") || lower.endsWith(".gif")
                || lower.endsWith(".webp");
    }

}
