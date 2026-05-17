package cn.iocoder.yudao.module.zc.dal.mysql.curtainstructureelement;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtainstructureelement.ZcCurtainStructureElementDO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import cn.iocoder.yudao.module.zc.controller.admin.curtainstructureelement.vo.*;

/**
 * 窗帘结构组件 Mapper
 *
 * @author 01Coder
 */
@Mapper
public interface ZcCurtainStructureElementMapper extends BaseMapperX<ZcCurtainStructureElementDO> {

    IPage<ZcCurtainStructureElementRespVO> selectPageWithVO(IPage<?> page, @Param("reqVO") ZcCurtainStructureElementPageReqVO reqVO);

    default PageResult<ZcCurtainStructureElementRespVO> selectPage(ZcCurtainStructureElementPageReqVO reqVO) {
        IPage<ZcCurtainStructureElementRespVO> result = selectPageWithVO(
                new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), reqVO);
        return new PageResult<>(result.getRecords(), result.getTotal());
    }

    default List<ZcCurtainStructureElementDO> selectList(ZcCurtainStructureElementListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ZcCurtainStructureElementDO>()
                .orderByDesc(ZcCurtainStructureElementDO::getId));
    }

}