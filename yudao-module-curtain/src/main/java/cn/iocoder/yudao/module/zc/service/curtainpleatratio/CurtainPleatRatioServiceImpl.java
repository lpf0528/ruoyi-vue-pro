package cn.iocoder.yudao.module.zc.service.curtainpleatratio;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.curtainpleatratio.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtainpleatratio.CurtainPleatRatioDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.curtainpleatratio.CurtainPleatRatioMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;

/**
 * 褶倍 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class CurtainPleatRatioServiceImpl implements CurtainPleatRatioService {

    @Resource
    private CurtainPleatRatioMapper curtainPleatRatioMapper;

    @Override
    public Long createCurtainPleatRatio(CurtainPleatRatioSaveReqVO createReqVO) {
        // 插入
        CurtainPleatRatioDO curtainPleatRatio = BeanUtils.toBean(createReqVO, CurtainPleatRatioDO.class);
        curtainPleatRatioMapper.insert(curtainPleatRatio);

        // 返回
        return curtainPleatRatio.getId();
    }

    @Override
    public void updateCurtainPleatRatio(CurtainPleatRatioSaveReqVO updateReqVO) {
        // 校验存在
        validateCurtainPleatRatioExists(updateReqVO.getId());
        // 更新
        CurtainPleatRatioDO updateObj = BeanUtils.toBean(updateReqVO, CurtainPleatRatioDO.class);
        curtainPleatRatioMapper.updateById(updateObj);
    }

    @Override
    public void deleteCurtainPleatRatio(Long id) {
        // 校验存在
        validateCurtainPleatRatioExists(id);
        // 删除
        curtainPleatRatioMapper.deleteById(id);
    }

    @Override
        public void deleteCurtainPleatRatioListByIds(List<Long> ids) {
        // 删除
        curtainPleatRatioMapper.deleteByIds(ids);
        }


    private void validateCurtainPleatRatioExists(Long id) {
        if (curtainPleatRatioMapper.selectById(id) == null) {
            throw exception(CURTAIN_PLEAT_RATIO_NOT_EXISTS);
        }
    }

    @Override
    public CurtainPleatRatioDO getCurtainPleatRatio(Long id) {
        return curtainPleatRatioMapper.selectById(id);
    }

    @Override
    public PageResult<CurtainPleatRatioDO> getCurtainPleatRatioPage(CurtainPleatRatioPageReqVO pageReqVO) {
        return curtainPleatRatioMapper.selectPage(pageReqVO);
    }

}