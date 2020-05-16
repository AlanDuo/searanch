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
 * @date 2020/5/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserConsumeVO implements Serializable {
    private Long goodsId;

    private String goodsName;

    private Integer amount;

    private BigDecimal price;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date orderTime;

    private String evaluate;
}
