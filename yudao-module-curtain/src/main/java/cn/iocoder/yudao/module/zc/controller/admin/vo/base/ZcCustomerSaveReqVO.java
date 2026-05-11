package cn.iocoder.yudao.module.zc.controller.admin.vo.base;

import lombok.Data;

@Data
public class ZcCustomerSaveReqVO {

    private Long id;
    private String shortName;
    private String name;
    private String contactName;
    private String address;
    private String province;
    private String city;
    private String district;
    private String deliveryAddress;
    private String mobile;
    private String mobile2;
    private Long logisticId;
    private Long brandId;
    private String note;

}
