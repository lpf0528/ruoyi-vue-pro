package cn.iocoder.yudao.module.zc.dal.mysql.bills;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.bills.ZcBillMethodsDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.bills.vo.*;

/**
 * 收款方式 Mapper
 *
 * @author 01Coder
 */
@Mapper
public interface ZcBillMethodsMapper extends BaseMapperX<ZcBillMethodsDO> {

    default PageResult<ZcBillMethodsDO> selectPage(ZcBillMethodsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ZcBillMethodsDO>()
                .likeIfPresent(ZcBillMethodsDO::getName, reqVO.getName())
                .betweenIfPresent(ZcBillMethodsDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ZcBillMethodsDO::getId));
    }

    default List<ZcBillMethodsDO> selectList(ZcBillMethodsListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ZcBillMethodsDO>()
                .orderByDesc(ZcBillMethodsDO::getId));
    }

}