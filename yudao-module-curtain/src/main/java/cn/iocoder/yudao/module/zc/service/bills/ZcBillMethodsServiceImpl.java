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

import java.util.List;

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
        // 插入，分组固定为手工配置（1），不依赖前端传值
        ZcBillMethodsDO billMethod = BeanUtils.toBean(createReqVO, ZcBillMethodsDO.class);
        billMethod.setGroup(1);
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
        // 系统内置收款方式（group=0）禁止编辑
        validateBillMethodsNotSystem(oldBillMethod);
        // 更新（group 不由前端传入，保持库中原值）
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

    /**
     * 校验收款方式不是系统内置（group=0），否则抛出异常
     *
     * @param billMethod 待校验的收款方式
     */
    private void validateBillMethodsNotSystem(ZcBillMethodsDO billMethod) {
        if (Integer.valueOf(0).equals(billMethod.getGroup())) {
            throw exception(BILL_METHODS_SYSTEM_CANNOT_MODIFY);
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

    @Override
    public List<ZcBillMethodsDO> getBillMethodsList(ZcBillMethodsListReqVO listReqVO) {
        return billMethodsMapper.selectList(listReqVO);
    }

}
