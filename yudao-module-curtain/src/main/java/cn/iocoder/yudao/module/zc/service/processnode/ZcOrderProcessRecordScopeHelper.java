package cn.iocoder.yudao.module.zc.service.processnode;

import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZCSalesOrderMaterialDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderCurtainDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderStructureDO;
import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZCSalesOrderMaterialMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZcSalesOrderCurtainMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZcSalesOrderStructureMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;
import java.util.Objects;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;

/**
 * 工序记录定位 ID 归一化工具
 *
 * <p>按最细粒度补齐/裁剪 curtainId、structureId、materialId，保证层级完整且互不越界：</p>
 * <ul>
 *   <li>用料级：orderId + curtainId + structureId + materialId</li>
 *   <li>结构级：orderId + curtainId + structureId（materialId 置 null）</li>
 *   <li>窗帘级：orderId + curtainId（structureId、materialId 置 null）</li>
 *   <li>订单级：仅 orderId</li>
 * </ul>
 */
@Component
public class ZcOrderProcessRecordScopeHelper {

    @Resource
    private ZCSalesOrderMaterialMapper materialMapper;
    @Resource
    private ZcSalesOrderStructureMapper structureMapper;
    @Resource
    private ZcSalesOrderCurtainMapper curtainMapper;

    /**
     * 工序记录定位范围
     */
    @Data
    @AllArgsConstructor
    public static class Scope {
        private Long orderId;
        private Long curtainId;
        private Long structureId;
        private Long materialId;
    }

    /**
     * 归一化工序记录的定位 ID
     *
     * @param orderId     销售订单 ID（必填）
     * @param curtainId   窗帘行 ID（可为空）
     * @param structureId 结构行 ID（可为空）
     * @param materialId  用料明细 ID（可为空）
     * @return 归一化后的定位范围
     */
    public Scope normalize(Long orderId, Long curtainId, Long structureId, Long materialId) {
        if (materialId != null) {
            return normalizeByMaterial(orderId, curtainId, structureId, materialId);
        }
        if (structureId != null) {
            return normalizeByStructure(orderId, curtainId, structureId);
        }
        if (curtainId != null) {
            return normalizeByCurtain(orderId, curtainId);
        }
        return new Scope(orderId, null, null, null);
    }

    private Scope normalizeByMaterial(Long orderId, Long curtainId, Long structureId, Long materialId) {
        ZCSalesOrderMaterialDO material = materialMapper.selectById(materialId);
        if (material == null) {
            throw exception(ZC_SALES_ORDER_MATERIAL_NOT_EXISTS);
        }
        validateOrderId(orderId, material.getOrderId());

        Long resolvedStructureId = material.getOrderStructureId();
        if (structureId != null && !Objects.equals(structureId, resolvedStructureId)) {
            throw exception(ORDER_PROCESS_RECORD_SCOPE_MISMATCH);
        }

        Long resolvedCurtainId = resolveCurtainIdByStructure(orderId, resolvedStructureId);
        if (curtainId != null && resolvedCurtainId != null && !Objects.equals(curtainId, resolvedCurtainId)) {
            throw exception(ORDER_PROCESS_RECORD_SCOPE_MISMATCH);
        }

        return new Scope(orderId, resolvedCurtainId, resolvedStructureId, materialId);
    }

    private Scope normalizeByStructure(Long orderId, Long curtainId, Long structureId) {
        ZcSalesOrderStructureDO structure = structureMapper.selectById(structureId);
        if (structure == null) {
            throw exception(SALES_ORDER_STRUCTURE_NOT_EXISTS);
        }
        validateOrderId(orderId, structure.getOrderId());

        Long resolvedCurtainId = structure.getOrderCurtainId();
        if (curtainId != null && resolvedCurtainId != null && !Objects.equals(curtainId, resolvedCurtainId)) {
            throw exception(ORDER_PROCESS_RECORD_SCOPE_MISMATCH);
        }
        if (resolvedCurtainId != null) {
            validateCurtainBelongsToOrder(orderId, resolvedCurtainId);
        }

        return new Scope(orderId, resolvedCurtainId, structureId, null);
    }

    private Scope normalizeByCurtain(Long orderId, Long curtainId) {
        validateCurtainBelongsToOrder(orderId, curtainId);
        return new Scope(orderId, curtainId, null, null);
    }

    private Long resolveCurtainIdByStructure(Long orderId, Long structureId) {
        if (structureId == null) {
            return null;
        }
        ZcSalesOrderStructureDO structure = structureMapper.selectById(structureId);
        if (structure == null) {
            throw exception(SALES_ORDER_STRUCTURE_NOT_EXISTS);
        }
        validateOrderId(orderId, structure.getOrderId());
        Long curtainId = structure.getOrderCurtainId();
        if (curtainId != null) {
            validateCurtainBelongsToOrder(orderId, curtainId);
        }
        return curtainId;
    }

    private void validateCurtainBelongsToOrder(Long orderId, Long curtainId) {
        ZcSalesOrderCurtainDO curtain = curtainMapper.selectById(curtainId);
        if (curtain == null) {
            throw exception(SALES_ORDER_CURTAIN_NOT_EXISTS);
        }
        validateOrderId(orderId, curtain.getOrderId());
    }

    private void validateOrderId(Long expectedOrderId, Long actualOrderId) {
        if (!Objects.equals(expectedOrderId, actualOrderId)) {
            throw exception(ORDER_PROCESS_RECORD_SCOPE_MISMATCH);
        }
    }

}
