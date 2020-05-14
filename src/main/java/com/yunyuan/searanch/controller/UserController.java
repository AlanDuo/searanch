package com.yunyuan.searanch.controller;

import com.yunyuan.searanch.vo.UserInfoVO;
import com.yunyuan.searanch.dto.UserUpdateDTO;
import com.yunyuan.searanch.entity.MerchantRegister;
import com.yunyuan.searanch.entity.User;
import com.yunyuan.searanch.service.UserService;
import com.yunyuan.searanch.utils.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author alan
 * @date 2020/3/17
 */
@RestController
@Api(value = "用户接口模块",tags = "用户接口模块信息")
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }

    @ApiOperation(value = "用户信息")
    @GetMapping("/userInfo")
    public ResponseData userInfo(){
        Subject subject= SecurityUtils.getSubject();
        User user=(User)subject.getPrincipal();
        UserInfoVO userInfoVO=new UserInfoVO();
        BeanUtils.copyProperties(user,userInfoVO);
        MerchantRegister merchant=userService.getMerchantByPhone(user.getPhoneNumber());
        if(null!=merchant){
            userInfoVO.setCountry(merchant.getCountry());
            userInfoVO.setCity(merchant.getCity());
            userInfoVO.setAddress(merchant.getAddress());
        }
        return ResponseData.ok().putDataValue(userInfoVO);
    }

    @ApiOperation(value="用户信息修改",notes="六个参数全传或挑着传")
    @PostMapping("/update")
    public ResponseData userUpdate(@RequestBody UserUpdateDTO userUpdateDTO){
        Subject subject= SecurityUtils.getSubject();
        User user=(User)subject.getPrincipal();
        User user1=userService.updateUser(user.getUserId(),userUpdateDTO);
        UserInfoVO userInfoVO=new UserInfoVO();
        BeanUtils.copyProperties(user1,userInfoVO);
        if(user1!=null) {
            return ResponseData.ok().putDataValue(userInfoVO);
        }else{
            return ResponseData.error();
        }
    }

    @ApiOperation(value="用户反馈")
    @PostMapping("/feedback")
    public ResponseData uploadFile(String imag, String content){
        Subject subject= SecurityUtils.getSubject();
        User user=(User)subject.getPrincipal();
        if(userService.feedback(user.getUserId(),content,imag)) {
            return ResponseData.ok();
        }else{
            return ResponseData.error();
        }
    }
}
