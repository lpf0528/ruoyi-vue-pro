package cn.iocoder.yudao.module.zc.service.curtainstructure;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.curtainstructure.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtainstructure.ZcCurtainStructureDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.curtainstructure.ZcCurtainStructureMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;

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
    public Long createCurtainStructure(ZcCurtainStructureSaveReqVO createReqVO) {
        // 插入
        ZcCurtainStructureDO curtainStructure = BeanUtils.toBean(createReqVO, ZcCurtainStructureDO.class);
        curtainStructureMapper.insert(curtainStructure);

        // 返回
        return curtainStructure.getId();
    }

    @Override
    public void updateCurtainStructure(ZcCurtainStructureSaveReqVO updateReqVO) {
        // 校验存在
        validateCurtainStructureExists(updateReqVO.getId());
        // 更新
        ZcCurtainStructureDO updateObj = BeanUtils.toBean(updateReqVO, ZcCurtainStructureDO.class);
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