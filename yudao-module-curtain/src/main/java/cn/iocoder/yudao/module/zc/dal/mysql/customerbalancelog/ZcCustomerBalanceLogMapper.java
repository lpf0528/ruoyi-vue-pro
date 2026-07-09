package cn.iocoder.yudao.module.zc.dal.mysql.customerbalancelog;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.customerbalancelog.ZcCustomerBalanceLogDO;
import cn.iocoder.yudao.module.zc.enums.ZcCustomerBalanceBizTypeEnum;
import cn.iocoder.yudao.module.zc.enums.ZcRefTypeEnum;
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

    /**
     * 查询指定客户、订单下最新一条「订单确认扣减」余额流水
     *
     * @param customerId 客户主键
     * @param refId      关联销售订单主键（zc_sales_order.id）
     * @return 最新流水，不存在则返回 null
     */
    default ZcCustomerBalanceLogDO selectLatestOrderConfirmLog(Long customerId, Long refId) {
        return selectOne(new LambdaQueryWrapperX<ZcCustomerBalanceLogDO>()
                .eq(ZcCustomerBalanceLogDO::getCustomerId, customerId)
                .eq(ZcCustomerBalanceLogDO::getRefId, refId)
                .eq(ZcCustomerBalanceLogDO::getRefType, ZcRefTypeEnum.SALES_ORDER.name())
                .eq(ZcCustomerBalanceLogDO::getBizType, ZcCustomerBalanceBizTypeEnum.ORDER_CONFIRM.name())
                .orderByDesc(ZcCustomerBalanceLogDO::getId)
                .last("LIMIT 1"));
    }

}