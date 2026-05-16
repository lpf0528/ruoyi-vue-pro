package cn.iocoder.yudao.module.zc.dal.mysql.productbatch;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.productbatch.ZcProductBatchDO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import cn.iocoder.yudao.module.zc.controller.admin.productbatch.vo.*;

/**
 * 产品批次 Mapper
 *
 * @author 01Coder
 */
@Mapper
public interface ZcProductBatchMapper extends BaseMapperX<ZcProductBatchDO> {

    IPage<ZcProductBatchRespVO> selectPageWithVO(IPage<?> page, @Param("reqVO") ZcProductBatchPageReqVO reqVO);

    default PageResult<ZcProductBatchRespVO> selectPage(ZcProductBatchPageReqVO reqVO) {
        IPage<ZcProductBatchRespVO> result = selectPageWithVO(
                new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), reqVO);
        return new PageResult<>(result.getRecords(), result.getTotal());
    }

}