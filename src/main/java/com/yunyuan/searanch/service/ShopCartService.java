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
     * @return
     */
    boolean updateShopCart(Long cartId, Integer amount);

    /**
     * 删除购物车
     *
     * @param cartId
     * @return
     */
    boolean deleteShopCart(Long cartId);
}
