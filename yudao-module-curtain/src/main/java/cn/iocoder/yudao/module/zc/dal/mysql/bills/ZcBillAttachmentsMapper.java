package cn.iocoder.yudao.module.zc.dal.mysql.bills;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.bills.ZcBillAttachmentsDO;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 账单附件 Mapper
 *
 * @author 01Coder
 */
@Mapper
public interface ZcBillAttachmentsMapper extends BaseMapperX<ZcBillAttachmentsDO> {

    default List<ZcBillAttachmentsDO> selectByBillId(Long billId) {
        return selectList(Wrappers.<ZcBillAttachmentsDO>lambdaQuery()
                .eq(ZcBillAttachmentsDO::getBillId, billId));
    }

    /** 删除指定收款单下的所有附件（用于删除/更新收款单时级联清理） */
    default void deleteByBillId(Long billId) {
        delete(Wrappers.<ZcBillAttachmentsDO>lambdaQuery()
                .eq(ZcBillAttachmentsDO::getBillId, billId));
    }

}
