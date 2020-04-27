package com.yunyuan.searanch.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author alan
 * @date 2020/4/25
 */
@Data
public class AdminOrderListVO {
    private String orderNumber;

    private String goodsName;

    private String userName;

    private String merchant;

    private Integer amount;

    private BigDecimal price;

    private Date orderTime;
}
