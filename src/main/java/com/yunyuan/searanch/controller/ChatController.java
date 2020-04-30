package com.yunyuan.searanch.controller;

import com.yunyuan.searanch.utils.ResponseData;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author alan
 * @date 2020/4/29
 */
@Api(value="获取客服",tags = "获取客服")
@RestController
public class ChatController {
    @GetMapping("/onlineAdmin")
    public ResponseData getChat(){
        Map<String,ChatWebSocket> map=ChatWebSocket.getAdminClient();
        List<String> keys=new ArrayList<>();
        for(String key:map.keySet()){
            keys.add(key);
        }
        return ResponseData.ok().putDataValue(keys);
    }
}
