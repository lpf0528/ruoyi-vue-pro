package cn.iocoder.yudao.module.zc.service.salesorder;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderCurtainDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZcSalesOrderCurtainMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;

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
    public Long createSalesOrderCurtain(ZcSalesOrderCurtainSaveReqVO createReqVO) {
        // 插入
        ZcSalesOrderCurtainDO salesOrderCurtain = BeanUtils.toBean(createReqVO, ZcSalesOrderCurtainDO.class);
        salesOrderCurtainMapper.insert(salesOrderCurtain);

        // 返回
        return salesOrderCurtain.getId();
    }

    @Override
    public void updateSalesOrderCurtain(ZcSalesOrderCurtainSaveReqVO updateReqVO) {
        // 校验存在
        validateSalesOrderCurtainExists(updateReqVO.getId());
        // 更新
        ZcSalesOrderCurtainDO updateObj = BeanUtils.toBean(updateReqVO, ZcSalesOrderCurtainDO.class);
        salesOrderCurtainMapper.updateById(updateObj);
    }

    @Override
    public void deleteSalesOrderCurtain(Long id) {
        // 校验存在
        validateSalesOrderCurtainExists(id);
        // 删除
        salesOrderCurtainMapper.deleteById(id);
    }

    @Override
        public void deleteSalesOrderCurtainListByIds(List<Long> ids) {
        // 删除
        salesOrderCurtainMapper.deleteByIds(ids);
        }


    private void validateSalesOrderCurtainExists(Long id) {
        if (salesOrderCurtainMapper.selectById(id) == null) {
            throw exception(SALES_ORDER_CURTAIN_NOT_EXISTS);
        }
    }

    @Override
    public ZcSalesOrderCurtainDO getSalesOrderCurtain(Long id) {
        return salesOrderCurtainMapper.selectById(id);
    }

    @Override
    public PageResult<ZcSalesOrderCurtainDO> getSalesOrderCurtainPage(ZcSalesOrderCurtainPageReqVO pageReqVO) {
        return salesOrderCurtainMapper.selectPage(pageReqVO);
    }

}