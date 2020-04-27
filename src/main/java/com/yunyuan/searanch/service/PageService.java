package com.yunyuan.searanch.service;

import com.yunyuan.searanch.vo.GoodsInfoVO;
import java.util.Map;

/**
 * @author alan
 * @date 2020/3/21
 */
public interface PageService {
    /**
     * 首页推荐商品信息，没有登录的情况
     *
     * @param page
     * @param limit
     * @return
     */
    Map<String,Object> recommendWithoutLogin(int page,int limit);
    /**
     * 首页推荐商品信息，有登录的情况
     *
     * @param page
     * @param limit
     * @param userId
     * @return
     */
    Map<String,Object> recommendWithLogin(int page,int limit,Long userId);

    /**
     * 获取单个商品的信息
     *
     * @param goodsId
     * @return
     */
    GoodsInfoVO getGoodsInfo(Long goodsId);

    /**
     * 商品的评价信息
     *
     * @param goodsId
     * @return
     */
    Map<String,Object> evaluateInfo(Long goodsId);

    /**
     * 搜索商品
     *
     * @param searchName
     * @return
     */
    Map<String,Object> searchGoods(String searchName);
}
