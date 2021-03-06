package com.yunyuan.searanch.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author alan
 * @date 2020/5/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVO implements Serializable {

    private String username;

    private String phoneNumber;

    private String email;

    private String nickname;

    private String image;

    private String country;

    private String province;

    private String city;

    private String address;

}
