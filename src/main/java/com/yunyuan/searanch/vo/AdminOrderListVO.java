package com.yunyuan.searanch.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author alan
 * @date 2020/4/25
 */
@Data
public class AdminOrderListVO implements Serializable {
    private String orderNumber;

    private String phone;

    private String userName;

    private Integer amount;

    private BigDecimal totalPrice;

    private String logisticsNo;

    private Date orderTime;

    private Long merchantId;

    private String merchant;
}
