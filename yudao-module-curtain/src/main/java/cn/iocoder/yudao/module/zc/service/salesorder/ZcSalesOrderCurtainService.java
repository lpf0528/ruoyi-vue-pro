package cn.iocoder.yudao.module.zc.service.salesorder;

/**
 * 成品订单-窗帘行 Service 接口
 *
 * @author o1Coder
 */
public interface ZcSalesOrderCurtainService {

    /**
     * 取消发货窗帘行
     *
     * <p>将窗帘行状态回退：若打包时间不为空则回退为已打包（DABAO），否则回退为已确认（CONFIRMED），
     * 并清空发货时间。联动更新订单状态：无已打包窗帘行 → 已确认，部分已打包 → 部分打包，全部已打包 → 已打包。</p>
     *
     * @param id          窗帘行 ID
     * @param masterId    主操作人员 ID
     * @param assistantId 副操作人员 ID（可为空）
     */
    void cancelShipCurtain(Long id, Long masterId, Long assistantId);

    /**
     * 打包窗帘行
     *
     * <p>将指定窗帘行状态更新为已打包（DABAO）。若订单当前状态不是部分发货或已发货，
     * 则检查该订单下所有窗帘行是否全部已打包：全部已打包时订单状态更新为已打包，
     * 否则更新为部分打包。</p>
     *
     * @param id          窗帘行 ID
     * @param masterId    主操作人员 ID
     * @param assistantId 副操作人员 ID（可为空）
     */
    void packCurtain(Long id, Long masterId, Long assistantId);

    /**
     * 取消打包窗帘行
     *
     * <p>将窗帘行状态回退为已确认（CONFIRMED），清空打包时间。
     * 若订单当前状态不是部分发货或已发货，则根据剩余已打包窗帘行数量联动更新订单状态：
     * 全部无打包记录 → 已确认，部分有打包 → 部分打包，全部已打包 → 已打包（此场景不会出现）。</p>
     *
     * @param id          窗帘行 ID
     * @param masterId    主操作人员 ID
     * @param assistantId 副操作人员 ID（可为空）
     */
    void cancelPackCurtain(Long id, Long masterId, Long assistantId);

    /**
     * 发货窗帘行
     *
     * <p>将指定窗帘行状态更新为已发货（FAHUO）。检查该订单下所有窗帘行是否全部已发货：
     * 全部已发货时订单状态更新为已发货（FAHUO），否则更新为部分发货（BUFEN_FAHUO）。</p>
     *
     * @param id          窗帘行 ID
     * @param masterId    主操作人员 ID
     * @param assistantId 副操作人员 ID（可为空）
     */
    void shipCurtain(Long id, Long masterId, Long assistantId);

}
