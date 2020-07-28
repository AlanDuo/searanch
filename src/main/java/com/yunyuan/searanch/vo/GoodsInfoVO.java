package com.yunyuan.searanch.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunyuan.searanch.entity.GoodsType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author alan
 * @date 2020/3/23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsInfoVO implements Serializable {
    private Long goodsId;

    private String goodsName;

    private String processMode;

    private String type;

    private String merchant;

    private String country;

    private String province;

    private String city;

    private List<String> pictures;

    private String desc;

    private BigDecimal price;

    private Integer stock;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date breedTime;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date produceTime;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date packTime;

    private List<DiscountVO> discountInfo;

    private Integer evaluateAmount;

    private List<GoodsType> goodsTypes;
}
