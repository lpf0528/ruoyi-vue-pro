package cn.iocoder.yudao.module.zc.service.curtaininstallprocess;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.curtaininstallprocess.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtaininstallprocess.ZcCurtainInstallProcessDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.curtaininstallprocess.ZcCurtainInstallProcessMapper;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.service.impl.DiffParseFunction;
import com.mzt.logapi.starter.annotation.LogRecord;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.zc.enums.LogRecordConstants.*;

/**
 * 安装工艺 Service 实现类
 *
 * @author 01Coder
 */
@Service
@Validated
public class ZcCurtainInstallProcessServiceImpl implements ZcCurtainInstallProcessService {

    @Resource
    private ZcCurtainInstallProcessMapper curtainInstallProcessMapper;

    @Override
    @LogRecord(type = ZC_CURTAIN_INSTALL_PROCESS_TYPE, subType = ZC_CURTAIN_INSTALL_PROCESS_CREATE_SUB_TYPE,
            bizNo = "{{#installProcess.id}}", success = ZC_CURTAIN_INSTALL_PROCESS_CREATE_SUCCESS)
    public Long createCurtainInstallProcess(ZcCurtainInstallProcessSaveReqVO createReqVO) {
        validateCurtainInstallProcessNameUnique(null, createReqVO.getName());
        // 插入
        ZcCurtainInstallProcessDO installProcess = BeanUtils.toBean(createReqVO, ZcCurtainInstallProcessDO.class);
        curtainInstallProcessMapper.insert(installProcess);
        // 记录操作日志上下文
        LogRecordContext.putVariable("installProcess", installProcess);
        return installProcess.getId();
    }

    @Override
    @LogRecord(type = ZC_CURTAIN_INSTALL_PROCESS_TYPE, subType = ZC_CURTAIN_INSTALL_PROCESS_UPDATE_SUB_TYPE,
            bizNo = "{{#updateReqVO.id}}", success = ZC_CURTAIN_INSTALL_PROCESS_UPDATE_SUCCESS)
    public void updateCurtainInstallProcess(ZcCurtainInstallProcessSaveReqVO updateReqVO) {
        // 校验存在
        ZcCurtainInstallProcessDO oldInstallProcess = validateCurtainInstallProcessExists(updateReqVO.getId());
        validateCurtainInstallProcessNameUnique(updateReqVO.getId(), updateReqVO.getName());
        // 更新
        ZcCurtainInstallProcessDO updateObj = BeanUtils.toBean(updateReqVO, ZcCurtainInstallProcessDO.class);
        curtainInstallProcessMapper.updateById(updateObj);
        // 记录操作日志上下文
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, BeanUtils.toBean(oldInstallProcess, ZcCurtainInstallProcessSaveReqVO.class));
        LogRecordContext.putVariable("installProcessName", oldInstallProcess.getName());
    }

    @Override
    @LogRecord(type = ZC_CURTAIN_INSTALL_PROCESS_TYPE, subType = ZC_CURTAIN_INSTALL_PROCESS_DELETE_SUB_TYPE,
            bizNo = "{{#id}}", success = ZC_CURTAIN_INSTALL_PROCESS_DELETE_SUCCESS)
    public void deleteCurtainInstallProcess(Long id) {
        // 校验存在
        ZcCurtainInstallProcessDO installProcess = validateCurtainInstallProcessExists(id);
        // 记录操作日志上下文
        LogRecordContext.putVariable("installProcessName", installProcess.getName());
        // 删除
        curtainInstallProcessMapper.deleteById(id);
    }

    @Override
        public void deleteCurtainInstallProcessListByIds(List<Long> ids) {
        // 删除
        curtainInstallProcessMapper.deleteByIds(ids);
        }


    private ZcCurtainInstallProcessDO validateCurtainInstallProcessExists(Long id) {
        ZcCurtainInstallProcessDO installProcess = curtainInstallProcessMapper.selectById(id);
        if (installProcess == null) {
            throw exception(CURTAIN_INSTALL_PROCESS_NOT_EXISTS);
        }
        return installProcess;
    }

    private void validateCurtainInstallProcessNameUnique(Long id, String name) {
        ZcCurtainInstallProcessDO existing = curtainInstallProcessMapper.selectByName(name);
        if (existing == null || existing.getId().equals(id)) {
            return;
        }
        throw exception(CURTAIN_INSTALL_PROCESS_NAME_EXISTS);
    }

    @Override
    public ZcCurtainInstallProcessDO getCurtainInstallProcess(Long id) {
        return curtainInstallProcessMapper.selectById(id);
    }

    @Override
    public PageResult<ZcCurtainInstallProcessDO> getCurtainInstallProcessPage(ZcCurtainInstallProcessPageReqVO pageReqVO) {
        return curtainInstallProcessMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ZcCurtainInstallProcessDO> getCurtainInstallProcessList(ZcCurtainInstallProcessListReqVO listReqVO) {
        return curtainInstallProcessMapper.selectList(listReqVO);
    }

}
