package cn.iocoder.yudao.module.zc.service.curtaintemplate;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.zc.controller.admin.curtaintemplate.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtaintemplate.CurtainTemplateDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 窗帘模板 Service 接口
 *
 * @author 芋道源码
 */
public interface CurtainTemplateService {

    /**
     * 创建窗帘模板
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCurtainTemplate(@Valid CurtainTemplateSaveReqVO createReqVO);

    /**
     * 更新窗帘模板
     *
     * @param updateReqVO 更新信息
     */
    void updateCurtainTemplate(@Valid CurtainTemplateSaveReqVO updateReqVO);

    /**
     * 删除窗帘模板
     *
     * @param id 编号
     */
    void deleteCurtainTemplate(Long id);

    /**
    * 批量删除窗帘模板
    *
    * @param ids 编号
    */
    void deleteCurtainTemplateListByIds(List<Long> ids);

    /**
     * 获得窗帘模板
     *
     * @param id 编号
     * @return 窗帘模板
     */
    CurtainTemplateDO getCurtainTemplate(Long id);

    /**
     * 获得窗帘模板分页
     *
     * @param pageReqVO 分页查询
     * @return 窗帘模板分页
     */
    PageResult<CurtainTemplateDO> getCurtainTemplatePage(CurtainTemplatePageReqVO pageReqVO);

}