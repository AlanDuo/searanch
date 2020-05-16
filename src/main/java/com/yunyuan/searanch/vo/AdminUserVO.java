package com.yunyuan.searanch.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author alan
 * @date 2020/5/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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
