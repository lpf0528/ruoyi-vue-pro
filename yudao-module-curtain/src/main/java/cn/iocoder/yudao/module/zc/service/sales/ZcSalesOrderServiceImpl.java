package cn.iocoder.yudao.module.zc.service.sales;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.zc.controller.admin.vo.sales.ZcSalesOrderPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.sales.ZcSalesOrderRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.sales.ZcSalesOrderSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.progress.ZcSalesOrderProductionQueueDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.sale.*;
import cn.iocoder.yudao.module.zc.dal.mysql.progress.ZcSalesOrderProductionQueueMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.sale.*;
import cn.iocoder.yudao.module.zc.dal.redis.ZcNoRedisDAO;
import cn.iocoder.yudao.module.zc.enums.ZcBizConstants;
import cn.iocoder.yudao.module.zc.service.balance.ZcCustomerBalanceService;
import cn.iocoder.yudao.module.zc.service.progress.ZcOrderProgressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service
@Validated
public class ZcSalesOrderServiceImpl implements ZcSalesOrderService {

    @Resource
    private ZcSalesOrderMapper salesOrderMapper;
    @Resource
    private ZcSalesOrderCurtainMapper salesOrderCurtainMapper;
    @Resource
    private ZcSalesOrderCurtainStructureMapper salesOrderCurtainStructureMapper;
    @Resource
    private ZcSalesOrderCurtainStructureElementMapper salesOrderCurtainStructureElementMapper;
    @Resource
    private ZcSalesOrderProductMapper salesOrderProductMapper;
    @Resource
    private ZcNoRedisDAO noRedisDAO;
    @Resource
    private ZcCustomerBalanceService customerBalanceService;
    @Resource
    private ZcOrderProgressService orderProgressService;
    @Resource
    private ZcSalesOrderProductionQueueMapper salesOrderProductionQueueMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createSalesOrder(ZcSalesOrderSaveReqVO reqVO) {
        ZcSalesOrderDO order = BeanUtils.toBean(reqVO, ZcSalesOrderDO.class);
        order.setOrderNo(noRedisDAO.generate(ZcNoRedisDAO.SALES_ORDER_PREFIX));
        order.setPayStatus(ZcBizConstants.PAY_STATUS_UNPAID);
        order.setConfirmStatus(ZcBizConstants.CONFIRM_UNCONFIRMED);
        order.setAmountReceived(BigDecimal.ZERO);
        if (order.getFreight() == null) {
            order.setFreight(BigDecimal.ZERO);
        }
        if (order.getAmount() == null) {
            order.setAmount(calcAmount(reqVO));
        }
        salesOrderMapper.insert(order);
        Long orderId = order.getId();
        saveChildren(orderId, reqVO);
        return orderId;
    }

    private BigDecimal calcAmount(ZcSalesOrderSaveReqVO reqVO) {
        BigDecimal total = BigDecimal.ZERO;
        if (ZcBizConstants.ORDER_TYPE_CURTAIN.equals(reqVO.getTypes())) {
            for (ZcSalesOrderSaveReqVO.ZcSalesOrderCurtainSaveVO c : CollUtil.emptyIfNull(reqVO.getCurtains())) {
                if (c.getAmount() != null) {
                    total = total.add(c.getAmount());
                }
            }
        } else {
            for (ZcSalesOrderSaveReqVO.ZcSalesOrderProductLineSaveVO p : CollUtil.emptyIfNull(reqVO.getFabricLines())) {
                if (p.getAmount() != null) {
                    total = total.add(p.getAmount());
                }
            }
        }
        return total;
    }

