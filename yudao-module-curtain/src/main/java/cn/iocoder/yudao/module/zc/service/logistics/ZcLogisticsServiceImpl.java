package cn.iocoder.yudao.module.zc.service.logistics;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.logistics.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.logistics.ZcLogisticsDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.logistics.ZcLogisticsMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;

/**
 * 物流公司 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class ZcLogisticsServiceImpl implements ZcLogisticsService {

    @Resource
    private ZcLogisticsMapper logisticsMapper;

    @Override
    public Long createLogistics(ZcLogisticsSaveReqVO createReqVO) {
        validateLogisticsNameUnique(null, createReqVO.getName());
        // 插入
        ZcLogisticsDO logistics = BeanUtils.toBean(createReqVO, ZcLogisticsDO.class);
        logisticsMapper.insert(logistics);
        return logistics.getId();
    }

    @Override
    public void updateLogistics(ZcLogisticsSaveReqVO updateReqVO) {
        // 校验存在
        validateLogisticsExists(updateReqVO.getId());
        validateLogisticsNameUnique(updateReqVO.getId(), updateReqVO.getName());
        // 更新
        ZcLogisticsDO updateObj = BeanUtils.toBean(updateReqVO, ZcLogisticsDO.class);
        logisticsMapper.updateById(updateObj);
    }

    @Override
    public void deleteLogistics(Long id) {
        // 校验存在
        validateLogisticsExists(id);
        // 删除
        logisticsMapper.deleteById(id);
    }

    @Override
        public void deleteLogisticsListByIds(List<Long> ids) {
        // 删除
        logisticsMapper.deleteByIds(ids);
        }


    private void validateLogisticsExists(Long id) {
        if (logisticsMapper.selectById(id) == null) {
            throw exception(LOGISTICS_NOT_EXISTS);
        }
    }

    private void validateLogisticsNameUnique(Long id, String name) {
        ZcLogisticsDO existing = logisticsMapper.selectByName(name);
        if (existing == null || existing.getId().equals(id)) {
            return;
        }
        throw exception(LOGISTICS_NAME_EXISTS);
    }

    @Override
    public ZcLogisticsDO getLogistics(Long id) {
        return logisticsMapper.selectById(id);
    }

    @Override
    public List<ZcLogisticsDO> getLogisticsList(ZcLogisticsListReqVO listReqVO) {
        return logisticsMapper.selectList(listReqVO);
    }

    @Override
    public PageResult<ZcLogisticsDO> getLogisticsPage(ZcLogisticsPageReqVO pageReqVO) {
        return logisticsMapper.selectPage(pageReqVO);
    }

}