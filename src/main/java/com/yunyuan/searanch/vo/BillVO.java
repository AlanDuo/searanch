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
 * @date 2020/5/1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillVO implements Serializable {
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date applyTime;

    private String goodsName;

    private String type;

    private BigDecimal price;

    private Integer amount;

    private BigDecimal income;

    private Boolean finished;
}
