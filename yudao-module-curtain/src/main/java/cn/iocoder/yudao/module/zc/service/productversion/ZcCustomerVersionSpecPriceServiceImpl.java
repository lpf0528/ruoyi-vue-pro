package cn.iocoder.yudao.module.zc.service.productversion;

import cn.iocoder.yudao.module.zc.controller.admin.productversion.vo.ZcCustomerVersionSpecPriceGetRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.productversion.vo.ZcCustomerVersionSpecPriceRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.productversion.vo.ZcCustomerVersionSpecPriceSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.productversion.ZcCustomerVersionSpecPriceDO;
import cn.iocoder.yudao.module.zc.dal.mysql.productversion.ZcCustomerVersionSpecPriceMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 客户版本销售授权价 Service 实现类
 *
 * @author 01Coder
 */
@Service
@Validated
public class ZcCustomerVersionSpecPriceServiceImpl implements ZcCustomerVersionSpecPriceService {

    @Resource
    private ZcCustomerVersionSpecPriceMapper customerVersionSpecPriceMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchSaveCustomerVersionSpecPrice(ZcCustomerVersionSpecPriceSaveReqVO saveReqVO) {
        // 物理删除该客户所有旧的版本规格授权价
        customerVersionSpecPriceMapper.deleteByCustomerIdPhysically(saveReqVO.getCustomerId());

        if (saveReqVO.getSpecPrices() == null || saveReqVO.getSpecPrices().isEmpty()) {
            return;
        }

        // 忽略 id，全量插入新记录
        List<ZcCustomerVersionSpecPriceDO> doList = saveReqVO.getSpecPrices().stream()
                .map(item -> {
                    ZcCustomerVersionSpecPriceDO d = new ZcCustomerVersionSpecPriceDO();
                    d.setCustomerId(saveReqVO.getCustomerId());
                    d.setVersionId(item.getVersionId());
                    d.setSpec(item.getSpec());
                    d.setAuthorizedPrice(item.getAuthorizedPrice());
                    return d;
                })
                .collect(Collectors.toList());
        customerVersionSpecPriceMapper.insertBatch(doList);
    }

    @Override
    public List<ZcCustomerVersionSpecPriceRespVO> getCustomerVersionSpecPriceList(Long customerId, Long versionId) {
        return customerVersionSpecPriceMapper.selectListWithVersionName(customerId, versionId);
    }

    @Override
    public ZcCustomerVersionSpecPriceGetRespVO getByProductIdAndCustomerIdAndSpec(Long productId, Long customerId, String spec) {
        return customerVersionSpecPriceMapper.selectByProductIdAndCustomerIdAndSpec(productId, customerId, spec);
    }

}
