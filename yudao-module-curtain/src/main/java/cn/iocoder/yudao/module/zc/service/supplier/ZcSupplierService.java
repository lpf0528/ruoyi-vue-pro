package cn.iocoder.yudao.module.zc.service.supplier;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.zc.controller.admin.supplier.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.supplier.ZcSupplierDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 供应商 Service 接口
 *
 * @author 芋道源码
 */
public interface ZcSupplierService {

    /**
     * 创建供应商
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSupplier(@Valid ZcSupplierSaveReqVO createReqVO);

    /**
     * 更新供应商
     *
     * @param updateReqVO 更新信息
     */
    void updateSupplier(@Valid ZcSupplierSaveReqVO updateReqVO);

    /**
     * 删除供应商
     *
     * @param id 编号
     */
    void deleteSupplier(Long id);

    /**
    * 批量删除供应商
    *
    * @param ids 编号
    */
    void deleteSupplierListByIds(List<Long> ids);

    /**
     * 获得供应商
     *
     * @param id 编号
     * @return 供应商
     */
    ZcSupplierDO getSupplier(Long id);

    /**
     * 获得供应商分页
     *
     * @param pageReqVO 分页查询
     * @return 供应商分页
     */
    PageResult<ZcSupplierDO> getSupplierPage(ZcSupplierPageReqVO pageReqVO);

}