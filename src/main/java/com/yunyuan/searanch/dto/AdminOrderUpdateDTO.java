package com.yunyuan.searanch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author alan
 * @date 2020/5/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminOrderUpdateDTO {
    @NotBlank(message = "订单编号不能为空")
    private String orderNumber;

    private String phone;

    private Integer amount;

    private String country;

    private String province;

    private String city;

    private String address;

    private String logisticsNo;
}
