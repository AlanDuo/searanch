package com.yunyuan.searanch.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author alan
 * @date 2020/5/13
 */
@Data
public class AdminUserVO implements Serializable {

    private Long userId;

    private String username;

    private String phoneNumber;

    private String email;

    private String nickname;

    private String country;

    private String province;

    private String city;

    private String address;

    private Integer growth;
}
