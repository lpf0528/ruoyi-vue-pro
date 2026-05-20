package cn.iocoder.yudao.module.zc.dal.mysql.bills;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.bills.ZcBillsDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.bills.vo.*;

/**
 * 收支账单 Mapper
 *
 * @author 01Coder
 */
@Mapper
public interface ZcBillsMapper extends BaseMapperX<ZcBillsDO> {

    default PageResult<ZcBillsDO> selectPage(ZcBillsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ZcBillsDO>()
                .eqIfPresent(ZcBillsDO::getBillNo, reqVO.getBillNo())
                .betweenIfPresent(ZcBillsDO::getBillDate, reqVO.getBillDate())
                .eqIfPresent(ZcBillsDO::getBillUserId, reqVO.getBillUserId())
                .eqIfPresent(ZcBillsDO::getCustomerId, reqVO.getCustomerId())
                .eqIfPresent(ZcBillsDO::getBillMethodId, reqVO.getBillMethodId())
                .orderByDesc(ZcBillsDO::getId));
    }

}