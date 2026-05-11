package cn.iocoder.yudao.module.zc.service.curtain;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.zc.dal.mysql.curtain.ZcCurtainInstallProcessMapper;
import cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainInstallProcessPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainInstallProcessSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtain.ZcCurtainInstallProcessDO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service
@Validated
public class ZcCurtainInstallProcessServiceImpl implements ZcCurtainInstallProcessService {

    @Resource
    private ZcCurtainInstallProcessMapper curtainInstallProcessMapper;

    @Override
    public Long create(ZcCurtainInstallProcessSaveReqVO reqVO) {
        ZcCurtainInstallProcessDO d = BeanUtils.toBean(reqVO, ZcCurtainInstallProcessDO.class);
        curtainInstallProcessMapper.insert(d);
        return d.getId();
    }

    @Override
    public void update(ZcCurtainInstallProcessSaveReqVO reqVO) {
        validate(reqVO.getId());
        curtainInstallProcessMapper.updateById(BeanUtils.toBean(reqVO, ZcCurtainInstallProcessDO.class));
    }

    @Override
    public void delete(Long id) {
        validate(id);
        curtainInstallProcessMapper.deleteById(id);
    }

    @Override
    public ZcCurtainInstallProcessDO get(Long id) {
        return curtainInstallProcessMapper.selectById(id);
    }

    @Override
    public PageResult<ZcCurtainInstallProcessDO> getPage(ZcCurtainInstallProcessPageReqVO pageReqVO) {
        return curtainInstallProcessMapper.selectPage(pageReqVO, new LambdaQueryWrapperX<ZcCurtainInstallProcessDO>()
                .likeIfPresent(ZcCurtainInstallProcessDO::getName, pageReqVO.getName())
                .orderByDesc(ZcCurtainInstallProcessDO::getId));
    }

    private void validate(Long id) {
        if (id == null || curtainInstallProcessMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.CURTAIN_INSTALL_PROCESS_NOT_EXISTS);
        }
    }

}
