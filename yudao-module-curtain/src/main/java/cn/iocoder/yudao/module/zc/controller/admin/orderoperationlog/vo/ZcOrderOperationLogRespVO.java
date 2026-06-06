package cn.iocoder.yudao.module.zc.controller.admin.orderoperationlog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 订单操作记录 Response VO")
@Data
public class ZcOrderOperationLogRespVO {

    @Schema(description = "主键", example = "1")
    private Long id;

    @Schema(description = "销售订单 ID", example = "1024")
    private Long orderId;

    @Schema(description = "订单号", example = "ZC120260601000001")
    private String orderNo;

    @Schema(description = "操作类型枚举值", example = "PACK")
    private String operateType;

    @Schema(description = "操作类型中文名", example = "打包")
    private String operateTypeLabel;

    @Schema(description = "操作对象类型枚举值", example = "CURTAIN")
    private String targetType;

    @Schema(description = "操作对象类型中文名", example = "窗帘行")
    private String targetTypeLabel;

    @Schema(description = "操作对象 ID", example = "101")
    private Long targetId;

    @Schema(description = "操作前状态", example = "CONFIRMED")
    private String beforeStatus;

    @Schema(description = "操作后状态", example = "DABAO")
    private String afterStatus;

    @Schema(description = "订单联动更新后的状态", example = "BUFEN_DABAO")
    private String orderAfterStatus;

    @Schema(description = "扩展信息 JSON", example = "{\"batchNo\":\"B001\",\"quantity\":12.5}")
    private String extJson;

    @Schema(description = "备注")
    private String note;

    @Schema(description = "操作人", example = "admin")
    private String creator;

    @Schema(description = "操作时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
