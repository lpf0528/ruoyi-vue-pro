package cn.iocoder.yudao.module.zc.dal.mysql.customerbalancelog;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.customerbalancelog.ZcCustomerBalanceLogDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import cn.iocoder.yudao.module.zc.controller.admin.customerbalancelog.vo.*;

/**
 * 客户余额变动流水 Mapper
 *
 * @author 01Coder
 */
@Mapper
public interface ZcCustomerBalanceLogMapper extends BaseMapperX<ZcCustomerBalanceLogDO> {

    /** XML 绑定方法，由分页插件自动注入 LIMIT/COUNT */
    IPage<ZcCustomerBalanceLogRespVO> selectPageWithVO(IPage<?> page, @Param("reqVO") ZcCustomerBalanceLogPageReqVO reqVO);

    /** 对外统一入口，封装 IPage → PageResult */
    default PageResult<ZcCustomerBalanceLogRespVO> selectPage(ZcCustomerBalanceLogPageReqVO reqVO) {
        IPage<ZcCustomerBalanceLogRespVO> result = selectPageWithVO(
                new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), reqVO);
        return new PageResult<>(result.getRecords(), result.getTotal());
    }

}