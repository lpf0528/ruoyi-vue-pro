package cn.iocoder.yudao.module.zc.service.curtain;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.curtain.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtain.CurtainDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.curtain.CurtainMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;

/**
 * 窗帘 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class CurtainServiceImpl implements CurtainService {

    @Resource
    private CurtainMapper curtainMapper;

    @Override
    public Long createCurtain(CurtainSaveReqVO createReqVO) {
        // 插入
        CurtainDO curtain = BeanUtils.toBean(createReqVO, CurtainDO.class);
        curtainMapper.insert(curtain);

        // 返回
        return curtain.getId();
    }

    @Override
    public void updateCurtain(CurtainSaveReqVO updateReqVO) {
        // 校验存在
        validateCurtainExists(updateReqVO.getId());
        // 更新
        CurtainDO updateObj = BeanUtils.toBean(updateReqVO, CurtainDO.class);
        curtainMapper.updateById(updateObj);
    }

    @Override
    public void deleteCurtain(Long id) {
        // 校验存在
        validateCurtainExists(id);
        // 删除
        curtainMapper.deleteById(id);
    }

    @Override
        public void deleteCurtainListByIds(List<Long> ids) {
        // 删除
        curtainMapper.deleteByIds(ids);
        }


    private void validateCurtainExists(Long id) {
        if (curtainMapper.selectById(id) == null) {
            throw exception(CURTAIN_NOT_EXISTS);
        }
    }

    @Override
    public CurtainDO getCurtain(Long id) {
        return curtainMapper.selectById(id);
    }

    @Override
    public PageResult<CurtainDO> getCurtainPage(CurtainPageReqVO pageReqVO) {
        return curtainMapper.selectPage(pageReqVO);
    }

}