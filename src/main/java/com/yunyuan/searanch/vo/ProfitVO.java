package com.yunyuan.searanch.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author alan
 * @date 2020/5/17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfitVO implements Serializable {
    private BigDecimal salesVolume;
    private BigDecimal cost;
    private BigDecimal profit;
}
