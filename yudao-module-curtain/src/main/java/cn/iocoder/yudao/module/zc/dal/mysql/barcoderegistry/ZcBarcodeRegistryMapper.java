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

}
