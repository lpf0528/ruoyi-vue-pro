package cn.iocoder.yudao.module.zc.controller.admin.vo.progress;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class ZcOrderProgressAppendReqVO {

    @NotNull
    private Long orderId;
    private Long definitionId;
    private String progressCode;
    private String progressName;
    private String actionType;
    private LocalDateTime bizTime;
    private String remark;

}
