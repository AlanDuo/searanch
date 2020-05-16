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
 * @date 2020/5/14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsAppliedVO implements Serializable {
    private Long applyId;

    private String goodsName;

    private String goodsType;

    private String picture;

    private Integer amount;

    private String merchant;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date breedTime;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date finishTime;

    private String country;

    private String province;

    private String city;

    private BigDecimal price;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date applyTime;

    private Boolean publish;
}
