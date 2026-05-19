package cn.iocoder.yudao.module.zc.service.productbatch;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.productbatch.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.productbatch.ZcProductBatchDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.productbatch.ZcProductBatchMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;

/**
 * 产品批次 Service 实现类
 *
 * @author 01Coder
 */
@Service
@Validated
public class ZcProductBatchServiceImpl implements ZcProductBatchService {

    @Resource
    private ZcProductBatchMapper productBatchMapper;

    @Override
    public Long createProductBatch(ZcProductBatchSaveReqVO createReqVO) {
        ZcProductBatchDO productBatch = BeanUtils.toBean(createReqVO, ZcProductBatchDO.class);
        // 生成批号：YYYYMMDD-XX（当日该产品的第N条入库记录，从01开始）
        Integer seq = productBatchMapper.countTodayBatchSeqByProductId(createReqVO.getProductId());
        productBatch.setBatchNo(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-" + String.format("%02d", seq));
        productBatchMapper.insert(productBatch);
        return productBatch.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Long> createProductBatchList(List<ZcProductBatchSaveReqVO> createReqVOs) {
        List<Long> ids = new ArrayList<>();
        for (ZcProductBatchSaveReqVO createReqVO : createReqVOs) {
            ids.add(createProductBatch(createReqVO));
        }
        return ids;
    }

    @Override
    public void updateProductBatch(ZcProductBatchSaveReqVO updateReqVO) {
        // 校验存在
        validateProductBatchExists(updateReqVO.getId());
        // 更新
        ZcProductBatchDO updateObj = BeanUtils.toBean(updateReqVO, ZcProductBatchDO.class);
        productBatchMapper.updateById(updateObj);
    }

    @Override
    public void deleteProductBatch(Long id) {
        // 校验存在
        validateProductBatchExists(id);
        // 删除
        productBatchMapper.deleteById(id);
    }

    @Override
        public void deleteProductBatchListByIds(List<Long> ids) {
        // 删除
        productBatchMapper.deleteByIds(ids);
        }


    private void validateProductBatchExists(Long id) {
        if (productBatchMapper.selectById(id) == null) {
            throw exception(PRODUCT_BATCH_NOT_EXISTS);
        }
    }

    @Override
    public ZcProductBatchRespVO getProductBatch(Long id) {
        return productBatchMapper.selectBatchWithVOById(id);
    }

    @Override
    public PageResult<ZcProductBatchRespVO> getProductBatchPage(ZcProductBatchPageReqVO pageReqVO) {
        return productBatchMapper.selectPage(pageReqVO);
    }

}