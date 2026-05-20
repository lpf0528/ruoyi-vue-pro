package cn.iocoder.yudao.module.zc.service.bills;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.bills.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.bills.ZcBillsDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.bills.ZcBillsMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;

/**
 * 收支账单 Service 实现类
 *
 * @author 01Coder
 */
@Service
@Validated
public class ZcBillsServiceImpl implements ZcBillsService {

    @Resource
    private ZcBillsMapper billsMapper;

    @Override
    public Long createBills(ZcBillsSaveReqVO createReqVO) {
        // 插入
        ZcBillsDO bills = BeanUtils.toBean(createReqVO, ZcBillsDO.class);
        billsMapper.insert(bills);

        // 返回
        return bills.getId();
    }

    @Override
    public void updateBills(ZcBillsSaveReqVO updateReqVO) {
        // 校验存在
        validateBillsExists(updateReqVO.getId());
        // 更新
        ZcBillsDO updateObj = BeanUtils.toBean(updateReqVO, ZcBillsDO.class);
        billsMapper.updateById(updateObj);
    }

    @Override
    public void deleteBills(Long id) {
        // 校验存在
        validateBillsExists(id);
        // 删除
        billsMapper.deleteById(id);
    }

    @Override
        public void deleteBillsListByIds(List<Long> ids) {
        // 删除
        billsMapper.deleteByIds(ids);
        }


    private void validateBillsExists(Long id) {
        if (billsMapper.selectById(id) == null) {
            throw exception(BILLS_NOT_EXISTS);
        }
    }

    @Override
    public ZcBillsDO getBills(Long id) {
        return billsMapper.selectById(id);
    }

    @Override
    public PageResult<ZcBillsDO> getBillsPage(ZcBillsPageReqVO pageReqVO) {
        return billsMapper.selectPage(pageReqVO);
    }

}