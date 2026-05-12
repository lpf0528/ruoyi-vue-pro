package cn.iocoder.yudao.module.zc.service.curtainstructureelement;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.curtainstructureelement.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtainstructureelement.CurtainStructureElementDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.curtainstructureelement.CurtainStructureElementMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;

/**
 * 结构配件类型 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class CurtainStructureElementServiceImpl implements CurtainStructureElementService {

    @Resource
    private CurtainStructureElementMapper curtainStructureElementMapper;

    @Override
    public Long createCurtainStructureElement(CurtainStructureElementSaveReqVO createReqVO) {
        // 插入
        CurtainStructureElementDO curtainStructureElement = BeanUtils.toBean(createReqVO, CurtainStructureElementDO.class);
        curtainStructureElementMapper.insert(curtainStructureElement);

        // 返回
        return curtainStructureElement.getId();
    }

    @Override
    public void updateCurtainStructureElement(CurtainStructureElementSaveReqVO updateReqVO) {
        // 校验存在
        validateCurtainStructureElementExists(updateReqVO.getId());
        // 更新
        CurtainStructureElementDO updateObj = BeanUtils.toBean(updateReqVO, CurtainStructureElementDO.class);
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

    @Override
    public CurtainStructureElementDO getCurtainStructureElement(Long id) {
        return curtainStructureElementMapper.selectById(id);
    }

    @Override
    public PageResult<CurtainStructureElementDO> getCurtainStructureElementPage(CurtainStructureElementPageReqVO pageReqVO) {
        return curtainStructureElementMapper.selectPage(pageReqVO);
    }

}