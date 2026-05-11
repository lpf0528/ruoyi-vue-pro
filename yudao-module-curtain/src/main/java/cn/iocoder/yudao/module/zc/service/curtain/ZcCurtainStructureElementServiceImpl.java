package cn.iocoder.yudao.module.zc.service.curtain;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.zc.dal.mysql.curtain.ZcCurtainStructureElementMapper;
import cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainStructureElementPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainStructureElementSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtain.ZcCurtainStructureElementDO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service
@Validated
public class ZcCurtainStructureElementServiceImpl implements ZcCurtainStructureElementService {

    @Resource
    private ZcCurtainStructureElementMapper curtainStructureElementMapper;

    @Override
    public Long create(ZcCurtainStructureElementSaveReqVO reqVO) {
        ZcCurtainStructureElementDO d = BeanUtils.toBean(reqVO, ZcCurtainStructureElementDO.class);
        curtainStructureElementMapper.insert(d);
        return d.getId();
    }

    @Override
    public void update(ZcCurtainStructureElementSaveReqVO reqVO) {
        validate(reqVO.getId());
        curtainStructureElementMapper.updateById(BeanUtils.toBean(reqVO, ZcCurtainStructureElementDO.class));
    }

    @Override
    public void delete(Long id) {
        validate(id);
        curtainStructureElementMapper.deleteById(id);
    }

    @Override
    public ZcCurtainStructureElementDO get(Long id) {
        return curtainStructureElementMapper.selectById(id);
    }

    @Override
    public PageResult<ZcCurtainStructureElementDO> getPage(ZcCurtainStructureElementPageReqVO pageReqVO) {
        return curtainStructureElementMapper.selectPage(pageReqVO, new LambdaQueryWrapperX<ZcCurtainStructureElementDO>()
                .likeIfPresent(ZcCurtainStructureElementDO::getName, pageReqVO.getName())
                .orderByDesc(ZcCurtainStructureElementDO::getId));
    }

    private void validate(Long id) {
        if (id == null || curtainStructureElementMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.CURTAIN_STRUCTURE_ELEMENT_NOT_EXISTS);
        }
    }

}
