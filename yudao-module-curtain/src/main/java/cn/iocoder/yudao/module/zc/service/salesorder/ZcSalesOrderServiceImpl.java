package cn.iocoder.yudao.module.zc.service.salesorder;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtain.ZcCurtainDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtaininstallprocess.ZcCurtainInstallProcessDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtainstructure.ZcCurtainStructureDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtainstructureelement.ZcCurtainStructureElementDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.product.ZcProductDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.productbatch.ZcProductBatchDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderCurtainDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderStructureDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZCSalesOrderMaterialDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.curtain.ZcCurtainMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.curtaininstallprocess.ZcCurtainInstallProcessMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.curtainstructure.ZcCurtainStructureMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.curtainstructureelement.ZcCurtainStructureElementMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.product.ZcProductMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.productbatch.ZcProductBatchMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZCSalesOrderMaterialMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZcSalesOrderCurtainMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZcSalesOrderMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZcSalesOrderStructureMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertMap;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;

/**
 * 销售订单 Service 实现类
 *
 * @author 01Coder
 */
@Service
@Validated
public class ZcSalesOrderServiceImpl implements ZcSalesOrderService {

    @Resource
    private ZcSalesOrderMapper salesOrderMapper;
    @Resource
    private ZcSalesOrderCurtainMapper salesOrderCurtainMapper;
    @Resource
    private ZcSalesOrderStructureMapper salesOrderStructureMapper;
    @Resource
    private ZCSalesOrderMaterialMapper salesOrderMaterialMapper;

    @Resource
    private ZcCurtainMapper curtainMapper;
    @Resource
    private ZcCurtainStructureMapper curtainStructureMapper;
    @Resource
    private ZcCurtainInstallProcessMapper curtainInstallProcessMapper;
    @Resource
    private ZcCurtainStructureElementMapper curtainStructureElementMapper;
    @Resource
    private ZcProductMapper productMapper;
    @Resource
    private ZcProductBatchMapper productBatchMapper;

    @Override
    public Long createSalesOrder(ZcSalesOrderSaveReqVO createReqVO) {
        // 插入
        ZcSalesOrderDO salesOrder = BeanUtils.toBean(createReqVO, ZcSalesOrderDO.class);
        salesOrderMapper.insert(salesOrder);

        // 返回
        return salesOrder.getId();
    }

    @Override
    public void updateSalesOrder(ZcSalesOrderSaveReqVO updateReqVO) {
        // 校验存在
        validateSalesOrderExists(updateReqVO.getId());
        // 更新
        ZcSalesOrderDO updateObj = BeanUtils.toBean(updateReqVO, ZcSalesOrderDO.class);
        salesOrderMapper.updateById(updateObj);
    }

    @Override
    public void deleteSalesOrder(Long id) {
        // 校验存在
        validateSalesOrderExists(id);
        // 删除
        salesOrderMapper.deleteById(id);
    }

    @Override
        public void deleteSalesOrderListByIds(List<Long> ids) {
        // 删除
        salesOrderMapper.deleteByIds(ids);
        }


    private void validateSalesOrderExists(Long id) {
        if (salesOrderMapper.selectById(id) == null) {
            throw exception(SALES_ORDER_NOT_EXISTS);
        }
    }

    @Override
    public ZcSalesOrderDO getSalesOrder(Long id) {
        return salesOrderMapper.selectById(id);
    }

