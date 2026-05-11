package cn.iocoder.yudao.module.zc.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * 智仓 ZC 错误码，段：1-031-000-000
 */
public interface ErrorCodeConstants {

    ErrorCode LOGISTICS_NOT_EXISTS = new ErrorCode(1_031_000_000, "物流公司不存在");
    ErrorCode BRAND_NOT_EXISTS = new ErrorCode(1_031_000_001, "品牌不存在");
    ErrorCode CUSTOMER_NOT_EXISTS = new ErrorCode(1_031_000_002, "客户不存在");
    ErrorCode SUPPLIER_NOT_EXISTS = new ErrorCode(1_031_000_003, "供应商不存在");
    ErrorCode WAREHOUSE_NOT_EXISTS = new ErrorCode(1_031_000_004, "仓库不存在");
    ErrorCode PAYMENT_NOT_EXISTS = new ErrorCode(1_031_000_005, "收款方式不存在");
    ErrorCode PRODUCT_NOT_EXISTS = new ErrorCode(1_031_000_006, "货号不存在");
    ErrorCode PRODUCT_VERSION_NOT_EXISTS = new ErrorCode(1_031_000_007, "产品版本不存在");
    ErrorCode PURCHASE_ORDER_NOT_EXISTS = new ErrorCode(1_031_000_008, "采购单不存在");
    ErrorCode PRODUCT_BATCH_NOT_EXISTS = new ErrorCode(1_031_000_009, "产品批次不存在");
    ErrorCode SALES_ORDER_NOT_EXISTS = new ErrorCode(1_031_000_010, "销售订单不存在");
    ErrorCode COLLECTION_NOT_EXISTS = new ErrorCode(1_031_000_011, "收款单不存在");
    ErrorCode PROGRESS_DEFINITION_NOT_EXISTS = new ErrorCode(1_031_000_012, "进度定义不存在");
    ErrorCode PROGRESS_DEFINITION_CODE_DUPLICATE = new ErrorCode(1_031_000_013, "进度编码已存在");
    ErrorCode PRODUCTION_QUEUE_NOT_EXISTS = new ErrorCode(1_031_000_014, "生产队列记录不存在");
    ErrorCode PRODUCT_CATEGORY_NOT_EXISTS = new ErrorCode(1_031_000_015, "货号类别不存在");
    ErrorCode PRODUCT_SPEC_NOT_EXISTS = new ErrorCode(1_031_000_016, "规格不存在");
    ErrorCode PRODUCT_UNIT_NOT_EXISTS = new ErrorCode(1_031_000_017, "单位不存在");
    ErrorCode CUSTOMER_PRODUCT_NOT_EXISTS = new ErrorCode(1_031_000_018, "客户货号授权不存在");
    ErrorCode CUSTOMER_PRODUCT_VERSION_NOT_EXISTS = new ErrorCode(1_031_000_019, "客户版本授权不存在");
    ErrorCode CURTAIN_SERIES_NOT_EXISTS = new ErrorCode(1_031_000_020, "窗帘系列不存在");
    ErrorCode CURTAIN_STYLE_NOT_EXISTS = new ErrorCode(1_031_000_021, "窗帘款式不存在");
    ErrorCode CURTAIN_STRUCTURE_NOT_EXISTS = new ErrorCode(1_031_000_022, "窗帘结构不存在");
    ErrorCode CURTAIN_STRUCTURE_ELEMENT_NOT_EXISTS = new ErrorCode(1_031_000_023, "窗帘结构部件不存在");
    ErrorCode CURTAIN_PLEAT_RATIO_NOT_EXISTS = new ErrorCode(1_031_000_024, "褶皱倍数不存在");
    ErrorCode CURTAIN_INSTALL_PROCESS_NOT_EXISTS = new ErrorCode(1_031_000_025, "安装工艺不存在");
    ErrorCode CURTAIN_TEMPLATE_NOT_EXISTS = new ErrorCode(1_031_000_026, "窗帘模板不存在");

    ErrorCode SALES_ORDER_CONFIRM_FAIL = new ErrorCode(1_031_001_000, "订单确认失败：状态不允许");
    ErrorCode SALES_ORDER_CANCEL_CONFIRM_FAIL = new ErrorCode(1_031_001_001, "取消确认失败：状态不允许");
    ErrorCode COLLECTION_ALLOC_AMOUNT_MISMATCH = new ErrorCode(1_031_001_002, "收款分摊金额与收款总额不一致");
    ErrorCode COLLECTION_ORDER_NOT_FOUND = new ErrorCode(1_031_001_003, "分摊订单不存在或不属于该客户");
    ErrorCode BATCH_QUANTITY_NOT_ENOUGH = new ErrorCode(1_031_001_004, "批次剩余数量不足");
    ErrorCode PURCHASE_ORDER_ALREADY_AUDITED = new ErrorCode(1_031_001_005, "采购单已审核，禁止修改或删除");

}
