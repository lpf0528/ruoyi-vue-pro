package cn.iocoder.yudao.module.zc.service.salesorder;

import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcSalesOrderProductCreateReqVO;

import javax.validation.Valid;

/**
 * 产品类销售订单 Service 接口
 *
 * <p>处理面料单等直接购买产品批次的订单类型，
 * 订单主记录写入 zc_sales_order，产品行明细写入 zc_sales_order_product。</p>
 *
 * @author 01Coder
 */
public interface ZcSalesOrderProductService {

    /**
     * 整单创建产品类销售订单
     *
     * <p>订单号自动生成（格式：ZC + 租户ID + yyyyMMdd + 5位累计序号）。
     * 结算状态默认 unpaid，订单状态默认 unconfirmed，是否加急默认 false。
     * 订单主记录与产品行在同一事务内级联保存。</p>
     *
     * @param createReqVO 整单创建请求（含产品批次行列表）
     * @return 新订单 ID
     */
    Long createSalesOrderProduct(@Valid ZcSalesOrderProductCreateReqVO createReqVO);

}
