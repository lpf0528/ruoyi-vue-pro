package cn.iocoder.yudao.module.zc.service.barcoderegistry;

import cn.iocoder.yudao.module.zc.controller.admin.barcoderegistry.vo.ZcBarcodeRegistryCreateReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.barcoderegistry.ZcBarcodeRegistryDO;

import jakarta.validation.Valid;

/**
 * 码注册表 Service 接口
 *
 * @author 智仓
 */
public interface ZcBarcodeRegistryService {

    /**
     * 生成并注册一个新的二维码
     *
     * <p>服务端自动生成 UUID 作为 {@code codeId}，确保全局唯一，
     * 调用方将 codeId 编码为二维码图片后粘贴到实物上</p>
     *
     * @param createReqVO 码注册信息
     * @return 生成的码唯一ID（UUID），供前端生成二维码图片
     */
    String createBarcodeRegistry(@Valid ZcBarcodeRegistryCreateReqVO createReqVO);

    /**
     * 根据码唯一ID获取注册信息
     *
     * <p>App 扫码后携带 codeId 调用此接口，获取跳转路由和业务数据</p>
     *
     * @param codeId 码唯一ID（UUID）
     * @return 码注册记录，不存在时返回 null
     */
    ZcBarcodeRegistryDO getBarcodeRegistry(String codeId);

}
