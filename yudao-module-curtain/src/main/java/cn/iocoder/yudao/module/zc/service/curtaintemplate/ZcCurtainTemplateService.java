package cn.iocoder.yudao.module.zc.service.curtaintemplate;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.zc.controller.admin.curtaintemplate.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtaintemplate.ZcCurtainTemplateDO;

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
     * 获得窗帘模板
     *
     * @param id 编号
     * @return 窗帘模板
     */
    ZcCurtainTemplateDO getCurtainTemplate(Long id);

}
