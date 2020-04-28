package com.yunyuan.searanch.controller;

import com.alibaba.fastjson.JSONObject;
import com.yunyuan.searanch.config.websocket.SpringConfigurator;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;

/**
 * @author alan
 * @date 2020/4/23
 */

@ServerEndpoint(value="/websocket/{userId}",configurator = SpringConfigurator.class)
@RestController
@Data
public class ChatController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatController.class);
    private String name;
    private Session session;
    private static Map<String,ChatController> allClient=new HashMap<>();

    @OnOpen
    public void onOpen(@PathParam("userId")String userId, Session session){
        LOGGER.info("Connect success!");
        this.name=userId;
        this.session=session;
        allClient.put(userId,this);
    }
    @OnMessage
    public void onMessage(String message,Session session){
        JSONObject jsonObject=JSONObject.parseObject(message);
        String to=jsonObject.getString("toUser");
        String toMessage=jsonObject.getString("toMessage");
        LOGGER.info(to+":"+toMessage);
        ChatController chat=allClient.get(to);
        if(null!=chat){
            Session toSession=chat.getSession();
            if(toSession.isOpen()){
                toSession.getAsyncRemote().sendText(toMessage);
            }
        }else{
            session.getAsyncRemote().sendText("您好！对方暂时离开，请稍后联系！");
        }
    }
    @OnClose
    public void onClose(){
        LOGGER.info("close");
    }
    @OnError
    public void onError(Session session,Throwable error){
        LOGGER.info("error");
    }

}
