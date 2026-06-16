package cn.iocoder.yudao.module.zc.service.productversion;

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
     * <p>按 customerId + versionId 分组做覆盖写：先删旧记录，再全量插入新记录。</p>
     *
     * @param saveReqVOList 平铺的授权价列表，每条含 customerId、versionId、spec、authorizedPrice
     */
    void batchSaveCustomerVersionSpecPrice(@Valid List<ZcCustomerVersionSpecPriceSaveReqVO> saveReqVOList);

    /**
     * 查询客户版本授权价列表（含版本名称），versionId 为空时查询该客户所有版本
     *
     * @param customerId 客户编号
     * @param versionId  版本编号（可为空）
     * @return 含版本名称的授权价列表
     */
    List<ZcCustomerVersionSpecPriceRespVO> getCustomerVersionSpecPriceList(Long customerId, Long versionId);

}
