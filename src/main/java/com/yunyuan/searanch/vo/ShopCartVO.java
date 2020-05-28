package com.yunyuan.searanch.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author alan
 * @date 2020/3/31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopCartVO implements Serializable {
    private Long cartId;

    private Long goodsId;

    private String goodsName;

    private String picture;

    private String desc;

    private String processMode;

    private Integer stock;

    private Integer amount;

    private Boolean invalid;

    private BigDecimal addPrice;

    private BigDecimal price;

    private String discount;

    private Long typeId;

    private String typeDesc;
}
