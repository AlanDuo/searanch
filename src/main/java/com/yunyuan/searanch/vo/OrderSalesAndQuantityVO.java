package com.yunyuan.searanch.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author alan
 * @date 2020/5/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderSalesAndQuantityVO implements Serializable {
    private BigDecimal salesVolume;
    private Integer orderQuantity;
}
