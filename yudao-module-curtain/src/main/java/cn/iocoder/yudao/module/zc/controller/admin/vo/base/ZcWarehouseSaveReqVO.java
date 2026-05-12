package cn.iocoder.yudao.module.zc.controller.admin.vo.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ZcWarehouseSaveReqVO {

    private Long id;
    private String name;
    private Long managerId;
    private String note;

}
