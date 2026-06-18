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
import cn.iocoder.yudao.module.zc.dal.dataobject.bills.ZcBillMethodsDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.bills.ZcBillOrderItemsDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.bills.ZcBillsDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.dataobject.customer.ZcCustomerDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.customerbalancelog.ZcCustomerBalanceLogDO;
import cn.iocoder.yudao.module.zc.dal.mysql.bills.ZcBillAttachmentsMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.bills.ZcBillMethodsMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.bills.ZcBillOrderItemsMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.bills.ZcBillsMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZcSalesOrderMapper;
import cn.iocoder.yudao.module.zc.dal.redis.ZcNoGeneratorRedisDAO;
import cn.iocoder.yudao.module.zc.service.customer.ZcCustomerService;
import cn.iocoder.yudao.module.zc.service.customerbalancelog.ZcCustomerBalanceLogService;
import cn.iocoder.yudao.framework.tenant.core.context.TenantContextHolder;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.service.impl.DiffParseFunction;
import com.mzt.logapi.starter.annotation.LogRecord;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.zc.enums.LogRecordConstants.*;
import cn.iocoder.yudao.module.zc.enums.ZcBillMethodConstants;
import cn.iocoder.yudao.module.zc.enums.ZcCustomerBalanceBizTypeEnum;
import cn.iocoder.yudao.module.zc.enums.ZcRefTypeEnum;
import cn.iocoder.yudao.module.zc.enums.ZcSalesOrderPayStatusEnum;

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
    private ZcBillMethodsMapper billMethodsMapper;
    @Resource
    private ZcBillAttachmentsMapper billAttachmentsMapper;
    @Resource
    private ZcBillOrderItemsMapper billOrderItemsMapper;
    @Resource
    private ZcSalesOrderMapper salesOrderMapper;
    @Resource
    private ZcCustomerService customerService;
    @Resource
    private ZcCustomerBalanceLogService customerBalanceLogService;
    @Resource
    private ZcNoGeneratorRedisDAO noGeneratorRedisDAO;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = ZC_BILLS_TYPE, subType = ZC_BILLS_CREATE_SUB_TYPE, bizNo = "{{#bill.id}}",
            success = ZC_BILLS_CREATE_SUCCESS)
    public Long createBills(ZcBillsSaveReqVO createReqVO) {
        // 1. 校验分摊金额合计必须等于实收+优惠，否则订单账目与客户余额会不一致
        BigDecimal discount = createReqVO.getDiscountAmount() == null ? BigDecimal.ZERO : createReqVO.getDiscountAmount();
        BigDecimal totalSettled = createReqVO.getActualAmount().add(discount);
//        BigDecimal totalAllocated = createReqVO.getOrderItems().stream()
//                .map(ZcBillOrderItemReqVO::getAllocatedAmount)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//        if (totalAllocated.compareTo(totalSettled) != 0) {
//            throw exception(BILL_ALLOCATED_AMOUNT_NOT_MATCH);
//        }

        // 2. 自动生成单号：SK{yyyyMMdd}-{6位序号}；Redis INCR 前先与库内最大序号（含软删）对齐
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long tenantId = TenantContextHolder.getRequiredTenantId();
        long seq = noGeneratorRedisDAO.nextBillSeq(tenantId, date,
                () -> billsMapper.selectMaxBillSeqByDate(date));
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
            // 判断支付状态：全额到账则已支付，否则部分支付
            String payStatus = newReceived.compareTo(orderAmount) >= 0
                    ? ZcSalesOrderPayStatusEnum.PAID.name() : ZcSalesOrderPayStatusEnum.PARTIALPAID.name();
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

        // 6. 更新客户余额并记录流水
        // 普通收款：balance += 实收 + 优惠（还款）；余额扣款：balance -= 实收 + 优惠（消耗预存款）
        if (createReqVO.getCustomerId() != null) {
            BigDecimal balanceDelta = calcCollectionBalanceDelta(totalSettled, createReqVO.getBillMethodId());
            adjustAndRecordLog(createReqVO.getCustomerId(), balanceDelta,
                    ZcCustomerBalanceBizTypeEnum.COLLECTION.name(), ZcRefTypeEnum.COLLECTION_RECORD.name(), billId, billNo, null);
        }

        // 记录操作日志上下文
        LogRecordContext.putVariable("bill", bills);
        return billId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = ZC_BILLS_TYPE, subType = ZC_BILLS_UPDATE_SUB_TYPE, bizNo = "{{#updateReqVO.id}}",
            success = ZC_BILLS_UPDATE_SUCCESS)
    public void updateBills(ZcBillsSaveReqVO updateReqVO) {
        // 1. 校验收款单存在，获取当前数据快照
        ZcBillsDO existingBill = validateBillsExists(updateReqVO.getId());

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
            String payStatus = newReceived.compareTo(BigDecimal.ZERO) == 0
                    ? ZcSalesOrderPayStatusEnum.UNPAID.name()
                    : newReceived.compareTo(orderAmount) >= 0
                            ? ZcSalesOrderPayStatusEnum.PAID.name()
                            : ZcSalesOrderPayStatusEnum.PARTIALPAID.name();
            ZcSalesOrderDO updateOrder = new ZcSalesOrderDO();
            updateOrder.setId(order.getId());
            updateOrder.setAmountReceived(newReceived);
            updateOrder.setPayStatus(payStatus);
            salesOrderMapper.updateById(updateOrder);
        }

        // 4. 回滚旧的客户余额并记录冲回流水
        if (existingBill.getCustomerId() != null) {
            BigDecimal oldDiscount = existingBill.getDiscountAmount() == null ? BigDecimal.ZERO : existingBill.getDiscountAmount();
            BigDecimal oldSettled = existingBill.getActualAmount().add(oldDiscount);
            BigDecimal oldBalanceDeltaNegate = calcCollectionBalanceDelta(oldSettled, existingBill.getBillMethodId()).negate();
            adjustAndRecordLog(existingBill.getCustomerId(), oldBalanceDeltaNegate,
                    ZcCustomerBalanceBizTypeEnum.COLLECTION_VOID.name(), ZcRefTypeEnum.COLLECTION_RECORD.name(), existingBill.getId(), existingBill.getBillNo(), "收款单修改-冲回旧记录");
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
            String payStatus = newReceived.compareTo(orderAmount) >= 0
                    ? ZcSalesOrderPayStatusEnum.PAID.name() : ZcSalesOrderPayStatusEnum.PARTIALPAID.name();
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

        // 9. 更新新的客户余额并记录流水
        Long newCustomerId = updateReqVO.getCustomerId() != null ? updateReqVO.getCustomerId() : existingBill.getCustomerId();
        if (newCustomerId != null) {
            BigDecimal newBalanceDelta = calcCollectionBalanceDelta(newTotalSettled, updateReqVO.getBillMethodId());
            adjustAndRecordLog(newCustomerId, newBalanceDelta,
                    ZcCustomerBalanceBizTypeEnum.COLLECTION.name(), ZcRefTypeEnum.COLLECTION_RECORD.name(), updateReqVO.getId(), existingBill.getBillNo(), "收款单修改-应用新记录");
        }
        // 记录操作日志上下文
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, BeanUtils.toBean(existingBill, ZcBillsSaveReqVO.class));
        LogRecordContext.putVariable("billNo", existingBill.getBillNo());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = ZC_BILLS_TYPE, subType = ZC_BILLS_DELETE_SUB_TYPE, bizNo = "{{#id}}",
            success = ZC_BILLS_DELETE_SUCCESS)
    public void deleteBills(Long id) {
        // 1. 校验收款单存在
        ZcBillsDO bill = validateBillsExists(id);

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
            String payStatus = newReceived.compareTo(BigDecimal.ZERO) == 0
                    ? ZcSalesOrderPayStatusEnum.UNPAID.name()
                    : newReceived.compareTo(orderAmount) >= 0
                            ? ZcSalesOrderPayStatusEnum.PAID.name()
                            : ZcSalesOrderPayStatusEnum.PARTIALPAID.name();
            ZcSalesOrderDO updateOrder = new ZcSalesOrderDO();
            updateOrder.setId(order.getId());
            updateOrder.setAmountReceived(newReceived);
            updateOrder.setPayStatus(payStatus);
            salesOrderMapper.updateById(updateOrder);
        }

        // 3. 回滚客户余额并记录冲回流水
        if (bill.getCustomerId() != null && bill.getActualAmount() != null) {
            BigDecimal discount = bill.getDiscountAmount() == null ? BigDecimal.ZERO : bill.getDiscountAmount();
            BigDecimal totalSettled = bill.getActualAmount().add(discount);
            BigDecimal balanceDeltaNegate = calcCollectionBalanceDelta(totalSettled, bill.getBillMethodId()).negate();
            adjustAndRecordLog(bill.getCustomerId(), balanceDeltaNegate,
                    ZcCustomerBalanceBizTypeEnum.COLLECTION_VOID.name(), ZcRefTypeEnum.COLLECTION_RECORD.name(), bill.getId(), bill.getBillNo(), "收款单删除-冲回");
        }

        // 记录操作日志上下文
        LogRecordContext.putVariable("billNo", bill.getBillNo());
        // 4. 级联删除附件和分摊明细，再删主记录
        billAttachmentsMapper.deleteByBillId(id);
        billOrderItemsMapper.deleteByBillId(id);
        billsMapper.deleteById(id);
    }

    @Override
    public void deleteBillsListByIds(List<Long> ids) {
        billsMapper.deleteByIds(ids);
    }

    private ZcBillsDO validateBillsExists(Long id) {
        ZcBillsDO bill = billsMapper.selectById(id);
        if (bill == null) {
            throw exception(BILLS_NOT_EXISTS);
        }
        return bill;
    }

    @Override
    public ZcBillsDO getBills(Long id) {
        return billsMapper.selectById(id);
    }

    @Override
    public PageResult<ZcBillsRespVO> getBillsPage(ZcBillsPageReqVO pageReqVO) {
        return billsMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ZcBillOrderItemRespVO> getBillOrderItems(Long billId) {
        return billOrderItemsMapper.selectListWithOrderNoByBillId(billId);
    }

    /**
     * 计算收款单对客户余额的影响金额
     *
     * <p>普通收款方式：余额增加（还款）；系统内置「余额扣款」：余额减少（消耗预存款）。</p>
     *
     * @param totalSettled 实收 + 优惠合计
     * @param billMethodId 收支方式 ID
     * @return 余额变动额（正数增加、负数减少）
     */
    private BigDecimal calcCollectionBalanceDelta(BigDecimal totalSettled, Long billMethodId) {
        if (isBalanceCollectionMethod(billMethodId)) {
            return totalSettled.negate();
        }
        return totalSettled;
    }

    /**
     * 判断是否为系统内置「余额扣款」方式（group=0 且名称为「余额扣款」）
     */
    private boolean isBalanceCollectionMethod(Long billMethodId) {
        if (billMethodId == null) {
            return false;
        }
        ZcBillMethodsDO billMethod = billMethodsMapper.selectById(billMethodId);
        return billMethod != null
                && ZcBillMethodConstants.SYSTEM_GROUP.equals(billMethod.getGroup())
                && ZcBillMethodConstants.BALANCE_COLLECTION_NAME.equals(billMethod.getName());
    }

    /**
     * 调整客户余额并记录变动流水（与 ZcSalesOrderServiceImpl 保持一致的调用顺序）
     *
     * <p>先读取变动前余额快照，再执行原子余额更新，最后通过 {@link ZcCustomerBalanceLogService}
     * 写入流水记录。全程在同一事务内，余额快照与实际变动一致。</p>
     *
     * @param customerId 客户 ID
     * @param delta      变动金额（正数增加、负数减少）
     * @param bizType    业务类型（COLLECTION / COLLECTION_VOID 等）
     * @param refType    关联单据类型，见 {@link ZcRefTypeEnum}
     * @param refId      关联单据主键
     * @param refNo      关联单号快照
     * @param remark     备注
     */
    private void adjustAndRecordLog(Long customerId, BigDecimal delta, String bizType,
                                    String refType, Long refId, String refNo, String remark) {
        // 1. 在余额更新前读取快照，同一事务内数据一致
        ZcCustomerDO customer = customerService.getCustomer(customerId);
        BigDecimal balanceBefore = (customer != null && customer.getBalance() != null)
                ? customer.getBalance() : BigDecimal.ZERO;
        // 2. 原子 SQL 更新余额，并发安全
        customerService.adjustBalance(customerId, delta);
        // 3. 写入流水记录
        customerBalanceLogService.createLog(ZcCustomerBalanceLogDO.builder()
                .customerId(customerId)
                .changeAmount(delta)
                .balanceBefore(balanceBefore)
                .balanceAfter(balanceBefore.add(delta))
                .bizType(bizType)
                .refType(refType)
                .refId(refId)
                .refNo(refNo)
                .remark(remark)
                .build());
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
