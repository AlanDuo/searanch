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
public class GoodsApplyVO implements Serializable {
    private Long applyId;

    private String goodsName;

    private String goodsType;

    private String picture;

    private Integer amount;

    private String merchant;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date breedTime;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date planTakeTime;

    private String country;

    private String province;

    private String city;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date applyTime;

}
