package cn.iocoder.yudao.module.zc.dal.mysql.processnode;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
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
     * 查询订单的全部工序记录（含操作人昵称），按创建时间升序排列
     *
     * @param orderId 订单 ID
     * @return 工序记录列表
     */
    List<ZcOrderProcessRecordRespVO> selectListWithUserByOrderId(@Param("orderId") Long orderId);

}
