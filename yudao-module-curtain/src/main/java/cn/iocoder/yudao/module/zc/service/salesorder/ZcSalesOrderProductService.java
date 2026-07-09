package cn.iocoder.yudao.module.zc.service.salesorder;

import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcCancelCutProductReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcCancelShipProductReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcCutProductReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcShipProductReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcSalesOrderProductCreateReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcSalesOrderProductDetailRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcSalesOrderProductUpdateReqVO;

import jakarta.validation.Valid;

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
     * 删除产品类销售订单（级联删除产品行）
     *
     * @param id 订单 ID
     */
    void deleteSalesOrderProduct(Long id);

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

    /**
     * 面料单产品行裁剪出库
     *
     * <p>使用产品行上已绑定的 batchId，扣减批次库存，记录裁剪数量，
     * 将产品行配料状态更新为 HAVE_PEILIAO，并写入库存变动流水。</p>
     *
     * @param reqVO 裁剪请求（产品行 ID + 裁剪数量）
     * @throws cn.iocoder.yudao.framework.common.exception.ServiceException 批次不存在或库存不足时抛出
     */
    void cutProduct(@Valid ZcCutProductReqVO reqVO);

    /**
     * 撤销面料单产品行裁剪
     *
     * <p>回退批次库存，清空裁剪数量，将产品行配料状态重置为 NOT_PEILIAO，
     * 并写入撤销裁剪库存变动流水。</p>
     *
     * @param reqVO 撤销裁剪请求（产品行 ID）
     * @throws cn.iocoder.yudao.framework.common.exception.ServiceException 产品行未处于已配料状态时抛出
     */
    void cancelCutProduct(@Valid ZcCancelCutProductReqVO reqVO);

    /**
     * 面料单产品行发货
     *
     * <p>将产品行状态变更为 FAHUO，记录发货时间，并联动更新订单主表状态：
     * 全部产品行已发货 → FAHUO；部分已发货 → BUFEN_FAHUO。</p>
     *
     * @param reqVO 发货请求（产品行 ID）
     * @throws cn.iocoder.yudao.framework.common.exception.ServiceException 产品行已发货时抛出
     */
    void shipProduct(@Valid ZcShipProductReqVO reqVO);

    /**
     * 撤销面料单产品行发货
     *
     * <p>将产品行状态回退为发货前状态（已裁剪 → HAVE_PEILIAO，否则 → CONFIRMED），
     * 清空发货时间，并联动更新订单主表状态。</p>
     *
     * @param reqVO 撤销发货请求（产品行 ID）
     * @throws cn.iocoder.yudao.framework.common.exception.ServiceException 产品行尚未发货时抛出
     */
    void cancelShipProduct(@Valid ZcCancelShipProductReqVO reqVO);

}
