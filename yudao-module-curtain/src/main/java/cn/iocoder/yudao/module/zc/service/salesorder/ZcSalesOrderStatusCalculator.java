package cn.iocoder.yudao.module.zc.service.salesorder;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZCSalesOrderMaterialDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderCurtainDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderStructureDO;
import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZCSalesOrderMaterialMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZcSalesOrderCurtainMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZcSalesOrderMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZcSalesOrderStructureMapper;
import cn.iocoder.yudao.module.zc.enums.ZcSalesOrderMaterialStatusEnum;
import cn.iocoder.yudao.module.zc.enums.ZcSalesOrderStatusEnum;

/**
 * 成品订单四层结构状态聚合计算器
 *
 * <p>层级与联动规则：
 * <ul>
 *   <li>L4 用料 {@code zc_sales_order_material}：裁剪手动更新 NOT_PEILIAO / HAVE_PEILIAO</li>
 *   <li>L2 窗帘 {@code zc_sales_order_curtain}：由下属用料聚合配料状态；打包/发货手动更新</li>
 *   <li>L1 订单 {@code zc_sales_order}：由所有窗帘行状态聚合；确认/完成手动更新</li>
 *   <li>L3 结构 {@code zc_sales_order_structure}：无状态字段</li>
 * </ul>
 * </p>
 */
@Component
public class ZcSalesOrderStatusCalculator {

    @Resource
    private ZcSalesOrderCurtainMapper salesOrderCurtainMapper;
    @Resource
    private ZcSalesOrderStructureMapper salesOrderStructureMapper;
    @Resource
    private ZCSalesOrderMaterialMapper salesOrderMaterialMapper;
    @Resource
    private ZcSalesOrderMapper salesOrderMapper;

    /**
     * 裁剪/撤销裁剪后：先联动更新所属窗帘行配料状态，再聚合更新订单主表状态
     *
     * @param orderId          销售订单 ID
     * @param orderStructureId 用料所属结构行 ID，可为 null（无法定位窗帘时仅更新订单）
     */
    public void syncAfterMaterialChange(Long orderId, Long orderStructureId) {
        Long curtainId = resolveCurtainId(orderStructureId);
        if (curtainId != null) {
            syncCurtainPeiliaoStatus(curtainId);
        }
        syncOrderStatus(orderId);
    }

    /**
     * 按订单 ID 查询最新窗帘行并聚合订单状态
     */
    public String calculateByOrderId(Long orderId) {
        List<ZcSalesOrderCurtainDO> curtains = salesOrderCurtainMapper.selectListByOrderId(orderId);
        return calculateOrderStatusFromCurtains(curtains);
    }

    /**
     * 聚合并写入订单主表状态
     *
     * @return 写入后的订单状态码
     */
    public String syncOrderStatus(Long orderId) {
        String status = calculateByOrderId(orderId);
        salesOrderMapper.updateStatusById(orderId, status);
        return status;
    }

    /**
     * 根据窗帘行下所有用料明细，计算窗帘行配料状态
     *
     * @param curtainId 窗帘行 ID
     * @return NOT_PEILIAO / BUFEN_PEILIAO / HAVE_PEILIAO
     */
    public String calculateCurtainPeiliaoStatusByCurtainId(Long curtainId) {
        return calculateCurtainPeiliaoStatus(selectMaterialsByCurtainId(curtainId));
    }

    /**
     * 若窗帘行处于配料阶段，则按用料明细重算并回写配料状态；已打包/已发货的窗帘行不覆盖
     */
    public void syncCurtainPeiliaoStatus(Long curtainId) {
        ZcSalesOrderCurtainDO curtain = salesOrderCurtainMapper.selectById(curtainId);
        if (curtain == null || isCurtainFulfillmentStatus(curtain.getStatus())) {
            return;
        }
        String newStatus = calculateCurtainPeiliaoStatusByCurtainId(curtainId);
        if (!newStatus.equals(curtain.getStatus())) {
            ZcSalesOrderCurtainDO updateObj = new ZcSalesOrderCurtainDO();
            updateObj.setId(curtainId);
            updateObj.setStatus(newStatus);
            salesOrderCurtainMapper.updateById(updateObj);
        }
    }

