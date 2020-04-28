package com.yunyuan.searanch.controller;

import com.yunyuan.searanch.entity.User;
import com.yunyuan.searanch.service.ShopCartService;
import com.yunyuan.searanch.utils.ResponseData;
import com.yunyuan.searanch.vo.ShopCartVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author alan
 * @date 2020/3/28
 */
@RestController
@RequestMapping("/shopCart")
@Api(value = "购物车",tags = "购物车相关接口")
public class ShopCartController {
    private ShopCartService shopCartService;
    @Autowired
    public ShopCartController(ShopCartService shopCartService){
        this.shopCartService=shopCartService;
    }

    public static User currentUser(){
        Subject subject= SecurityUtils.getSubject();
        return (User)subject.getPrincipal();
    }

    @ApiOperation(value="添加购物车")
    @PostMapping("/addShopCart")
    public ResponseData addShopCart(Long goodsId,Long typeId,Integer amount){
        User user=currentUser();
        if(null!=user) {
            shopCartService.addShopCart(user.getUserId(), goodsId, typeId, amount);
            return ResponseData.ok();
        }
        return ResponseData.forbidden();
    }
    @ApiOperation(value="我的购物车")
    @GetMapping("/myShopCart")
    public ResponseData myShopCart(){
        User user=currentUser();
        if(null!=user){
            List<ShopCartVO> shopCartVOList=shopCartService.getShopCart(user.getUserId());
            return ResponseData.ok().putDataValue(shopCartVOList);
        }
        return ResponseData.notFound();
    }
    @ApiOperation(value="修改购物车")
    @PostMapping("/updateShopCart/{cartId}")
    public ResponseData updateShopCart(@PathVariable("cartId")Long cartId, Integer amount){
        User user=currentUser();
        if(null!=user){
            shopCartService.updateShopCart(cartId,amount);
            return ResponseData.ok();
        }
        return ResponseData.notFound();
    }
    @ApiOperation(value="删除购物车")
    @DeleteMapping("/deleteShopCart")
    public ResponseData deleteShopCart(@RequestBody List<Long> cartIdList){
        for(Long cartId:cartIdList) {
            shopCartService.deleteShopCart(cartId);
        }
        return ResponseData.ok();
    }
}
