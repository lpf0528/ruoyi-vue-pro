package cn.iocoder.yudao.module.zc.service.curtainstructureelement;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.zc.controller.admin.curtainstructureelement.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtainstructureelement.ZcCurtainStructureElementDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 窗帘结构组件 Service 接口
 *
 * @author 01Coder
 */
public interface ZcCurtainStructureElementService {

    /**
     * 创建窗帘结构组件
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCurtainStructureElement(@Valid ZcCurtainStructureElementSaveReqVO createReqVO);

    /**
     * 更新窗帘结构组件
     *
     * @param updateReqVO 更新信息
     */
    void updateCurtainStructureElement(@Valid ZcCurtainStructureElementSaveReqVO updateReqVO);

    /**
     * 删除窗帘结构组件
     *
     * @param id 编号
     */
    void deleteCurtainStructureElement(Long id);

    /**
    * 批量删除窗帘结构组件
    *
    * @param ids 编号
    */
    void deleteCurtainStructureElementListByIds(List<Long> ids);

    /**
     * 获得窗帘结构组件
     *
     * @param id 编号
     * @return 窗帘结构组件
     */
    ZcCurtainStructureElementDO getCurtainStructureElement(Long id);

    /**
     * 获得窗帘结构组件分页
     *
     * @param pageReqVO 分页查询
     * @return 窗帘结构组件分页
     */
    PageResult<ZcCurtainStructureElementDO> getCurtainStructureElementPage(ZcCurtainStructureElementPageReqVO pageReqVO);

    /**
     * 获得窗帘结构组件列表
     *
     * @param listReqVO 列表查询
     * @return 窗帘结构组件列表
     */
    List<ZcCurtainStructureElementDO> getCurtainStructureElementList(ZcCurtainStructureElementListReqVO listReqVO);

}