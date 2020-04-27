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
public class UserRegisterDTO implements Serializable {
    private String username;

    private String password;

    private String phoneNumber;

    private String email;

    private String nickname;

    private String image;

    private String country;

    private String city;

    private String address;
}
