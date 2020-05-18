package com.yunyuan.searanch.service.impl;

import com.yunyuan.searanch.dao.*;
import com.yunyuan.searanch.dto.OrderDTO;
import com.yunyuan.searanch.dto.OrderGoodsDTO;
import com.yunyuan.searanch.entity.*;
import com.yunyuan.searanch.service.OrderService;
import com.yunyuan.searanch.vo.OrderListVO;
import com.yunyuan.searanch.vo.OrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author alan
 * @date 2020/4/2
 */
@Service
public class OrderServiceImpl implements OrderService{
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private MerchantRegisterMapper merchantRegisterMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private EvaluateMapper evaluateMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "order",key = "#user.userId")
    public boolean addOrder(OrderDTO orderDTO, User user) {
        Order order=new Order();
        BeanUtils.copyProperties(orderDTO,order);
        order.setUserId(user.getUserId());
        if(null!=orderDTO.getAddressee() && !"".equals(orderDTO.getAddress())) {
            order.setUserName(user.getUsername());
        }else{
            order.setUserName(orderDTO.getAddress());
        }
        order.setPaid(false);
        order.setFinished(false);
        order.setOrderTime(new Date());

        List<OrderGoodsDTO> orderGoodsList=orderDTO.getGoodsList();
        for(OrderGoodsDTO goodsDTO:orderGoodsList) {
            GoodsExample goodsExample = new GoodsExample();
            GoodsExample.Criteria goodsCriteria = goodsExample.createCriteria();
            goodsCriteria.andGoodsIdEqualTo(goodsDTO.getGoodsId());
            Goods goods = goodsMapper.selectByExample(goodsExample).get(0);
            order.setGoodsId(goodsDTO.getGoodsId());
            order.setMerchantId(goods.getBusiness());
            order.setGoodsName(goods.getGoodsName());
            order.setAmount(goodsDTO.getAmount());
            order.setPrice(goodsDTO.getPrice());
            if(null!=goodsDTO.getTypeId() && goodsDTO.getTypeId()!=0){
                order.setTypeId(goodsDTO.getTypeId());
            }

            MerchantRegisterExample merchantRegisterExample = new MerchantRegisterExample();
            MerchantRegisterExample.Criteria registerCriteria = merchantRegisterExample.createCriteria();
            registerCriteria.andRegistraIdEqualTo(goods.getBusiness());
            MerchantRegister merchantRegister = merchantRegisterMapper.selectByExample(merchantRegisterExample).get(0);
            order.setMerchant(merchantRegister.getMerchantName());

            orderMapper.insertSelective(order);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean payOrder(String orderNumber) {
        OrderExample orderExample=new OrderExample();
        OrderExample.Criteria orderCriteria=orderExample.createCriteria();
        orderCriteria.andOrderNumberEqualTo(orderNumber);
        List<Order> orderList=orderMapper.selectByExample(orderExample);
        for(Order order:orderList){
            order.setPaid(true);
            order.setPayTime(new Date());
            orderMapper.updateByPrimaryKeySelective(order);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean sureFinish(String orderNumber) {
        OrderExample orderExample=new OrderExample();
        OrderExample.Criteria orderCriteria=orderExample.createCriteria();
        orderCriteria.andOrderNumberEqualTo(orderNumber);
        Order order=orderMapper.selectByExample(orderExample).get(0);
        order.setFinished(true);
        order.setFinishTime(new Date());
        orderMapper.updateByPrimaryKeySelective(order);
        Long userId=order.getUserId();
        User user=userMapper.selectByPrimaryKey(userId);
        user.setGrowth(user.getGrowth()+10);
        return userMapper.updateByPrimaryKeySelective(user)>0;
    }

    @Override
    @Cacheable(value = "order",key = "#userId")
    public Map<String,Object> myOrderList(Long userId) {
        Map<String,Object> map=new HashMap<>(2);
        OrderExample orderExample=new OrderExample();
        OrderExample.Criteria orderCriteria=orderExample.createCriteria();
        orderCriteria.andUserIdEqualTo(userId);
        List<Order> orderList=orderMapper.selectByExample(orderExample);
        map.put("pageInfo",orderList);
        List<OrderListVO> orderListVOS=new ArrayList<>();
        for(Order order:orderList){
            OrderListVO orderListVO=new OrderListVO();
            orderListVO.setOrderNumber(order.getOrderNumber());
            Goods goods=goodsMapper.selectByPrimaryKey(order.getGoodsId());

            BeanUtils.copyProperties(order,orderListVO);

            //orderListVO.setPrice(order.getPrice().subtract(order.getDiscount()));
            orderListVO.setGoodsName(goods.getGoodsName());
            orderListVO.setDesc(goods.getGoodsDesc());
            orderListVO.setPicture(Arrays.asList(goods.getPicture().split(",")).get(0));
            //orderListVO.setType(goodsTypeMapper.selectByPrimaryKey(order.getTypeId()).getTypeDesc());

            orderListVOS.add(orderListVO);
        }
        map.put("orderListVOS",orderListVOS);
        return map;
    }

    @Override
    @Cacheable(value = "orderInfo")
    public OrderVO orderInfo(String orderNumber) {
        OrderExample orderExample=new OrderExample();
        OrderExample.Criteria orderCriteria=orderExample.createCriteria();
        orderCriteria.andOrderNumberEqualTo(orderNumber);
        Order order=orderMapper.selectByExample(orderExample).get(0);
        OrderVO orderVO=new OrderVO();
        BeanUtils.copyProperties(order,orderVO);
        Long goodsId=order.getGoodsId();
        Goods goods=goodsMapper.selectByPrimaryKey(goodsId);
        orderVO.setGoodsName(goods.getGoodsName());
        orderVO.setPicture(Arrays.asList(goods.getPicture().split(",")).get(0));
        return orderVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean evaluateOrder(String orderNumber, Integer goodsAbout, String goodsEva, User user) {
        OrderExample orderExample=new OrderExample();
        OrderExample.Criteria orderCriteria=orderExample.createCriteria();
        orderCriteria.andOrderNumberEqualTo(orderNumber);
        Order order=orderMapper.selectByExample(orderExample).get(0);
        Evaluate evaluate=new Evaluate();
        evaluate.setOrderId(order.getOrderId());
        evaluate.setUserId(order.getUserId());
        evaluate.setGoodsId(order.getGoodsId());
        evaluate.setGoodsEva(goodsEva);
        evaluate.setGoodsAbout(goodsAbout);
        evaluate.setEvaluateTime(new Date());
        evaluateMapper.insertSelective(evaluate);
        OrderExample orderExample1=new OrderExample();
        OrderExample.Criteria orderCriteria1=orderExample1.createCriteria();
        orderCriteria1.andMerchantIdEqualTo(order.getMerchantId());
        List<Order> orderList=orderMapper.selectByExample(orderExample1);
        int unitNumberSum=0;
        int mulNumberSum=0;
        for(Order o:orderList){
            EvaluateExample evaluateExample=new EvaluateExample();
            EvaluateExample.Criteria evaluateCriteria=evaluateExample.createCriteria();
            evaluateCriteria.andOrderIdEqualTo(o.getOrderId());
            List<Evaluate> evaluateList=evaluateMapper.selectByExample(evaluateExample);
            if(evaluateList.size()!=0){
                for(Evaluate e:evaluateList) {
                    int eva = e.getGoodsAbout();
                    mulNumberSum += (eva * o.getAmount());
                    unitNumberSum += o.getAmount();
                }
            }
        }
        int star=0;
        if(unitNumberSum!=0) {
            star = mulNumberSum / unitNumberSum;
        }
        MerchantRegister merchant=merchantRegisterMapper.selectByPrimaryKey(order.getMerchantId());
        merchant.setStar(star);
        return merchantRegisterMapper.updateByPrimaryKeySelective(merchant)>0;
    }
}
