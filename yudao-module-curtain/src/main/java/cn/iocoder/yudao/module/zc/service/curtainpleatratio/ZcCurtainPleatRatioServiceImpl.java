package cn.iocoder.yudao.module.zc.service.curtainpleatratio;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.curtainpleatratio.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtainpleatratio.ZcCurtainPleatRatioDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.curtainpleatratio.ZcCurtainPleatRatioMapper;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.service.impl.DiffParseFunction;
import com.mzt.logapi.starter.annotation.LogRecord;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.zc.enums.LogRecordConstants.*;

/**
 * 褶倍 Service 实现类
 *
 * @author 01Coder
 */
@Service
@Validated
public class ZcCurtainPleatRatioServiceImpl implements ZcCurtainPleatRatioService {

    @Resource
    private ZcCurtainPleatRatioMapper curtainPleatRatioMapper;

    @Override
    @LogRecord(type = ZC_CURTAIN_PLEAT_RATIO_TYPE, subType = ZC_CURTAIN_PLEAT_RATIO_CREATE_SUB_TYPE, bizNo = "{{#pleatRatio.id}}",
            success = ZC_CURTAIN_PLEAT_RATIO_CREATE_SUCCESS)
    public Long createCurtainPleatRatio(ZcCurtainPleatRatioSaveReqVO createReqVO) {
        validateCurtainPleatRatioValueUnique(null, createReqVO.getValue());
        // 插入
        ZcCurtainPleatRatioDO pleatRatio = BeanUtils.toBean(createReqVO, ZcCurtainPleatRatioDO.class);
        curtainPleatRatioMapper.insert(pleatRatio);
        // 记录操作日志上下文
        LogRecordContext.putVariable("pleatRatio", pleatRatio);
        return pleatRatio.getId();
    }

    @Override
    @LogRecord(type = ZC_CURTAIN_PLEAT_RATIO_TYPE, subType = ZC_CURTAIN_PLEAT_RATIO_UPDATE_SUB_TYPE, bizNo = "{{#updateReqVO.id}}",
            success = ZC_CURTAIN_PLEAT_RATIO_UPDATE_SUCCESS)
    public void updateCurtainPleatRatio(ZcCurtainPleatRatioSaveReqVO updateReqVO) {
        // 校验存在
        ZcCurtainPleatRatioDO oldPleatRatio = validateCurtainPleatRatioExists(updateReqVO.getId());
        validateCurtainPleatRatioValueUnique(updateReqVO.getId(), updateReqVO.getValue());
        // 更新
        ZcCurtainPleatRatioDO updateObj = BeanUtils.toBean(updateReqVO, ZcCurtainPleatRatioDO.class);
        curtainPleatRatioMapper.updateById(updateObj);
        // 记录操作日志上下文
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, BeanUtils.toBean(oldPleatRatio, ZcCurtainPleatRatioSaveReqVO.class));
        LogRecordContext.putVariable("pleatRatioName", String.valueOf(oldPleatRatio.getValue()));
    }

    @Override
    @LogRecord(type = ZC_CURTAIN_PLEAT_RATIO_TYPE, subType = ZC_CURTAIN_PLEAT_RATIO_DELETE_SUB_TYPE, bizNo = "{{#id}}",
            success = ZC_CURTAIN_PLEAT_RATIO_DELETE_SUCCESS)
    public void deleteCurtainPleatRatio(Long id) {
        // 校验存在
        ZcCurtainPleatRatioDO pleatRatio = validateCurtainPleatRatioExists(id);
        // 记录操作日志上下文
        LogRecordContext.putVariable("pleatRatioName", String.valueOf(pleatRatio.getValue()));
        // 删除
        curtainPleatRatioMapper.deleteById(id);
    }

    @Override
        public void deleteCurtainPleatRatioListByIds(List<Long> ids) {
        // 删除
        curtainPleatRatioMapper.deleteByIds(ids);
        }


    private ZcCurtainPleatRatioDO validateCurtainPleatRatioExists(Long id) {
        ZcCurtainPleatRatioDO pleatRatio = curtainPleatRatioMapper.selectById(id);
        if (pleatRatio == null) {
            throw exception(CURTAIN_PLEAT_RATIO_NOT_EXISTS);
        }
        return pleatRatio;
    }

    private void validateCurtainPleatRatioValueUnique(Long id, java.math.BigDecimal value) {
        ZcCurtainPleatRatioDO existing = curtainPleatRatioMapper.selectByValue(value);
        if (existing == null || existing.getId().equals(id)) {
            return;
        }
        throw exception(CURTAIN_PLEAT_RATIO_VALUE_EXISTS);
    }

    @Override
    public ZcCurtainPleatRatioDO getCurtainPleatRatio(Long id) {
        return curtainPleatRatioMapper.selectById(id);
    }

    @Override
    public PageResult<ZcCurtainPleatRatioDO> getCurtainPleatRatioPage(ZcCurtainPleatRatioPageReqVO pageReqVO) {
        return curtainPleatRatioMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ZcCurtainPleatRatioDO> getCurtainPleatRatioList(ZcCurtainPleatRatioListReqVO listReqVO) {
        return curtainPleatRatioMapper.selectList(listReqVO);
    }

}
