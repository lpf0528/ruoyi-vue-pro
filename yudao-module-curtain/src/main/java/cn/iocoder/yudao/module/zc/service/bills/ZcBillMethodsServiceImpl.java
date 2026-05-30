package cn.iocoder.yudao.module.zc.service.bills;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import cn.iocoder.yudao.module.zc.controller.admin.bills.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.bills.ZcBillMethodsDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.bills.ZcBillMethodsMapper;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.service.impl.DiffParseFunction;
import com.mzt.logapi.starter.annotation.LogRecord;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.zc.enums.LogRecordConstants.*;

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
    @LogRecord(type = ZC_BILL_METHODS_TYPE, subType = ZC_BILL_METHODS_CREATE_SUB_TYPE, bizNo = "{{#billMethod.id}}",
            success = ZC_BILL_METHODS_CREATE_SUCCESS)
    public Long createBillMethods(ZcBillMethodsSaveReqVO createReqVO) {
        // 插入
        ZcBillMethodsDO billMethod = BeanUtils.toBean(createReqVO, ZcBillMethodsDO.class);
        billMethodsMapper.insert(billMethod);
        // 记录操作日志上下文
        LogRecordContext.putVariable("billMethod", billMethod);
        return billMethod.getId();
    }

    @Override
    @LogRecord(type = ZC_BILL_METHODS_TYPE, subType = ZC_BILL_METHODS_UPDATE_SUB_TYPE, bizNo = "{{#updateReqVO.id}}",
            success = ZC_BILL_METHODS_UPDATE_SUCCESS)
    public void updateBillMethods(ZcBillMethodsSaveReqVO updateReqVO) {
        // 校验存在
        ZcBillMethodsDO oldBillMethod = validateBillMethodsExists(updateReqVO.getId());
        // 更新
        ZcBillMethodsDO updateObj = BeanUtils.toBean(updateReqVO, ZcBillMethodsDO.class);
        billMethodsMapper.updateById(updateObj);
        // 记录操作日志上下文
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, BeanUtils.toBean(oldBillMethod, ZcBillMethodsSaveReqVO.class));
        LogRecordContext.putVariable("billMethodName", oldBillMethod.getName());
    }

    private ZcBillMethodsDO validateBillMethodsExists(Long id) {
        ZcBillMethodsDO billMethod = billMethodsMapper.selectById(id);
        if (billMethod == null) {
            throw exception(BILL_METHODS_NOT_EXISTS);
        }
        return billMethod;
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
