package cn.iocoder.yudao.module.zc.dal.mysql.curtainstructureelement;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtainstructureelement.CurtainStructureElementDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.curtainstructureelement.vo.*;

/**
 * 结构配件类型 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface CurtainStructureElementMapper extends BaseMapperX<CurtainStructureElementDO> {

    default PageResult<CurtainStructureElementDO> selectPage(CurtainStructureElementPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CurtainStructureElementDO>()
                .likeIfPresent(CurtainStructureElementDO::getName, reqVO.getName())
                .eqIfPresent(CurtainStructureElementDO::getNote, reqVO.getNote())
                .betweenIfPresent(CurtainStructureElementDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CurtainStructureElementDO::getId));
    }

}