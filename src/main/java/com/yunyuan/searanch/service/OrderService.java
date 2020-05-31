package com.yunyuan.searanch.service;

import com.yunyuan.searanch.dto.OrderDTO;
import com.yunyuan.searanch.entity.User;
import com.yunyuan.searanch.utils.ResponseData;
import com.yunyuan.searanch.vo.OrderVO;

import java.util.List;
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
     * @param orderId
     * @return
     */
    boolean sureFinish(Long orderId);

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
    List<OrderVO> orderInfo(String orderNumber);

    /**
     * 判断订单是否完成
     *
     * @param orderId
     * @return
     */
    boolean orderIsFinish(Long orderId);

    /**
     * 获取用户已经评价订单的次数
     *
     * @param orderId
     * @param user
     * @return
     */
    int orderEvaluateTimes(Long orderId,User user);

    /**
     * 评价订单
     *
     * @param orderId
     * @param goodsAbout
     * @param goodsEva
     * @param image
     * @param user
     * @return
     */
    boolean evaluateOrder(Long orderId,Integer goodsAbout,String goodsEva,String image,User user);
}
