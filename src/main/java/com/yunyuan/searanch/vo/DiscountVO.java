package com.yunyuan.searanch.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author alan
 * @date 2020/3/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountVO implements Serializable {
    private Long discountId;

    private String discountDesc;

    private BigDecimal discountAmount;

    private BigDecimal condition;

    private Byte discountType;
}
