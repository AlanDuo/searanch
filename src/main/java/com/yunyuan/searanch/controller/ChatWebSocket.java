package com.yunyuan.searanch.controller;

import com.alibaba.fastjson.JSONObject;
import com.yunyuan.searanch.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@ServerEndpoint(value="/websocket/{userId}")
public class ChatWebSocket {
    private static ChatService chatService;
    @Autowired
    public void  setChatService(ChatService chatService){
        ChatWebSocket.chatService=chatService;
    }
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatWebSocket.class);

    private String userId;
    private Session session;
    private static Map<String, ChatWebSocket> merchantClient=new HashMap<>();
    private static Map<String, ChatWebSocket> adminClient=new HashMap<>();

    @OnOpen
    public void onOpen(@PathParam("userId")String userId, Session session){
        LOGGER.info("Connect success!");
        this.userId=userId;
        this.session=session;
        if(chatService.isAdmin(Long.parseLong(userId))){
            adminClient.put(userId,this);
        }else{
            merchantClient.put(userId,this);
        }
    }
    @OnMessage
    public void onMessage(String message,Session session){
        JSONObject jsonObject=JSONObject.parseObject(message);
        String to=jsonObject.getString("toUser");
        String toMessage=jsonObject.getString("toMessage");
        ChatWebSocket chat;
        if(chatService.isAdmin(Long.parseLong(to))){
            chat=adminClient.get(to);
        }else{
            chat=merchantClient.get(to);
        }
        if(null!=chat){
            Session toSession=chat.session;
            if(toSession.isOpen()){
                toSession.getAsyncRemote().sendText(this.userId+":"+toMessage);
                chatService.addChatRecord(Long.parseLong(this.userId),Long.parseLong(to),toMessage);
            }
        }else{
            session.getAsyncRemote().sendText("您好！对方不在线，请稍后联系！");
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


    public static Map<String, ChatWebSocket> getAdminClient() {
        return adminClient;
    }
}
