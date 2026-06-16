package cn.iocoder.yudao.module.zc.dal.mysql.productversion;

import java.util.*;

import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.productversion.ZcProductVersionSpcDO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 产品版本规格 Mapper
 *
 * @author 01Coder
 */
@Mapper
public interface ZcProductVersionSpcMapper extends BaseMapperX<ZcProductVersionSpcDO> {

    default List<ZcProductVersionSpcDO> selectListByVersionId(Long versionId) {
        return selectList(ZcProductVersionSpcDO::getVersionId, versionId);
    }

    default int deleteByVersionId(Long versionId) {
        return delete(new LambdaQueryWrapperX<ZcProductVersionSpcDO>()
                .eq(ZcProductVersionSpcDO::getVersionId, versionId));
    }

    @Delete("DELETE FROM zc_product_version_spc WHERE version_id = #{versionId}")
    void deleteByVersionIdPhysically(@Param("versionId") Long versionId);

    default int deleteByVersionIds(Collection<Long> versionIds) {
        return delete(new LambdaQueryWrapperX<ZcProductVersionSpcDO>()
                .in(ZcProductVersionSpcDO::getVersionId, versionIds));
    }

    @Delete("<script>DELETE FROM zc_product_version_spc WHERE version_id IN " +
            "<foreach item='id' collection='versionIds' open='(' separator=',' close=')'>#{id}</foreach></script>")
    void deleteByVersionIdsPhysically(@Param("versionIds") Collection<Long> versionIds);

}