    /**
     * 根据所有窗帘行状态聚合订单主表状态
     *
     * <p>优先级：发货 &gt; 打包 &gt; 配料 &gt; 已确认</p>
     */
    public String calculateOrderStatusFromCurtains(List<ZcSalesOrderCurtainDO> curtains) {
        if (curtains.isEmpty()) {
            return ZcSalesOrderStatusEnum.CONFIRMED.name();
        }
        long total = curtains.size();

        long fahuoCount = curtains.stream()
                .filter(c -> ZcSalesOrderStatusEnum.FAHUO.name().equals(c.getStatus()))
                .count();
        if (fahuoCount == total) {
            return ZcSalesOrderStatusEnum.FAHUO.name();
        }
        if (fahuoCount > 0) {
            return ZcSalesOrderStatusEnum.BUFEN_FAHUO.name();
        }

        long dabaoCount = curtains.stream()
                .filter(c -> ZcSalesOrderStatusEnum.DABAO.name().equals(c.getStatus()))
                .count();
        if (dabaoCount == total) {
            return ZcSalesOrderStatusEnum.DABAO.name();
        }
        if (dabaoCount > 0) {
            return ZcSalesOrderStatusEnum.BUFEN_DABAO.name();
        }

        long fullPeiliaoCount = curtains.stream()
                .filter(c -> ZcSalesOrderStatusEnum.HAVE_PEILIAO.name().equals(c.getStatus()))
                .count();
        if (fullPeiliaoCount == total) {
            return ZcSalesOrderStatusEnum.HAVE_PEILIAO.name();
        }
        long hasPeiliaoCount = curtains.stream()
                .filter(this::isCurtainPeiliaoProgress)
                .count();
        if (hasPeiliaoCount > 0) {
            return ZcSalesOrderStatusEnum.BUFEN_PEILIAO.name();
        }
        return ZcSalesOrderStatusEnum.CONFIRMED.name();
    }

    /**
     * 根据窗帘行下属用料明细计算配料状态
     */
    public String calculateCurtainPeiliaoStatus(List<ZCSalesOrderMaterialDO> materials) {
        if (materials.isEmpty()) {
            return ZcSalesOrderStatusEnum.NOT_PEILIAO.name();
        }
        long total = materials.size();
        long peiliaoCount = materials.stream()
                .filter(m -> ZcSalesOrderMaterialStatusEnum.HAVE_PEILIAO.name().equals(m.getStatus()))
                .count();
        if (peiliaoCount == total) {
            return ZcSalesOrderStatusEnum.HAVE_PEILIAO.name();
        }
        if (peiliaoCount > 0) {
            return ZcSalesOrderStatusEnum.BUFEN_PEILIAO.name();
        }
        return ZcSalesOrderStatusEnum.NOT_PEILIAO.name();
    }

    private List<ZCSalesOrderMaterialDO> selectMaterialsByCurtainId(Long curtainId) {
        List<ZcSalesOrderStructureDO> structures = salesOrderStructureMapper.selectListByOrderCurtainId(curtainId);
        if (structures.isEmpty()) {
            return Collections.emptyList();
        }
        List<Long> structureIds = structures.stream()
                .map(ZcSalesOrderStructureDO::getId)
                .collect(Collectors.toList());
        return salesOrderMaterialMapper.selectListByStructureIds(structureIds);
    }

    private Long resolveCurtainId(Long orderStructureId) {
        if (orderStructureId == null) {
            return null;
        }
        ZcSalesOrderStructureDO structure = salesOrderStructureMapper.selectById(orderStructureId);
        return structure != null ? structure.getOrderCurtainId() : null;
    }

    /** 窗帘行是否已进入打包/发货阶段（配料状态不再自动覆盖） */
    private boolean isCurtainFulfillmentStatus(String status) {
        return ZcSalesOrderStatusEnum.DABAO.name().equals(status)
                || ZcSalesOrderStatusEnum.FAHUO.name().equals(status);
    }

    /** 窗帘行是否已有配料进度（部分配料或已全部配料） */
    private boolean isCurtainPeiliaoProgress(ZcSalesOrderCurtainDO curtain) {
        return ZcSalesOrderStatusEnum.BUFEN_PEILIAO.name().equals(curtain.getStatus())
                || ZcSalesOrderStatusEnum.HAVE_PEILIAO.name().equals(curtain.getStatus());
    }

}
