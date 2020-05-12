package com.yunyuan.searanch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author alan
 * @date 2020/5/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminRegisterDTO {
    @NotBlank(message = "用户名")
    private String username;
    @NotBlank(message = "密码")
    private String password;
    @NotBlank(message = "手机号不能为空")
    private String phoneNumber;
    @Email(message = "必须为邮件格式xxx@xxx.xxx")
    private String email;
}
