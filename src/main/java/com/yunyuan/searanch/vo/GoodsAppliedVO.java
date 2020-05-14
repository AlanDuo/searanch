package com.yunyuan.searanch.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author alan
 * @date 2020/5/14
 */
@Data
public class GoodsAppliedVO implements Serializable {
    private Long applyId;

    private String goodsName;

    private String goodsType;

    private String picture;

    private Integer amount;

    private String merchant;

    private Date breedTime;

    private Date finishTime;

    private String country;

    private String province;

    private String city;

    private BigDecimal price;

    private Date applyTime;

    private Boolean publish;
}
