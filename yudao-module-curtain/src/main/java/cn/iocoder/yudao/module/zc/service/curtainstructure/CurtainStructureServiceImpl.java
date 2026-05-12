package cn.iocoder.yudao.module.zc.service.curtainstructure;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.curtainstructure.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtainstructure.CurtainStructureDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.curtainstructure.CurtainStructureMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;

/**
 * 窗帘结构部位 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class CurtainStructureServiceImpl implements CurtainStructureService {

    @Resource
    private CurtainStructureMapper curtainStructureMapper;

    @Override
    public Long createCurtainStructure(CurtainStructureSaveReqVO createReqVO) {
        // 插入
        CurtainStructureDO curtainStructure = BeanUtils.toBean(createReqVO, CurtainStructureDO.class);
        curtainStructureMapper.insert(curtainStructure);

        // 返回
        return curtainStructure.getId();
    }

    @Override
    public void updateCurtainStructure(CurtainStructureSaveReqVO updateReqVO) {
        // 校验存在
        validateCurtainStructureExists(updateReqVO.getId());
        // 更新
        CurtainStructureDO updateObj = BeanUtils.toBean(updateReqVO, CurtainStructureDO.class);
        curtainStructureMapper.updateById(updateObj);
    }

    @Override
    public void deleteCurtainStructure(Long id) {
        // 校验存在
        validateCurtainStructureExists(id);
        // 删除
        curtainStructureMapper.deleteById(id);
    }

    @Override
        public void deleteCurtainStructureListByIds(List<Long> ids) {
        // 删除
        curtainStructureMapper.deleteByIds(ids);
        }


    private void validateCurtainStructureExists(Long id) {
        if (curtainStructureMapper.selectById(id) == null) {
            throw exception(CURTAIN_STRUCTURE_NOT_EXISTS);
        }
    }

    @Override
    public CurtainStructureDO getCurtainStructure(Long id) {
        return curtainStructureMapper.selectById(id);
    }

    @Override
    public PageResult<CurtainStructureDO> getCurtainStructurePage(CurtainStructurePageReqVO pageReqVO) {
        return curtainStructureMapper.selectPage(pageReqVO);
    }

}