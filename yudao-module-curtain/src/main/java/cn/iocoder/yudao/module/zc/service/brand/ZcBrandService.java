package cn.iocoder.yudao.module.zc.service.brand;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.zc.controller.admin.brand.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.brand.ZcBrandDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 品牌 Service 接口
 *
 * @author 芋道源码
 */
public interface ZcBrandService {

    /**
     * 创建品牌
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createBrand(@Valid ZcBrandSaveReqVO createReqVO);

    /**
     * 更新品牌
     *
     * @param updateReqVO 更新信息
     */
    void updateBrand(@Valid ZcBrandSaveReqVO updateReqVO);

    /**
     * 删除品牌
     *
     * @param id 编号
     */
    void deleteBrand(Long id);

    /**
    * 批量删除品牌
    *
    * @param ids 编号
    */
    void deleteBrandListByIds(List<Long> ids);

    /**
     * 获得品牌
     *
     * @param id 编号
     * @return 品牌
     */
    ZcBrandDO getBrand(Long id);

    /**
     * 获得品牌分页
     *
     * @param pageReqVO 分页查询
     * @return 品牌分页
     */
    PageResult<ZcBrandDO> getBrandPage(ZcBrandPageReqVO pageReqVO);

    /**
     * 获得品牌列表
     *
     * @param listReqVO 列表查询
     * @return 品牌列表
     */
    List<ZcBrandDO> getBrandList(ZcBrandListReqVO listReqVO);

}