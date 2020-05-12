package com.yunyuan.searanch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author alan
 * @date 2020/4/16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO implements Serializable {
    @NotBlank(message = "用户名")
    private String username;
    @NotBlank(message = "密码")
    private String password;
    @NotBlank(message = "手机号不能为空")
    private String phoneNumber;
    @Email(message = "必须为邮件格式xxx@xxx.xxx")
    private String email;

    private String nickname;

    private String image;

    private String country;

    private String province;

    private String city;

    private String address;
}
