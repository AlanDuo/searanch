package com.yunyuan.searanch.service.impl;

import com.yunyuan.searanch.dao.PermissionMapper;
import com.yunyuan.searanch.dao.RoleMapper;
import com.yunyuan.searanch.dao.UserMapper;
import com.yunyuan.searanch.entity.*;
import com.yunyuan.searanch.service.SuperManagerService;
import com.yunyuan.searanch.vo.AdminListVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author alan
 * @date 2020/4/21
 */
@Service
public class SuperManagerServiceImpl implements SuperManagerService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public Map<String, Object> adminList(boolean check,Long userId,String username,String phoneNumber) {
        Map<String,Object> map=new HashMap<>(2);
        UserExample userExample=new UserExample();
        UserExample.Criteria userCriteria=userExample.createCriteria();
        if(check){
            userCriteria.andRoleEqualTo("admin");
        }else{
            userCriteria.andRoleEqualTo("noadmin");
        }
        if(null!=userId && userId!=0){
            userCriteria.andUserIdEqualTo(userId);
        }
        if(null!=username && !"".equals(username.trim())){
            userCriteria.andUsernameLike("%"+username+"%");
        }
        if(null!=phoneNumber && !"".equals(phoneNumber.trim())){
            userCriteria.andPhoneNumberLike("%"+phoneNumber+"%");
        }
        List<User> users=userMapper.selectByExample(userExample);
        map.put("pageInfo",users);
        List<AdminListVO> adminListVOList=new ArrayList<>();
        for(User user:users){
            AdminListVO adminVO=new AdminListVO();
            adminVO.setUserId(user.getUserId());
            adminVO.setUsername(user.getUsername());
            adminVO.setPhoneNumber(user.getPhoneNumber());
            if(check) {
                adminVO.setCheck(true);
            }else{
                adminVO.setCheck(false);
            }
            adminListVOList.add(adminVO);
        }
        map.put("adminListVOList",adminListVOList);
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean examineAdmin(Long userId, boolean check) {
        Role role=roleMapper.selectByPrimaryKey(userId);
        User user=userMapper.selectByPrimaryKey(userId);
        Permission permission=permissionMapper.selectByPrimaryKey(userId);
        if(check) {
            role.setRole("admin");
            user.setRole("admin");
            permission.setPermission("user:add,user:delete,user:update,user:select");
        }else{
            role.setRole("noadmin");
            user.setRole("noadmin");
            permission.setPermission("user:update,user:select");
        }
        roleMapper.updateByPrimaryKeySelective(role);
        permissionMapper.updateByPrimaryKeySelective(permission);
        return userMapper.updateByPrimaryKeySelective(user)>0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteAdmin(Long userId) {
        userMapper.deleteByPrimaryKey(userId);
        roleMapper.deleteByPrimaryKey(userId);
        return permissionMapper.deleteByPrimaryKey(userId)>0;
    }
}
