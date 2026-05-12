package cn.iocoder.yudao.module.zc.service.curtain;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.zc.controller.admin.curtain.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtain.CurtainDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 窗帘 Service 接口
 *
 * @author 芋道源码
 */
public interface CurtainService {

    /**
     * 创建窗帘
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCurtain(@Valid CurtainSaveReqVO createReqVO);

    /**
     * 更新窗帘
     *
     * @param updateReqVO 更新信息
     */
    void updateCurtain(@Valid CurtainSaveReqVO updateReqVO);

    /**
     * 删除窗帘
     *
     * @param id 编号
     */
    void deleteCurtain(Long id);

    /**
    * 批量删除窗帘
    *
    * @param ids 编号
    */
    void deleteCurtainListByIds(List<Long> ids);

    /**
     * 获得窗帘
     *
     * @param id 编号
     * @return 窗帘
     */
    CurtainDO getCurtain(Long id);

    /**
     * 获得窗帘分页
     *
     * @param pageReqVO 分页查询
     * @return 窗帘分页
     */
    PageResult<CurtainDO> getCurtainPage(CurtainPageReqVO pageReqVO);

}