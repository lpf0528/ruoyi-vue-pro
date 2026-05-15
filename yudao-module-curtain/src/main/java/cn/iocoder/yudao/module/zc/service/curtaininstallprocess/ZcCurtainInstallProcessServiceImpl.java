package cn.iocoder.yudao.module.zc.service.curtaininstallprocess;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.curtaininstallprocess.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtaininstallprocess.ZcCurtainInstallProcessDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.curtaininstallprocess.ZcCurtainInstallProcessMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;

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
    public Long createCurtainInstallProcess(ZcCurtainInstallProcessSaveReqVO createReqVO) {
        // 插入
        ZcCurtainInstallProcessDO curtainInstallProcess = BeanUtils.toBean(createReqVO, ZcCurtainInstallProcessDO.class);
        curtainInstallProcessMapper.insert(curtainInstallProcess);

        // 返回
        return curtainInstallProcess.getId();
    }

    @Override
    public void updateCurtainInstallProcess(ZcCurtainInstallProcessSaveReqVO updateReqVO) {
        // 校验存在
        validateCurtainInstallProcessExists(updateReqVO.getId());
        // 更新
        ZcCurtainInstallProcessDO updateObj = BeanUtils.toBean(updateReqVO, ZcCurtainInstallProcessDO.class);
        curtainInstallProcessMapper.updateById(updateObj);
    }

    @Override
    public void deleteCurtainInstallProcess(Long id) {
        // 校验存在
        validateCurtainInstallProcessExists(id);
        // 删除
        curtainInstallProcessMapper.deleteById(id);
    }

    @Override
        public void deleteCurtainInstallProcessListByIds(List<Long> ids) {
        // 删除
        curtainInstallProcessMapper.deleteByIds(ids);
        }


    private void validateCurtainInstallProcessExists(Long id) {
        if (curtainInstallProcessMapper.selectById(id) == null) {
            throw exception(CURTAIN_INSTALL_PROCESS_NOT_EXISTS);
        }
    }

    @Override
    public ZcCurtainInstallProcessDO getCurtainInstallProcess(Long id) {
        return curtainInstallProcessMapper.selectById(id);
    }

    @Override
    public PageResult<ZcCurtainInstallProcessDO> getCurtainInstallProcessPage(ZcCurtainInstallProcessPageReqVO pageReqVO) {
        return curtainInstallProcessMapper.selectPage(pageReqVO);
    }

}