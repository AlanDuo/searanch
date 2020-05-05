package com.yunyuan.searanch.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * @author alan
 * @date 2020/5/1
 */
@Data
public class BillVO implements Serializable {
    private Date recordDate;

    private String goodsName;

    private String type;

    private BigDecimal price;

    private Integer amount;

    private BigDecimal income;
}
