package com.yunyuan.searanch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author alan
 * @date 2020/4/2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    @NotBlank(message = "手机号不能为空")
    private String phone;

    private String addressee;

    private List<OrderGoodsDTO> goodsList;

    private  String country;

    private String province;

    private String city;
    @NotBlank(message = "地址不能为空")
    private String address;

}
