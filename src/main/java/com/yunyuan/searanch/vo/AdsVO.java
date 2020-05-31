package com.yunyuan.searanch.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author alan
 * @date 2020/5/3
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdsVO implements Serializable {
    private Long goodsId;
    private String picture;
}
