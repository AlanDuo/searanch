package com.yunyuan.searanch.service.impl;

import com.yunyuan.searanch.dao.*;
import com.yunyuan.searanch.dto.AdminRegisterDTO;
import com.yunyuan.searanch.entity.*;
import com.yunyuan.searanch.service.AdminService;
import com.yunyuan.searanch.vo.AdminMerchantListVO;
import com.yunyuan.searanch.vo.AdminOrderListVO;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author alan
 * @date 2020/4/25
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private MerchantRegisterMapper merchantRegisterMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private PermissionMapper permissionMapper;

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
    public boolean adminExist(String phone) {
        User user=getUserByPhone(phone);
        if(user!=null) {
            Role role = roleMapper.selectByPrimaryKey(user.getUserId());
            if("admin".equals(role.getRole()) || "noadmin".equals(role.getRole())){
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean adminRegister(AdminRegisterDTO adminRegisterDTO) {
        User user1=getUserByPhone(adminRegisterDTO.getPhoneNumber());
        if(null!=user1){
            BeanUtils.copyProperties(adminRegisterDTO,user1);
            user1.setPassword(new Md5Hash(adminRegisterDTO.getPassword(),adminRegisterDTO.getPhoneNumber(),3).toString());
            userMapper.updateByPrimaryKeySelective(user1);
            User user2=getUserByPhone(adminRegisterDTO.getPhoneNumber());
            Role role=new Role();
            role.setUserId(user2.getUserId());
            role.setRole("noadmin");
            return roleMapper.updateByPrimaryKeySelective(role)>0;
        }
        User user=new User();
        BeanUtils.copyProperties(adminRegisterDTO,user);
        user.setPassword(new Md5Hash(adminRegisterDTO.getPassword(),adminRegisterDTO.getPhoneNumber(),3).toString());
        user.setRegisterTime(new Date());
        user.setGrowth(0);
        userMapper.insertSelective(user);
        User user2=getUserByPhone(adminRegisterDTO.getPhoneNumber());
        Role role=new Role();
        role.setUserId(user2.getUserId());
        role.setRole("noadmin");
        roleMapper.insertSelective(role);
        Permission permission=new Permission();
        permission.setUserId(user2.getUserId());
        permission.setPermission("user:update,user:select");
        return permissionMapper.insertSelective(permission)>0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean changePassword(String oldPassword,String newPassword, User user) {
        oldPassword=new Md5Hash(oldPassword,user.getPhoneNumber(),3).toString();
        if (oldPassword.equals(user.getPassword())) {
            newPassword=new Md5Hash(newPassword,user.getPhoneNumber(),3).toString();
            user.setPassword(newPassword);
            return userMapper.insertSelective(user)>0;
        }
        return false;
    }

    @Override
    public Map<String, Object> adminOrderList(String orderNumber, String goodsName, String userName, String merchant) {
        OrderExample orderExample=new OrderExample();
        OrderExample.Criteria orderCriteria=orderExample.createCriteria();
        if(null!=orderNumber && !"".equals(orderNumber.trim())){
            orderCriteria.andOrderNumberLike("%"+orderNumber+"%");
        }
        if(null!=goodsName && !"".equals(goodsName.trim())) {
            orderCriteria.andGoodsNameLike("%"+goodsName+"%");
        }
        if(null!=userName && !"".equals(userName.trim())){
            orderCriteria.andUserNameLike("%"+userName+"%");
        }
        if(null!=merchant && !"".equals(merchant.trim())){
            orderCriteria.andMerchantLike("%"+merchant+"%");
        }
        List<Order> orderList=orderMapper.selectByExample(orderExample);
        Map<String,Object> map=new HashMap<>(2);
        map.put("pageInfo",orderList);
        List<AdminOrderListVO> orderListVOs=new ArrayList<>();
        for(Order order:orderList){
            AdminOrderListVO adminOrderListVO=new AdminOrderListVO();
            BeanUtils.copyProperties(order,adminOrderListVO);
            orderListVOs.add(adminOrderListVO);
        }
        map.put("orderListVOs",orderListVOs);
        return map;
    }

    @Override
    public boolean adminUpdateOrder(String orderNumber, Integer amount) {
        OrderExample orderExample=new OrderExample();
        OrderExample.Criteria orderCriteria=orderExample.createCriteria();
        orderCriteria.andOrderNumberEqualTo(orderNumber);
        Order order=orderMapper.selectByExample(orderExample).get(0);
        order.setAmount(amount);
        return orderMapper.insertSelective(order)>0;
    }

    @Override
    public boolean adminDeleteOrder(String orderNumber) {
        OrderExample orderExample=new OrderExample();
        OrderExample.Criteria orderCriteria=orderExample.createCriteria();
        orderCriteria.andOrderNumberEqualTo(orderNumber);
        return orderMapper.deleteByExample(orderExample)>0;
    }

    @Override
    public Map<String,Object> adminMerchantList(Long merchantId, String userName, String merchantName, Boolean check) {
        Map<String,Object> map=new HashMap<>(2);
        MerchantRegisterExample merchantRegisterExample=new MerchantRegisterExample();
        MerchantRegisterExample.Criteria registerCriteria=merchantRegisterExample.createCriteria();
        if(merchantId!=0 && null!=merchantId){
            registerCriteria.andRegistraIdEqualTo(merchantId);
        }
        if(null!=userName && !"".equals(userName.trim())){
            registerCriteria.andUsernameLike("%"+userName+"%");
        }
        if(null!=merchantName && !"".equals(merchantName.trim())){
            registerCriteria.andMerchantNameLike("%"+merchantName+"%");
        }
        if(null!=check){
            registerCriteria.andExamineEqualTo(check);
        }
        List<MerchantRegister> merchantRegisters=merchantRegisterMapper.selectByExample(merchantRegisterExample);
        map.put("pageInfo",merchantRegisters);
        List<AdminMerchantListVO> merchantListVOList=new ArrayList<>();
        for(MerchantRegister merchantRegister:merchantRegisters){
            AdminMerchantListVO adminMerchantVO=new AdminMerchantListVO();
            BeanUtils.copyProperties(merchantRegister,adminMerchantVO);
            merchantListVOList.add(adminMerchantVO);
        }
        map.put("adminMerchantVOs",merchantListVOList);
        return map;
    }

    @Override
    public Map<String, String> adminMerchantInfo(Long registerId) {
        MerchantRegister merchantRegister=merchantRegisterMapper.selectByPrimaryKey(registerId);
        Map<String,String> map=new HashMap<>(2);
        map.put("idCardImag",merchantRegister.getIdCardImag());
        map.put("license",merchantRegister.getLicenseImag());
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean adminCheckMerchant(Long registerId, boolean check) {
        MerchantRegister merchantRegister=merchantRegisterMapper.selectByPrimaryKey(registerId);
        merchantRegister.setExamine(check);
        return merchantRegisterMapper.updateByPrimaryKey(merchantRegister)>0;
    }
}
