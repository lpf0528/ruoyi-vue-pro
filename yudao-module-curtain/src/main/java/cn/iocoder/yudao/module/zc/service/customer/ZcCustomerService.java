package cn.iocoder.yudao.module.zc.service.customer;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.zc.controller.admin.customer.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.customer.ZcCustomerDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 客户资料 Service 接口
 *
 * @author 芋道源码
 */
public interface ZcCustomerService {

    /**
     * 创建客户资料
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCustomer(@Valid ZcCustomerSaveReqVO createReqVO);

    /**
     * 更新客户资料
     *
     * @param updateReqVO 更新信息
     */
    void updateCustomer(@Valid ZcCustomerSaveReqVO updateReqVO);

    /**
     * 删除客户资料
     *
     * @param id 编号
     */
    void deleteCustomer(Long id);

    /**
    * 批量删除客户资料
    *
    * @param ids 编号
    */
    void deleteCustomerListByIds(List<Long> ids);

    /**
     * 获得客户资料
     *
     * @param id 编号
     * @return 客户资料
     */
    ZcCustomerDO getCustomer(Long id);

    /**
     * 获得客户资料分页
     *
     * @param pageReqVO 分页查询
     * @return 客户资料分页
     */
    PageResult<ZcCustomerDO> getCustomerPage(ZcCustomerPageReqVO pageReqVO);

}