    @Override
    public PageResult<ZcSalesOrderRespVO> getSalesOrderPage(ZcSalesOrderPageReqVO pageReqVO) {
        return salesOrderMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ZcSalesOrderCurtainDetailRespVO> getSalesOrderDetail(Long orderId) {
        // 1. 查询该订单下所有窗帘行
        List<ZcSalesOrderCurtainDO> curtainList = salesOrderCurtainMapper.selectListByOrderId(orderId);
        if (CollUtil.isEmpty(curtainList)) {
            return Collections.emptyList();
        }

        // 2. 查询该订单下所有结构行与用料明细（一次性批量取出，避免 N+1）
        List<ZcSalesOrderStructureDO> structureList = salesOrderStructureMapper.selectListByOrderId(orderId);
        List<ZCSalesOrderMaterialDO> materialList = salesOrderMaterialMapper.selectListByOrderId(orderId);

        // 3. 构建窗帘款式名称 Map
        Set<Long> curtainIds = curtainList.stream()
                .map(ZcSalesOrderCurtainDO::getCurtainId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Map<Long, String> curtainNameMap = CollUtil.isNotEmpty(curtainIds)
                ? convertMap(curtainMapper.selectBatchIds(curtainIds), ZcCurtainDO::getId, ZcCurtainDO::getName)
                : Collections.emptyMap();

        // 4. 构建结构名称与安装工艺名称 Map
        Set<Long> structureIds = structureList.stream()
                .map(ZcSalesOrderStructureDO::getStructureId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Map<Long, String> structureNameMap = CollUtil.isNotEmpty(structureIds)
                ? convertMap(curtainStructureMapper.selectBatchIds(structureIds), ZcCurtainStructureDO::getId, ZcCurtainStructureDO::getName)
                : Collections.emptyMap();
        Set<Long> installProcessIds = structureList.stream()
                .map(ZcSalesOrderStructureDO::getInstallProcessId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Map<Long, String> installProcessNameMap = CollUtil.isNotEmpty(installProcessIds)
                ? convertMap(curtainInstallProcessMapper.selectBatchIds(installProcessIds), ZcCurtainInstallProcessDO::getId, ZcCurtainInstallProcessDO::getName)
                : Collections.emptyMap();

        // 5. 构建组件类型名称、产品名称、批次号 Map
        Set<Long> elementIds = materialList.stream()
                .map(ZCSalesOrderMaterialDO::getElementId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Map<Long, String> elementNameMap = CollUtil.isNotEmpty(elementIds)
                ? convertMap(curtainStructureElementMapper.selectBatchIds(elementIds), ZcCurtainStructureElementDO::getId, ZcCurtainStructureElementDO::getName)
                : Collections.emptyMap();
        Set<Long> productIds = materialList.stream()
                .map(ZCSalesOrderMaterialDO::getProductId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Map<Long, String> productNameMap = CollUtil.isNotEmpty(productIds)
                ? convertMap(productMapper.selectBatchIds(productIds), ZcProductDO::getId, ZcProductDO::getName)
                : Collections.emptyMap();
        Set<Long> batchIds = materialList.stream()
                .map(ZCSalesOrderMaterialDO::getBatchId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Map<Long, String> batchNoMap = CollUtil.isNotEmpty(batchIds)
                ? convertMap(productBatchMapper.selectBatchIds(batchIds), ZcProductBatchDO::getId, ZcProductBatchDO::getBatchNo)
                : Collections.emptyMap();

        // 6. 按结构行 ID 分组用料明细
        Map<Long, List<ZCSalesOrderMaterialDetailRespVO>> materialsByStructureId = materialList.stream()
                .collect(Collectors.groupingBy(
                        ZCSalesOrderMaterialDO::getOrderStructureId,
                        Collectors.mapping(m -> {
                            ZCSalesOrderMaterialDetailRespVO vo = BeanUtils.toBean(m, ZCSalesOrderMaterialDetailRespVO.class);
                            vo.setElementName(elementNameMap.get(m.getElementId()));
                            vo.setProductName(productNameMap.get(m.getProductId()));
                            vo.setBatchNo(batchNoMap.get(m.getBatchId()));
                            return vo;
                        }, Collectors.toList())
                ));

        // 7. 按窗帘行 ID 分组结构行
        Map<Long, List<ZcSalesOrderStructureDetailRespVO>> structuresByCurtainId = structureList.stream()
                .collect(Collectors.groupingBy(
                        ZcSalesOrderStructureDO::getOrderCurtainId,
                        Collectors.mapping(s -> {
                            ZcSalesOrderStructureDetailRespVO vo = BeanUtils.toBean(s, ZcSalesOrderStructureDetailRespVO.class);
                            vo.setStructureName(structureNameMap.get(s.getStructureId()));
                            vo.setInstallProcessName(installProcessNameMap.get(s.getInstallProcessId()));
                            vo.setElements(materialsByStructureId.getOrDefault(s.getId(), Collections.emptyList()));
                            return vo;
                        }, Collectors.toList())
                ));

        // 8. 组装最终结果
        return curtainList.stream().map(curtain -> {
            ZcSalesOrderCurtainDetailRespVO vo = BeanUtils.toBean(curtain, ZcSalesOrderCurtainDetailRespVO.class);
            vo.setCurtainName(curtainNameMap.get(curtain.getCurtainId()));
            vo.setStructures(structuresByCurtainId.getOrDefault(curtain.getId(), Collections.emptyList()));
            return vo;
        }).collect(Collectors.toList());
    }

}