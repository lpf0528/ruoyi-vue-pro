package cn.iocoder.yudao.module.zc.service.salesorder;

/**
 * 成品订单-窗帘行 Service 接口
 *
 * @author o1Coder
 */
public interface ZcSalesOrderCurtainService {

    /**
     * 打包窗帘行
     *
     * <p>将指定窗帘行状态更新为已打包（DABAO）。若订单当前状态不是部分发货或已发货，
     * 则检查该订单下所有窗帘行是否全部已打包：全部已打包时订单状态更新为已打包，
     * 否则更新为部分打包。</p>
     *
     * @param id 窗帘行 ID
     */
    void packCurtain(Long id);

}
