package com.yunyuan.searanch.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yunyuan.searanch.entity.MerchantRegister;
import com.yunyuan.searanch.entity.Order;
import com.yunyuan.searanch.entity.User;
import com.yunyuan.searanch.service.AdminService;
import com.yunyuan.searanch.utils.ResponseData;
import com.yunyuan.searanch.vo.AdminMerchantListVO;
import com.yunyuan.searanch.vo.AdminOrderListVO;
import com.yunyuan.searanch.vo.TableVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author alan
 * @date 2020/4/23
 */
@Api(value = "管理员",tags = "管理员")
@RestController
@RequestMapping("/admin")
public class AdminController {
    private AdminService adminService;
    @Autowired
    public AdminController(AdminService adminService){
        this.adminService=adminService;
    }

    @ApiOperation(value="修改密码")
    @PostMapping("/changePassword")
    public ResponseData changePassword(String oldPassword,String newPassword){
        Subject subject= SecurityUtils.getSubject();
        User user=(User)subject.getPrincipal();
        adminService.changePassword(oldPassword,newPassword,user);
        return ResponseData.ok();
    }

    @ApiOperation(value="订单列表")
    @GetMapping("/orderList")
    @RequiresRoles("admin")
    public TableVO getOrderList(String orderNumber, String goodsName, String userName, String merchant,
                                @RequestParam(value = "page",defaultValue = "1") Integer page,
                                @RequestParam(value = "limit",defaultValue = "10") Integer limit){
        PageHelper.startPage(page,limit);
        Map<String,Object> map=adminService.adminOrderList(orderNumber,goodsName,userName,merchant);
        PageInfo pageInfo=new PageInfo<>((List<Order>)map.get("pageInfo"));
        return new TableVO(pageInfo,(List<AdminOrderListVO>)map.get("orderListVOs"));
    }
    @ApiOperation(value="修改订单")
    @PostMapping("/updateOrder")
    @RequiresRoles("admin")
    public ResponseData updateOrder(String orderNumber,Integer amount){
        adminService.adminUpdateOrder(orderNumber, amount);
        return ResponseData.ok();
    }
    @ApiOperation(value="删除订单")
    @PostMapping("/deleteOrder")
    @RequiresRoles("admin")
    public ResponseData deleteOrder(String orderNumber,Integer amount){
        adminService.adminDeleteOrder(orderNumber);
        return ResponseData.ok();
    }

    @ApiOperation(value="商家列表")
    @GetMapping("/merchantList")
    @RequiresRoles("admin")
    public TableVO getMerchantList(Long merchantId,String userName,String merchantName,Boolean check,
                                   @RequestParam(value = "page",defaultValue = "1") Integer page,
                                   @RequestParam(value = "limit",defaultValue = "10") Integer limit){
        PageHelper.startPage(page,limit);
        Map<String,Object> map=adminService.adminMerchantList(merchantId,userName,merchantName,check);
        PageInfo pageInfo=new PageInfo<>((List<MerchantRegister>)map.get("pageInfo"));
        return new TableVO(pageInfo,(List<AdminMerchantListVO>)map.get("adminMerchantVOs"));
    }
    @ApiOperation(value="单个商家信息证件信息")
    @GetMapping("/merchantInfo/{registerId}")
    @RequiresRoles("admin")
    public ResponseData merchantInfo(@PathVariable("registerId")Long registerId){
        Map<String,String> map=adminService.adminMerchantInfo(registerId);
        return ResponseData.ok().putDataValue(map);
    }

    @ApiOperation(value="审核商家")
    @PostMapping("/examine/{registerId}")
    @RequiresRoles("admin")
    public ResponseData examineMerchant(@PathVariable("registerId")Long registerId, Boolean examine){
        adminService.adminCheckMerchant(registerId,examine);
        return ResponseData.ok();
    }
}
