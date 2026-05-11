package cn.iocoder.yudao.module.zc.service.base;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.zc.controller.admin.vo.base.ZcBrandPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.base.ZcBrandSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.base.ZcBrandDO;
import cn.iocoder.yudao.module.zc.dal.mysql.base.ZcBrandMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service
@Validated
public class ZcBrandServiceImpl implements ZcBrandService {

    @Resource
    private ZcBrandMapper brandMapper;

    @Override
    public Long create(ZcBrandSaveReqVO reqVO) {
        ZcBrandDO d = BeanUtils.toBean(reqVO, ZcBrandDO.class);
        brandMapper.insert(d);
        return d.getId();
    }

    @Override
    public void update(ZcBrandSaveReqVO reqVO) {
        validate(reqVO.getId());
        brandMapper.updateById(BeanUtils.toBean(reqVO, ZcBrandDO.class));
    }

    @Override
    public void delete(Long id) {
        validate(id);
        brandMapper.deleteById(id);
    }

    @Override
    public ZcBrandDO get(Long id) {
        return brandMapper.selectById(id);
    }

    @Override
    public PageResult<ZcBrandDO> getPage(ZcBrandPageReqVO pageReqVO) {
        return brandMapper.selectPage(pageReqVO, new LambdaQueryWrapperX<ZcBrandDO>()
                .likeIfPresent(ZcBrandDO::getName, pageReqVO.getName())
                .orderByDesc(ZcBrandDO::getId));
    }

    private void validate(Long id) {
        if (id == null || brandMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.BRAND_NOT_EXISTS);
        }
    }

}
