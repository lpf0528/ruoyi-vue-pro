package cn.iocoder.yudao.module.zc.dal.mysql.productbatch;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.productbatch.ZcProductBatchDO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import cn.iocoder.yudao.module.zc.controller.admin.productbatch.vo.*;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

/**
 * 产品批次 Mapper
 *
 * @author 01Coder
 */
@Mapper
public interface ZcProductBatchMapper extends BaseMapperX<ZcProductBatchDO> {

    @Select("SELECT COUNT(*) + 1 FROM zc_product_batch WHERE product_id = #{productId} AND DATE(create_time) = CURDATE() AND deleted = 0")
    Integer countTodayBatchSeqByProductId(@Param("productId") Long productId);

    default List<Long> selectProductIdsWithBatch(List<Long> productIds) {
        return selectList(new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<ZcProductBatchDO>()
                .select(ZcProductBatchDO::getProductId)
                .in(ZcProductBatchDO::getProductId, productIds)
                .groupBy(ZcProductBatchDO::getProductId))
                .stream()
                .map(ZcProductBatchDO::getProductId)
                .collect(java.util.stream.Collectors.toList());
    }

    IPage<ZcProductBatchRespVO> selectPageWithVO(IPage<?> page, @Param("reqVO") ZcProductBatchPageReqVO reqVO);

    ZcProductBatchRespVO selectBatchWithVOById(@Param("id") Long id);

    default PageResult<ZcProductBatchRespVO> selectPage(ZcProductBatchPageReqVO reqVO) {
        IPage<ZcProductBatchRespVO> result = selectPageWithVO(
                new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), reqVO);
        return new PageResult<>(result.getRecords(), result.getTotal());
    }

    /**
     * 原子扣减批次剩余数量（裁剪时调用，防止并发超卖）
     *
     * @param batchId     批次ID
     * @param cutQuantity 裁剪数量
     */
    @Update("UPDATE zc_product_batch SET quantity = quantity - #{cutQuantity} WHERE id = #{batchId} AND deleted = 0")
    void decreaseQuantity(@Param("batchId") Long batchId, @Param("cutQuantity") BigDecimal cutQuantity);

    /**
     * 原子回退批次剩余数量（撤销裁剪时调用）
     *
     * @param batchId     批次ID
     * @param cutQuantity 回退数量
     */
    @Update("UPDATE zc_product_batch SET quantity = quantity + #{cutQuantity} WHERE id = #{batchId} AND deleted = 0")
    void increaseQuantity(@Param("batchId") Long batchId, @Param("cutQuantity") BigDecimal cutQuantity);

}