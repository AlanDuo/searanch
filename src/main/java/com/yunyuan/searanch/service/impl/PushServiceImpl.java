package com.yunyuan.searanch.service.impl;

import com.yunyuan.searanch.dao.MerchantRegisterMapper;
import com.yunyuan.searanch.dao.PushMapper;
import com.yunyuan.searanch.dao.UserMapper;
import com.yunyuan.searanch.entity.*;
import com.yunyuan.searanch.service.PushService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author alan
 * @date 2020/4/23
 */
@Service
public class PushServiceImpl implements PushService {
    @Value("${spring.mail.username}")
    private String from;

    @Resource
    private JavaMailSender mailSender;
    @Resource
    private PushMapper pushMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private MerchantRegisterMapper registerMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendMail(String subject, String content,Long userId,String... tos) {
        SimpleMailMessage message=new SimpleMailMessage();
        for(String to:tos) {
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content);
            message.setFrom(from);
            mailSender.send(message);
            UserExample userExample = new UserExample();
            UserExample.Criteria criteria = userExample.createCriteria();
            criteria.andEmailEqualTo(to);
            Long userIdTo = userMapper.selectByExample(userExample).get(0).getUserId();
            Push push = new Push();
            push.setPushFrom(userId);
            push.setPushTo(userIdTo);
            push.setPushContent(subject + ": " + content);
            push.setPushTime(new Date());
            pushMapper.insertSelective(push);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendMailAllMerchant(String subject, String content, Long userId) {
        MerchantRegisterExample merchantRegisterExample=new MerchantRegisterExample();
        List<MerchantRegister> merchantRegisters= registerMapper.selectByExample(merchantRegisterExample);
        for(MerchantRegister merchantRegister:merchantRegisters){
            UserExample userExample=new UserExample();
            UserExample.Criteria criteria=userExample.createCriteria();
            criteria.andPhoneNumberEqualTo(merchantRegister.getMerchantPhone());
            List<User> users=userMapper.selectByExample(userExample);
            if(users.size()==0){
                continue;
            }
            User user=users.get(0);
            sendMail(subject,content,userId,user.getEmail());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendMailToAllUser(String subject, String content, Long userId) {
        UserExample userExample=new UserExample();
        List<User> userList=userMapper.selectByExample(userExample);
        for(User user:userList){
            sendMail(subject,content,userId,user.getEmail());
        }
    }
}
