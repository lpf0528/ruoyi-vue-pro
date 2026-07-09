package cn.iocoder.yudao.module.zc.service.productbatch;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.zc.controller.admin.inventoryrecord.vo.ZcInventoryRecordSaveReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.productbatch.vo.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 产品批次 Service 接口
 *
 * @author 01Coder
 */
public interface ZcProductBatchService {

    /**
     * 创建产品批次
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProductBatch(@Valid ZcProductBatchSaveReqVO createReqVO);

    /**
     * 批量创建产品批次
     *
     * @param createReqVOs 创建信息列表
     * @return 创建后的产品批次列表（与分页列表结构一致）
     */
    List<ZcProductBatchRespVO> createProductBatchList(List<ZcProductBatchSaveReqVO> createReqVOs);

    /**
     * 更新产品批次
     *
     * @param updateReqVO 更新信息
     */
    void updateProductBatch(@Valid ZcProductBatchSaveReqVO updateReqVO);

    /**
     * 更新产品批次状态
     *
     * @param updateReqVO 更新信息
     */
    void updateProductBatchStatus(@Valid ZcProductBatchUpdateStatusReqVO updateReqVO);

    /**
     * 删除产品批次
     *
     * @param id 编号
     */
    void deleteProductBatch(Long id);

    /**
    * 批量删除产品批次
    *
    * @param ids 编号
    */
    void deleteProductBatchListByIds(List<Long> ids);

    /**
     * 获得产品批次
     *
     * @param id 编号
     * @return 产品批次
     */
    ZcProductBatchRespVO getProductBatch(Long id);

    /**
     * 获得产品批次分页
     *
     * @param pageReqVO 分页查询
     * @return 产品批次分页
     */
    PageResult<ZcProductBatchRespVO> getProductBatchPage(ZcProductBatchPageReqVO pageReqVO);

    /**
     * 盘点产品批次：写入盘点流水并更新批次剩余数量
     *
     * @param inventoryReqVO 盘点信息（产品、批次、盘点前后数量、规格、备注）
     * @return 盘点记录 ID
     */
    Long inventoryProductBatch(@Valid ZcInventoryRecordSaveReqVO inventoryReqVO);

}