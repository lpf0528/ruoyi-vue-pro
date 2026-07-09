package cn.iocoder.yudao.module.zc.service.curtainstructure;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.curtainstructure.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtainstructure.ZcCurtainStructureDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.curtainstructure.ZcCurtainStructureMapper;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.service.impl.DiffParseFunction;
import com.mzt.logapi.starter.annotation.LogRecord;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.zc.enums.LogRecordConstants.*;

/**
 * 窗帘结构 Service 实现类
 *
 * @author 01Coder
 */
@Service
@Validated
public class ZcCurtainStructureServiceImpl implements ZcCurtainStructureService {

    @Resource
    private ZcCurtainStructureMapper curtainStructureMapper;

    @Override
    @LogRecord(type = ZC_CURTAIN_STRUCTURE_TYPE, subType = ZC_CURTAIN_STRUCTURE_CREATE_SUB_TYPE, bizNo = "{{#curtainStructure.id}}",
            success = ZC_CURTAIN_STRUCTURE_CREATE_SUCCESS)
    public Long createCurtainStructure(ZcCurtainStructureSaveReqVO createReqVO) {
        validateCurtainStructureNameUnique(null, createReqVO.getName());
        // 插入
        ZcCurtainStructureDO curtainStructure = BeanUtils.toBean(createReqVO, ZcCurtainStructureDO.class);
        curtainStructureMapper.insert(curtainStructure);
        // 记录操作日志上下文
        LogRecordContext.putVariable("curtainStructure", curtainStructure);
        return curtainStructure.getId();
    }

    @Override
    @LogRecord(type = ZC_CURTAIN_STRUCTURE_TYPE, subType = ZC_CURTAIN_STRUCTURE_UPDATE_SUB_TYPE, bizNo = "{{#updateReqVO.id}}",
            success = ZC_CURTAIN_STRUCTURE_UPDATE_SUCCESS)
    public void updateCurtainStructure(ZcCurtainStructureSaveReqVO updateReqVO) {
        // 校验存在
        ZcCurtainStructureDO oldCurtainStructure = validateCurtainStructureExists(updateReqVO.getId());
        validateCurtainStructureNameUnique(updateReqVO.getId(), updateReqVO.getName());
        // 更新
        ZcCurtainStructureDO updateObj = BeanUtils.toBean(updateReqVO, ZcCurtainStructureDO.class);
        curtainStructureMapper.updateById(updateObj);
        // 记录操作日志上下文
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, BeanUtils.toBean(oldCurtainStructure, ZcCurtainStructureSaveReqVO.class));
        LogRecordContext.putVariable("curtainStructureName", oldCurtainStructure.getName());
    }

    @Override
    @LogRecord(type = ZC_CURTAIN_STRUCTURE_TYPE, subType = ZC_CURTAIN_STRUCTURE_DELETE_SUB_TYPE, bizNo = "{{#id}}",
            success = ZC_CURTAIN_STRUCTURE_DELETE_SUCCESS)
    public void deleteCurtainStructure(Long id) {
        // 校验存在
        ZcCurtainStructureDO curtainStructure = validateCurtainStructureExists(id);
        // 记录操作日志上下文
        LogRecordContext.putVariable("curtainStructureName", curtainStructure.getName());
        // 删除
        curtainStructureMapper.deleteById(id);
    }

    @Override
        public void deleteCurtainStructureListByIds(List<Long> ids) {
        // 删除
        curtainStructureMapper.deleteByIds(ids);
        }


    private ZcCurtainStructureDO validateCurtainStructureExists(Long id) {
        ZcCurtainStructureDO curtainStructure = curtainStructureMapper.selectById(id);
        if (curtainStructure == null) {
            throw exception(CURTAIN_STRUCTURE_NOT_EXISTS);
        }
        return curtainStructure;
    }

    private void validateCurtainStructureNameUnique(Long id, String name) {
        ZcCurtainStructureDO existing = curtainStructureMapper.selectByName(name);
        if (existing == null || existing.getId().equals(id)) {
            return;
        }
        throw exception(CURTAIN_STRUCTURE_NAME_EXISTS);
    }

    @Override
    public ZcCurtainStructureDO getCurtainStructure(Long id) {
        return curtainStructureMapper.selectById(id);
    }

    @Override
    public PageResult<ZcCurtainStructureDO> getCurtainStructurePage(ZcCurtainStructurePageReqVO pageReqVO) {
        return curtainStructureMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ZcCurtainStructureDO> getCurtainStructureList(ZcCurtainStructureListReqVO listReqVO) {
        return curtainStructureMapper.selectList(listReqVO);
    }

}
