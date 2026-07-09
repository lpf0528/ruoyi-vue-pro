package cn.iocoder.yudao.module.zc.service.logistics;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderDO;
import cn.iocoder.yudao.module.zc.controller.admin.logistics.vo.ZcLogisticsListReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.logistics.vo.ZcLogisticsPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.logistics.vo.ZcLogisticsSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.logistics.ZcLogisticsDO;
import cn.iocoder.yudao.module.zc.dal.mysql.logistics.ZcLogisticsMapper;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.service.impl.DiffParseFunction;
import com.mzt.logapi.starter.annotation.LogRecord;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.LOGISTICS_NAME_EXISTS;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.LOGISTICS_NOT_EXISTS;
import static cn.iocoder.yudao.module.zc.enums.LogRecordConstants.*;

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
    @LogRecord(type = ZC_LOGISTICS_TYPE, subType = ZC_LOGISTICS_CREATE_SUB_TYPE, bizNo = "{{#logistics.id}}",
            success = ZC_LOGISTICS_CREATE_SUCCESS)
    public Long createLogistics(ZcLogisticsSaveReqVO createReqVO) {
        validateLogisticsNameUnique(null, createReqVO.getName());
        // 插入
        ZcLogisticsDO logistics = BeanUtils.toBean(createReqVO, ZcLogisticsDO.class);
        logisticsMapper.insert(logistics);
        // 记录操作日志上下文
        LogRecordContext.putVariable("logistics", logistics);
        return logistics.getId();
    }

    @Override
    @LogRecord(type = ZC_LOGISTICS_TYPE, subType = ZC_LOGISTICS_UPDATE_SUB_TYPE, bizNo = "{{#updateReqVO.id}}",
            success = ZC_LOGISTICS_UPDATE_SUCCESS)
    public void updateLogistics(ZcLogisticsSaveReqVO updateReqVO) {
        // 校验存在
        ZcLogisticsDO oldLogistics = validateLogisticsExists(updateReqVO.getId());
        validateLogisticsNameUnique(updateReqVO.getId(), updateReqVO.getName());
        // 更新
        ZcLogisticsDO updateObj = BeanUtils.toBean(updateReqVO, ZcLogisticsDO.class);
        logisticsMapper.updateById(updateObj);
        // 记录操作日志上下文
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, BeanUtils.toBean(oldLogistics, ZcLogisticsSaveReqVO.class));
        LogRecordContext.putVariable("logisticsName", oldLogistics.getName());
    }

    @Override
    @LogRecord(type = ZC_LOGISTICS_TYPE, subType = ZC_LOGISTICS_DELETE_SUB_TYPE, bizNo = "{{#id}}",
            success = ZC_LOGISTICS_DELETE_SUCCESS)
    public void deleteLogistics(Long id) {
        // 校验存在
        ZcLogisticsDO logistics = validateLogisticsExists(id);
        // 记录操作日志上下文
        LogRecordContext.putVariable("logisticsName", logistics.getName());
        // 删除
        logisticsMapper.deleteById(id);
    }

    @Override
        public void deleteLogisticsListByIds(List<Long> ids) {
        // 删除
        logisticsMapper.deleteByIds(ids);
        }


    private ZcLogisticsDO validateLogisticsExists(Long id) {
        ZcLogisticsDO logistics = logisticsMapper.selectById(id);
        if (logistics == null) {
            throw exception(LOGISTICS_NOT_EXISTS);
        }
        return logistics;
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

    @Override
    public Long resolveLogisticId(Long logisticId, String logisticName) {
        if (logisticId != null) {
            validateLogisticsExists(logisticId);
            return logisticId;
        }
        if (StrUtil.isBlank(logisticName)) {
            return null;
        }
        String name = logisticName.trim();
        ZcLogisticsDO existing = logisticsMapper.selectByName(name);
        if (existing != null) {
            return existing.getId();
        }
        ZcLogisticsDO newLogistic = ZcLogisticsDO.builder()
                .code(name)
                .name(name)
                .build();
        logisticsMapper.insert(newLogistic);
        return newLogistic.getId();
    }

    @Override
    public void resolveLogisticsForOrder(ZcSalesOrderDO salesOrder) {
        if (salesOrder.getLogisticId() != null) {
            ZcLogisticsDO logistics = validateLogisticsExists(salesOrder.getLogisticId());
            salesOrder.setLogisticName(logistics.getName());
            return;
        }
        if (StrUtil.isBlank(salesOrder.getLogisticName())) {
            salesOrder.setLogisticName(null);
            return;
        }
        String name = salesOrder.getLogisticName().trim();
        salesOrder.setLogisticName(name);
        salesOrder.setLogisticId(resolveLogisticId(null, name));
    }

}
