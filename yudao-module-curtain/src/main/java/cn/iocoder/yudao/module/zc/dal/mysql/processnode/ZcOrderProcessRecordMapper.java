package cn.iocoder.yudao.module.zc.dal.mysql.processnode;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.zc.controller.admin.processnode.vo.ZcOrderProcessRecordMasterMaterialRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.processnode.vo.ZcOrderProcessRecordRespVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.processnode.ZcOrderProcessRecordDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单工序记录 Mapper
 *
 * @author 01Coder
 */
@Mapper
public interface ZcOrderProcessRecordMapper extends BaseMapperX<ZcOrderProcessRecordDO> {

    List<ZcOrderProcessRecordRespVO> selectListWithVO(@Param("orderId") Long orderId,
                                                       @Param("masterId") Long masterId,
                                                       @Param("curtainId") Long curtainId,
                                                       @Param("structureId") Long structureId,
                                                       @Param("materialId") Long materialId,
                                                       @Param("nodeId") Long nodeId,
                                                       @Param("groups") List<Integer> groups);

    /**
     * 查询指定范围内某工序节点是否已存在有效记录（status=1，未撤销）
     *
     * <p>orderId 必填；curtainId / structureId / materialId 不为 null 时才加入条件，
     * 实现"传了哪些就精确到哪个层级"的重复校验。</p>
     *
     * @param orderId     订单 ID（必填）
     * @param curtainId   窗帘行 ID（可为 null）
     * @param structureId 结构行 ID（可为 null）
     * @param materialId  用料明细 ID（可为 null）
     * @param nodeId      工序节点 ID（必填）
     * @return 存在则返回记录，否则返回 null
     */
    default ZcOrderProcessRecordDO selectCompletedRecord(Long orderId, Long curtainId,
                                                         Long structureId, Long materialId, Long nodeId) {
        return selectOne(new LambdaQueryWrapperX<ZcOrderProcessRecordDO>()
                .eq(ZcOrderProcessRecordDO::getOrderId, orderId)
                .eqIfPresent(ZcOrderProcessRecordDO::getCurtainId, curtainId)
                .eqIfPresent(ZcOrderProcessRecordDO::getStructureId, structureId)
                .eqIfPresent(ZcOrderProcessRecordDO::getMaterialId, materialId)
                .eq(ZcOrderProcessRecordDO::getNodeId, nodeId)
                .eq(ZcOrderProcessRecordDO::getStatus, 1));
    }

    /**
     * 统计某操作员在指定工序节点下的工序次数与用料合计
     *
     * <p>仅统计完成状态（status=1）的工序记录，用料通过节点绑定的组件与订单用料明细匹配后累加。</p>
     *
     * @param masterId       主操作人员 ID
     * @param nodeId         工序节点 ID
     * @param beginCreateTime 创建时间范围（开始），可为 null
     * @param endCreateTime   创建时间范围（结束），可为 null
     * @return 统计结果（工序次数、用料合计）
     */
    ZcOrderProcessRecordMasterMaterialRespVO selectMasterMaterialStat(@Param("masterId") Long masterId,
                                                                       @Param("nodeId") Long nodeId,
                                                                       @Param("beginCreateTime") LocalDateTime beginCreateTime,
                                                                       @Param("endCreateTime") LocalDateTime endCreateTime);

}
