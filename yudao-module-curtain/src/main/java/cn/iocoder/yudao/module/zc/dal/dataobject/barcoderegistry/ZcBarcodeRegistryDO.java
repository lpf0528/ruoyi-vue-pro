package cn.iocoder.yudao.module.zc.dal.dataobject.barcoderegistry;

import lombok.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 码注册表 DO
 *
 * <p>统一管理系统中所有二维码的元数据，包括入库码、工序码、位置码等，
 * 扫码后根据 {@code targetRoute} 路由到对应的 App 功能页面</p>
 *
 * @author 智仓
 */
@TableName("zc_barcode_registry")
@KeySequence("zc_barcode_registry_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcBarcodeRegistryDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 码唯一ID（UUID），用于生成二维码内容及扫码后的查询参数
     */
    private String codeId;

    /**
     * 码类型，如 INBOUND_QR（入库码）、PROCESS_QR（工序码）、LOCATION_QR（位置码）等
     */
    private String codeType;

    /**
     * 扫码后跳转的路由路径，App 端根据此字段决定跳转页面
     */
    private String targetRoute;

    /**
     * 二维码原始内容（JSON 格式），包含该码对应的业务数据快照
     */
    private String codeContent;

}
