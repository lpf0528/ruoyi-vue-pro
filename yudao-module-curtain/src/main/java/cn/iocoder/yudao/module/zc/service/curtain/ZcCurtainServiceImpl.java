package cn.iocoder.yudao.module.zc.service.curtain;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.curtain.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtain.ZcCurtainDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.curtain.ZcCurtainMapper;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.service.impl.DiffParseFunction;
import com.mzt.logapi.starter.annotation.LogRecord;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.zc.enums.LogRecordConstants.*;

/**
 * 窗帘 Service 实现类
 *
 * @author 01Coder
 */
@Service
@Validated
public class ZcCurtainServiceImpl implements ZcCurtainService {

    @Resource
    private ZcCurtainMapper curtainMapper;

    @Override
    @LogRecord(type = ZC_CURTAIN_TYPE, subType = ZC_CURTAIN_CREATE_SUB_TYPE, bizNo = "{{#curtain.id}}",
            success = ZC_CURTAIN_CREATE_SUCCESS)
    public Long createCurtain(ZcCurtainSaveReqVO createReqVO) {
        validateCurtainNameUnique(null, createReqVO.getName());
        // 插入
        ZcCurtainDO curtain = BeanUtils.toBean(createReqVO, ZcCurtainDO.class);
        curtainMapper.insert(curtain);
        // 记录操作日志上下文
        LogRecordContext.putVariable("curtain", curtain);
        return curtain.getId();
    }

    @Override
    @LogRecord(type = ZC_CURTAIN_TYPE, subType = ZC_CURTAIN_UPDATE_SUB_TYPE, bizNo = "{{#updateReqVO.id}}",
            success = ZC_CURTAIN_UPDATE_SUCCESS)
    public void updateCurtain(ZcCurtainSaveReqVO updateReqVO) {
        // 校验存在
        ZcCurtainDO oldCurtain = validateCurtainExists(updateReqVO.getId());
        validateCurtainNameUnique(updateReqVO.getId(), updateReqVO.getName());
        // 更新
        ZcCurtainDO updateObj = BeanUtils.toBean(updateReqVO, ZcCurtainDO.class);
        curtainMapper.updateById(updateObj);
        // 记录操作日志上下文
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, BeanUtils.toBean(oldCurtain, ZcCurtainSaveReqVO.class));
        LogRecordContext.putVariable("curtainName", oldCurtain.getName());
    }

    @Override
    @LogRecord(type = ZC_CURTAIN_TYPE, subType = ZC_CURTAIN_DELETE_SUB_TYPE, bizNo = "{{#id}}",
            success = ZC_CURTAIN_DELETE_SUCCESS)
    public void deleteCurtain(Long id) {
        // 校验存在
        ZcCurtainDO curtain = validateCurtainExists(id);
        // 记录操作日志上下文
        LogRecordContext.putVariable("curtainName", curtain.getName());
        // 删除
        curtainMapper.deleteById(id);
    }

    @Override
        public void deleteCurtainListByIds(List<Long> ids) {
        // 删除
        curtainMapper.deleteByIds(ids);
        }


    private ZcCurtainDO validateCurtainExists(Long id) {
        ZcCurtainDO curtain = curtainMapper.selectById(id);
        if (curtain == null) {
            throw exception(CURTAIN_NOT_EXISTS);
        }
        return curtain;
    }

    private void validateCurtainNameUnique(Long id, String name) {
        ZcCurtainDO existing = curtainMapper.selectByName(name);
        if (existing == null || existing.getId().equals(id)) {
            return;
        }
        throw exception(CURTAIN_NAME_EXISTS);
    }

    @Override
    public ZcCurtainDO getCurtain(Long id) {
        return curtainMapper.selectById(id);
    }

    @Override
    public PageResult<ZcCurtainDO> getCurtainPage(ZcCurtainPageReqVO pageReqVO) {
        return curtainMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ZcCurtainDO> getCurtainList(ZcCurtainListReqVO listReqVO) {
        return curtainMapper.selectList(listReqVO);
    }

}
