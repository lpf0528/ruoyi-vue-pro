package cn.iocoder.yudao.module.zc.service.curtainstructureelement;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.curtainstructureelement.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtainstructureelement.ZcCurtainStructureElementDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.curtainstructureelement.ZcCurtainStructureElementMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;

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

    @Override
    public Long createCurtainStructureElement(ZcCurtainStructureElementSaveReqVO createReqVO) {
        validateCurtainStructureElementNameUnique(null, createReqVO.getName());
        // 插入
        ZcCurtainStructureElementDO curtainStructureElement = BeanUtils.toBean(createReqVO, ZcCurtainStructureElementDO.class);
        curtainStructureElementMapper.insert(curtainStructureElement);
        return curtainStructureElement.getId();
    }

    @Override
    public void updateCurtainStructureElement(ZcCurtainStructureElementSaveReqVO updateReqVO) {
        // 校验存在
        validateCurtainStructureElementExists(updateReqVO.getId());
        validateCurtainStructureElementNameUnique(updateReqVO.getId(), updateReqVO.getName());
        // 更新
        ZcCurtainStructureElementDO updateObj = BeanUtils.toBean(updateReqVO, ZcCurtainStructureElementDO.class);
        curtainStructureElementMapper.updateById(updateObj);
    }

    @Override
    public void deleteCurtainStructureElement(Long id) {
        // 校验存在
        validateCurtainStructureElementExists(id);
        // 删除
        curtainStructureElementMapper.deleteById(id);
    }

    @Override
        public void deleteCurtainStructureElementListByIds(List<Long> ids) {
        // 删除
        curtainStructureElementMapper.deleteByIds(ids);
        }


    private void validateCurtainStructureElementExists(Long id) {
        if (curtainStructureElementMapper.selectById(id) == null) {
            throw exception(CURTAIN_STRUCTURE_ELEMENT_NOT_EXISTS);
        }
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