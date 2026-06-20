package cn.iocoder.yudao.module.zc.dal.mysql.processnode;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.zc.controller.admin.processnode.vo.ZcOrderProcessRecordRespVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.processnode.ZcOrderProcessRecordDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单工序记录 Mapper
 *
 * @author 01Coder
 */
@Mapper
public interface ZcOrderProcessRecordMapper extends BaseMapperX<ZcOrderProcessRecordDO> {

    /**
     * 查询订单的工序记录（含操作人名称），按创建时间降序排列
     *
     * <p>关联 {@code zc_process_node}，按 {@code group} 多选筛选节点记录。</p>
     *
     * @param orderId     订单 ID，为 null 时不过滤
     * @param masterId    主操作人员 ID，为 null 时不过滤
     * @param curtainId   窗帘行 ID，为 null 时不过滤
     * @param structureId 结构行 ID，为 null 时不过滤
     * @param materialId  用料明细 ID，为 null 时不过滤
     * @param nodeId      工序节点 ID，为 null 时不过滤
     * @param groups      工序节点分组多选（0=系统配置，1=手工配置）
     * @return 工序记录列表
     */
    List<ZcOrderProcessRecordRespVO> selectListWithUserByOrderId(@Param("orderId") Long orderId,
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

}
