package com.yunyuan.searanch.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yunyuan.searanch.dto.AdminOrderUpdateDTO;
import com.yunyuan.searanch.dto.AdminRegisterDTO;
import com.yunyuan.searanch.entity.MerchantRegister;
import com.yunyuan.searanch.entity.Order;
import com.yunyuan.searanch.entity.User;
import com.yunyuan.searanch.service.AdminService;
import com.yunyuan.searanch.service.PushService;
import com.yunyuan.searanch.utils.ResponseData;
import com.yunyuan.searanch.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
    private static final String PAGE_INFO="pageInfo";
    private AdminService adminService;
    private PushService pushService;

    @Autowired
    public AdminController(AdminService adminService,PushService pushService){
        this.adminService=adminService;
        this.pushService=pushService;
    }

    @ApiOperation(value = "管理员注册")
    @PostMapping("/register")
    public ResponseData adminRegister(@Validated @RequestBody AdminRegisterDTO adminRegisterDTO){
        if(adminService.adminExist(adminRegisterDTO.getPhoneNumber())){
            return new ResponseData(500,"该管理员已经被注册");
        }
        adminService.adminRegister(adminRegisterDTO);
        return ResponseData.ok();
    }

    @ApiOperation(value="修改密码")
    @PostMapping("/changePassword")
    public ResponseData changePassword(String oldPassword,String newPassword){
        Subject subject= SecurityUtils.getSubject();
        User user=(User)subject.getPrincipal();
        adminService.changePassword(oldPassword,newPassword,user);
        return ResponseData.ok();
    }
    @ApiOperation(value="海牧场主问题反馈")
    @ApiImplicitParam(name = "status",value = "问题处理进度（0未处理，1处理中，2已处理）",dataType = "Integer")
    @GetMapping("/problems")
    @RequiresRoles("admin")
    public ResponseData merchantProblems(Byte status){
        List<ProblemVO> problemVOS=adminService.getProblems("merchant",status);
        return ResponseData.ok().putDataValue(problemVOS);
    }

    @ApiOperation(value= "处理问题")
    @PostMapping("/deal")
    @RequiresRoles("admin")
    public ResponseData dealProblems(Long feedbackId,String email,String content){
        Subject subject= SecurityUtils.getSubject();
        User user=(User)subject.getPrincipal();
        String messageSubject="海洋牧场问题反馈";
        pushService.sendMail(messageSubject,content,user.getUserId(),email);
        adminService.dealWithProblem(feedbackId,content,user.getUserId());
        return ResponseData.ok();
    }

    @ApiOperation(value="查看问题")
    @GetMapping("/proInfo/{feedbackId}")
    @RequiresRoles("admin")
    public ResponseData problemInfo(@PathVariable("feedbackId")Long feedbackId){
        ProblemDetailVO detailVO =adminService.problemDetails(feedbackId);
        return ResponseData.ok().putDataValue(detailVO);
    }


    @ApiOperation(value="订单列表")
    @ApiImplicitParam(name = "deliver",value = "是否发货（true或者false）",dataType = "Boolean")
    @GetMapping("/orderList")
    @RequiresRoles("admin")
    public TableVO getOrderList(String orderNumber, String phoneNumber, String userName,Boolean deliver,
                                @RequestParam(value = "page",defaultValue = "1") Integer page,
                                @RequestParam(value = "limit",defaultValue = "10") Integer limit){
        PageHelper.startPage(page,limit);
        Map<String,Object> map=adminService.adminOrderList(orderNumber,phoneNumber,userName,deliver);
        PageInfo pageInfo=new PageInfo<>((List<Order>)map.get(PAGE_INFO));
        return new TableVO(pageInfo,(List<AdminOrderListVO>)map.get("orderListVOs"));
    }

    @ApiOperation(value="订单信息")
    @GetMapping("/orderInfo")
    @RequiresRoles("admin")
    public ResponseData orderInfoForUpdate(String orderNumber){
        AdminOrderInfoVO orderInfoVO=adminService.adminOrderInfo(orderNumber);
        return ResponseData.ok().putDataValue(orderInfoVO);
    }
    @ApiOperation(value="修改订单")
    @PostMapping("/updateOrder")
    @RequiresRoles("admin")
    public ResponseData updateOrder(@RequestBody AdminOrderUpdateDTO orderUpdateDTO){
        adminService.adminUpdateOrder(orderUpdateDTO);
        return ResponseData.ok();
    }

    @ApiOperation(value="删除订单")
    @PostMapping("/deleteOrder")
    @RequiresRoles("admin")
    public ResponseData deleteOrder(String orderNumber){
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
        PageInfo pageInfo=new PageInfo<>((List<MerchantRegister>)map.get(PAGE_INFO));
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

    @ApiOperation(value="用户列表")
    @GetMapping("/userList")
    @RequiresRoles("admin")
    public TableVO userList(String userName,String phoneNumber,
                            @RequestParam(value = "page",defaultValue = "1") Integer page,
                            @RequestParam(value = "limit",defaultValue = "10") Integer limit){
        PageHelper.startPage(page,limit);
        Map<String,Object> map=adminService.adminUserList(userName,phoneNumber);
        List<User> roleList=(List<User>)map.get(PAGE_INFO);
        PageInfo pageInfo=new PageInfo<>(roleList);
        return new TableVO<>(pageInfo,(List<AdminUserVO>)map.get("userVOList"));
    }

    @ApiOperation(value = "用户购买记录")
    @GetMapping("/userConsume/{userId}")
    @RequiresRoles("admin")
    public ResponseData userConsumeRecord(@PathVariable("userId") Long userId){
        List<UserConsumeVO> consumeVOList=adminService.userConsumeRecord(userId);
        return ResponseData.ok().putDataValue(consumeVOList);
    }
    @ApiOperation(value = "商品推送到广告位")
    @PostMapping("/pushGoods/{goodsId}")
    @RequiresRoles("admin")
    public ResponseData pushGoodsToAd(@PathVariable("goodsId")Long goodsId){
        adminService.pushGoodsToAds(goodsId);
        return ResponseData.ok();
    }
    @ApiOperation(value="发货")
    @PostMapping("/deliver")
    public ResponseData deliverGoods(Long orderId,String logisticsNo){
        adminService.deliverGoods(orderId,logisticsNo);
        return ResponseData.ok();
    }
}
