package com.yunyuan.searanch.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author alan
 * @date 2020/5/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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
