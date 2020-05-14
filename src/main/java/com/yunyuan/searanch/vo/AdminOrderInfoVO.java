package com.yunyuan.searanch.vo;

import lombok.Data;

/**
 * @author alan
 * @date 2020/5/13
 */
@Data
public class AdminOrderInfoVO {
    private String orderNumber;

    private String phone;

    private Integer amount;

    private String country;

    private String province;

    private String city;

    private String address;

    private String logisticsNo;

    private String evaluate;
}
