package com.yunyuan.searanch.service.impl;

import com.yunyuan.searanch.dao.*;
import com.yunyuan.searanch.dto.AdminOrderUpdateDTO;
import com.yunyuan.searanch.dto.AdminRegisterDTO;
import com.yunyuan.searanch.entity.*;
import com.yunyuan.searanch.service.AdminService;
import com.yunyuan.searanch.vo.*;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
    @Resource
    private FeedbackMapper feedbackMapper;
    @Resource
    private EvaluateMapper evaluateMapper;
    @Resource
    private RedisTemplate redisTemplate;

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
            redisTemplate.delete("loginUser"+user.getPhoneNumber());
            return userMapper.insertSelective(user)>0;
        }
        return false;
    }

    @Override
    public List<ProblemVO> getProblems(String role) {
        FeedbackExample feedbackExample=new FeedbackExample();
        FeedbackExample.Criteria feedbackCriteria=feedbackExample.createCriteria();
        feedbackCriteria.andUserTypeLike("%"+role+"%");
        List<Feedback> feedbackList=feedbackMapper.selectByExample(feedbackExample);
        List<ProblemVO> problemVOList=new ArrayList<>();
        for(Feedback feedback:feedbackList){
            ProblemVO problemVO=new ProblemVO();
            BeanUtils.copyProperties(feedback,problemVO);
            User user=userMapper.selectByPrimaryKey(feedback.getFeedbackUser());
            problemVO.setFeedbackUserId(feedback.getFeedbackUser());
            problemVO.setFeedbackUser(user.getUsername());
            problemVO.setEmail(user.getEmail());
            problemVOList.add(problemVO);
        }
        return problemVOList;
    }

    @Override
    public boolean dealWithProblem(Long feedbackId, String content, Long userId) {
        Feedback feedback=feedbackMapper.selectByPrimaryKey(feedbackId);
        Byte progressRate=2;
        feedback.setProgressRate(progressRate);
        feedback.setResponse(content);
        feedback.setHandler(userId);
        feedback.setHandlerTime(new Date());
        return feedbackMapper.updateByPrimaryKeySelective(feedback)>0;
    }

    @Override
    public ProblemDetailVO problemDetails(Long feedbackId) {
        Feedback feedback=feedbackMapper.selectByPrimaryKey(feedbackId);
        ProblemDetailVO detailVO=new ProblemDetailVO();
        BeanUtils.copyProperties(feedback,detailVO);
        User handler=userMapper.selectByPrimaryKey(feedback.getHandler());
        detailVO.setHandler(handler.getUsername());
        if(feedback.getProgressRate()==2){
            detailVO.setProgressRate(true);
        }else{
            detailVO.setProgressRate(false);
        }
        return detailVO;
    }

    @Override
    public Map<String, Object> adminOrderList(String orderNumber, String phoneNumber, String userName) {
        OrderExample orderExample=new OrderExample();
        OrderExample.Criteria orderCriteria=orderExample.createCriteria();
        if(null!=orderNumber && !"".equals(orderNumber.trim())){
            orderCriteria.andOrderNumberLike("%"+orderNumber+"%");
        }
        if(null!=userName && !"".equals(userName.trim())){
            orderCriteria.andUserNameLike("%"+userName+"%");
        }
        if(null!=phoneNumber && !"".equals(phoneNumber.trim())){
            orderCriteria.andPhoneEqualTo(phoneNumber);
        }
        List<Order> orderList=orderMapper.selectByExample(orderExample);
        Map<String,Object> map=new HashMap<>(2);
        map.put("pageInfo",orderList);
        List<AdminOrderListVO> orderListVOs=new ArrayList<>();
        for(Order order:orderList){
            AdminOrderListVO adminOrderListVO=new AdminOrderListVO();
            BeanUtils.copyProperties(order,adminOrderListVO);
            adminOrderListVO.setTotalPrice(order.getPrice().multiply(new BigDecimal(order.getAmount())));
            orderListVOs.add(adminOrderListVO);
        }
        map.put("orderListVOs",orderListVOs);
        return map;
    }

    @Override
    public AdminOrderInfoVO adminOrderInfo(String orderNumber) {
        OrderExample orderExample=new OrderExample();
        OrderExample.Criteria orderCriteria=orderExample.createCriteria();
        orderCriteria.andOrderNumberEqualTo(orderNumber);
        Order order=orderMapper.selectByExample(orderExample).get(0);
        AdminOrderInfoVO orderInfoVO=new AdminOrderInfoVO();
        BeanUtils.copyProperties(order,orderInfoVO);
        EvaluateExample evaluateExample=new EvaluateExample();
        EvaluateExample.Criteria evaluateCriteria=evaluateExample.createCriteria();
        evaluateCriteria.andOrderIdEqualTo(order.getOrderId());
        List<Evaluate> evaluateList=evaluateMapper.selectByExample(evaluateExample);
        if(evaluateList.size()!=0){
            orderInfoVO.setEvaluate(evaluateList.get(0).getGoodsEva());
        }
        return orderInfoVO;
    }

    @Override
    public boolean adminUpdateOrder(AdminOrderUpdateDTO orderUpdateDTO) {
        OrderExample orderExample=new OrderExample();
        OrderExample.Criteria orderCriteria=orderExample.createCriteria();
        orderCriteria.andOrderNumberEqualTo(orderUpdateDTO.getOrderNumber());
        Order order=orderMapper.selectByExample(orderExample).get(0);
        if(null!=orderUpdateDTO.getPhone() && !"".equals(orderUpdateDTO.getPhone().trim())){
            order.setPhone(orderUpdateDTO.getPhone());
        }
        if(null!=orderUpdateDTO.getAmount() && orderUpdateDTO.getAmount()!=0){
            order.setAmount(orderUpdateDTO.getAmount());
        }
        if(null!=orderUpdateDTO.getCountry() && !"".equals(orderUpdateDTO.getCountry().trim())){
            order.setCountry(orderUpdateDTO.getCountry());
        }
        if(null!=orderUpdateDTO.getProvince() && !"".equals(orderUpdateDTO.getProvince().trim())){
            order.setProvince(orderUpdateDTO.getProvince());
        }
        if(null!=orderUpdateDTO.getCity() && !"".equals(orderUpdateDTO.getCity().trim())){
            order.setCity(orderUpdateDTO.getCity());
        }
        if(null!=orderUpdateDTO.getAddress() && !"".equals(orderUpdateDTO.getAddress().trim())){
            order.setAddress(orderUpdateDTO.getAddress());
        }
        if(null!=orderUpdateDTO.getLogisticsNo() && !"".equals(orderUpdateDTO.getLogisticsNo().trim())){
            order.setLogisticsNo(orderUpdateDTO.getLogisticsNo());
        }
        return orderMapper.updateByPrimaryKeySelective(order)>0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean adminDeleteOrder(String orderNumber) {
        OrderExample orderExample=new OrderExample();
        OrderExample.Criteria orderCriteria=orderExample.createCriteria();
        orderCriteria.andOrderNumberEqualTo(orderNumber);
        return orderMapper.deleteByExample(orderExample)>0;
    }

    @Override
    public Map<String,Object> adminMerchantList(Long merchantId, String userName, String phoneNumber, Boolean check) {
        Map<String,Object> map=new HashMap<>(2);
        MerchantRegisterExample merchantRegisterExample=new MerchantRegisterExample();
        MerchantRegisterExample.Criteria registerCriteria=merchantRegisterExample.createCriteria();
        if(merchantId!=0 && null!=merchantId){
            registerCriteria.andRegistraIdEqualTo(merchantId);
        }
        if(null!=userName && !"".equals(userName.trim())){
            registerCriteria.andUsernameLike("%"+userName+"%");
        }
        if(null!=phoneNumber && !"".equals(phoneNumber.trim())){
            registerCriteria.andMerchantPhoneEqualTo(phoneNumber);
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

    @Override
    public Map<String,Object> adminUserList() {
        Map<String,Object> map=new HashMap<>(2);
        RoleExample roleExample=new RoleExample();
        RoleExample.Criteria roleCriteria=roleExample.createCriteria();
        roleCriteria.andRoleEqualTo("user");
        List<Role> roleList=roleMapper.selectByExample(roleExample);
        map.put("pageInfo",roleList);
        List<AdminUserVO> userVOList=new ArrayList<>();
        for(Role role:roleList){
            User user=userMapper.selectByPrimaryKey(role.getUserId());
            AdminUserVO userVO=new AdminUserVO();
            BeanUtils.copyProperties(user,userVO);
            userVOList.add(userVO);
        }
        map.put("userVOList",userVOList);
        return map;
    }

    @Override
    public List<UserConsumeVO> userConsumeRecord(Long userId) {
        OrderExample orderExample=new OrderExample();
        OrderExample.Criteria orderCriteria=orderExample.createCriteria();
        orderCriteria.andUserIdEqualTo(userId);
        List<Order> orderList=orderMapper.selectByExample(orderExample);
        List<UserConsumeVO> consumeVOList=new ArrayList<>();
        for(Order order:orderList){
            UserConsumeVO consumeVO=new UserConsumeVO();
            BeanUtils.copyProperties(order,consumeVO);
            EvaluateExample evaluateExample=new EvaluateExample();
            EvaluateExample.Criteria evaluateCriteria=evaluateExample.createCriteria();
            evaluateCriteria.andOrderIdEqualTo(order.getOrderId());
            List<Evaluate> evaluateList=evaluateMapper.selectByExample(evaluateExample);
            if(evaluateList.size()!=0){
                consumeVO.setEvaluate(evaluateList.get(0).getGoodsEva());
            }
            consumeVOList.add(consumeVO);
        }
        return consumeVOList;
    }
}
