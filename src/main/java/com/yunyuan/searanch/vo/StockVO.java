package com.yunyuan.searanch.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author alan
 * @date 2020/4/30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockVO implements Serializable {
    private Long goodsId;

    private String goodsName;

    private String type;

    private String picture;

    private Integer stock;
}
