package cn.iocoder.yudao.module.zc.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

public interface ErrorCodeConstants {
    ErrorCode CUSTOMER_NOT_EXISTS = new ErrorCode(100001, "参数配置不存在");
    ErrorCode BRAND_NOT_EXISTS = new ErrorCode(100002, "品牌不存在");
    ErrorCode CURTAIN_NOT_EXISTS = new ErrorCode(100003, "窗帘不存在");
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
    ErrorCode PRODUCT_HAS_BATCH = new ErrorCode(100019, "产品存在批次记录，禁止删除");
    ErrorCode CURTAIN_INSTALL_PROCESS_NOT_EXISTS = new ErrorCode(100020, "安装工艺不存在");
    ErrorCode SALES_ORDER_NOT_EXISTS = new ErrorCode(100021, "销售订单不存在");
    ErrorCode SALES_ORDER_STRUCTURE_NOT_EXISTS = new ErrorCode(100022, "成品订单-结构不存在");
    ErrorCode ZC_SALES_ORDER_MATERIAL_NOT_EXISTS = new ErrorCode(100023, "成品订单-用料明细不存在");
    ErrorCode SALES_ORDER_CURTAIN_NOT_EXISTS = new ErrorCode(100024, "成品订单-窗帘行不存在");
    /**
     * 确认订单时，订单状态不是待确认
     */
    ErrorCode SALES_ORDER_STATUS_NOT_UNCONFIRMED = new ErrorCode(100025, "订单状态不是待确认，无法执行确认操作");
    /**
     * 取消确认时，订单状态不是已确认
     */
    ErrorCode SALES_ORDER_STATUS_NOT_CONFIRMED = new ErrorCode(100026, "订单状态不是已确认，无法取消确认");
    ErrorCode BILLS_NOT_EXISTS = new ErrorCode(100027, "收支账单不存在");
    ErrorCode BILL_METHODS_NOT_EXISTS = new ErrorCode(100028, "收款方式不存在");
    ErrorCode PROCESS_NODE_NOT_EXISTS = new ErrorCode(100029, "工序节点配置不存在");
    ErrorCode ORDER_PROCESS_RECORD_NOT_EXISTS = new ErrorCode(100030, "工序记录不存在");
    /** 员工未绑定该工序节点，无权操作 */
    ErrorCode USER_PROCESS_NODE_NOT_AUTHORIZED = new ErrorCode(100031, "您没有操作该工序节点的权限，请联系管理员分配");
    /** 订单不处于生产流程中（非 pending/processing），不能新增工序记录 */
    ErrorCode SALES_ORDER_STATUS_CANNOT_PROCESS = new ErrorCode(100032, "订单不在生产流程中，无法新增工序记录");
    /** 工序记录已完成，不允许删除，防止历史数据被篡改 */
    ErrorCode ORDER_PROCESS_RECORD_ALREADY_COMPLETED = new ErrorCode(100033, "工序记录已完成，不允许删除");
    /**
     * 取消确认时，订单已存在收款记录，不允许取消：否则客户余额会凭空增加
     */
    ErrorCode SALES_ORDER_HAS_RECEIVED_AMOUNT = new ErrorCode(100034, "订单已有收款记录，取消确认将导致账目异常，请先撤销相关收款单");
    /**
     * 创建/更新收款单时，分摊到各订单的金额合计与实收+优惠不一致
     */
    ErrorCode BILL_ALLOCATED_AMOUNT_NOT_MATCH = new ErrorCode(100035, "分摊到各订单的金额合计与实收金额+优惠金额不一致，请检查后重试");
    /**
     * 删除客户时，该客户下存在销售订单，禁止删除
     */
    ErrorCode CUSTOMER_HAS_ORDERS = new ErrorCode(100036, "该客户存在销售订单，禁止删除");
    /**
     * 删除客户时，该客户下存在收支账单，禁止删除
     */
    ErrorCode CUSTOMER_HAS_BILLS = new ErrorCode(100037, "该客户存在收支账单，禁止删除");
    /**
     * 删除产品版本时，该版本下存在绑定产品，禁止删除
     */
    ErrorCode PRODUCT_VERSION_HAS_PRODUCTS = new ErrorCode(100038, "该产品版本下存在绑定产品，禁止删除");
    /**
     * 删除产品批次时，该批次已被订单用料明细引用，禁止删除
     */
    ErrorCode PRODUCT_BATCH_HAS_ORDER_MATERIALS = new ErrorCode(100039, "该批次已被订单引用，禁止删除");
    /**
     * 产品类销售订单行不存在
     */
    ErrorCode SALES_ORDER_PRODUCT_NOT_EXISTS = new ErrorCode(100040, "销售订单产品行不存在");
    /**
     * 已确认的订单禁止删除（confirm_time 不为空）
     */
    ErrorCode SALES_ORDER_CONFIRMED_CANNOT_DELETE = new ErrorCode(100041, "已确认的订单禁止删除");
    /**
     * 产品版本名称已存在（创建/更新时唯一性校验）
     */
    ErrorCode PRODUCT_VERSION_NAME_EXISTS = new ErrorCode(100042, "产品版本名称已存在");
    /**
     * 产品名称已存在（创建/更新时唯一性校验）
     */
    ErrorCode PRODUCT_NAME_EXISTS = new ErrorCode(100043, "产品名称已存在");
    /**
     * 产品规格名称已存在（创建/更新时唯一性校验）
     */
    ErrorCode PRODUCT_SPEC_VALUE_EXISTS = new ErrorCode(100044, "产品规格名称已存在");
    /**
     * 产品类别名称已存在（创建/更新时唯一性校验）
     */
    ErrorCode PRODUCT_CATEGORY_VALUE_EXISTS = new ErrorCode(100045, "产品类别名称已存在");
}

