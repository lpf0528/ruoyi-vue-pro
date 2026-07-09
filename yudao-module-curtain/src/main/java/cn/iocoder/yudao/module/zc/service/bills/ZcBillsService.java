package cn.iocoder.yudao.module.zc.service.bills;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.zc.controller.admin.bills.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.bills.ZcBillsDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 收支账单 Service 接口
 *
 * @author 01Coder
 */
public interface ZcBillsService {

    /**
     * 创建收支账单
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createBills(@Valid ZcBillsSaveReqVO createReqVO);

    /**
     * 更新收支账单
     *
     * @param updateReqVO 更新信息
     */
    void updateBills(@Valid ZcBillsSaveReqVO updateReqVO);

    /**
     * 删除收支账单
     *
     * @param id 编号
     */
    void deleteBills(Long id);

    /**
    * 批量删除收支账单
    *
    * @param ids 编号
    */
    void deleteBillsListByIds(List<Long> ids);

    /**
     * 获得收支账单
     *
     * @param id 编号
     * @return 收支账单
     */
    ZcBillsDO getBills(Long id);

    /**
     * 获得收支账单分页（含客户简称、收款方式名称）
     *
     * @param pageReqVO 分页查询
     * @return 收支账单分页
     */
    PageResult<ZcBillsRespVO> getBillsPage(ZcBillsPageReqVO pageReqVO);

    /**
     * 获得收款单的订单分摊明细列表（含订单号）
     *
     * @param billId 收款单 ID
     * @return 分摊明细列表
     */
    List<ZcBillOrderItemRespVO> getBillOrderItems(Long billId);

}