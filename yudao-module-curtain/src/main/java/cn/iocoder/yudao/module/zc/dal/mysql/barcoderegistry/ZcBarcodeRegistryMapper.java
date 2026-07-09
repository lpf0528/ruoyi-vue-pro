package cn.iocoder.yudao.module.zc.dal.mysql.barcoderegistry;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.barcoderegistry.ZcBarcodeRegistryDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 码注册表 Mapper
 *
 * @author 智仓
 */
@Mapper
public interface ZcBarcodeRegistryMapper extends BaseMapperX<ZcBarcodeRegistryDO> {

    /**
     * 根据码唯一ID查询注册记录
     *
     * @param codeId 码唯一ID（UUID）
     * @return 注册记录，不存在时返回 null
     */
    default ZcBarcodeRegistryDO selectByCodeId(String codeId) {
        return selectOne(new LambdaQueryWrapperX<ZcBarcodeRegistryDO>()
                .eq(ZcBarcodeRegistryDO::getCodeId, codeId)
                .last("LIMIT 1"));
    }

    /**
     * 根据去重三元组查询注册记录（用于幂等创建）
     *
     * <p>tenant_id 由 MyBatis-Plus 租户插件自动追加，无需显式传入</p>
     *
     * @param codeType    码类型
     * @param targetRoute 跳转路由
     * @param contentHash code_content 规范化 SHA-256 指纹
     * @return 已存在的注册记录，不存在时返回 null
     */
    default ZcBarcodeRegistryDO selectByDedup(String codeType, String targetRoute, String contentHash) {
        return selectOne(new LambdaQueryWrapperX<ZcBarcodeRegistryDO>()
                .eq(ZcBarcodeRegistryDO::getCodeType, codeType)
                .eq(ZcBarcodeRegistryDO::getTargetRoute, targetRoute)
                .eq(ZcBarcodeRegistryDO::getContentHash, contentHash)
                .last("LIMIT 1"));
    }

}
