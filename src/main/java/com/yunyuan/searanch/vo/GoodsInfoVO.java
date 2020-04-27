package com.yunyuan.searanch.vo;

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

    private List<String> pictures;

    private String desc;

    private BigDecimal price;

    private Integer stock;

    private Date breedTime;

    private Date produceTime;

    private Date packTime;

    private List<DiscountVO> discountInfo;

    private Integer evaluateAmount;

    private List<GoodsType> goodsTypes;
}
