package cn.iocoder.yudao.module.zc.controller.admin.vo.base;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ZcCustomerRespVO {

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
    private BigDecimal balance;
    private String note;

}
