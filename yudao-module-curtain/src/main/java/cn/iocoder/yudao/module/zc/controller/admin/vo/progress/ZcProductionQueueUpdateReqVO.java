package cn.iocoder.yudao.module.zc.controller.admin.vo.progress;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ZcProductionQueueUpdateReqVO {

    @NotNull
    private Long id;
    @NotNull
    private Integer queueStatus;
    private String remark;

}
