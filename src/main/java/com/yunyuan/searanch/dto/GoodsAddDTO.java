package com.yunyuan.searanch.dto;

import com.yunyuan.searanch.entity.GoodsType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author alan
 * @date 2020/4/5
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsAddDTO {
    private String goodsName;

    private String type;

    private String processMode;

    private String video;

    private String desc;

    private BigDecimal price;

    private Boolean upShelf;

    private Integer stock;

    private String business;

    private Date breedTime;

    private Date produceTime;

    private Date packTime;

    private String country;

    private String region;

    private String city;

    List<GoodsType> goodsTypeList;
}
