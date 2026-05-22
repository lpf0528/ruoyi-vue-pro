package cn.iocoder.yudao.module.zc.controller.admin.processnode.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 管理后台 - 工序节点配置列表 Request VO
 *
 * <p>用于前端下拉选项的精简列表查询，全量返回所有节点</p>
 */
@Schema(description = "管理后台 - 工序节点配置列表 Request VO")
@Data
@Accessors(chain = true)
public class ZcProcessNodeListReqVO {

}
