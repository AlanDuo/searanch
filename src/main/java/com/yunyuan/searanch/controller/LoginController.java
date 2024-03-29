package com.yunyuan.searanch.controller;

import com.yunyuan.searanch.dto.MerchantRegisterDTO;
import com.yunyuan.searanch.dto.UserRegisterDTO;
import com.yunyuan.searanch.entity.MerchantRegister;
import com.yunyuan.searanch.entity.User;
import com.yunyuan.searanch.service.UserService;
import com.yunyuan.searanch.utils.MessageCodeUtil;
import com.yunyuan.searanch.utils.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author alan
 * @date 2020/3/18
 */
@Api(value = "用户注册登录",tags = "用户注册登录")
@RestController
public class LoginController {
    private static final Logger LOGGER= LoggerFactory.getLogger(LoginController.class);
    private RedisTemplate redisTemplate;

    private UserService userService;
    private String messageCode;
    private String phone;
    @Autowired
    public LoginController(RedisTemplate redisTemplate,UserService userService){
        this.redisTemplate=redisTemplate;
        this.userService=userService;
    }

    @ApiOperation(value="获取验证码")
    @PostMapping("/getMessageCode")
    public void getMessageCode(String phoneNumber){
        messageCode=new Random().nextInt(999999)+"";
        phone=phoneNumber;
        MessageCodeUtil.senMessage(phone,messageCode);
    }

    @ApiOperation(value ="用户注册",notes = "普通用户注册")
    @PostMapping("/userRegister")
    public ResponseData userRegister(@Validated @RequestBody UserRegisterDTO userRegisterDTO){
        if(null!=userService.getUserByPhone(userRegisterDTO.getPhoneNumber())){
            return new ResponseData(500,"该用户已经注册");
        }
        if (messageCode.equals(userRegisterDTO.getCode()) && userRegisterDTO.getPhoneNumber().equals(phone)) {
            userService.registerUser(userRegisterDTO);
            return ResponseData.ok();
        }else{
            return new ResponseData(500,"验证码错误");
        }
    }
    @ApiOperation(value ="商户注册",notes = "商户注册")
    @PostMapping("/merchantRegister")
    public ResponseData merchantRegister(@Validated @RequestBody MerchantRegisterDTO merchantRegisterDTO){
        if(null!=userService.getMerchantByPhone(merchantRegisterDTO.getMerchantPhone())){
            return new ResponseData(500,"该商户已经注册");
        }

        if(messageCode.equals(merchantRegisterDTO.getCode()) && merchantRegisterDTO.getMerchantPhone().equals(phone)) {
            userService.registerMerchant(merchantRegisterDTO);
            return ResponseData.ok();
        }else{
            return new ResponseData(500,"注册失败，该商户可能已存在");
        }
    }
    @ApiOperation(value = "用户登录",notes = "根据手机号密码登录")
    @PostMapping("/login")
    public ResponseData userLogin(String phoneNumber, String password){
        if(null==userService.getUserByPhone(phoneNumber)){
            return new ResponseData(500,"用户不存在");
        }
        try {
            Map<String,String> map=new HashMap<>(8);
            password=new Md5Hash(password,phoneNumber,3).toString();
            UsernamePasswordToken token=new UsernamePasswordToken(phoneNumber,password);
            Subject subject= SecurityUtils.getSubject();
            String sessionId=(String)subject.getSession().getId();
            subject.login(token);
            User user=(User)redisTemplate.opsForValue().get("loginUser"+phoneNumber);
            userService.updateLoginStatus(user);
            String role=userService.getRole(user.getUserId());
            map.put("token",sessionId);
            map.put("phoneNumber",phoneNumber);
            map.put("nickname",user.getNickname());
            map.put("image",user.getImage());
            map.put("role",role);
            map.put("userId",user.getUserId()+"");
            MerchantRegister merchant=userService.getMerchantByPhone(user.getPhoneNumber());
            if(null!=merchant && "merchant".equals(role)){
                map.put("star",merchant.getStar()+"");
            }else{
                map.put("growth",user.getGrowth()+"");
            }
            return ResponseData.ok().putDataValue(map);
        }catch (Exception e){
            LOGGER.info(e.getMessage());
            return new ResponseData(500,"login failed");
        }
    }
    @ApiOperation(value="密码重置")
    @PostMapping("/resetPwd")
    public ResponseData resetPassword(String password,String phoneNumber,String code){
        if(null==userService.getUserByPhone(phoneNumber)){
            return new ResponseData(500,"该用户尚未注册");
        }
        if(messageCode.equals(code) && phone.equals(phoneNumber)) {
            password=new Md5Hash(password,phoneNumber,3).toString();
            userService.resetPassword(password,phoneNumber);
            return ResponseData.ok();
        }else{
            return new ResponseData(500,"验证码错误");
        }
    }
    @ApiOperation(value = "退出登录")
    @GetMapping("/logout")
    public ResponseData logout(){
        Subject subject= SecurityUtils.getSubject();
        User user=(User)subject.getPrincipal();
        subject.logout();
        redisTemplate.delete("loginUser"+user.getPhoneNumber());
        return ResponseData.ok();
    }
    @GetMapping("/noAuth")
    public ResponseData noAuth(){
        return ResponseData.unauthorized();
    }
}
