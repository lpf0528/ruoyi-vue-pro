package cn.iocoder.yudao.module.zc.service.curtaintemplate;

import javax.validation.*;
import cn.iocoder.yudao.module.zc.controller.admin.curtaintemplate.vo.ZcCurtainTemplateGetRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.curtaintemplate.vo.ZcCurtainTemplateSaveReqVO;

/**
 * 窗帘模板 Service 接口
 *
 * @author 01Coder
 */
public interface ZcCurtainTemplateService {

    /**
     * 保存窗帘模板（先删除 curtainId 关联的所有数据，再批量新增）
     *
     * @param saveReqVO 保存信息
     */
    void saveCurtainTemplate(@Valid ZcCurtainTemplateSaveReqVO saveReqVO);

    /**
     * 根据款式ID查询窗帘模板，聚合返回结构列表
     *
     * @param curtainId 款式ID
     * @return 窗帘模板聚合结果
     */
    ZcCurtainTemplateGetRespVO getCurtainTemplateByCurtainId(Long curtainId);

}
