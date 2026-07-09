package cn.iocoder.yudao.module.zc.service.curtaininstallprocess;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.zc.controller.admin.curtaininstallprocess.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtaininstallprocess.ZcCurtainInstallProcessDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 安装工艺 Service 接口
 *
 * @author 01Coder
 */
public interface ZcCurtainInstallProcessService {

    /**
     * 创建安装工艺
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCurtainInstallProcess(@Valid ZcCurtainInstallProcessSaveReqVO createReqVO);

    /**
     * 更新安装工艺
     *
     * @param updateReqVO 更新信息
     */
    void updateCurtainInstallProcess(@Valid ZcCurtainInstallProcessSaveReqVO updateReqVO);

    /**
     * 删除安装工艺
     *
     * @param id 编号
     */
    void deleteCurtainInstallProcess(Long id);

    /**
    * 批量删除安装工艺
    *
    * @param ids 编号
    */
    void deleteCurtainInstallProcessListByIds(List<Long> ids);

    /**
     * 获得安装工艺
     *
     * @param id 编号
     * @return 安装工艺
     */
    ZcCurtainInstallProcessDO getCurtainInstallProcess(Long id);

    /**
     * 获得安装工艺分页
     *
     * @param pageReqVO 分页查询
     * @return 安装工艺分页
     */
    PageResult<ZcCurtainInstallProcessDO> getCurtainInstallProcessPage(ZcCurtainInstallProcessPageReqVO pageReqVO);

    /**
     * 获得安装工艺列表
     *
     * @param listReqVO 列表查询
     * @return 安装工艺列表
     */
    List<ZcCurtainInstallProcessDO> getCurtainInstallProcessList(ZcCurtainInstallProcessListReqVO listReqVO);

}