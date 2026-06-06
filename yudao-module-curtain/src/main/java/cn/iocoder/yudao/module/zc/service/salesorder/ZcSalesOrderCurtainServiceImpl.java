package cn.iocoder.yudao.module.zc.service.salesorder;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderCurtainDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZcSalesOrderCurtainMapper;

/**
 * 成品订单-窗帘行 Service 实现类
 *
 * @author o1Coder
 */
@Service
@Validated
public class ZcSalesOrderCurtainServiceImpl implements ZcSalesOrderCurtainService {

    @Resource
    private ZcSalesOrderCurtainMapper salesOrderCurtainMapper;

    @Override
    public ZcSalesOrderCurtainDO getSalesOrderCurtain(Long id) {
        return salesOrderCurtainMapper.selectById(id);
    }

    @Override
    public PageResult<ZcSalesOrderCurtainDO> getSalesOrderCurtainPage(ZcSalesOrderCurtainPageReqVO pageReqVO) {
        return salesOrderCurtainMapper.selectPage(pageReqVO);
    }

}
