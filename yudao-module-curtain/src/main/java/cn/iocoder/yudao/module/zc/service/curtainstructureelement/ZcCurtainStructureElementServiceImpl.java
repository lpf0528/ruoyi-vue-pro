package cn.iocoder.yudao.module.zc.service.curtainstructureelement;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.curtainstructureelement.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtainstructureelement.ZcCurtainStructureElementDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.curtainstructureelement.ZcCurtainStructureElementMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.curtaintemplate.ZcCurtainTemplateMapper;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.service.impl.DiffParseFunction;
import com.mzt.logapi.starter.annotation.LogRecord;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.zc.enums.LogRecordConstants.*;

/**
 * 窗帘结构组件 Service 实现类
 *
 * @author 01Coder
 */
@Service
@Validated
public class ZcCurtainStructureElementServiceImpl implements ZcCurtainStructureElementService {

    @Resource
    private ZcCurtainStructureElementMapper curtainStructureElementMapper;
    @Resource
    private ZcCurtainTemplateMapper curtainTemplateMapper;

    @Override
    @LogRecord(type = ZC_CURTAIN_STRUCTURE_ELEMENT_TYPE, subType = ZC_CURTAIN_STRUCTURE_ELEMENT_CREATE_SUB_TYPE,
            bizNo = "{{#element.id}}", success = ZC_CURTAIN_STRUCTURE_ELEMENT_CREATE_SUCCESS)
    public Long createCurtainStructureElement(ZcCurtainStructureElementSaveReqVO createReqVO) {
        validateCurtainStructureElementNameUnique(null, createReqVO.getName());
        // 插入
        ZcCurtainStructureElementDO element = BeanUtils.toBean(createReqVO, ZcCurtainStructureElementDO.class);
        curtainStructureElementMapper.insert(element);
        // 记录操作日志上下文
        LogRecordContext.putVariable("element", element);
        return element.getId();
    }

    @Override
    @LogRecord(type = ZC_CURTAIN_STRUCTURE_ELEMENT_TYPE, subType = ZC_CURTAIN_STRUCTURE_ELEMENT_UPDATE_SUB_TYPE,
            bizNo = "{{#updateReqVO.id}}", success = ZC_CURTAIN_STRUCTURE_ELEMENT_UPDATE_SUCCESS)
    public void updateCurtainStructureElement(ZcCurtainStructureElementSaveReqVO updateReqVO) {
        // 校验存在
        ZcCurtainStructureElementDO oldElement = validateCurtainStructureElementExists(updateReqVO.getId());
        validateCurtainStructureElementNameUnique(updateReqVO.getId(), updateReqVO.getName());
        // 更新
        ZcCurtainStructureElementDO updateObj = BeanUtils.toBean(updateReqVO, ZcCurtainStructureElementDO.class);
        curtainStructureElementMapper.updateById(updateObj);
        // 记录操作日志上下文
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, BeanUtils.toBean(oldElement, ZcCurtainStructureElementSaveReqVO.class));
        LogRecordContext.putVariable("elementName", oldElement.getName());
    }

    @Override
    @LogRecord(type = ZC_CURTAIN_STRUCTURE_ELEMENT_TYPE, subType = ZC_CURTAIN_STRUCTURE_ELEMENT_DELETE_SUB_TYPE,
            bizNo = "{{#id}}", success = ZC_CURTAIN_STRUCTURE_ELEMENT_DELETE_SUCCESS)
    public void deleteCurtainStructureElement(Long id) {
        // 校验存在
        ZcCurtainStructureElementDO element = validateCurtainStructureElementExists(id);
        // 校验该组件是否已被窗帘模板引用，引用时禁止删除
        if (curtainTemplateMapper.selectByElementId(id) != null) {
            throw exception(CURTAIN_STRUCTURE_ELEMENT_HAS_TEMPLATE);
        }
        // 记录操作日志上下文
        LogRecordContext.putVariable("elementName", element.getName());
        // 删除
        curtainStructureElementMapper.deleteById(id);
    }

    @Override
    public void deleteCurtainStructureElementListByIds(List<Long> ids) {
        // 校验批量删除的组件中是否有已被窗帘模板引用的，引用时禁止删除
        if (curtainTemplateMapper.selectByElementIds(ids) != null) {
            throw exception(CURTAIN_STRUCTURE_ELEMENT_HAS_TEMPLATE);
        }
        // 删除
        curtainStructureElementMapper.deleteByIds(ids);
    }


    private ZcCurtainStructureElementDO validateCurtainStructureElementExists(Long id) {
        ZcCurtainStructureElementDO element = curtainStructureElementMapper.selectById(id);
        if (element == null) {
            throw exception(CURTAIN_STRUCTURE_ELEMENT_NOT_EXISTS);
        }
        return element;
    }

    private void validateCurtainStructureElementNameUnique(Long id, String name) {
        ZcCurtainStructureElementDO existing = curtainStructureElementMapper.selectByName(name);
        if (existing == null || existing.getId().equals(id)) {
            return;
        }
        throw exception(CURTAIN_STRUCTURE_ELEMENT_NAME_EXISTS);
    }

    @Override
    public ZcCurtainStructureElementDO getCurtainStructureElement(Long id) {
        return curtainStructureElementMapper.selectById(id);
    }

    @Override
    public PageResult<ZcCurtainStructureElementRespVO> getCurtainStructureElementPage(ZcCurtainStructureElementPageReqVO pageReqVO) {
        return curtainStructureElementMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ZcCurtainStructureElementDO> getCurtainStructureElementList(ZcCurtainStructureElementListReqVO listReqVO) {
        return curtainStructureElementMapper.selectList(listReqVO);
    }

}
