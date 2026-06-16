package cn.iocoder.yudao.module.zc.service.productversion;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.zc.controller.admin.productversion.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.productversion.ZcProductVersionDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.productversion.ZcProductVersionSpcDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.module.zc.controller.admin.productversion.vo.ZcProductVersionSpcPageReqVO;

/**
 * 产品版本 Service 接口
 *
 * @author 01Coder
 */
public interface ZcProductVersionService {

    /**
     * 创建产品版本
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProductVersion(@Valid ZcProductVersionSaveReqVO createReqVO);

    /**
     * 更新产品版本
     *
     * @param updateReqVO 更新信息
     */
    void updateProductVersion(@Valid ZcProductVersionSaveReqVO updateReqVO);

    /**
     * 删除产品版本
     *
     * @param id 编号
     */
    void deleteProductVersion(Long id);

    /**
    * 批量删除产品版本
    *
    * @param ids 编号
    */
    void deleteProductVersionListByIds(List<Long> ids);

    /**
     * 获得产品版本
     *
     * @param id 编号
     * @return 产品版本
     */
    ZcProductVersionDO getProductVersion(Long id);

    /**
     * 获得产品版本分页
     *
     * @param pageReqVO 分页查询
     * @return 产品版本分页
     */
    PageResult<ZcProductVersionRespVO> getProductVersionPage(ZcProductVersionPageReqVO pageReqVO);

    /**
     * 获得产品版本列表
     *
     * @param listReqVO 查询条件
     * @return 产品版本列表
     */
    List<ZcProductVersionDO> getProductVersionList(ZcProductVersionListReqVO listReqVO);

    /**
     * 获得产品版本精简列表
     *
     * @return 精简列表
     */
    List<ZcProductVersionSimpleRespVO> getProductVersionSimpleList();

    /**
     * 获得产品版本规格列表
     *
     * @param versionId 版本编号
     * @return 规格列表
     */
    List<ZcProductVersionSpcDO> getProductVersionSpcListByVersionId(Long versionId);

    /**
     * 分页查询版本规格列表（含版本名称）
     *
     * @param pageReqVO 分页查询条件
     * @return 版本规格分页列表
     */
    PageResult<ZcProductVersionSpcSimpleRespVO> getProductVersionSpcPage(ZcProductVersionSpcPageReqVO pageReqVO);

}