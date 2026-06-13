package cn.iocoder.yudao.module.zc.controller.admin.customer.vo;

import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 客户资料 Excel 导入 VO
 *
 * <p>物流和品牌通过名称匹配 ID，名称不存在时该行导入失败并记录原因。</p>
 */
@Schema(description = "管理后台 - 客户资料 Excel 导入 VO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ZcCustomerImportExcelVO {

    /** 简称，作为唯一键判断新增还是更新，不能为空 */
    @ExcelProperty("简称")
    private String shortName;

    /** 全称 */
    @ExcelProperty("全称")
    private String name;

    /** 联系人 */
    @ExcelProperty("联系人")
    private String contactName;

    /** 送货地址 */
    @ExcelProperty("送货地址")
    private String deliveryAddress;

    /** 手机 */
    @ExcelProperty("手机")
    private String mobile;

    /** 手机2 */
    @ExcelProperty("手机2")
    private String mobile2;

    /** 物流公司名称，通过名称匹配物流 ID；为空则不关联物流 */
    @ExcelProperty("物流")
    private String logisticName;

    /** 品牌名称，通过名称匹配品牌 ID；为空则不关联品牌 */
    @ExcelProperty("关联品牌")
    private String brandName;

    /** 备注 */
    @ExcelProperty("备注")
    private String note;

}
