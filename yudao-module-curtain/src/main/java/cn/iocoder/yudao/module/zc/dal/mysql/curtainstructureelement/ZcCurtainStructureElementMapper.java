package cn.iocoder.yudao.module.zc.dal.mysql.curtainstructureelement;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtainstructureelement.ZcCurtainStructureElementDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.curtainstructureelement.vo.*;

/**
 * 窗帘结构组件 Mapper
 *
 * @author 01Coder
 */
@Mapper
public interface ZcCurtainStructureElementMapper extends BaseMapperX<ZcCurtainStructureElementDO> {

    default PageResult<ZcCurtainStructureElementDO> selectPage(ZcCurtainStructureElementPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ZcCurtainStructureElementDO>()
                .likeIfPresent(ZcCurtainStructureElementDO::getName, reqVO.getName())
                .orderByDesc(ZcCurtainStructureElementDO::getId));
    }

}