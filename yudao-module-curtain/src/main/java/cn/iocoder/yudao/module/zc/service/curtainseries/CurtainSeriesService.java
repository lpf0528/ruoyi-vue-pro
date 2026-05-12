package cn.iocoder.yudao.module.zc.service.curtainseries;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.zc.controller.admin.curtainseries.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtainseries.CurtainSeriesDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 窗帘系列 Service 接口
 *
 * @author 芋道源码
 */
public interface CurtainSeriesService {

    /**
     * 创建窗帘系列
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCurtainSeries(@Valid CurtainSeriesSaveReqVO createReqVO);

    /**
     * 更新窗帘系列
     *
     * @param updateReqVO 更新信息
     */
    void updateCurtainSeries(@Valid CurtainSeriesSaveReqVO updateReqVO);

    /**
     * 删除窗帘系列
     *
     * @param id 编号
     */
    void deleteCurtainSeries(Long id);

    /**
    * 批量删除窗帘系列
    *
    * @param ids 编号
    */
    void deleteCurtainSeriesListByIds(List<Long> ids);

    /**
     * 获得窗帘系列
     *
     * @param id 编号
     * @return 窗帘系列
     */
    CurtainSeriesDO getCurtainSeries(Long id);

    /**
     * 获得窗帘系列分页
     *
     * @param pageReqVO 分页查询
     * @return 窗帘系列分页
     */
    PageResult<CurtainSeriesDO> getCurtainSeriesPage(CurtainSeriesPageReqVO pageReqVO);

}