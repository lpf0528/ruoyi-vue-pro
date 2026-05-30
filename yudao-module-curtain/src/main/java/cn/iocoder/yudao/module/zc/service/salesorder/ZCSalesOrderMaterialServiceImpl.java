package cn.iocoder.yudao.module.zc.service.salesorder;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZCSalesOrderMaterialDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZCSalesOrderMaterialMapper;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.service.impl.DiffParseFunction;
import com.mzt.logapi.starter.annotation.LogRecord;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.zc.enums.LogRecordConstants.*;

/**
 * 成品订单-用料明细 Service 实现类
 *
 * @author 01Coder
 */
@Service
@Validated
public class ZCSalesOrderMaterialServiceImpl implements ZCSalesOrderMaterialService {

    @Resource
    private ZCSalesOrderMaterialMapper zCSalesOrderMaterialMapper;

    @Override
    @LogRecord(type = ZC_SALES_ORDER_MATERIAL_TYPE, subType = ZC_SALES_ORDER_MATERIAL_CREATE_SUB_TYPE, bizNo = "{{#material.id}}",
            success = ZC_SALES_ORDER_MATERIAL_CREATE_SUCCESS)
    public Long createZCSalesOrderMaterial(ZCSalesOrderMaterialSaveReqVO createReqVO) {
        // 插入
        ZCSalesOrderMaterialDO material = BeanUtils.toBean(createReqVO, ZCSalesOrderMaterialDO.class);
        zCSalesOrderMaterialMapper.insert(material);
        // 记录操作日志上下文
        LogRecordContext.putVariable("material", material);
        return material.getId();
    }

    @Override
    @LogRecord(type = ZC_SALES_ORDER_MATERIAL_TYPE, subType = ZC_SALES_ORDER_MATERIAL_UPDATE_SUB_TYPE, bizNo = "{{#updateReqVO.id}}",
            success = ZC_SALES_ORDER_MATERIAL_UPDATE_SUCCESS)
    public void updateZCSalesOrderMaterial(ZCSalesOrderMaterialSaveReqVO updateReqVO) {
        // 校验存在
        ZCSalesOrderMaterialDO oldMaterial = validateZCSalesOrderMaterialExists(updateReqVO.getId());
        // 更新
        ZCSalesOrderMaterialDO updateObj = BeanUtils.toBean(updateReqVO, ZCSalesOrderMaterialDO.class);
        zCSalesOrderMaterialMapper.updateById(updateObj);
        // 记录操作日志上下文
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, BeanUtils.toBean(oldMaterial, ZCSalesOrderMaterialSaveReqVO.class));
        LogRecordContext.putVariable("materialId", oldMaterial.getId());
    }

    @Override
    @LogRecord(type = ZC_SALES_ORDER_MATERIAL_TYPE, subType = ZC_SALES_ORDER_MATERIAL_DELETE_SUB_TYPE, bizNo = "{{#id}}",
            success = ZC_SALES_ORDER_MATERIAL_DELETE_SUCCESS)
    public void deleteZCSalesOrderMaterial(Long id) {
        // 校验存在
        ZCSalesOrderMaterialDO material = validateZCSalesOrderMaterialExists(id);
        // 记录操作日志上下文
        LogRecordContext.putVariable("materialId", material.getId());
        // 删除
        zCSalesOrderMaterialMapper.deleteById(id);
    }

    @Override
    public void deleteZCSalesOrderMaterialListByIds(List<Long> ids) {
        // 删除
        zCSalesOrderMaterialMapper.deleteByIds(ids);
    }

    private ZCSalesOrderMaterialDO validateZCSalesOrderMaterialExists(Long id) {
        ZCSalesOrderMaterialDO material = zCSalesOrderMaterialMapper.selectById(id);
        if (material == null) {
            throw exception(ZC_SALES_ORDER_MATERIAL_NOT_EXISTS);
        }
        return material;
    }

    @Override
    public ZCSalesOrderMaterialDO getZCSalesOrderMaterial(Long id) {
        return zCSalesOrderMaterialMapper.selectById(id);
    }

    @Override
    public PageResult<ZCSalesOrderMaterialDO> getZCSalesOrderMaterialPage(ZCSalesOrderMaterialPageReqVO pageReqVO) {
        return zCSalesOrderMaterialMapper.selectPage(pageReqVO);
    }

}
