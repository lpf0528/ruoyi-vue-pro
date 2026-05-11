package cn.iocoder.yudao.module.zc.service.stock;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.stock.ZcProductBatchDO;
import cn.iocoder.yudao.module.zc.dal.mysql.stock.ZcProductBatchMapper;
import cn.iocoder.yudao.module.zc.dal.redis.ZcNoRedisDAO;
import cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants;
import cn.iocoder.yudao.module.zc.controller.admin.vo.stock.ZcProductBatchPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.stock.ZcProductBatchSaveReqVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service
@Validated
public class ZcProductBatchServiceImpl implements ZcProductBatchService {

    @Resource
    private ZcProductBatchMapper productBatchMapper;
    @Resource
    private ZcNoRedisDAO noRedisDAO;

    @Override
    public Long createInbound(ZcProductBatchSaveReqVO reqVO) {
        ZcProductBatchDO d = BeanUtils.toBean(reqVO, ZcProductBatchDO.class);
        d.setBatchNo(noRedisDAO.generate(ZcNoRedisDAO.BATCH_PREFIX));
        d.setQuantity(reqVO.getInboundQuantity());
        productBatchMapper.insert(d);
        return d.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deductQuantity(Long batchId, BigDecimal quantity) {
        if (quantity == null || quantity.signum() <= 0) {
            return;
        }
        ZcProductBatchDO batch = productBatchMapper.selectById(batchId);
        if (batch == null) {
            throw exception(ErrorCodeConstants.PRODUCT_BATCH_NOT_EXISTS);
        }
        BigDecimal rest = batch.getQuantity() != null ? batch.getQuantity() : BigDecimal.ZERO;
        if (rest.compareTo(quantity) < 0) {
            throw exception(ErrorCodeConstants.BATCH_QUANTITY_NOT_ENOUGH);
        }
        ZcProductBatchDO u = new ZcProductBatchDO();
        u.setId(batchId);
        u.setQuantity(rest.subtract(quantity));
        productBatchMapper.updateById(u);
    }

    @Override
    public ZcProductBatchDO get(Long id) {
        return productBatchMapper.selectById(id);
    }

    @Override
    public PageResult<ZcProductBatchDO> getPage(ZcProductBatchPageReqVO pageReqVO) {
        return productBatchMapper.selectPage(pageReqVO, new LambdaQueryWrapperX<ZcProductBatchDO>()
                .likeIfPresent(ZcProductBatchDO::getBatchNo, pageReqVO.getBatchNo())
                .eqIfPresent(ZcProductBatchDO::getProductId, pageReqVO.getProductId())
                .eqIfPresent(ZcProductBatchDO::getWarehouseId, pageReqVO.getWarehouseId())
                .eqIfPresent(ZcProductBatchDO::getPurchaseOrderId, pageReqVO.getPurchaseOrderId())
                .orderByDesc(ZcProductBatchDO::getId));
    }

}
