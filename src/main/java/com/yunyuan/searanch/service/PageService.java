package com.yunyuan.searanch.service;

import com.yunyuan.searanch.entity.User;
import com.yunyuan.searanch.vo.AdsVO;
import com.yunyuan.searanch.vo.GoodsInfoVO;

import java.util.List;
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
     * 获取用户最经常浏览的商品
     *
     * @param userId
     * @return
     */
    List<String> getRecentBrowse(Long userId);
    /**
     * 获取单个商品的信息
     *
     * @param goodsId
     * @param user
     * @return
     */
    GoodsInfoVO getGoodsInfo(Long goodsId, User user);

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
     * @param userId
     * @return
     */
    Map<String,Object> searchGoods(String searchName,Long userId);

    /**
     * 底部商品推荐
     *
     * @param page
     * @param limit
     * @param goodsId
     * @return
     */
    Map<String,Object> bottomRecommend(int page,int limit,long goodsId);

    /**
     * 首页轮播图推荐
     *
     * @return
     */
    List<AdsVO> adsRecommend();

    /**
     * 获取点赞的次数
     *
     * @param userId
     * @param goodsId
     * @return
     */
    int getLikeTimes(Long userId,Long goodsId);

    /**
     * 点赞商品
     *
     * @param userId
     * @param goodsId
     * @return
     */
    boolean likeGoods(Long userId,Long goodsId);

    /**
     * 取消点赞
     *
     * @param userId
     * @param goodsId
     * @return
     */
    boolean cancelLike(Long userId,Long goodsId);
}
