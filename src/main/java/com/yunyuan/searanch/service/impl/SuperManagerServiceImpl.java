package com.yunyuan.searanch.service.impl;

import com.yunyuan.searanch.dao.PermissionMapper;
import com.yunyuan.searanch.dao.RoleMapper;
import com.yunyuan.searanch.dao.UserMapper;
import com.yunyuan.searanch.entity.*;
import com.yunyuan.searanch.service.SuperManagerService;
import com.yunyuan.searanch.vo.AdminListVO;
import org.springframework.beans.BeanUtils;
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
    public Map<String, Object> adminList(boolean check) {
        Map<String,Object> map=new HashMap<>(2);
        RoleExample roleExample=new RoleExample();
        RoleExample.Criteria roleCriteria=roleExample.createCriteria();
        if(check) {
            roleCriteria.andRoleEqualTo("admin");
        }else{
            roleCriteria.andRoleEqualTo("noadmin");
        }
        List<Role> roleList=roleMapper.selectByExample(roleExample);
        map.put("pageInfo",roleList);
        List<AdminListVO> adminListVOList=new ArrayList<>();
        for(Role role:roleList){
            User user=userMapper.selectByPrimaryKey(role.getUserId());
            AdminListVO adminVO=new AdminListVO();
            adminVO.setUserId(role.getUserId());
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
    public AdminListVO searchAdmin(boolean check, Long userId, String username, String phoneNumber) {
        UserExample userExample=new UserExample();
        UserExample.Criteria userCriteria=userExample.createCriteria();
        if(userId!=0 && null!=userId){
            userCriteria.andUserIdEqualTo(userId);
        }
        if(null!=username && !"".equals(username.trim())){
            userCriteria.andUsernameEqualTo(username);
        }
        if(null!=phoneNumber && !"".equals(phoneNumber.trim())){
            userCriteria.andPhoneNumberEqualTo(phoneNumber);
        }
        List<User> users=userMapper.selectByExample(userExample);
        if(users.size()!=0){
            User user=users.get(0);
            Role role=roleMapper.selectByPrimaryKey(user.getUserId());
            if("admin".equals(role.getRole()) || "noadmin".equals(role.getRole())) {
                AdminListVO adminVO=new AdminListVO();
                BeanUtils.copyProperties(user,adminVO);
                if(check){
                    adminVO.setCheck(true);
                }else{
                    adminVO.setCheck(false);
                }
                return adminVO;
            }
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean examineAdmin(Long userId, boolean check) {
        Role role=roleMapper.selectByPrimaryKey(userId);
        role.setRole("admin");
        roleMapper.updateByPrimaryKeySelective(role);
        Permission permission=permissionMapper.selectByPrimaryKey(userId);
        permission.setPermission("user:add,user:delete,user:update,user:select");
        return permissionMapper.updateByPrimaryKeySelective(permission)>0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteAdmin(Long userId) {
        userMapper.deleteByPrimaryKey(userId);
        roleMapper.deleteByPrimaryKey(userId);
        return permissionMapper.deleteByPrimaryKey(userId)>0;
    }
}
