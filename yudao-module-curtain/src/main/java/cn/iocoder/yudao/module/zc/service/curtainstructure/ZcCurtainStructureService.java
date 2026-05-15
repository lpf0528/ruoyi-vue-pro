package cn.iocoder.yudao.module.zc.service.curtainstructure;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.zc.controller.admin.curtainstructure.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtainstructure.ZcCurtainStructureDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 窗帘结构 Service 接口
 *
 * @author 01Coder
 */
public interface ZcCurtainStructureService {

    /**
     * 创建窗帘结构
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCurtainStructure(@Valid ZcCurtainStructureSaveReqVO createReqVO);

    /**
     * 更新窗帘结构
     *
     * @param updateReqVO 更新信息
     */
    void updateCurtainStructure(@Valid ZcCurtainStructureSaveReqVO updateReqVO);

    /**
     * 删除窗帘结构
     *
     * @param id 编号
     */
    void deleteCurtainStructure(Long id);

    /**
    * 批量删除窗帘结构
    *
    * @param ids 编号
    */
    void deleteCurtainStructureListByIds(List<Long> ids);

    /**
     * 获得窗帘结构
     *
     * @param id 编号
     * @return 窗帘结构
     */
    ZcCurtainStructureDO getCurtainStructure(Long id);

    /**
     * 获得窗帘结构分页
     *
     * @param pageReqVO 分页查询
     * @return 窗帘结构分页
     */
    PageResult<ZcCurtainStructureDO> getCurtainStructurePage(ZcCurtainStructurePageReqVO pageReqVO);

}