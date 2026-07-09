package cn.iocoder.yudao.module.zc.dal.mysql.curtaininstallprocess;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtaininstallprocess.ZcCurtainInstallProcessDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.curtaininstallprocess.vo.*;

/**
 * 安装工艺 Mapper
 *
 * @author 01Coder
 */
@Mapper
public interface ZcCurtainInstallProcessMapper extends BaseMapperX<ZcCurtainInstallProcessDO> {

    default PageResult<ZcCurtainInstallProcessDO> selectPage(ZcCurtainInstallProcessPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ZcCurtainInstallProcessDO>()
                .likeIfPresent(ZcCurtainInstallProcessDO::getName, reqVO.getName())
                .orderByDesc(ZcCurtainInstallProcessDO::getId));
    }

    default List<ZcCurtainInstallProcessDO> selectList(ZcCurtainInstallProcessListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ZcCurtainInstallProcessDO>()
                .likeIfPresent(ZcCurtainInstallProcessDO::getName, reqVO.getName())
                .orderByDesc(ZcCurtainInstallProcessDO::getId));
    }

    default ZcCurtainInstallProcessDO selectByName(String name) {
        return selectOne(new LambdaQueryWrapperX<ZcCurtainInstallProcessDO>()
                .eq(ZcCurtainInstallProcessDO::getName, name)
                .last("LIMIT 1"));
    }

}