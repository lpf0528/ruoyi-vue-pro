package cn.iocoder.yudao.module.zc.service.bills;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.zc.controller.admin.bills.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.bills.ZcBillMethodsDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 收款方式 Service 接口
 *
 * @author 01Coder
 */
public interface ZcBillMethodsService {

    /**
     * 创建收款方式
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createBillMethods(@Valid ZcBillMethodsSaveReqVO createReqVO);

    /**
     * 更新收款方式
     *
     * @param updateReqVO 更新信息
     */
    void updateBillMethods(@Valid ZcBillMethodsSaveReqVO updateReqVO);

    /**
     * 删除收款方式
     *
     * @param id 编号
     */
    void deleteBillMethods(Long id);

    /**
    * 批量删除收款方式
    *
    * @param ids 编号
    */
    void deleteBillMethodsListByIds(List<Long> ids);

    /**
     * 获得收款方式
     *
     * @param id 编号
     * @return 收款方式
     */
    ZcBillMethodsDO getBillMethods(Long id);

    /**
     * 获得收款方式分页
     *
     * @param pageReqVO 分页查询
     * @return 收款方式分页
     */
    PageResult<ZcBillMethodsDO> getBillMethodsPage(ZcBillMethodsPageReqVO pageReqVO);

}