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

    /**
     * 按 ID 查询单条销售订单（含关联的客户名称、物流名称、创建人名称），用于 PDF 导出等场景。
     *
     * @param id 销售订单 ID
     * @return 含名称冗余字段的 VO，不存在时返回 null
     */
    ZcSalesOrderRespVO selectVOById(@Param("id") Long id);

}