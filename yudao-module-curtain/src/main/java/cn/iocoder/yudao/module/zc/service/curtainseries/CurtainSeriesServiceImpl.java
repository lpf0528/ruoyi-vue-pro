package cn.iocoder.yudao.module.zc.service.curtainseries;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.curtainseries.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtainseries.CurtainSeriesDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.curtainseries.CurtainSeriesMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;

/**
 * 窗帘系列 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class CurtainSeriesServiceImpl implements CurtainSeriesService {

    @Resource
    private CurtainSeriesMapper curtainSeriesMapper;

    @Override
    public Long createCurtainSeries(CurtainSeriesSaveReqVO createReqVO) {
        // 插入
        CurtainSeriesDO curtainSeries = BeanUtils.toBean(createReqVO, CurtainSeriesDO.class);
        curtainSeriesMapper.insert(curtainSeries);

        // 返回
        return curtainSeries.getId();
    }

    @Override
    public void updateCurtainSeries(CurtainSeriesSaveReqVO updateReqVO) {
        // 校验存在
        validateCurtainSeriesExists(updateReqVO.getId());
        // 更新
        CurtainSeriesDO updateObj = BeanUtils.toBean(updateReqVO, CurtainSeriesDO.class);
        curtainSeriesMapper.updateById(updateObj);
    }

    @Override
    public void deleteCurtainSeries(Long id) {
        // 校验存在
        validateCurtainSeriesExists(id);
        // 删除
        curtainSeriesMapper.deleteById(id);
    }

    @Override
        public void deleteCurtainSeriesListByIds(List<Long> ids) {
        // 删除
        curtainSeriesMapper.deleteByIds(ids);
        }


    private void validateCurtainSeriesExists(Long id) {
        if (curtainSeriesMapper.selectById(id) == null) {
            throw exception(CURTAIN_SERIES_NOT_EXISTS);
        }
    }

    @Override
    public CurtainSeriesDO getCurtainSeries(Long id) {
        return curtainSeriesMapper.selectById(id);
    }

    @Override
    public PageResult<CurtainSeriesDO> getCurtainSeriesPage(CurtainSeriesPageReqVO pageReqVO) {
        return curtainSeriesMapper.selectPage(pageReqVO);
    }

}