package cn.iocoder.yudao.module.zc.service.stock;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.zc.dal.dataobject.stock.ZcPurchaseOrderDO;
import cn.iocoder.yudao.module.zc.dal.mysql.stock.ZcPurchaseOrderMapper;
import cn.iocoder.yudao.module.zc.dal.redis.ZcNoRedisDAO;
import cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants;
import cn.iocoder.yudao.module.zc.controller.admin.vo.stock.ZcPurchaseOrderPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.stock.ZcPurchaseOrderSaveReqVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service
@Validated
public class ZcPurchaseOrderServiceImpl implements ZcPurchaseOrderService {

    @Resource
    private ZcPurchaseOrderMapper purchaseOrderMapper;
    @Resource
    private ZcNoRedisDAO noRedisDAO;

    @Override
    public Long create(ZcPurchaseOrderSaveReqVO reqVO) {
        ZcPurchaseOrderDO d = BeanUtils.toBean(reqVO, ZcPurchaseOrderDO.class);
        d.setPurchaseNo(noRedisDAO.generate(ZcNoRedisDAO.PURCHASE_ORDER_PREFIX));
        if (d.getInboundType() == null) {
            d.setInboundType("normal");
        }
        d.setAuditStatus(0);
        purchaseOrderMapper.insert(d);
        return d.getId();
    }

    @Override
    public void update(ZcPurchaseOrderSaveReqVO reqVO) {
        ZcPurchaseOrderDO old = validateExists(reqVO.getId());
        assertNotAudited(old);
        ZcPurchaseOrderDO d = BeanUtils.toBean(reqVO, ZcPurchaseOrderDO.class);
        d.setPurchaseNo(old.getPurchaseNo());
        d.setAuditStatus(old.getAuditStatus());
        purchaseOrderMapper.updateById(d);
    }

    @Override
    public void delete(Long id) {
        ZcPurchaseOrderDO old = validateExists(id);
        assertNotAudited(old);
        purchaseOrderMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void audit(Long id) {
        ZcPurchaseOrderDO old = validateExists(id);
        assertNotAudited(old);
        Long auditorId = SecurityFrameworkUtils.getLoginUserId();
        ZcPurchaseOrderDO u = new ZcPurchaseOrderDO();
        u.setId(id);
        u.setAuditStatus(1);
        u.setAuditTime(LocalDateTime.now());
        u.setAuditorId(auditorId);
        purchaseOrderMapper.updateById(u);
    }

    @Override
    public ZcPurchaseOrderDO get(Long id) {
        return purchaseOrderMapper.selectById(id);
    }

    @Override
    public PageResult<ZcPurchaseOrderDO> getPage(ZcPurchaseOrderPageReqVO pageReqVO) {
        return purchaseOrderMapper.selectPage(pageReqVO, new LambdaQueryWrapperX<ZcPurchaseOrderDO>()
                .likeIfPresent(ZcPurchaseOrderDO::getPurchaseNo, pageReqVO.getPurchaseNo())
                .eqIfPresent(ZcPurchaseOrderDO::getSupplierId, pageReqVO.getSupplierId())
                .eqIfPresent(ZcPurchaseOrderDO::getAuditStatus, pageReqVO.getAuditStatus())
                .orderByDesc(ZcPurchaseOrderDO::getId));
    }

    private ZcPurchaseOrderDO validateExists(Long id) {
        if (id == null) {
            throw exception(ErrorCodeConstants.PURCHASE_ORDER_NOT_EXISTS);
        }
        ZcPurchaseOrderDO d = purchaseOrderMapper.selectById(id);
        if (d == null) {
            throw exception(ErrorCodeConstants.PURCHASE_ORDER_NOT_EXISTS);
        }
        return d;
    }

    private static void assertNotAudited(ZcPurchaseOrderDO d) {
        if (d.getAuditStatus() != null && d.getAuditStatus() == 1) {
            throw exception(ErrorCodeConstants.PURCHASE_ORDER_ALREADY_AUDITED);
        }
    }

}
