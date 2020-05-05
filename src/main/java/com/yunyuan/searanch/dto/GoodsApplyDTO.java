package com.yunyuan.searanch.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author alan
 * @date 2020/5/1
 */
@Data
public class GoodsApplyDTO implements Serializable {
    @NotBlank(message = "商品名字不能为空")
    private String goodsName;
    @NotBlank(message = "商品种类不能为空")
    private String goodsType;

    private String picture;
    @NotNull
    private Integer amount;

    private Date breedTime;

    private Date planUpTime;

    private String country;

    private String region;

    private String city;
}
