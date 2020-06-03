package com.yunyuan.searanch.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author alan
 * @date 2020/3/21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageGoodsVO implements Serializable {
    private Long goodsId;
    private String goodsName;
    private String picture;
    private String desc;
    private BigDecimal price;
    private Integer evaluateAmount;
    private Integer likeAmount;
    private Boolean isLike;
    private String type;
}
