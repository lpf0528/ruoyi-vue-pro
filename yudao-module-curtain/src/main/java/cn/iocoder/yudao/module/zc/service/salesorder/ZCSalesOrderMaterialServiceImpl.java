package cn.iocoder.yudao.module.zc.service.salesorder;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZCSalesOrderMaterialDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZCSalesOrderMaterialMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;

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
    public Long createZCSalesOrderMaterial(ZCSalesOrderMaterialSaveReqVO createReqVO) {
        // 插入
        ZCSalesOrderMaterialDO zCSalesOrderMaterial = BeanUtils.toBean(createReqVO, ZCSalesOrderMaterialDO.class);
        zCSalesOrderMaterialMapper.insert(zCSalesOrderMaterial);

        // 返回
        return zCSalesOrderMaterial.getId();
    }

    @Override
    public void updateZCSalesOrderMaterial(ZCSalesOrderMaterialSaveReqVO updateReqVO) {
        // 校验存在
        validateZCSalesOrderMaterialExists(updateReqVO.getId());
        // 更新
        ZCSalesOrderMaterialDO updateObj = BeanUtils.toBean(updateReqVO, ZCSalesOrderMaterialDO.class);
        zCSalesOrderMaterialMapper.updateById(updateObj);
    }

    @Override
    public void deleteZCSalesOrderMaterial(Long id) {
        // 校验存在
        validateZCSalesOrderMaterialExists(id);
        // 删除
        zCSalesOrderMaterialMapper.deleteById(id);
    }

    @Override
        public void deleteZCSalesOrderMaterialListByIds(List<Long> ids) {
        // 删除
        zCSalesOrderMaterialMapper.deleteByIds(ids);
        }


    private void validateZCSalesOrderMaterialExists(Long id) {
        if (zCSalesOrderMaterialMapper.selectById(id) == null) {
            throw exception(ZC_SALES_ORDER_MATERIAL_NOT_EXISTS);
        }
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