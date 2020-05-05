package com.yunyuan.searanch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

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

    private String picture;

    private String goodsDesc;

    private BigDecimal price;

    private Integer stock;

    private Date upTime;

    private Date breedTime;

    private Date produceTime;

    private Date packTime;

    private String country;

    private String region;

    private String city;

}
