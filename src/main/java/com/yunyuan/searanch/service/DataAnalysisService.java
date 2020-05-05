package com.yunyuan.searanch.service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author alan
 * @date 2020/5/3
 */
public interface DataAnalysisService {

    /**
     * 每个月销售额
     *
     * @param year
     * @return
     */
    Map<Integer, BigDecimal> monthlySales(Integer year);

    /**
     * 该月份各类商品的销售额
     *
     * @param year
     * @param month
     * @return
     */
    Map<String,BigDecimal> monthlyGoodsTypeSales(Integer year,Integer month);

    /**
     * 该类商品所有商品的销售额
     *
     * @param year
     * @param month
     * @param type
     * @return
     */
    Map<String,BigDecimal> goodsSalesOfType(Integer year,Integer month,String type);

    /**
     * 每个季度订单的数量
     *
     * @param year
     * @return
     */
    Map<Integer,Integer> quarterOrderAmount(Integer year);

    /**
     * 该季度该类商品的订单的数量
     *
     * @param year
     * @param quarter
     * @return
     */
    Map<String,Integer> quarterGoodsTypeOrderAmount(Integer year,Integer quarter);

    /**
     * 该季度该类商品所有商品的订单量
     *
     * @param year
     * @param quarter
     * @param type
     * @return
     */
    Map<String,Integer> quarterOrderAmountOfType(Integer year,Integer quarter,String type);
}
