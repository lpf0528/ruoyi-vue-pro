package cn.iocoder.yudao.module.zc.service.curtainpleatratio;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.zc.controller.admin.curtainpleatratio.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtainpleatratio.ZcCurtainPleatRatioDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 褶倍 Service 接口
 *
 * @author 01Coder
 */
public interface ZcCurtainPleatRatioService {

    /**
     * 创建褶倍
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCurtainPleatRatio(@Valid ZcCurtainPleatRatioSaveReqVO createReqVO);

    /**
     * 更新褶倍
     *
     * @param updateReqVO 更新信息
     */
    void updateCurtainPleatRatio(@Valid ZcCurtainPleatRatioSaveReqVO updateReqVO);

    /**
     * 删除褶倍
     *
     * @param id 编号
     */
    void deleteCurtainPleatRatio(Long id);

    /**
    * 批量删除褶倍
    *
    * @param ids 编号
    */
    void deleteCurtainPleatRatioListByIds(List<Long> ids);

    /**
     * 获得褶倍
     *
     * @param id 编号
     * @return 褶倍
     */
    ZcCurtainPleatRatioDO getCurtainPleatRatio(Long id);

    /**
     * 获得褶倍分页
     *
     * @param pageReqVO 分页查询
     * @return 褶倍分页
     */
    PageResult<ZcCurtainPleatRatioDO> getCurtainPleatRatioPage(ZcCurtainPleatRatioPageReqVO pageReqVO);

}