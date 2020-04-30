package com.yunyuan.searanch.service.impl;

import com.yunyuan.searanch.dao.MessageMapper;
import com.yunyuan.searanch.dao.RoleMapper;
import com.yunyuan.searanch.entity.Message;
import com.yunyuan.searanch.service.ChatService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author alan
 * @date 2020/4/28
 */
@Service
public class ChatServiceImpl implements ChatService {
    @Resource
    private MessageMapper messageMapper;
    @Resource
    private RoleMapper roleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addChatRecord(Long fromId, Long toId, String content) {
        Message message=new Message();
        message.setFromId(fromId);
        message.setToId(toId);
        message.setContent(content);
        message.setFormat("txt");
        message.setSendTime(new Date());
        return messageMapper.insertSelective(message)>0;
    }

    @Override
    public boolean isAdmin(Long userId) {
        String role=roleMapper.selectByPrimaryKey(userId).getRole();
        if("admin".equals(role)){
            return true;
        }
        return false;
    }
}
