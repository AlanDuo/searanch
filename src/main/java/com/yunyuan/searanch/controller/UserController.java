package com.yunyuan.searanch.controller;

import com.yunyuan.searanch.dto.UserUpdateDTO;
import com.yunyuan.searanch.entity.User;
import com.yunyuan.searanch.service.UserService;
import com.yunyuan.searanch.utils.FileUploadUtil;
import com.yunyuan.searanch.utils.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


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

    @ApiOperation(value="用户反馈")
    @PostMapping("/feedback")
    public ResponseData uploadFile(MultipartFile file, String content,HttpServletRequest request){
        Subject subject= SecurityUtils.getSubject();
        User user=(User)subject.getPrincipal();
        String url="";
        if(null!=file) {
            FileUploadUtil.uploadFile(file, request);
            url= FileUploadUtil.getUrl();
        }
        if(userService.feedback(user.getUserId(),content,url)) {
            return ResponseData.ok();
        }else{
            return ResponseData.error();
        }
    }
    @ApiOperation(value="用户信息修改",notes="六个参数全传或挑着传")
    @PostMapping("/update")
    public ResponseData userUpdate(UserUpdateDTO userUpdateDTO, MultipartFile file, HttpServletRequest request){
        Subject subject= SecurityUtils.getSubject();
        User user=(User)subject.getPrincipal();
        User user1=userService.updateUser(user.getUserId(),userUpdateDTO,file,request);
        if(user1!=null) {
            return ResponseData.ok();
        }else{
            return ResponseData.error();
        }
    }
}
