package cn.iocoder.yudao.module.zc.service.salesorder;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZCSalesOrderMaterialDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 成品订单-用料明细 Service 接口
 *
 * @author 01Coder
 */
public interface ZCSalesOrderMaterialService {

    /**
     * 创建成品订单-用料明细
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createZCSalesOrderMaterial(@Valid ZCSalesOrderMaterialSaveReqVO createReqVO);

    /**
     * 更新成品订单-用料明细
     *
     * @param updateReqVO 更新信息
     */
    void updateZCSalesOrderMaterial(@Valid ZCSalesOrderMaterialSaveReqVO updateReqVO);

    /**
     * 删除成品订单-用料明细
     *
     * @param id 编号
     */
    void deleteZCSalesOrderMaterial(Long id);

    /**
    * 批量删除成品订单-用料明细
    *
    * @param ids 编号
    */
    void deleteZCSalesOrderMaterialListByIds(List<Long> ids);

    /**
     * 获得成品订单-用料明细
     *
     * @param id 编号
     * @return 成品订单-用料明细
     */
    ZCSalesOrderMaterialDO getZCSalesOrderMaterial(Long id);

    /**
     * 获得成品订单-用料明细分页
     *
     * @param pageReqVO 分页查询
     * @return 成品订单-用料明细分页
     */
    PageResult<ZCSalesOrderMaterialDO> getZCSalesOrderMaterialPage(ZCSalesOrderMaterialPageReqVO pageReqVO);

    /**
     * 裁剪用料明细
     *
     * <p>绑定批次、记录裁剪数量、将状态更新为已配料（HAVE_PEILIAO），并原子扣减批次剩余库存。
     * 若批次库存不足则抛出 {@link cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants#PRODUCT_BATCH_INSUFFICIENT_QUANTITY}。</p>
     *
     * @param reqVO 裁剪请求（用料明细ID、批次ID、裁剪数量）
     */
    void cutMaterial(@Valid ZcCutMaterialReqVO reqVO);

}