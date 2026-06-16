package cn.iocoder.yudao.module.zc.service.productversion;

import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.zc.controller.admin.productversion.vo.ZcCustomerVersionSpecPriceRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.productversion.vo.ZcCustomerVersionSpecPriceSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.productversion.ZcCustomerVersionSpecPriceDO;
import cn.iocoder.yudao.module.zc.dal.mysql.productversion.ZcCustomerVersionSpecPriceMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
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
    public void batchSaveCustomerVersionSpecPrice(List<ZcCustomerVersionSpecPriceSaveReqVO> saveReqVOList) {
        if (saveReqVOList == null || saveReqVOList.isEmpty()) {
            return;
        }

        // 按 customerId + versionId 分组，每组独立覆盖写
        Map<String, List<ZcCustomerVersionSpecPriceSaveReqVO>> grouped = saveReqVOList.stream()
                .collect(Collectors.groupingBy(vo -> vo.getCustomerId() + "_" + vo.getVersionId()));

        grouped.forEach((key, items) -> {
            Long customerId = items.get(0).getCustomerId();
            Long versionId = items.get(0).getVersionId();

            // 物理删除旧记录，避免唯一索引 (customer_id, version_id, spec) 冲突
            customerVersionSpecPriceMapper.deleteByCustomerIdAndVersionIdPhysically(customerId, versionId);

            // 批量插入新记录
            List<ZcCustomerVersionSpecPriceDO> doList = BeanUtils.toBean(items, ZcCustomerVersionSpecPriceDO.class);
            customerVersionSpecPriceMapper.insertBatch(doList);
        });
    }

    @Override
    public List<ZcCustomerVersionSpecPriceRespVO> getCustomerVersionSpecPriceList(Long customerId, Long versionId) {
        return customerVersionSpecPriceMapper.selectListWithVersionName(customerId, versionId);
    }

}
