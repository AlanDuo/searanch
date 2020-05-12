package com.yunyuan.searanch.service.impl;

import com.yunyuan.searanch.dao.*;
import com.yunyuan.searanch.dto.MerchantRegisterDTO;
import com.yunyuan.searanch.dto.UserRegisterDTO;
import com.yunyuan.searanch.dto.UserUpdateDTO;
import com.yunyuan.searanch.entity.*;
import com.yunyuan.searanch.service.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author alan
 * @date 2020/3/18
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private UserMapper userMapper;
    @Resource
    private MerchantRegisterMapper merchantRegisterMapper;
    @Resource
    private FeedbackMapper feedbackMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private PermissionMapper permissionMapper;


    @Override
    public String getRole(Long userId) {
        return roleMapper.selectByPrimaryKey(userId).getRole();
    }

    @Override
    public User getUserByPhone(String phone) {
        UserExample userExample=new UserExample();
        UserExample.Criteria criteria=userExample.createCriteria();
        criteria.andPhoneNumberEqualTo(phone);
        try {
            return userMapper.selectByExample(userExample).get(0);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateLoginStatus(User user) {
        user.setLoginTime(new Date());
        return userMapper.updateByPrimaryKey(user)>0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Cacheable(value = "user",key = "#userId")
    public User getUserById(Long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CachePut(value = "user",key = "#userId")
    public User updateUser(Long userId, UserUpdateDTO userUpdateDTO) {
        User user=userMapper.selectByPrimaryKey(userId);
        String phone=user.getPhoneNumber();
        if(null!=userUpdateDTO.getUsername() && !"".equals(userUpdateDTO.getUsername().trim())){
            user.setUsername(userUpdateDTO.getUsername());
        }
        if(null!=userUpdateDTO.getPassword() && !"".equals(userUpdateDTO.getPassword().trim())){
            user.setPassword(new Md5Hash(userUpdateDTO.getPassword(),user.getPhoneNumber(),3).toString());
        }
        if(null!=userUpdateDTO.getPhoneNumber() && !"".equals(userUpdateDTO.getPhoneNumber())){
            user.setPhoneNumber(userUpdateDTO.getPhoneNumber());
        }
        if(null!=userUpdateDTO.getNickname() && !"".equals(userUpdateDTO.getNickname().trim())){
            user.setNickname(userUpdateDTO.getNickname());
        }
        if(null!=userUpdateDTO.getImage() && !"".equals(userUpdateDTO.getImage())){
            user.setImage(userUpdateDTO.getImage());
        }
        if(null!=userUpdateDTO.getEmail() && !"".equals(userUpdateDTO.getEmail())){
            user.setEmail(userUpdateDTO.getEmail());
        }
        if(userMapper.updateByPrimaryKeySelective(user)>0){
            redisTemplate.delete("loginUser"+phone);
            return user;
        } else{
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean registerUser(UserRegisterDTO userRegisterDTO) {
        User user=new User();
        BeanUtils.copyProperties(userRegisterDTO,user);
        user.setPassword(new Md5Hash(userRegisterDTO.getPassword(),userRegisterDTO.getPhoneNumber(),3).toString());
        user.setRegisterTime(new Date());
        user.setLoginTime(new Date());
        user.setGrowth(0);
        if(null==userRegisterDTO.getNickname() || "".equals(userRegisterDTO.getNickname().trim())) {
            user.setNickname(userRegisterDTO.getPhoneNumber());
        }
        userMapper.insertSelective(user);
        Long userId=getUserByPhone(user.getPhoneNumber()).getUserId();
        Role role=new Role();
        role.setUserId(userId);
        role.setRole("user");
        roleMapper.insert(role);
        Permission permission=new Permission();
        permission.setUserId(userId);
        permission.setPermission("user:update,user:select");
        return permissionMapper.insert(permission)>0;
    }

    @Override
    public MerchantRegister getMerchantByPhone(String phone) {
        MerchantRegisterExample merchantRegisterExample=new MerchantRegisterExample();
        MerchantRegisterExample.Criteria criteria=merchantRegisterExample.createCriteria();
        criteria.andMerchantPhoneEqualTo(phone);
        List<MerchantRegister> merchantRegisters=merchantRegisterMapper.selectByExample(merchantRegisterExample);
        if(merchantRegisters.size()!=0){
            return merchantRegisters.get(0);
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean registerMerchant(MerchantRegisterDTO merchantRegisterDTO) {
        User user=getUserByPhone(merchantRegisterDTO.getMerchantPhone());
        if(null==user){
            User user1=new User();
            BeanUtils.copyProperties(merchantRegisterDTO,user1);
            user1.setPassword(new Md5Hash(merchantRegisterDTO.getPassword()
                    ,merchantRegisterDTO.getMerchantPhone(),3).toString());
            user1.setPhoneNumber(merchantRegisterDTO.getMerchantPhone());
            user1.setNickname(merchantRegisterDTO.getMerchantPhone());
            user1.setRegisterTime(new Date());
            user1.setLoginTime(new Date());
            user1.setGrowth(0);
            userMapper.insertSelective(user1);
            Long userId=getUserByPhone(user1.getPhoneNumber()).getUserId();
            Role role=new Role();
            role.setUserId(userId);
            role.setRole("merchant");
            roleMapper.insert(role);
            Permission permission=new Permission();
            permission.setUserId(userId);
            permission.setPermission("user:update,user:select,goods:apply");
            permissionMapper.insert(permission);
        }else{
            user.setEmail(merchantRegisterDTO.getEmail());
            user.setPassword(new Md5Hash(merchantRegisterDTO.getPassword()
                    ,merchantRegisterDTO.getMerchantPhone(),3).toString());
            user.setImage(merchantRegisterDTO.getImage());
            userMapper.updateByPrimaryKey(user);
            redisTemplate.delete("loginUser"+user.getPhoneNumber());
            Long userId=getUserByPhone(merchantRegisterDTO.getMerchantPhone()).getUserId();
            Role role=roleMapper.selectByPrimaryKey(userId);
            role.setRole("merchant");
            roleMapper.updateByPrimaryKey(role);
            Permission permission=permissionMapper.selectByPrimaryKey(userId);
            permission.setPermission("user:update,user:select,goods:apply");
            permissionMapper.updateByPrimaryKey(permission);
        }
        MerchantRegister merchantRegister=new MerchantRegister();
        BeanUtils.copyProperties(merchantRegisterDTO,merchantRegister);
        merchantRegister.setRegistraTime(new Date());
        merchantRegister.setExamine(false);
        merchantRegister.setStar(0);
        return merchantRegisterMapper.insertSelective(merchantRegister)>0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean feedback(Long userId, String content, String image) {
        Feedback feedback=new Feedback();
        feedback.setFeedbackUser(userId);
        feedback.setFeedbackContent(content);
        feedback.setFeedbackTime(new Date());
        return feedbackMapper.insertSelective(feedback)>0;
    }


}
