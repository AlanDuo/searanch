package com.yunyuan.searanch.service;

import com.yunyuan.searanch.dto.OrderDTO;
import com.yunyuan.searanch.entity.User;
import com.yunyuan.searanch.utils.ResponseData;
import com.yunyuan.searanch.vo.OrderVO;

import java.util.Map;

/**
 * @author alan
 * @date 2020/4/2
 */
public interface OrderService {

    /**
     * 下单
     *
     * @param orderDTO
     * @param user
     * @return
     */
    String addOrder(OrderDTO orderDTO, User user);

    /**
     * 订单支付
     *
     * @param orderNumber
     * @return
     */
    boolean payOrder(String orderNumber);

    /**
     * 确认订单完成
     *
     * @param orderNumber
     * @return
     */
    boolean sureFinish(String orderNumber);

    /**
     * 查看我的订单
     *
     * @param userId
     * @return
     */
    Map<String,Object> myOrderList(Long userId);

    /**
     * 单个订单的详情
     *
     * @param orderNumber
     * @return
     */
    OrderVO orderInfo(String orderNumber);

    /**
     * 评价订单
     *
     * @param orderNumber
     * @param goodsAbout
     * @param goodsEva
     * @param user
     * @return
     */
    boolean evaluateOrder(String orderNumber,Integer goodsAbout,String goodsEva,User user);
}
