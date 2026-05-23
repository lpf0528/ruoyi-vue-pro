package cn.iocoder.yudao.module.zc.dal.mysql.warehouse;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.warehouse.ZcWarehouseDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.iocoder.yudao.module.zc.controller.admin.warehouse.vo.*;

/**
 * 仓库 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ZcWarehouseMapper extends BaseMapperX<ZcWarehouseDO> {

    /**
     * 分页查询（含负责人昵称）——对应 XML selectPageWithVO
     */
    IPage<ZcWarehouseRespVO> selectPageWithVO(IPage<?> page, @Param("reqVO") ZcWarehousePageReqVO reqVO);

    /**
     * 单条查询（含负责人昵称）——对应 XML selectByIdWithVO
     */
    ZcWarehouseRespVO selectByIdWithVO(@Param("id") Long id);

    default PageResult<ZcWarehouseRespVO> selectPageVO(ZcWarehousePageReqVO reqVO) {
        IPage<ZcWarehouseRespVO> result = selectPageWithVO(
                new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), reqVO);
        return new PageResult<>(result.getRecords(), result.getTotal());
    }

    default PageResult<ZcWarehouseDO> selectPage(ZcWarehousePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ZcWarehouseDO>()
                .likeIfPresent(ZcWarehouseDO::getName, reqVO.getName())
                .eqIfPresent(ZcWarehouseDO::getManagerId, reqVO.getManagerId())
                .orderByDesc(ZcWarehouseDO::getId));
    }

    default List<ZcWarehouseDO> selectList(ZcWarehouseListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ZcWarehouseDO>()
                .likeIfPresent(ZcWarehouseDO::getName, reqVO.getName())
                .orderByDesc(ZcWarehouseDO::getId));
    }

}