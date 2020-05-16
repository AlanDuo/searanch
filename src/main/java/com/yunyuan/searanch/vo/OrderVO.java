package com.yunyuan.searanch.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author alan
 * @date 2020/4/2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderVO implements Serializable {
    private Long orderId;

    private String orderNumber;

    private Long userId;

    private Long goodsId;

    private String goodsName;

    private String picture;

    private String type;

    private Integer amount;

    private BigDecimal price;

    private BigDecimal discount;

    private Long discountId;

    private String address;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date orderTime;

    private Boolean paid;

    private Boolean finished;
}
