package cn.iocoder.yudao.module.zc.service.productbatch;

import java.util.*;
import javax.validation.*;
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
     * @return 编号列表
     */
    List<Long> createProductBatchList(List<ZcProductBatchSaveReqVO> createReqVOs);

    /**
     * 更新产品批次
     *
     * @param updateReqVO 更新信息
     */
    void updateProductBatch(@Valid ZcProductBatchSaveReqVO updateReqVO);

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

}