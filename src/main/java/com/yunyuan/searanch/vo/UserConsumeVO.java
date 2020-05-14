package com.yunyuan.searanch.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author alan
 * @date 2020/5/13
 */
@Data
public class UserConsumeVO implements Serializable {
    private Long goodsId;

    private String goodsName;

    private Integer amount;

    private BigDecimal price;

    private Date orderTime;

    private String evaluate;
}
