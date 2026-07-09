package cn.iocoder.yudao.module.zc.service.workshopuser;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.zc.controller.admin.workshopuser.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.workshopuser.ZcWorkshopUserDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 车间员工 Service 接口
 *
 * @author 01Coder
 */
public interface ZcWorkshopUserService {

    /**
     * 创建车间员工
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createWorkshopUser(@Valid ZcWorkshopUserSaveReqVO createReqVO);

    /**
     * 更新车间员工
     *
     * @param updateReqVO 更新信息
     */
    void updateWorkshopUser(@Valid ZcWorkshopUserSaveReqVO updateReqVO);

    /**
     * 删除车间员工
     *
     * @param id 编号
     */
    void deleteWorkshopUser(Long id);

    /**
    * 批量删除车间员工
    *
    * @param ids 编号
    */
    void deleteWorkshopUserListByIds(List<Long> ids);

    /**
     * 获得车间员工
     *
     * @param id 编号
     * @return 车间员工
     */
    ZcWorkshopUserDO getWorkshopUser(Long id);

    /**
     * 获得车间员工分页
     *
     * @param pageReqVO 分页查询
     * @return 车间员工分页
     */
    PageResult<ZcWorkshopUserDO> getWorkshopUserPage(ZcWorkshopUserPageReqVO pageReqVO);

    /**
     * 获得车间员工列表
     *
     * @param listReqVO 查询条件
     * @return 车间员工列表
     */
    List<ZcWorkshopUserDO> getWorkshopUserList(ZcWorkshopUserListReqVO listReqVO);

}