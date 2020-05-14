package com.yunyuan.searanch.service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author alan
 * @date 2020/4/3
 */
public interface GoodsManagerService {
    /**
     * 待收购商品列表
     *
     * @return
     */
    Map<String,Object> goodsWaitToTake();

    /**
     * 对申请收购的商品处理是否收购
     *
     * @param applyId
     * @param isTake
     * @param remarks
     * @param handler
     * @return
     */
    boolean sureTakeGoods(Long applyId,boolean isTake,String remarks,Long handler);

    /**
     * 获取已经收购的商品
     *
     * @return
     */
    Map<String,Object> getGoodsApplied();

    /**
     * 发布商品
     *
     * @param applyId
     * @param price
     * @return
     */
    boolean publishGoods(Long applyId, BigDecimal price);

    /**
     * 获取已经发布的商品
     *
     * @param goodsName
     * @return
     */
    Map<String,Object> getPublishedGoods(String goodsName);
}
