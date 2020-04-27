package com.yunyuan.searanch.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author alan
 * @date 2020/4/8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderListVO implements Serializable {
    private String orderNumber;

    private String type;

    private Long goodsId;

    private String goodsName;

    private String picture;

    private String desc;

    private Integer amount;

    private BigDecimal price;

    private Date orderTime;

}
