package com.yunyuan.searanch.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yunyuan.searanch.dto.OrderDTO;
import com.yunyuan.searanch.entity.Order;
import com.yunyuan.searanch.entity.User;
import com.yunyuan.searanch.service.OrderService;
import com.yunyuan.searanch.utils.ResponseData;
import com.yunyuan.searanch.vo.OrderListVO;
import com.yunyuan.searanch.vo.OrderVO;
import com.yunyuan.searanch.vo.TableVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@author alan
 *@date 2020/4/2
 */
@Api(value = "订单模块",tags = "订单模块接口，下单，个人订单查询")
@RestController
@RequestMapping("/order")
public class OrderController {
    private OrderService orderService;
    @Autowired
    public OrderController(OrderService orderService){
        this.orderService=orderService;
    }

    @ApiOperation(value="下单")
    @PostMapping("/addOrder")
    public ResponseData addOrder(@Validated @RequestBody OrderDTO orderDTO){
        Subject subject= SecurityUtils.getSubject();
        User user=(User)subject.getPrincipal();
        String orderNumber=orderService.addOrder(orderDTO,user);
        Map<String,String> map=new HashMap<>(1);
        map.put("orderNumber",orderNumber);
        return  ResponseData.ok().putDataValue(map);
    }
    @ApiOperation(value="支付")
    @PostMapping("/pay")
    public ResponseData pay(String orderNumber){
        orderService.payOrder(orderNumber);
        return ResponseData.ok();
    }
    @ApiOperation(value="确认订单完成")
    @PutMapping("/sureFinish/{orderId}")
    public ResponseData sureOrderFinish(@PathVariable("orderId") Long orderId){
        orderService.sureFinish(orderId);
        return ResponseData.ok();
    }
    @ApiOperation(value="查询订单")
    @GetMapping("/orderList")
    public TableVO searchMyOrder(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                 @RequestParam(value = "limit",defaultValue = "10") Integer limit){
        Subject subject= SecurityUtils.getSubject();
        User user=(User)subject.getPrincipal();
        if(null!=user) {
            PageHelper.startPage(page, limit);
            Map<String,Object> map=orderService.myOrderList(user.getUserId());
            PageInfo pageInfo=new PageInfo<>((List<Order>)map.get("pageInfo"));
            List<OrderListVO> orderVOList = (List<OrderListVO>)map.get("orderListVOS");
            return new TableVO(pageInfo,orderVOList);
        }else{ return null;}
    }
    @ApiOperation(value="单个订单详情")
    @GetMapping("/orderInfo/{orderNumber}")
    public ResponseData singleOrderInfo(@PathVariable("orderNumber")String orderNumber){
        Subject subject= SecurityUtils.getSubject();
        User user=(User)subject.getPrincipal();
        if(null!=user){
            List<OrderVO> orderVOList=orderService.orderInfo(orderNumber);
            return ResponseData.ok().putDataValue(orderVOList);
        }else{return ResponseData.notFound();}
    }
    @ApiOperation(value="订单评价")
    @PostMapping("/evaluate")
    public ResponseData evaluateOrder(Long orderId,@RequestParam(value = "goodsAbout",defaultValue = "0")Integer goodsAbout,String goodsEva,String image){
        if(!orderService.orderIsFinish(orderId)){
            return new ResponseData(500,"订单尚未完成");
        }
        Subject subject= SecurityUtils.getSubject();
        User user=(User)subject.getPrincipal();
        if(orderService.orderEvaluateTimes(orderId,user)>=2){
            return new ResponseData(500,"评价订单次数已达上限");
        }
        orderService.evaluateOrder(orderId,goodsAbout,goodsEva,image,user);
        return ResponseData.ok();
    }
}
