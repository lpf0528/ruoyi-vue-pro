package cn.iocoder.yudao.module.zc.controller.admin.customer.vo;

import cn.iocoder.yudao.module.zc.framework.operatelog.core.ZcBrandParseFunction;
import cn.iocoder.yudao.module.zc.framework.operatelog.core.ZcLogisticsParseFunction;
import com.mzt.logapi.starter.annotation.DiffLogField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 客户资料新增/修改 Request VO")
@Data
public class ZcCustomerSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "8647")
    private Long id;

    @Schema(description = "简称", example = "张三")
    @DiffLogField(name = "简称")
    private String shortName;

    @Schema(description = "全称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @DiffLogField(name = "全称")
    @NotEmpty(message = "全称不能为空")
    private String name;

    @Schema(description = "联系人", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @DiffLogField(name = "联系人")
    @NotEmpty(message = "联系人不能为空")
    private String contactName;

    @Schema(description = "固定地址")
    @DiffLogField(name = "固定地址")
    private String address;

    @Schema(description = "送货地址")
    @DiffLogField(name = "送货地址")
    private String deliveryAddress;

    @Schema(description = "手机")
    @DiffLogField(name = "手机")
    private String mobile;

    @Schema(description = "联系电话")
    @DiffLogField(name = "联系电话")
    private String mobile2;

    @Schema(description = "物流 ID，可为空；与 logisticName 二选一或同时传（优先 ID）", example = "429")
    @DiffLogField(name = "物流", function = ZcLogisticsParseFunction.NAME)
    private Long logisticId;

    /** 物流名称；logisticId 为空时按名称查找，不存在则自动创建 */
    @Schema(description = "物流名称；logisticId 为空时按名称查找，不存在则自动创建", example = "顺丰速运")
    private String logisticName;

    @Schema(description = "关联品牌", example = "22168")
    @DiffLogField(name = "关联品牌", function = ZcBrandParseFunction.NAME)
    private Long brandId;

    @Schema(description = "备注")
    @DiffLogField(name = "备注")
    private String note;

}
