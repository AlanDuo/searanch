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
public class AdminListVO implements Serializable {
    private Long userId;
    private String username;
    private String phoneNumber;
    private Boolean check;
}
