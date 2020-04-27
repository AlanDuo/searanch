package com.yunyuan.searanch.service.impl;

import com.yunyuan.searanch.dao.PermissionMapper;
import com.yunyuan.searanch.dao.RoleMapper;
import com.yunyuan.searanch.dao.UserMapper;
import com.yunyuan.searanch.entity.Permission;
import com.yunyuan.searanch.entity.Role;
import com.yunyuan.searanch.entity.User;
import com.yunyuan.searanch.entity.UserExample;
import com.yunyuan.searanch.service.ShiroService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author alan
 * @date 2020/3/16
 */
@Service
public class ShiroServiceImpl implements ShiroService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private RedisTemplate redisTemplate;

    private static final String LOGIN_USER="loginUser";

    @Override
    public User getUser(String phoneNumber) {
        RedisSerializer redisSerializer=new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        User user=(User)redisTemplate.opsForValue().get(LOGIN_USER+phoneNumber);
        if(null==user) {
            synchronized (this) {
                user=(User)redisTemplate.opsForValue().get(LOGIN_USER+phoneNumber);
                if(null==user) {
                    UserExample userExample = new UserExample();
                    UserExample.Criteria criteria = userExample.createCriteria();
                    criteria.andPhoneNumberEqualTo(phoneNumber);
                    user = userMapper.selectByExample(userExample).get(0);
                    redisTemplate.opsForValue().set(LOGIN_USER + phoneNumber, user,1, TimeUnit.HOURS);
                }
            }
        }
        return user;
    }

    @Cacheable("role")
    @Override
    public Role getRole(Long userId) {
        return roleMapper.selectByPrimaryKey(userId);
    }

    @Cacheable("permission")
    @Override
    public Permission getPermission(Long userId) {
        return permissionMapper.selectByPrimaryKey(userId);
    }
}
