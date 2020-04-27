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

    private String phoneNumber;

    private String email;

    private String nickname;

    private String address;

    private Integer growth;

}
