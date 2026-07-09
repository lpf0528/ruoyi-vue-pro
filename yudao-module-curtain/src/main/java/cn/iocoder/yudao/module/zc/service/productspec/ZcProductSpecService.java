package cn.iocoder.yudao.module.zc.service.productspec;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.zc.controller.admin.productspec.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.productspec.ZcProductSpecDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 产品规格 Service 接口
 *
 * @author 芋道源码
 */
public interface ZcProductSpecService {

    /**
     * 创建产品规格
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProductSpec(@Valid ZcProductSpecSaveReqVO createReqVO);

    /**
     * 更新产品规格
     *
     * @param updateReqVO 更新信息
     */
    void updateProductSpec(@Valid ZcProductSpecSaveReqVO updateReqVO);

    /**
     * 删除产品规格
     *
     * @param id 编号
     */
    void deleteProductSpec(Long id);

    /**
    * 批量删除产品规格
    *
    * @param ids 编号
    */
    void deleteProductSpecListByIds(List<Long> ids);

    /**
     * 获得产品规格
     *
     * @param id 编号
     * @return 产品规格
     */
    ZcProductSpecDO getProductSpec(Long id);

    /**
     * 获得产品规格分页
     *
     * @param pageReqVO 分页查询
     * @return 产品规格分页
     */
    PageResult<ZcProductSpecDO> getProductSpecPage(ZcProductSpecPageReqVO pageReqVO);

    /**
     * 获得产品规格列表
     *
     * @param listReqVO 列表查询
     * @return 产品规格列表
     */
    List<ZcProductSpecDO> getProductSpecList(ZcProductSpecListReqVO listReqVO);

}