package cn.iocoder.yudao.module.zc.service.logistics;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.zc.controller.admin.logistics.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.logistics.LogisticsDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 物流公司 Service 接口
 *
 * @author 芋道源码
 */
public interface LogisticsService {

    /**
     * 创建物流公司
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createLogistics(@Valid LogisticsSaveReqVO createReqVO);

    /**
     * 更新物流公司
     *
     * @param updateReqVO 更新信息
     */
    void updateLogistics(@Valid LogisticsSaveReqVO updateReqVO);

    /**
     * 删除物流公司
     *
     * @param id 编号
     */
    void deleteLogistics(Long id);

    /**
    * 批量删除物流公司
    *
    * @param ids 编号
    */
    void deleteLogisticsListByIds(List<Long> ids);

    /**
     * 获得物流公司
     *
     * @param id 编号
     * @return 物流公司
     */
    LogisticsDO getLogistics(Long id);

    /**
     * 获得物流公司分页
     *
     * @param pageReqVO 分页查询
     * @return 物流公司分页
     */
    PageResult<LogisticsDO> getLogisticsPage(LogisticsPageReqVO pageReqVO);

}