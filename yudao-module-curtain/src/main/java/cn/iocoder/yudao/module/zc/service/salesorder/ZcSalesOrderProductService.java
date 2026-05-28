package cn.iocoder.yudao.module.zc.service.salesorder;

import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcSalesOrderProductCreateReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcSalesOrderProductDetailRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcSalesOrderProductUpdateReqVO;

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

    /**
     * 整单更新产品类销售订单
     *
     * <p>订单主记录覆盖写入，产品行先全量删除再重新插入，与创建接口保持相同的整单风格。
     * 订单号、结算状态、订单状态、是否加急等系统字段不允许通过此接口修改。</p>
     *
     * @param updateReqVO 整单更新请求（含产品批次行列表）
     */
    void updateSalesOrderProduct(@Valid ZcSalesOrderProductUpdateReqVO updateReqVO);

    /**
     * 查询面料单详情（含产品批次行列表）
     *
     * <p>一次性返回订单主信息及所有产品行，行内冗余产品名称、批次号，
     * 前端无需二次请求。</p>
     *
     * @param id 订单 ID
     * @return 面料单详情 VO；若订单不存在则抛出业务异常
     */
    ZcSalesOrderProductDetailRespVO getSalesOrderProductDetail(Long id);

}
