package com.yunyuan.searanch.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yunyuan.searanch.dto.OrderDTO;
import com.yunyuan.searanch.entity.Order;
import com.yunyuan.searanch.entity.User;
import com.yunyuan.searanch.service.OrderService;
import com.yunyuan.searanch.utils.ResponseData;
import com.yunyuan.searanch.vo.OrderVO;
import com.yunyuan.searanch.vo.TableVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ResponseData addOrder(OrderDTO orderDTO){
        Subject subject= SecurityUtils.getSubject();
        User user=(User)subject.getPrincipal();
        orderService.addOrder(orderDTO,user);
        return  ResponseData.ok();
    }
    @ApiOperation(value="支付")
    @PostMapping("/pay")
    public ResponseData pay(){

        return ResponseData.ok();
    }
    @ApiOperation(value="查询订单")
    @GetMapping("/orderList")
    public TableVO searchMyOrder(Integer page,Integer limit){
        Subject subject= SecurityUtils.getSubject();
        User user=(User)subject.getPrincipal();
        if(null!=user) {
            PageHelper.startPage(page, limit);
            Map<String,Object> map=orderService.myOrderList(user.getUserId());
            PageInfo pageInfo=new PageInfo<>((List<Order>)map.get("pageInfo"));
            List<OrderVO> orderVOList = (List<OrderVO>)map.get("orderListVOS");
            return new TableVO(pageInfo,orderVOList);
        }else{ return null;}
    }
    @ApiOperation(value="单个订单详情")
    @GetMapping("/orderInfo/{orderNumber}")
    public ResponseData singleOrderInfo(@PathVariable("orderNumber")String orderNumber){
        Subject subject= SecurityUtils.getSubject();
        User user=(User)subject.getPrincipal();
        if(null!=user){
            OrderVO orderVO=orderService.orderInfo(orderNumber);
            return ResponseData.ok().putDataValue(orderVO);
        }else{return ResponseData.notFound();}
    }
}
