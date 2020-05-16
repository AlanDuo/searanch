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
 * @date 2020/4/25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminOrderListVO implements Serializable {
    private String orderNumber;

    private String phone;

    private String userName;

    private Integer amount;

    private BigDecimal totalPrice;

    private String logisticsNo;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date orderTime;

    private Long merchantId;

    private String merchant;
}
