package com.yunyuan.searanch.service;


import com.yunyuan.searanch.dto.ShopCartDTO;
import com.yunyuan.searanch.vo.ShopCartVO;

import java.util.List;

/**
 * @author alan
 * @date 2020/3/28
 */
public interface ShopCartService {
    /**
     * 加入购物车
     *
     * @param userId
     * @param goodsId
     * @param typeId
     * @param amount
     * @return
     */
    boolean addShopCart(Long userId,Long goodsId,Long typeId,Integer amount);

    /**
     * 我的购物车
     *
     * @param userId
     * @return
     */
    List<ShopCartVO> getShopCart(Long userId);

    /**
     * 更新购物车内容
     *
     * @param cartId
     * @param amount
     * @param userId
     * @return
     */
    boolean updateShopCart(Long cartId, Integer amount,Long userId);

    /**
     * 删除购物车
     *
     * @param cartId
     * @param userId
     * @return
     */
    boolean deleteShopCart(Long cartId,Long userId);
}
