package com.yunyuan.searanch.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author alan
 * @date 2020/5/14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminGoodsVO implements Serializable {
    private Long goodsId;

    private String goodsName;

    private String type;

    private String picture;

    private String goodsDesc;

    private BigDecimal price;

    private Integer stock;

    private Integer salesAmount;

    private String country;

    private String province;

    private String city;
}
