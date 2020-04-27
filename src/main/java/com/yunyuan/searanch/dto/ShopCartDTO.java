package com.yunyuan.searanch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author alan
 * @date 2020/3/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopCartDTO {
    private Long goods;

    private Integer amount;

    private Long typeId;
}
