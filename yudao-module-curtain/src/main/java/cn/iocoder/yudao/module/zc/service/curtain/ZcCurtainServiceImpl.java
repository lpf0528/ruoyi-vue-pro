package cn.iocoder.yudao.module.zc.service.curtain;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.curtain.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtain.ZcCurtainDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.curtain.ZcCurtainMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;

/**
 * 窗帘 Service 实现类
 *
 * @author 01Coder
 */
@Service
@Validated
public class ZcCurtainServiceImpl implements ZcCurtainService {

    @Resource
    private ZcCurtainMapper curtainMapper;

    @Override
    public Long createCurtain(ZcCurtainSaveReqVO createReqVO) {
        // 插入
        ZcCurtainDO curtain = BeanUtils.toBean(createReqVO, ZcCurtainDO.class);
        curtainMapper.insert(curtain);

        // 返回
        return curtain.getId();
    }

    @Override
    public void updateCurtain(ZcCurtainSaveReqVO updateReqVO) {
        // 校验存在
        validateCurtainExists(updateReqVO.getId());
        // 更新
        ZcCurtainDO updateObj = BeanUtils.toBean(updateReqVO, ZcCurtainDO.class);
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
    public ZcCurtainDO getCurtain(Long id) {
        return curtainMapper.selectById(id);
    }

    @Override
    public PageResult<ZcCurtainDO> getCurtainPage(ZcCurtainPageReqVO pageReqVO) {
        return curtainMapper.selectPage(pageReqVO);
    }

}