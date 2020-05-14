package com.yunyuan.searanch.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author alan
 * @date 2020/4/25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminMerchantListVO implements Serializable {
    private Long registraId;

    private String userName;

    private String merchantName;

    private String merchantPhone;

    private String country;

    private String province;

    private String city;

    private String address;

    private String idCard;

    private String license;

    private Boolean examine;

    private Integer star;

    private Date registraTime;
}
