package com.yunyuan.searanch.controller;

import com.yunyuan.searanch.entity.User;
import com.yunyuan.searanch.service.UserService;
import com.yunyuan.searanch.utils.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author alan
 * @date 2020/4/29
 */
@Api(value="获取websocket的用户信息",tags = "获取websocket的用户信息")
@RestController
public class ChatController {
    public static List<Map<Long,String>> messageUser=new ArrayList<>();
    private UserService userService;
    @Autowired
    public ChatController(UserService userService){
        this.userService=userService;
    }

    @ApiOperation(value = "获取在线管理员")
    @GetMapping("/onlineAdmin")
    public ResponseData getChat(){
        Map<String,ChatWebSocket> map=ChatWebSocket.getAdminClient();
        List<String> keys=new ArrayList<>();
        for(String key:map.keySet()){
            keys.add(key);
        }
        return ResponseData.ok().putDataValue(keys);
    }

    @ApiOperation(value="接收userId")
    @PostMapping("saveUserId/{userId}")
    public ResponseData saveUserId(@PathVariable("userId") Long userId){
        User user=userService.getUserById(userId);
        Map<Long,String> map=new HashMap<>();
        map.put(userId,user.getUsername());
        messageUser.add(map);
        return ResponseData.ok();
    }
    @ApiOperation(value="获取发消息的用户")
    @GetMapping("/messageUsers")
    public ResponseData getMessageUser(){
        return ResponseData.ok().putDataValue(messageUser);
    }
}
