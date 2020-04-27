package com.yunyuan.searanch.controller;

import com.yunyuan.searanch.utils.ResponseData;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author alan
 * @date 2020/4/23
 */
@Api(value = "聊天",tags = "聊天")
@RestController
public class ChatManager {

    @PostMapping("/send")
    public ResponseData send(){
        return ResponseData.ok();
    }
}