    private void saveChildren(Long orderId, ZcSalesOrderSaveReqVO reqVO) {
        if (ZcBizConstants.ORDER_TYPE_CURTAIN.equals(reqVO.getTypes())) {
            for (ZcSalesOrderSaveReqVO.ZcSalesOrderCurtainSaveVO c : CollUtil.emptyIfNull(reqVO.getCurtains())) {
                ZcSalesOrderCurtainDO line = BeanUtils.toBean(c, ZcSalesOrderCurtainDO.class);
                line.setOrderId(orderId);
                line.setId(null);
                salesOrderCurtainMapper.insert(line);
                Long curtainId = line.getId();
                for (ZcSalesOrderSaveReqVO.ZcSalesOrderStructureSaveVO s : CollUtil.emptyIfNull(c.getStructures())) {
                    ZcSalesOrderCurtainStructureDO st = BeanUtils.toBean(s, ZcSalesOrderCurtainStructureDO.class);
                    st.setOrderId(orderId);
                    st.setOrderCurtainId(curtainId);
                    st.setId(null);
                    if (st.getIsShaping() == null) {
                        st.setIsShaping(false);
                    }
                    salesOrderCurtainStructureMapper.insert(st);
                    Long sid = st.getId();
                    for (ZcSalesOrderSaveReqVO.ZcSalesOrderElementSaveVO e : CollUtil.emptyIfNull(s.getElements())) {
                        ZcSalesOrderCurtainStructureElementDO el = BeanUtils.toBean(e, ZcSalesOrderCurtainStructureElementDO.class);
                        el.setOrderId(orderId);
                        el.setOrderCurtainStructureId(sid);
                        el.setId(null);
                        salesOrderCurtainStructureElementMapper.insert(el);
                    }
                }
            }
        } else {
            for (ZcSalesOrderSaveReqVO.ZcSalesOrderProductLineSaveVO p : CollUtil.emptyIfNull(reqVO.getFabricLines())) {
                ZcSalesOrderProductDO pl = BeanUtils.toBean(p, ZcSalesOrderProductDO.class);
                pl.setOrderId(orderId);
                pl.setId(null);
                salesOrderProductMapper.insert(pl);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSalesOrder(ZcSalesOrderSaveReqVO reqVO) {
        validateExists(reqVO.getId());
        ZcSalesOrderDO db = salesOrderMapper.selectById(reqVO.getId());
        if (!ZcBizConstants.CONFIRM_UNCONFIRMED.equals(db.getConfirmStatus())) {
            throw exception(ErrorCodeConstants.SALES_ORDER_CONFIRM_FAIL);
        }
        deleteChildren(reqVO.getId());
        ZcSalesOrderDO order = BeanUtils.toBean(reqVO, ZcSalesOrderDO.class);
        if (order.getAmount() == null) {
            order.setAmount(calcAmount(reqVO));
        }
        salesOrderMapper.updateById(order);
        saveChildren(reqVO.getId(), reqVO);
    }

    private void deleteChildren(Long orderId) {
        salesOrderCurtainStructureElementMapper.delete(new LambdaQueryWrapperX<ZcSalesOrderCurtainStructureElementDO>()
                .eq(ZcSalesOrderCurtainStructureElementDO::getOrderId, orderId));
        salesOrderCurtainStructureMapper.delete(new LambdaQueryWrapperX<ZcSalesOrderCurtainStructureDO>()
                .eq(ZcSalesOrderCurtainStructureDO::getOrderId, orderId));
        salesOrderCurtainMapper.delete(new LambdaQueryWrapperX<ZcSalesOrderCurtainDO>()
                .eq(ZcSalesOrderCurtainDO::getOrderId, orderId));
        salesOrderProductMapper.delete(new LambdaQueryWrapperX<ZcSalesOrderProductDO>()
                .eq(ZcSalesOrderProductDO::getOrderId, orderId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSalesOrder(Long id) {
        validateExists(id);
        ZcSalesOrderDO db = salesOrderMapper.selectById(id);
        if (!ZcBizConstants.CONFIRM_UNCONFIRMED.equals(db.getConfirmStatus())) {
            throw exception(ErrorCodeConstants.SALES_ORDER_CONFIRM_FAIL);
        }
        deleteChildren(id);
        salesOrderMapper.deleteById(id);
    }

    private void validateExists(Long id) {
        if (id == null || salesOrderMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.SALES_ORDER_NOT_EXISTS);
        }
    }

    @Override
    public ZcSalesOrderRespVO getSalesOrder(Long id) {
        ZcSalesOrderDO order = salesOrderMapper.selectById(id);
        if (order == null) {
            throw exception(ErrorCodeConstants.SALES_ORDER_NOT_EXISTS);
        }
        ZcSalesOrderRespVO vo = BeanUtils.toBean(order, ZcSalesOrderRespVO.class);
        if (ZcBizConstants.ORDER_TYPE_CURTAIN.equals(order.getTypes())) {
            List<ZcSalesOrderCurtainDO> curtains = salesOrderCurtainMapper.selectList(
                    new LambdaQueryWrapperX<ZcSalesOrderCurtainDO>()
                            .eq(ZcSalesOrderCurtainDO::getOrderId, id));
            vo.setCurtains(curtains.stream().map(c -> {
                ZcSalesOrderSaveReqVO.ZcSalesOrderCurtainSaveVO cv = BeanUtils.toBean(c, ZcSalesOrderSaveReqVO.ZcSalesOrderCurtainSaveVO.class);
                List<ZcSalesOrderCurtainStructureDO> sts = salesOrderCurtainStructureMapper.selectList(
                        new LambdaQueryWrapperX<ZcSalesOrderCurtainStructureDO>()
                                .eq(ZcSalesOrderCurtainStructureDO::getOrderCurtainId, c.getId()));
                cv.setStructures(sts.stream().map(s -> {
                    ZcSalesOrderSaveReqVO.ZcSalesOrderStructureSaveVO sv = BeanUtils.toBean(s, ZcSalesOrderSaveReqVO.ZcSalesOrderStructureSaveVO.class);
                    List<ZcSalesOrderCurtainStructureElementDO> els = salesOrderCurtainStructureElementMapper.selectList(
                            new LambdaQueryWrapperX<ZcSalesOrderCurtainStructureElementDO>()
                                    .eq(ZcSalesOrderCurtainStructureElementDO::getOrderCurtainStructureId, s.getId()));
                    sv.setElements(els.stream().map(e -> BeanUtils.toBean(e, ZcSalesOrderSaveReqVO.ZcSalesOrderElementSaveVO.class))
                            .collect(Collectors.toList()));
                    return sv;
                }).collect(Collectors.toList()));
                return cv;
            }).collect(Collectors.toList()));
        } else {
            List<ZcSalesOrderProductDO> ps = salesOrderProductMapper.selectList(
                    new LambdaQueryWrapperX<ZcSalesOrderProductDO>()
                            .eq(ZcSalesOrderProductDO::getOrderId, id));
            vo.setFabricLines(ps.stream().map(p -> BeanUtils.toBean(p, ZcSalesOrderSaveReqVO.ZcSalesOrderProductLineSaveVO.class))
                    .collect(Collectors.toList()));
        }
        return vo;
    }

    @Override
    public PageResult<ZcSalesOrderDO> getSalesOrderPage(ZcSalesOrderPageReqVO pageReqVO) {
        return salesOrderMapper.selectPage(pageReqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmSalesOrder(Long id) {
        ZcSalesOrderDO order = salesOrderMapper.selectById(id);
        if (order == null) {
            throw exception(ErrorCodeConstants.SALES_ORDER_NOT_EXISTS);
        }
        if (!ZcBizConstants.CONFIRM_UNCONFIRMED.equals(order.getConfirmStatus())) {
            throw exception(ErrorCodeConstants.SALES_ORDER_CONFIRM_FAIL);
        }
        BigDecimal amt = order.getAmount() != null ? order.getAmount() : BigDecimal.ZERO;
        customerBalanceService.changeBalance(order.getCustomerId(), amt.negate(),
                ZcBizConstants.BIZ_ORDER_CONFIRM, ZcBizConstants.REF_SALES_ORDER, order.getId(), order.getOrderNo(), "订单确认");
        salesOrderMapper.updateById(new ZcSalesOrderDO().setId(id)
                .setConfirmStatus(ZcBizConstants.CONFIRM_CONFIRMED)
                .setConfirmTime(LocalDateTime.now()));
        orderProgressService.appendLog(id, null, "ORDER_CONFIRM", "订单确认", "DONE",
                LocalDateTime.now(), null);
        orderProgressService.initProductionQueue(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelConfirmSalesOrder(Long id) {
        ZcSalesOrderDO order = salesOrderMapper.selectById(id);
        if (order == null) {
            throw exception(ErrorCodeConstants.SALES_ORDER_NOT_EXISTS);
        }
        if (!ZcBizConstants.CONFIRM_CONFIRMED.equals(order.getConfirmStatus())) {
            throw exception(ErrorCodeConstants.SALES_ORDER_CANCEL_CONFIRM_FAIL);
        }
        BigDecimal amt = order.getAmount() != null ? order.getAmount() : BigDecimal.ZERO;
        customerBalanceService.changeBalance(order.getCustomerId(), amt,
                ZcBizConstants.BIZ_ORDER_UNCONFIRM, ZcBizConstants.REF_SALES_ORDER, order.getId(), order.getOrderNo(), "取消订单确认");
        salesOrderProductionQueueMapper.delete(new LambdaQueryWrapperX<ZcSalesOrderProductionQueueDO>()
                .eq(ZcSalesOrderProductionQueueDO::getOrderId, id));
        salesOrderMapper.updateById(new ZcSalesOrderDO().setId(id)
                .setConfirmStatus(ZcBizConstants.CONFIRM_UNCONFIRMED)
                .setConfirmTime(null));
        orderProgressService.appendLog(id, null, "ORDER_UNCONFIRM", "取消订单确认", "DONE",
                LocalDateTime.now(), null);
    }

}
