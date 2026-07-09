package cn.iocoder.yudao.module.zc.service.productversion;

import cn.iocoder.yudao.module.zc.controller.admin.productversion.vo.ZcCustomerVersionSpecPriceGetRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.productversion.vo.ZcCustomerVersionSpecPriceRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.productversion.vo.ZcCustomerVersionSpecPriceSaveReqVO;

import javax.validation.Valid;
import java.util.List;

/**
 * 客户版本销售授权价 Service 接口
 *
 * @author 01Coder
 */
public interface ZcCustomerVersionSpecPriceService {

    /**
     * 批量保存客户版本规格授权价
     *
     * <p>先物理删除该客户所有旧记录，再全量插入新记录；id 字段忽略。</p>
     *
     * @param saveReqVO 批量保存请求
     */
    void batchSaveCustomerVersionSpecPrice(@Valid ZcCustomerVersionSpecPriceSaveReqVO saveReqVO);

    /**
     * 查询客户版本授权价列表（含版本名称），versionId 为空时查询该客户所有版本
     *
     * @param customerId 客户编号
     * @param versionId  版本编号（可为空）
     * @return 含版本名称的授权价列表
     */
    List<ZcCustomerVersionSpecPriceRespVO> getCustomerVersionSpecPriceList(Long customerId, Long versionId);

    /**
     * 根据产品ID、客户ID、规格查询客户版本规格授权价
     *
     * <p>无客户授权价时回退为版本规格一级销售价（one_price）。</p>
     *
     * @param productId  产品编号（zc_product.id）
     * @param customerId 客户编号
     * @param spec       规格名称
     * @return 授权价记录；产品对应版本规格不存在时返回 null
     */
    ZcCustomerVersionSpecPriceGetRespVO getByProductIdAndCustomerIdAndSpec(Long productId, Long customerId, String spec);

}
