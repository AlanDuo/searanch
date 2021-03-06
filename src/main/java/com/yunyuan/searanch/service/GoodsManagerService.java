package com.yunyuan.searanch.service;

import com.yunyuan.searanch.vo.SalesRankVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author alan
 * @date 2020/4/3
 */
public interface GoodsManagerService {
    /**
     * 待收购商品列表
     *
     * @param goodsName
     * @return
     */
    Map<String,Object> goodsWaitToTake(String goodsName);

    /**
     * 对申请收购的商品处理是否收购
     *
     * @param applyId
     * @param isTake
     * @param remarks
     * @param handler
     * @return
     */
    boolean sureTakeGoods(Long applyId,boolean isTake,BigDecimal price,String remarks,Long handler);

    /**
     * 获取已经收购的商品
     *
     * @param goodsName
     * @return
     */
    Map<String,Object> getGoodsApplied(String goodsName);

    /**
     * 发布商品
     *
     * @param applyId
     * @param price
     * @param processMode
     * @param goodsDesc
     * @return
     */
    boolean publishGoods(Long applyId, BigDecimal price,String processMode,String goodsDesc);

    /**
     * 获取已经发布的商品
     *
     * @param goodsName
     * @return
     */
    Map<String,Object> getPublishedGoods(String goodsName);

    /**
     * 下架商品
     *
     * @param goodsId
     * @return
     */
    boolean downShelfGoods(Long goodsId);
}
