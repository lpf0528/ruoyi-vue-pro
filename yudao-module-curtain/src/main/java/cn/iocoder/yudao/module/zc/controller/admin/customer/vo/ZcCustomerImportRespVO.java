package cn.iocoder.yudao.module.zc.controller.admin.customer.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 管理后台 - 客户资料导入 Response VO
 */
@Schema(description = "管理后台 - 客户资料导入 Response VO")
@Data
@Builder
public class ZcCustomerImportRespVO {

    /** 创建成功的客户简称列表 */
    @Schema(description = "创建成功的客户简称列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> createShortNames;

    /** 更新成功的客户简称列表 */
    @Schema(description = "更新成功的客户简称列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> updateShortNames;

    /** 导入失败集合，key 为客户简称（或行标记），value 为失败原因 */
    @Schema(description = "导入失败集合，key 为客户简称，value 为失败原因", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String, String> failureShortNames;

}
