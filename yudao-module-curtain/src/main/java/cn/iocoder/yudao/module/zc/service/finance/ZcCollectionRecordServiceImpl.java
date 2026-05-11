package cn.iocoder.yudao.module.zc.service.finance;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.module.zc.controller.admin.vo.finance.ZcCollectionDetailRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.finance.ZcCollectionPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.finance.ZcCollectionSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.finance.ZcCollectionOrderAllocDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.finance.ZcCollectionRecordDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.sale.ZcSalesOrderDO;
import cn.iocoder.yudao.module.zc.dal.mysql.finance.ZcCollectionOrderAllocMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.finance.ZcCollectionRecordMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.sale.ZcSalesOrderMapper;
import cn.iocoder.yudao.module.zc.dal.redis.ZcNoRedisDAO;
import cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants;
import cn.iocoder.yudao.module.zc.enums.ZcBizConstants;
import cn.iocoder.yudao.module.zc.service.balance.ZcCustomerBalanceService;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service
@Validated
public class ZcCollectionRecordServiceImpl implements ZcCollectionRecordService {

    @Resource
    private ZcCollectionRecordMapper collectionRecordMapper;
    @Resource
    private ZcCollectionOrderAllocMapper collectionOrderAllocMapper;
    @Resource
    private ZcSalesOrderMapper salesOrderMapper;
    @Resource
    private ZcNoRedisDAO noRedisDAO;
    @Resource
    private ZcCustomerBalanceService customerBalanceService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createCollection(ZcCollectionSaveReqVO reqVO) {
        BigDecimal sum = CollUtil.emptyIfNull(reqVO.getAllocs()).stream()
                .map(ZcCollectionSaveReqVO.AllocItem::getPayAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (sum.compareTo(reqVO.getAmount()) != 0) {
            throw exception(ErrorCodeConstants.COLLECTION_ALLOC_AMOUNT_MISMATCH);
        }

        ZcCollectionRecordDO rec = new ZcCollectionRecordDO();
        rec.setCollectionNo(noRedisDAO.generate(ZcNoRedisDAO.COLLECTION_PREFIX));
        rec.setCollectionDate(reqVO.getCollectionDate());
        rec.setCollectionerId(reqVO.getCollectionerId());
        rec.setCustomerId(reqVO.getCustomerId());
        rec.setAmount(reqVO.getAmount());
        rec.setDiscountAmount(reqVO.getDiscountAmount() != null ? reqVO.getDiscountAmount() : BigDecimal.ZERO);
        rec.setPaymentId(reqVO.getPaymentId());
        rec.setImage1(reqVO.getImage1());
        rec.setImage2(reqVO.getImage2());
        rec.setNote(reqVO.getNote());
        collectionRecordMapper.insert(rec);

        for (ZcCollectionSaveReqVO.AllocItem item : reqVO.getAllocs()) {
            ZcSalesOrderDO order = salesOrderMapper.selectById(item.getOrderId());
            if (order == null || !order.getCustomerId().equals(reqVO.getCustomerId())) {
                throw exception(ErrorCodeConstants.COLLECTION_ORDER_NOT_FOUND);
            }
            ZcCollectionOrderAllocDO alloc = new ZcCollectionOrderAllocDO();
            alloc.setCollectionId(rec.getId());
            alloc.setOrderId(item.getOrderId());
            alloc.setPayAmount(item.getPayAmount());
            collectionOrderAllocMapper.insert(alloc);

            BigDecimal baseRecv = order.getAmountReceived() != null ? order.getAmountReceived() : BigDecimal.ZERO;
            BigDecimal newRecv = baseRecv.add(item.getPayAmount());
            String payStatus = resolvePayStatus(order.getAmount(), newRecv);
            salesOrderMapper.updateById(new ZcSalesOrderDO().setId(order.getId())
                    .setAmountReceived(newRecv)
                    .setPayStatus(payStatus));
        }

        BigDecimal credit = reqVO.getAmount().add(rec.getDiscountAmount());
        customerBalanceService.changeBalance(reqVO.getCustomerId(), credit,
                ZcBizConstants.BIZ_COLLECTION, ZcBizConstants.REF_COLLECTION,
                rec.getId(), rec.getCollectionNo(), "收款入账");

        return rec.getId();
    }

    private static String resolvePayStatus(BigDecimal total, BigDecimal received) {
        if (total == null || total.signum() <= 0) {
            return ZcBizConstants.PAY_STATUS_PAID;
        }
        int c = received.compareTo(total);
        if (c >= 0) {
            return ZcBizConstants.PAY_STATUS_PAID;
        }
        if (received.signum() > 0) {
            return ZcBizConstants.PAY_STATUS_PARTIAL;
        }
        return ZcBizConstants.PAY_STATUS_UNPAID;
    }

    @Override
    public ZcCollectionRecordDO getCollection(Long id) {
        return collectionRecordMapper.selectById(id);
    }

    @Override
    public ZcCollectionDetailRespVO getCollectionDetail(Long id) {
        ZcCollectionRecordDO rec = collectionRecordMapper.selectById(id);
        if (rec == null) {
            return null;
        }
        ZcCollectionDetailRespVO vo = new ZcCollectionDetailRespVO();
        vo.setId(rec.getId());
        vo.setCollectionNo(rec.getCollectionNo());
        vo.setCollectionDate(rec.getCollectionDate());
        vo.setCollectionerId(rec.getCollectionerId());
        vo.setCustomerId(rec.getCustomerId());
        vo.setAmount(rec.getAmount());
        vo.setDiscountAmount(rec.getDiscountAmount());
        vo.setPaymentId(rec.getPaymentId());
        vo.setImage1(rec.getImage1());
        vo.setImage2(rec.getImage2());
        vo.setNote(rec.getNote());
        vo.setAllocs(collectionOrderAllocMapper.selectList(
                        new LambdaQueryWrapperX<ZcCollectionOrderAllocDO>()
                                .eq(ZcCollectionOrderAllocDO::getCollectionId, id))
                .stream()
                .map(a -> {
                    ZcCollectionDetailRespVO.AllocItem item = new ZcCollectionDetailRespVO.AllocItem();
                    item.setOrderId(a.getOrderId());
                    item.setPayAmount(a.getPayAmount());
                    return item;
                })
                .collect(Collectors.toList()));
        return vo;
    }

    @Override
    public PageResult<ZcCollectionRecordDO> getCollectionPage(ZcCollectionPageReqVO pageReqVO) {
        return collectionRecordMapper.selectPage(pageReqVO, new LambdaQueryWrapperX<ZcCollectionRecordDO>()
                .eqIfPresent(ZcCollectionRecordDO::getCustomerId, pageReqVO.getCustomerId())
                .orderByDesc(ZcCollectionRecordDO::getId));
    }

}
