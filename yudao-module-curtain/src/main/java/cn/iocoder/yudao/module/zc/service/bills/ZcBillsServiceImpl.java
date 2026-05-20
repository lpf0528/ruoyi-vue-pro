package cn.iocoder.yudao.module.zc.service.bills;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
import cn.iocoder.yudao.module.zc.dal.dataobject.customer.ZcCustomerDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.bills.ZcBillAttachmentsMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.bills.ZcBillOrderItemsMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.bills.ZcBillsMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.customer.ZcCustomerMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZcSalesOrderMapper;

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
    private ZcCustomerMapper customerMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createBills(ZcBillsSaveReqVO createReqVO) {
        // 1. 自动生成单号：SK{yyyyMMdd}-{6位累计序号}
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long billCount = billsMapper.selectCount(Wrappers.emptyWrapper());
        String billNo = String.format("SK%s-%06d", date, billCount + 1);

        // 2. 保存账单主记录，billUserId 取当前登录用户
        ZcBillsDO bills = BeanUtils.toBean(createReqVO, ZcBillsDO.class);
        bills.setBillNo(billNo);
        bills.setBillUserId(SecurityFrameworkUtils.getLoginUserId());
        if (bills.getDiscountAmount() == null) {
            bills.setDiscountAmount(BigDecimal.ZERO);
        }
        billsMapper.insert(bills);
        Long billId = bills.getId();

        // 3. 保存附件记录（根据扩展名判断类型：jpg/jpeg/png/gif/webp 为图片，其余为文件）
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

        // 4. 处理订单分摊明细：更新各订单已收金额与结算状态，并统计客户总收款
        BigDecimal totalAllocated = BigDecimal.ZERO;
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

            totalAllocated = totalAllocated.add(item.getAllocatedAmount());
        }

        // 5. 更新客户余额（将本次全部分摊金额累加至余额）
        if (createReqVO.getCustomerId() != null && totalAllocated.compareTo(BigDecimal.ZERO) > 0) {
            ZcCustomerDO customer = customerMapper.selectById(createReqVO.getCustomerId());
            if (customer != null) {
                BigDecimal currentBalance = customer.getBalance() == null
                        ? BigDecimal.ZERO : customer.getBalance();
                ZcCustomerDO updateCustomer = new ZcCustomerDO();
                updateCustomer.setId(customer.getId());
                updateCustomer.setBalance(currentBalance.add(totalAllocated));
                customerMapper.updateById(updateCustomer);
            }
        }

        return billId;
    }

    @Override
    public void updateBills(ZcBillsSaveReqVO updateReqVO) {
        // 校验存在
        validateBillsExists(updateReqVO.getId());
        // 更新
        ZcBillsDO updateObj = BeanUtils.toBean(updateReqVO, ZcBillsDO.class);
        billsMapper.updateById(updateObj);
    }

    @Override
    public void deleteBills(Long id) {
        // 校验存在
        validateBillsExists(id);
        // 删除
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
    public PageResult<ZcBillsDO> getBillsPage(ZcBillsPageReqVO pageReqVO) {
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
