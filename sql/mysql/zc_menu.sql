/*
  智仓（ZC）菜单与按钮权限初始化脚本
  - 执行前请确认 system_menu.id 不与现有数据冲突；可按需调整主键区间。
  - 组件路径对应前端文件：yudao-ui-admin-vue3/src/views/zc/**/index.vue
  - 分配权限：在「角色管理」中为角色勾选「智仓管理」及相关菜单，或使用 system_role_menu 绑定。
  - 字典建议（后台「字典管理」按需新建 dict_type / dict_data）：
      zc_order_types：成品帘 curtain，面料单 fabric
      zc_pay_status：未收款 unpaid，部分收款 partial，已结清 paid
      zc_confirm_status：未确认 unconfirmed，已确认 confirmed
      zc_order_category：与 zc_sales_order.category 业务取值一致
*/

SET NAMES utf8mb4;

-- 一级：智仓管理（目录）
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (10000, '智仓管理', '', 1, 55, 0, '/zc', 'ep:box', NULL, NULL, 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

-- 二级菜单（示例核心页面，其余主数据可在后台「菜单管理」复制模板增补）
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
(10001, '仓库', 'zc:warehouse:query', 2, 10, 10000, 'warehouse', 'ep:home-filled', 'zc/base/warehouse/index', 'ZcWarehouse', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
(10002, '客户', 'zc:customer:query', 2, 20, 10000, 'customer', 'ep:user', 'zc/base/customer/index', 'ZcCustomer', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
(10003, '销售订单', 'zc:sales-order:query', 2, 30, 10000, 'sale/order', 'ep:document', 'zc/sale/order/index', 'ZcSalesOrder', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
(10004, '收款', 'zc:collection:query', 2, 40, 10000, 'collection', 'ep:money', 'zc/finance/collection/index', 'ZcCollection', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
(10005, '采购单', 'zc:purchase-order:query', 2, 50, 10000, 'purchase-order', 'ep:shopping-cart', 'zc/stock/purchase/index', 'ZcPurchaseOrder', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
(10006, '产品批次', 'zc:product-batch:query', 2, 60, 10000, 'product-batch', 'ep:files', 'zc/stock/batch/index', 'ZcProductBatch', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
(10007, '进度定义', 'zc:progress-definition:query', 2, 70, 10000, 'progress-definition', 'ep:flag', 'zc/progress/definition/index', 'ZcProgressDefinition', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

-- 销售订单按钮
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
(10020, '销售查询', 'zc:sales-order:query', 3, 1, 10003, '', '', NULL, NULL, 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
(10021, '销售创建', 'zc:sales-order:create', 3, 2, 10003, '', '', NULL, NULL, 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
(10022, '销售更新', 'zc:sales-order:update', 3, 3, 10003, '', '', NULL, NULL, 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
(10023, '销售删除', 'zc:sales-order:delete', 3, 4, 10003, '', '', NULL, NULL, 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
(10024, '确认订单', 'zc:sales-order:confirm', 3, 5, 10003, '', '', NULL, NULL, 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');
