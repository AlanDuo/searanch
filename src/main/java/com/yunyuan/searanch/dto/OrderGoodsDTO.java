package com.yunyuan.searanch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author alan
 * @date 2020/5/11
 */
@Data
@AllArgsConstructor
public class OrderGoodsDTO {
    private Long goodsId;

    private Long typeId;

    private Integer amount;

    private BigDecimal price;
}
