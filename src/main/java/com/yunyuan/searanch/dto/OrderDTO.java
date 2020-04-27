package com.yunyuan.searanch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author alan
 * @date 2020/4/2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long orderId;

    private String orderNumber;

    private Long goodsId;

    private Long typeId;

    private Integer amount;

    private BigDecimal price;

    private BigDecimal discount;

    private Long discountId;

    private String address;

    private Date orderTime;

    private Boolean paid;

    private Boolean finished;

}
