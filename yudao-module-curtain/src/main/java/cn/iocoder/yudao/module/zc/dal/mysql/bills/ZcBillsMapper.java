package cn.iocoder.yudao.module.zc.dal.mysql.bills;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.bills.ZcBillsDO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import cn.iocoder.yudao.module.zc.controller.admin.bills.vo.*;

/**
 * 收支账单 Mapper
 *
 * @author 01Coder
 */
@Mapper
public interface ZcBillsMapper extends BaseMapperX<ZcBillsDO> {

    /** XML 绑定方法，由 MyBatis Plus 分页插件注入 LIMIT/OFFSET 及 COUNT */
    IPage<ZcBillsRespVO> selectPageWithVO(IPage<?> page, @Param("reqVO") ZcBillsPageReqVO reqVO);

    default PageResult<ZcBillsRespVO> selectPage(ZcBillsPageReqVO reqVO) {
        IPage<ZcBillsRespVO> result = selectPageWithVO(
                new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), reqVO);
        return new PageResult<>(result.getRecords(), result.getTotal());
    }

    /**
     * 查询指定日期前缀（yyyyMMdd）下收款单号的最大序号
     *
     * <p>单号格式 SK{yyyyMMdd}-{6位序号}，序号从 SUBSTRING(bill_no, 12) 解析。
     * 含已软删记录：uk_tenant_bill_no 对租户内全表生效，软删单号仍不可复用。
     * 查询范围由多租户拦截器自动限定当前 tenant_id。</p>
     *
     * @param date 日期字符串，格式 yyyyMMdd
     * @return 最大序号，无记录时返回 null
     */
    @Select("SELECT MAX(CAST(SUBSTRING(bill_no, 12) AS UNSIGNED)) FROM zc_bills " +
            "WHERE bill_no LIKE CONCAT('SK', #{date}, '-%')")
    Long selectMaxBillSeqByDate(@Param("date") String date);

}