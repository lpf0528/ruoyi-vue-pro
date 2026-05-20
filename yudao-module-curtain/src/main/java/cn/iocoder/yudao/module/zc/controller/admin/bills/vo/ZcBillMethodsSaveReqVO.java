package cn.iocoder.yudao.module.zc.controller.admin.bills.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 收款方式新增/修改 Request VO")
@Data
public class ZcBillMethodsSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "5831")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "：支付宝、微信、银行卡")
    @NotEmpty(message = "名称不能为空")
    private String name;

    @Schema(description = "卡号")
    private String cardNo;

    @Schema(description = "备注")
    private String note;

}