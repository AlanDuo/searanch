package com.yunyuan.searanch.service;

import com.yunyuan.searanch.entity.Order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author alan
 * @date 2020/5/3
 */
public interface DataAnalysisService {
    /**
     * 根据时间区间查询订单
     *
     * @param date1
     * @param date2
     * @return
     */
    List<Order> getOrdersByTime(Date date1, Date date2);
    /**
     * 根据时间区间查询订单的数量
     *
     * @param date1
     * @param date2
     * @return
     */
    int getOrderAmountByTime(Date date1,Date date2);

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
     * 每个季度订单的数量占比
     *
     * @param year
     * @return
     */
    Map<Integer,Float> quarterOrderRatio(Integer year);

    /**
     * 该季度该类商品的订单的数量占比
     *
     * @param year
     * @param quarter
     * @return
     */
    Map<String,Float> quarterGoodsTypeOrderRatio(Integer year,Integer quarter);

    /**
     * 该季度该类商品所有商品的订单量占比
     *
     * @param year
     * @param quarter
     * @param type
     * @return
     */
    Map<String,Float> quarterOrderRatioOfType(Integer year,Integer quarter,String type);

    /**
     * 各个省份，订单总额与销售总额的比值
     * @param year
     * @return
     */
    Map<String,BigDecimal> ratioOfProvince(Integer year);

    /**
     * 此省份各个城市，订单总额与销售总额的比值
     *
     * @param year
     * @param province
     * @return
     */
    Map<String,BigDecimal> ratioOfCity(Integer year,String province);

}
