package com.yunyuan.searanch.controller;

import com.yunyuan.searanch.entity.User;
import com.yunyuan.searanch.service.PushService;
import com.yunyuan.searanch.utils.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author alan
 * @date 2020/4/23
 */
@Api(value = "推送",tags = "推送")
@RestController
public class PushController {
    private PushService pushService;
    @Autowired
    public PushController(PushService pushService){
        this.pushService=pushService;
    }

    @ApiOperation(value="向挑选的商家推送")
    @PostMapping("/pushToMerchant")
    public ResponseData pushToMerchant(String subject, String content,String... to){
        Subject subject1= SecurityUtils.getSubject();
        User user=(User)subject1.getPrincipal();
        pushService.sendMail(subject,content,user.getUserId(),to);
        return ResponseData.ok();
    }
    @ApiOperation(value="向全部商家推送")
    @PostMapping("/pushToAllMerchant")
    public ResponseData pushToAllMerchant(String subject,String content){
        Subject subject1= SecurityUtils.getSubject();
        User user=(User)subject1.getPrincipal();
        pushService.sendMailAllMerchant(subject,content,user.getUserId());
        return ResponseData.ok();
    }
}
