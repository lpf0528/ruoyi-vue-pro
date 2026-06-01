package cn.iocoder.yudao.module.zc.dal.mysql.workshopuser;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.workshopuser.ZcWorkshopUserDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.workshopuser.vo.*;

/**
 * 车间员工 Mapper
 *
 * @author 01Coder
 */
@Mapper
public interface ZcWorkshopUserMapper extends BaseMapperX<ZcWorkshopUserDO> {

    default PageResult<ZcWorkshopUserDO> selectPage(ZcWorkshopUserPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ZcWorkshopUserDO>()
                .likeIfPresent(ZcWorkshopUserDO::getName, reqVO.getName())
                .eqIfPresent(ZcWorkshopUserDO::getStatus, reqVO.getStatus())
                .orderByDesc(ZcWorkshopUserDO::getId));
    }

}