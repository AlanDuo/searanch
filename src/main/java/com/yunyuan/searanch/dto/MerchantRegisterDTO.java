package com.yunyuan.searanch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author alan
 * @date 2020/4/16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantRegisterDTO implements Serializable {
    @NotBlank(message = "用户名")
    private String username;
    @NotBlank(message = "密码")
    private String password;
    @Email(message = "必须为邮件格式xxxx@xx.xxx")
    private String email;
    @NotBlank(message = "商家名称")
    private String merchantName;
    @NotBlank(message = "商家电话")
    private String merchantPhone;

    private String image;
    @NotNull
    private String idCard;

    private String idCardImag;
    @NotNull
    private String license;

    private String licenseImag;

    private String country;

    private String province;

    private String city;

    private String address;

}
