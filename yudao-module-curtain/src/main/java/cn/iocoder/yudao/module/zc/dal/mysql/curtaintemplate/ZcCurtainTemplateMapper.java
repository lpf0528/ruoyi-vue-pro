package cn.iocoder.yudao.module.zc.dal.mysql.curtaintemplate;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtaintemplate.ZcCurtainTemplateDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;

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

}
