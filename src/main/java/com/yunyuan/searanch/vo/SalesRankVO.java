package com.yunyuan.searanch.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author alan
 * @date 2020/5/30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesRankVO implements Serializable {
    private Long goodsId;

    private String goodsName;

    private String type;

    private String processMode;

    private String picture;

    private String goodsDesc;

    private BigDecimal price;

    private Integer stock;

    private Integer salesAmount;
}
