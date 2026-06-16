package cn.iocoder.yudao.module.zc.dal.mysql.productversion;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.controller.admin.productversion.vo.ZcCustomerVersionSpecPriceRespVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.productversion.ZcCustomerVersionSpecPriceDO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 客户版本销售授权价 Mapper
 *
 * @author 01Coder
 */
@Mapper
public interface ZcCustomerVersionSpecPriceMapper extends BaseMapperX<ZcCustomerVersionSpecPriceDO> {

    /**
     * 联表查询授权价列表（含版本名称），versionId 为空时查询该客户所有版本
     *
     * @param customerId 客户编号
     * @param versionId  版本编号（可为空）
     * @return 含版本名称的授权价列表
     */
    List<ZcCustomerVersionSpecPriceRespVO> selectListWithVersionName(@Param("customerId") Long customerId,
                                                                     @Param("versionId") Long versionId);

    /**
     * 物理删除指定客户、版本下的所有授权价记录
     *
     * <p>覆盖写场景使用物理删除，避免唯一索引 (customer_id, version_id, spec) 冲突。</p>
     *
     * @param customerId 客户编号
     * @param versionId  版本编号
     */
    @Delete("DELETE FROM zc_customer_version_spec_price WHERE customer_id = #{customerId} AND version_id = #{versionId}")
    void deleteByCustomerIdAndVersionIdPhysically(@Param("customerId") Long customerId, @Param("versionId") Long versionId);

}
