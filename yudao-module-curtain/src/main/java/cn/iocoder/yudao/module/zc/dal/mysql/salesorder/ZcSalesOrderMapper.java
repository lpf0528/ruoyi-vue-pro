package cn.iocoder.yudao.module.zc.dal.mysql.salesorder;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderDO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.*;

/**
 * 销售订单 Mapper
 *
 * @author 01Coder
 */
@Mapper
public interface ZcSalesOrderMapper extends BaseMapperX<ZcSalesOrderDO> {

    /** XML 绑定方法，由 MyBatis Plus 分页插件注入 LIMIT/OFFSET 及 COUNT */
    IPage<ZcSalesOrderRespVO> selectPageWithVO(IPage<?> page, @Param("reqVO") ZcSalesOrderPageReqVO reqVO);

    default PageResult<ZcSalesOrderRespVO> selectPage(ZcSalesOrderPageReqVO reqVO) {
        IPage<ZcSalesOrderRespVO> result = selectPageWithVO(
                new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), reqVO);
        return new PageResult<>(result.getRecords(), result.getTotal());
    }

}