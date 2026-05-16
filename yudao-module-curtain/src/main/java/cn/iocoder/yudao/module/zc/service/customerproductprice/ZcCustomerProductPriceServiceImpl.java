package cn.iocoder.yudao.module.zc.service.customerproductprice;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import cn.iocoder.yudao.module.zc.controller.admin.customerproductprice.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.customerproductprice.ZcCustomerProductPriceDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.customerproductprice.ZcCustomerProductPriceMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;

/**
 * 客户产品销售授权价 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class ZcCustomerProductPriceServiceImpl implements ZcCustomerProductPriceService {

    @Resource
    private ZcCustomerProductPriceMapper customerProductPriceMapper;

    @Override
    public Long createCustomerProductPrice(ZcCustomerProductPriceSaveReqVO createReqVO) {
        // 插入
        ZcCustomerProductPriceDO customerProductPrice = BeanUtils.toBean(createReqVO, ZcCustomerProductPriceDO.class);
        customerProductPriceMapper.insert(customerProductPrice);

        // 返回
        return customerProductPrice.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createCustomerProductPriceList(List<ZcCustomerProductPriceSaveReqVO> reqVOs) {
        List<ZcCustomerProductPriceSaveReqVO> createList = reqVOs.stream()
                .filter(vo -> vo.getId() == null).collect(Collectors.toList());
        List<ZcCustomerProductPriceSaveReqVO> updateList = reqVOs.stream()
                .filter(vo -> vo.getId() != null).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(createList)) {
            customerProductPriceMapper.insertBatch(BeanUtils.toBean(createList, ZcCustomerProductPriceDO.class));
        }
        if (CollUtil.isNotEmpty(updateList)) {
            customerProductPriceMapper.updateBatch(BeanUtils.toBean(updateList, ZcCustomerProductPriceDO.class));
        }
    }

    @Override
    public void updateCustomerProductPrice(ZcCustomerProductPriceSaveReqVO updateReqVO) {
        // 校验存在
        validateCustomerProductPriceExists(updateReqVO.getId());
        // 更新
        ZcCustomerProductPriceDO updateObj = BeanUtils.toBean(updateReqVO, ZcCustomerProductPriceDO.class);
        customerProductPriceMapper.updateById(updateObj);
    }

    @Override
    public void deleteCustomerProductPrice(Long id) {
        // 校验存在
        validateCustomerProductPriceExists(id);
        // 删除
        customerProductPriceMapper.deleteById(id);
    }

    @Override
        public void deleteCustomerProductPriceListByIds(List<Long> ids) {
        // 删除
        customerProductPriceMapper.deleteByIds(ids);
        }


    private void validateCustomerProductPriceExists(Long id) {
        if (customerProductPriceMapper.selectById(id) == null) {
            throw exception(CUSTOMER_PRODUCT_PRICE_NOT_EXISTS);
        }
    }

    @Override
    public ZcCustomerProductPriceDO getCustomerProductPrice(Long id) {
        return customerProductPriceMapper.selectById(id);
    }

    @Override
    public PageResult<ZcCustomerProductPriceRespVO> getCustomerProductPricePage(ZcCustomerProductPricePageReqVO pageReqVO) {
        return customerProductPriceMapper.selectPage(pageReqVO);
    }

}