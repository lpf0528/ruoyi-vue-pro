package cn.iocoder.yudao.module.zc.service.processnode;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.zc.controller.admin.processnode.vo.ZcOrderProcessRecordRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZCSalesOrderMaterialDetailRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcSalesOrderCurtainDetailRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcSalesOrderCurtainProcessRecordRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcSalesOrderDetailRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcSalesOrderMaterialProcessRecordRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcSalesOrderProcessRecordDetailRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcSalesOrderStructureDetailRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcSalesOrderStructureProcessRecordRespVO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 销售订单工序记录详情组装工具
 *
 * <p>以订单完整详情为骨架，将工序记录挂载到对应窗帘/结构/用料层级，
 * 各层 {@code processRecords} 按创建时间升序排列。</p>
 */
public final class ZcSalesOrderProcessRecordBuilder {

    private ZcSalesOrderProcessRecordBuilder() {
    }

    /**
     * 将订单完整结构与工序记录组装为分层详情
     *
     * @param orderDetail 销售订单完整详情（含 curtains 三层嵌套）
     * @param records     扁平工序记录列表
     * @return 工序记录详情
     */
    public static ZcSalesOrderProcessRecordDetailRespVO build(ZcSalesOrderDetailRespVO orderDetail,
                                                              List<ZcOrderProcessRecordRespVO> records) {
        RecordBuckets buckets = partitionRecords(records);

        ZcSalesOrderProcessRecordDetailRespVO result = BeanUtils.toBean(orderDetail, ZcSalesOrderProcessRecordDetailRespVO.class);
        result.setOrderRecords(buckets.orderRecords);

        List<ZcSalesOrderCurtainProcessRecordRespVO> curtains = new ArrayList<>();
        if (CollUtil.isNotEmpty(orderDetail.getCurtains())) {
            for (ZcSalesOrderCurtainDetailRespVO curtain : orderDetail.getCurtains()) {
                curtains.add(buildCurtainItem(curtain, buckets));
            }
        }
        result.setCurtains(curtains);
        return result;
    }

    private static ZcSalesOrderCurtainProcessRecordRespVO buildCurtainItem(ZcSalesOrderCurtainDetailRespVO curtain,
                                                                           RecordBuckets buckets) {
        ZcSalesOrderCurtainProcessRecordRespVO item = BeanUtils.toBean(curtain, ZcSalesOrderCurtainProcessRecordRespVO.class);
        item.setCurtainName(curtain.getCurtainName());
        item.setProcessRecords(buckets.curtainRecords.getOrDefault(curtain.getId(), Collections.emptyList()));

        List<ZcSalesOrderStructureProcessRecordRespVO> structures = new ArrayList<>();
        if (CollUtil.isNotEmpty(curtain.getStructures())) {
            for (ZcSalesOrderStructureDetailRespVO structure : curtain.getStructures()) {
                structures.add(buildStructureItem(structure, buckets));
            }
        }
        item.setStructures(structures);
        return item;
    }

    private static ZcSalesOrderStructureProcessRecordRespVO buildStructureItem(ZcSalesOrderStructureDetailRespVO structure,
                                                                               RecordBuckets buckets) {
        ZcSalesOrderStructureProcessRecordRespVO item = BeanUtils.toBean(structure, ZcSalesOrderStructureProcessRecordRespVO.class);
        item.setStructureName(structure.getStructureName());
        item.setInstallProcessName(structure.getInstallProcessName());
        item.setProcessRecords(buckets.structureRecords.getOrDefault(structure.getId(), Collections.emptyList()));

        List<ZcSalesOrderMaterialProcessRecordRespVO> materials = new ArrayList<>();
        if (CollUtil.isNotEmpty(structure.getMaterials())) {
            for (ZCSalesOrderMaterialDetailRespVO material : structure.getMaterials()) {
                ZcSalesOrderMaterialProcessRecordRespVO materialItem =
                        BeanUtils.toBean(material, ZcSalesOrderMaterialProcessRecordRespVO.class);
                materialItem.setProcessRecords(buckets.materialRecords.getOrDefault(material.getId(), Collections.emptyList()));
                materials.add(materialItem);
            }
        }
        item.setMaterials(materials);
        return item;
    }

    private static RecordBuckets partitionRecords(List<ZcOrderProcessRecordRespVO> records) {
        RecordBuckets buckets = new RecordBuckets();
        if (CollUtil.isEmpty(records)) {
            return buckets;
        }
        for (ZcOrderProcessRecordRespVO record : records) {
            if (record.getCurtainId() == null) {
                buckets.orderRecords.add(record);
            } else if (record.getStructureId() == null) {
                buckets.curtainRecords.computeIfAbsent(record.getCurtainId(), k -> new ArrayList<>()).add(record);
            } else if (record.getMaterialId() == null) {
                buckets.structureRecords.computeIfAbsent(record.getStructureId(), k -> new ArrayList<>()).add(record);
            } else {
                buckets.materialRecords.computeIfAbsent(record.getMaterialId(), k -> new ArrayList<>()).add(record);
            }
        }
        sortAsc(buckets.orderRecords);
        buckets.curtainRecords.values().forEach(ZcSalesOrderProcessRecordBuilder::sortAsc);
        buckets.structureRecords.values().forEach(ZcSalesOrderProcessRecordBuilder::sortAsc);
        buckets.materialRecords.values().forEach(ZcSalesOrderProcessRecordBuilder::sortAsc);
        return buckets;
    }

    private static void sortAsc(List<ZcOrderProcessRecordRespVO> records) {
        records.sort(Comparator.comparing(ZcOrderProcessRecordRespVO::getCreateTime,
                Comparator.nullsLast(Comparator.naturalOrder())));
    }

    private static final class RecordBuckets {
        private final List<ZcOrderProcessRecordRespVO> orderRecords = new ArrayList<>();
        private final Map<Long, List<ZcOrderProcessRecordRespVO>> curtainRecords = new HashMap<>();
        private final Map<Long, List<ZcOrderProcessRecordRespVO>> structureRecords = new HashMap<>();
        private final Map<Long, List<ZcOrderProcessRecordRespVO>> materialRecords = new HashMap<>();
    }

}
