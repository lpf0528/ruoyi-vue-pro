package cn.iocoder.yudao.module.zc.service.curtain;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.zc.dal.mysql.curtain.ZcCurtainStructureMapper;
import cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainStructurePageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainStructureSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtain.ZcCurtainStructureDO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service
@Validated
public class ZcCurtainStructureServiceImpl implements ZcCurtainStructureService {

    @Resource
    private ZcCurtainStructureMapper curtainStructureMapper;

    @Override
    public Long create(ZcCurtainStructureSaveReqVO reqVO) {
        ZcCurtainStructureDO d = BeanUtils.toBean(reqVO, ZcCurtainStructureDO.class);
        curtainStructureMapper.insert(d);
        return d.getId();
    }

    @Override
    public void update(ZcCurtainStructureSaveReqVO reqVO) {
        validate(reqVO.getId());
        curtainStructureMapper.updateById(BeanUtils.toBean(reqVO, ZcCurtainStructureDO.class));
    }

    @Override
    public void delete(Long id) {
        validate(id);
        curtainStructureMapper.deleteById(id);
    }

    @Override
    public ZcCurtainStructureDO get(Long id) {
        return curtainStructureMapper.selectById(id);
    }

    @Override
    public PageResult<ZcCurtainStructureDO> getPage(ZcCurtainStructurePageReqVO pageReqVO) {
        return curtainStructureMapper.selectPage(pageReqVO, new LambdaQueryWrapperX<ZcCurtainStructureDO>()
                .likeIfPresent(ZcCurtainStructureDO::getName, pageReqVO.getName())
                .eqIfPresent(ZcCurtainStructureDO::getType, pageReqVO.getType())
                .orderByDesc(ZcCurtainStructureDO::getId));
    }

    private void validate(Long id) {
        if (id == null || curtainStructureMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.CURTAIN_STRUCTURE_NOT_EXISTS);
        }
    }

}
