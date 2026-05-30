package cn.iocoder.yudao.module.zc.service.customerproductprice;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.customerproductprice.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.customerproductprice.ZcCustomerProductPriceDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.customerproductprice.ZcCustomerProductPriceMapper;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.service.impl.DiffParseFunction;
import com.mzt.logapi.starter.annotation.LogRecord;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.zc.enums.LogRecordConstants.*;

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
    @LogRecord(type = ZC_CUSTOMER_PRODUCT_PRICE_TYPE, subType = ZC_CUSTOMER_PRODUCT_PRICE_CREATE_SUB_TYPE,
            bizNo = "{{#customerProductPrice.id}}", success = ZC_CUSTOMER_PRODUCT_PRICE_CREATE_SUCCESS)
    public Long createCustomerProductPrice(ZcCustomerProductPriceSaveReqVO createReqVO) {
        // 插入
        ZcCustomerProductPriceDO customerProductPrice = BeanUtils.toBean(createReqVO, ZcCustomerProductPriceDO.class);
        customerProductPriceMapper.insert(customerProductPrice);
        // 记录操作日志上下文
        LogRecordContext.putVariable("customerProductPrice", customerProductPrice);
        return customerProductPrice.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = ZC_CUSTOMER_PRODUCT_PRICE_TYPE, subType = ZC_CUSTOMER_PRODUCT_PRICE_BATCH_CREATE_SUB_TYPE,
            bizNo = "{{#customerId}}", success = ZC_CUSTOMER_PRODUCT_PRICE_BATCH_CREATE_SUCCESS)
    public void createCustomerProductPriceList(List<ZcCustomerProductPriceSaveReqVO> reqVOs) {
        if (CollUtil.isEmpty(reqVOs)) {
            return;
        }
        // 自定义 XML INSERT 不经过 MetaObjectHandler，需手动填充审计字段
        String loginUserId = String.valueOf(SecurityFrameworkUtils.getLoginUserId());
        List<ZcCustomerProductPriceDO> list = BeanUtils.toBean(reqVOs, ZcCustomerProductPriceDO.class);
        list.forEach(item -> {
            item.setCreator(loginUserId);
            item.setUpdater(loginUserId);
        });
        customerProductPriceMapper.insertOrUpdateBatch(list);
        // 记录操作日志上下文
        LogRecordContext.putVariable("customerId", reqVOs.get(0).getCustomerId());
        LogRecordContext.putVariable("count", reqVOs.size());
    }

    @Override
    @LogRecord(type = ZC_CUSTOMER_PRODUCT_PRICE_TYPE, subType = ZC_CUSTOMER_PRODUCT_PRICE_UPDATE_SUB_TYPE,
            bizNo = "{{#updateReqVO.id}}", success = ZC_CUSTOMER_PRODUCT_PRICE_UPDATE_SUCCESS)
    public void updateCustomerProductPrice(ZcCustomerProductPriceSaveReqVO updateReqVO) {
        // 校验存在
        ZcCustomerProductPriceDO oldCustomerProductPrice = validateCustomerProductPriceExists(updateReqVO.getId());
        // 更新
        ZcCustomerProductPriceDO updateObj = BeanUtils.toBean(updateReqVO, ZcCustomerProductPriceDO.class);
        customerProductPriceMapper.updateById(updateObj);
        // 记录操作日志上下文
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, BeanUtils.toBean(oldCustomerProductPrice, ZcCustomerProductPriceSaveReqVO.class));
    }

    @Override
    @LogRecord(type = ZC_CUSTOMER_PRODUCT_PRICE_TYPE, subType = ZC_CUSTOMER_PRODUCT_PRICE_DELETE_SUB_TYPE,
            bizNo = "{{#id}}", success = ZC_CUSTOMER_PRODUCT_PRICE_DELETE_SUCCESS)
    public void deleteCustomerProductPrice(Long id) {
        // 校验存在
        validateCustomerProductPriceExists(id);
        // 记录操作日志上下文
        LogRecordContext.putVariable("priceId", id);
        // 删除
        customerProductPriceMapper.deleteById(id);
    }

    @Override
        public void deleteCustomerProductPriceListByIds(List<Long> ids) {
        // 删除
        customerProductPriceMapper.deleteByIds(ids);
        }


    private ZcCustomerProductPriceDO validateCustomerProductPriceExists(Long id) {
        ZcCustomerProductPriceDO customerProductPrice = customerProductPriceMapper.selectById(id);
        if (customerProductPrice == null) {
            throw exception(CUSTOMER_PRODUCT_PRICE_NOT_EXISTS);
        }
        return customerProductPrice;
    }

    @Override
    public ZcCustomerProductPriceDO getCustomerProductPrice(Long customerId, Long productId) {
        return customerProductPriceMapper.selectByCustomerIdAndProductId(customerId, productId);
    }

    @Override
    public PageResult<ZcCustomerProductPriceRespVO> getCustomerProductPricePage(ZcCustomerProductPricePageReqVO pageReqVO) {
        return customerProductPriceMapper.selectPage(pageReqVO);
    }

}
