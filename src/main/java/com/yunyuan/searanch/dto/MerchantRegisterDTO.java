package com.yunyuan.searanch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author alan
 * @date 2020/4/16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantRegisterDTO implements Serializable {
    private String username;

    private String password;

    private String email;

    private String merchantName;

    private String merchantPhone;

    private String image;

    private String idCard;

    private String idCardImag;

    private String license;

    private String licenseImag;

    private String country;

    private String city;

    private String address;

}
