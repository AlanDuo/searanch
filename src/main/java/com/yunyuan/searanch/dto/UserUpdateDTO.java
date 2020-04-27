package com.yunyuan.searanch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author alan
 * @date 2020/3/24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO implements Serializable {
    private String username;

    private String password;

    private String phoneNumber;

    private String nickname;

    private String address;
}
