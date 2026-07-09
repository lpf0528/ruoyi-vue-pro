package cn.iocoder.yudao.module.zc.dal.dataobject.bills;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 账单附件 DO
 *
 * <p>存储与收支账单关联的图片或文件附件。</p>
 * <p>该表无软删除列，不继承 BaseDO；tenant_id 由 MyBatis Plus 多租户拦截器自动注入。</p>
 *
 * @author 01Coder
 */
@TableName("zc_bill_attachments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcBillAttachmentsDO {

    /** 主键 */
    @TableId
    private Long id;

    /** 关联账单 ID */
    private Long billId;

    /** 附件 URL */
    private String url;

    /**
     * 附件类型：1=图片，2=文件
     */
    private Integer type;

}
