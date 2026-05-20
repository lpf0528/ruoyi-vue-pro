package cn.iocoder.yudao.module.zc.service.bills;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.bills.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.bills.ZcBillMethodsDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.bills.ZcBillMethodsMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;

/**
 * 收款方式 Service 实现类
 *
 * @author 01Coder
 */
@Service
@Validated
public class ZcBillMethodsServiceImpl implements ZcBillMethodsService {

    @Resource
    private ZcBillMethodsMapper billMethodsMapper;

    @Override
    public Long createBillMethods(ZcBillMethodsSaveReqVO createReqVO) {
        // 插入
        ZcBillMethodsDO billMethods = BeanUtils.toBean(createReqVO, ZcBillMethodsDO.class);
        billMethodsMapper.insert(billMethods);

        // 返回
        return billMethods.getId();
    }

    @Override
    public void updateBillMethods(ZcBillMethodsSaveReqVO updateReqVO) {
        // 校验存在
        validateBillMethodsExists(updateReqVO.getId());
        // 更新
        ZcBillMethodsDO updateObj = BeanUtils.toBean(updateReqVO, ZcBillMethodsDO.class);
        billMethodsMapper.updateById(updateObj);
    }

    private void validateBillMethodsExists(Long id) {
        if (billMethodsMapper.selectById(id) == null) {
            throw exception(BILL_METHODS_NOT_EXISTS);
        }
    }

    @Override
    public ZcBillMethodsDO getBillMethods(Long id) {
        return billMethodsMapper.selectById(id);
    }

    @Override
    public PageResult<ZcBillMethodsDO> getBillMethodsPage(ZcBillMethodsPageReqVO pageReqVO) {
        return billMethodsMapper.selectPage(pageReqVO);
    }

}