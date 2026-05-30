package cn.iocoder.yudao.module.zc.dal.mysql.curtaintemplate;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtaintemplate.ZcCurtainTemplateDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * 窗帘模板 Mapper
 *
 * @author 01Coder
 */
@Mapper
public interface ZcCurtainTemplateMapper extends BaseMapperX<ZcCurtainTemplateDO> {

    default void deleteByCurtainId(Long curtainId) {
        delete(new LambdaQueryWrapper<ZcCurtainTemplateDO>()
                .eq(ZcCurtainTemplateDO::getCurtainId, curtainId));
    }

    default List<ZcCurtainTemplateDO> selectByCurtainId(Long curtainId) {
        return selectList(new LambdaQueryWrapper<ZcCurtainTemplateDO>()
                .eq(ZcCurtainTemplateDO::getCurtainId, curtainId));
    }

    /** 查询指定组件是否已在模板中配置（用于删除前校验） */
    default ZcCurtainTemplateDO selectByElementId(Long elementId) {
        return selectOne(new LambdaQueryWrapper<ZcCurtainTemplateDO>()
                .eq(ZcCurtainTemplateDO::getElementId, elementId)
                .last("LIMIT 1"));
    }

    /** 批量查询指定组件集合中是否有任一已在模板中配置 */
    default ZcCurtainTemplateDO selectByElementIds(Collection<Long> elementIds) {
        return selectOne(new LambdaQueryWrapper<ZcCurtainTemplateDO>()
                .in(ZcCurtainTemplateDO::getElementId, elementIds)
                .last("LIMIT 1"));
    }

}
