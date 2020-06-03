package com.yunyuan.searanch.service.impl;

import com.yunyuan.searanch.dao.*;
import com.yunyuan.searanch.dto.AdminOrderUpdateDTO;
import com.yunyuan.searanch.dto.AdminRegisterDTO;
import com.yunyuan.searanch.entity.*;
import com.yunyuan.searanch.service.AdminService;
import com.yunyuan.searanch.vo.*;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
    @Value("${spring.mail.username}")
    private String from;
    @Resource
    private JavaMailSender mailSender;
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
    private GoodsPushMapper goodsPushMapper;
    @Resource
    private PushMapper pushMapper;
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
            user1.setRole("noadmin");
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
        user.setRole("noadmin");
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
    public List<ProblemVO> getProblems(String role,Byte status) {
        FeedbackExample feedbackExample=new FeedbackExample();
        FeedbackExample.Criteria feedbackCriteria=feedbackExample.createCriteria();
        feedbackCriteria.andUserTypeLike("%"+role+"%");
        feedbackCriteria.andProgressRateEqualTo(status);
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
    public Map<String, Object> adminOrderList(String orderNumber, String phoneNumber, String userName,Boolean deliver) {
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
        if(null!=deliver){
            orderCriteria.andDeliverEqualTo(deliver);
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
    public Map<String,Object> adminMerchantList(Long merchantId, String userName, String merchantName, Boolean check) {
        Map<String,Object> map=new HashMap<>(2);
        MerchantRegisterExample merchantRegisterExample=new MerchantRegisterExample();
        MerchantRegisterExample.Criteria registerCriteria=merchantRegisterExample.createCriteria();
        if(null!=merchantId && merchantId!=0){
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
        merchantRegisterMapper.updateByPrimaryKey(merchantRegister);
        SimpleMailMessage message=new SimpleMailMessage();
        User user=getUserByPhone(merchantRegister.getMerchantPhone());
        String email=user.getEmail();
        String subject="商户审核通知";
        String content =null;
        if(check) {
            content = "恭喜您！成为了海洋牧场的商户，期待更多的合作！";
        }else{
            content="很遗憾！您未能通过审核，请重新上传资料";
        }
        message.setTo(email);
        message.setSubject(subject);
        message.setText(content);
        message.setFrom(from);
        mailSender.send(message);
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andEmailEqualTo(email);
        Long userIdTo = userMapper.selectByExample(userExample).get(0).getUserId();
        Push push = new Push();
        push.setPushFrom(user.getUserId());
        push.setPushTo(userIdTo);
        push.setPushContent(subject + ": " + content);
        push.setPushTime(new Date());
        return pushMapper.insertSelective(push)>0;
    }

    @Override
    public Map<String,Object> adminUserList(String userName,String phoneNumber) {
        Map<String,Object> map=new HashMap<>(2);
        UserExample userExample=new UserExample();
        UserExample.Criteria userCriteria=userExample.createCriteria();
        if(null!=userName && !"".equals(userName.trim())){
            userCriteria.andUsernameLike("%"+userName+"%");
        }
        if(null!=phoneNumber && !"".equals(phoneNumber.trim())){
            userCriteria.andPhoneNumberEqualTo(phoneNumber);
        }
        userCriteria.andRoleEqualTo("user");
        List<User> userList=userMapper.selectByExample(userExample);
        map.put("pageInfo",userList);
        List<AdminUserVO> userVOList=new ArrayList<>();
        for(User user:userList){
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

    @Override
    public boolean pushGoodsToAds(Long goodsId) {
        GoodsPush goodsPush=new GoodsPush();
        goodsPush.setGoodsId(goodsId);
        goodsPush.setPushTime(new Date());
        return goodsPushMapper.insertSelective(goodsPush)>0;
    }

    @Override
    public boolean deliverGoods(Long orderId, String logisticsNo) {
        Order order=orderMapper.selectByPrimaryKey(orderId);
        order.setLogisticsNo(logisticsNo);
        order.setDeliver(true);
        order.setDeliverTime(new Date());
        orderMapper.updateByPrimaryKeySelective(order);

        SimpleMailMessage message=new SimpleMailMessage();
        Long userId=order.getUserId();
        String email=userMapper.selectByPrimaryKey(userId).getEmail();
        String subject="发货通知";
        String content="亲爱的客官!您的"+order.getGoodsName()+"已经发货"+"物流单号为："+logisticsNo;
        message.setTo(email);
        message.setSubject(subject);
        message.setText(content);
        message.setFrom(from);
        mailSender.send(message);
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andEmailEqualTo(email);
        Long userIdTo = userMapper.selectByExample(userExample).get(0).getUserId();
        Push push = new Push();
        push.setPushFrom(userId);
        push.setPushTo(userIdTo);
        push.setPushContent(subject + ": " + content);
        push.setPushTime(new Date());
        return pushMapper.insertSelective(push)>0;
    }
}
