package cn.iocoder.yudao.module.zc.service.curtainstructureelement;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.zc.controller.admin.curtainstructureelement.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtainstructureelement.CurtainStructureElementDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 结构配件类型 Service 接口
 *
 * @author 芋道源码
 */
public interface CurtainStructureElementService {

    /**
     * 创建结构配件类型
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCurtainStructureElement(@Valid CurtainStructureElementSaveReqVO createReqVO);

    /**
     * 更新结构配件类型
     *
     * @param updateReqVO 更新信息
     */
    void updateCurtainStructureElement(@Valid CurtainStructureElementSaveReqVO updateReqVO);

    /**
     * 删除结构配件类型
     *
     * @param id 编号
     */
    void deleteCurtainStructureElement(Long id);

    /**
    * 批量删除结构配件类型
    *
    * @param ids 编号
    */
    void deleteCurtainStructureElementListByIds(List<Long> ids);

    /**
     * 获得结构配件类型
     *
     * @param id 编号
     * @return 结构配件类型
     */
    CurtainStructureElementDO getCurtainStructureElement(Long id);

    /**
     * 获得结构配件类型分页
     *
     * @param pageReqVO 分页查询
     * @return 结构配件类型分页
     */
    PageResult<CurtainStructureElementDO> getCurtainStructureElementPage(CurtainStructureElementPageReqVO pageReqVO);

}