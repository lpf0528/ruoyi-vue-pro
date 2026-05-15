package cn.iocoder.yudao.module.zc.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

public interface ErrorCodeConstants {
    ErrorCode CUSTOMER_NOT_EXISTS = new ErrorCode(100001, "参数配置不存在");
    ErrorCode BRAND_NOT_EXISTS = new ErrorCode(100002, "品牌不存在");
    ErrorCode CURTAIN_NOT_EXISTS = new ErrorCode(100003, "窗帘不存在");
    ErrorCode CURTAIN_SERIES_NOT_EXISTS = new ErrorCode(100004, "窗帘系列不存在");
    ErrorCode CURTAIN_STRUCTURE_NOT_EXISTS = new ErrorCode(100005, "窗帘结构不存在");
    ErrorCode CURTAIN_STRUCTURE_ELEMENT_NOT_EXISTS = new ErrorCode(100006, "窗帘结构元素不存在");
    ErrorCode CURTAIN_TEMPLATE_NOT_EXISTS = new ErrorCode(100007, "窗帘模板不存在");
    ErrorCode LOGISTICS_NOT_EXISTS = new ErrorCode(100008, "物流不存在");
    ErrorCode SUPPLIER_NOT_EXISTS = new ErrorCode(100009, "供应商不存在");
    ErrorCode WAREHOUSE_NOT_EXISTS = new ErrorCode(100010, "仓库不存在");
    ErrorCode CURTAIN_PLEAT_RATIO_NOT_EXISTS = new ErrorCode(100011, "窗帘 pleat ratio 不存在");
    ErrorCode PRODUCT_CATEGORY_NOT_EXISTS = new ErrorCode(100012, "产品类别不存在");
    ErrorCode PRODUCT_VERSION_NOT_EXISTS = new ErrorCode(100013, "产品版本不存在");
    ErrorCode PRODUCT_SPEC_NOT_EXISTS = new ErrorCode(100014, "产品规格不存在");
    ErrorCode INVENTORY_RECORD_NOT_EXISTS = new ErrorCode(100015, "盘点记录不存在");
    ErrorCode CUSTOMER_PRODUCT_PRICE_NOT_EXISTS = new ErrorCode(100016, "客户产品销售授权价不存在");
    ErrorCode PRODUCT_BATCH_NOT_EXISTS = new ErrorCode(100017, "产品批次不存在");
    ErrorCode PRODUCT_NOT_EXISTS = new ErrorCode(100018, "产品不存在");
}

