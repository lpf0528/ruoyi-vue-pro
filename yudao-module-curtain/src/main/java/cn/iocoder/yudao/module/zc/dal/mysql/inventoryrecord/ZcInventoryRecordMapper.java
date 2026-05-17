package cn.iocoder.yudao.module.zc.dal.mysql.inventoryrecord;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.inventoryrecord.ZcInventoryRecordDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.iocoder.yudao.module.zc.controller.admin.inventoryrecord.vo.*;

/**
 * 盘点记录 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ZcInventoryRecordMapper extends BaseMapperX<ZcInventoryRecordDO> {

    IPage<ZcInventoryRecordRespVO> selectPageWithVO(IPage<?> page, @Param("reqVO") ZcInventoryRecordPageReqVO reqVO);

    default PageResult<ZcInventoryRecordRespVO> selectPage(ZcInventoryRecordPageReqVO reqVO) {
        IPage<ZcInventoryRecordRespVO> result = selectPageWithVO(
                new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), reqVO);
        return new PageResult<>(result.getRecords(), result.getTotal());
    }

}