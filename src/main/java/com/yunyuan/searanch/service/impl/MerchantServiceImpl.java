package com.yunyuan.searanch.service.impl;

import com.yunyuan.searanch.dao.*;
import com.yunyuan.searanch.dto.GoodsApplyDTO;
import com.yunyuan.searanch.entity.*;
import com.yunyuan.searanch.service.MerchantService;
import com.yunyuan.searanch.vo.BillVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author alan
 * @date 2020/4/30
 */
@Service
public class MerchantServiceImpl implements MerchantService {
    @Resource
    private MerchantRegisterMapper merchantRegisterMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private GoodsApplyMapper goodsApplyMapper;

    @Override
    public MerchantRegister getMerchantIdByUserId(Long userId) {
        String phone=userMapper.selectByPrimaryKey(userId).getPhoneNumber();
        MerchantRegisterExample merchantExample=new MerchantRegisterExample();
        MerchantRegisterExample.Criteria merchantCriteria=merchantExample.createCriteria();
        merchantCriteria.andMerchantPhoneEqualTo(phone);
        List<MerchantRegister> merchantRegisters=merchantRegisterMapper.selectByExample(merchantExample);
        if(merchantRegisters.size()!=0){
            return merchantRegisters.get(0);
        }
        return null;
    }

    @Override
    public Map<String, Object> getBill(Long merchantId, Date date) {
        Map<String,Object> map=new HashMap<>(2);
        GoodsApplyExample goodsApplyExample=new GoodsApplyExample();
        GoodsApplyExample.Criteria applyCriteria=goodsApplyExample.createCriteria();
        applyCriteria.andMerchantIdEqualTo(merchantId);
        if(null!=date){
            applyCriteria.andApplyTimeEqualTo(date);
        }
        List<GoodsApply> applyList=goodsApplyMapper.selectByExample(goodsApplyExample);
        map.put("pageInfo",applyList);
        List<BillVO> billVOList=new ArrayList<>();
        for(GoodsApply apply:applyList){
            BillVO billVO=new BillVO();
            BeanUtils.copyProperties(apply,billVO);
            if(null==apply.getPrice()){
                billVO.setPrice(BigDecimal.ZERO);
            }
            if(apply.getFinished() && null!=apply.getPrice()){
                billVO.setIncome(apply.getPrice().multiply(new BigDecimal(apply.getAmount())));
            }else{
                billVO.setIncome(BigDecimal.ZERO);
            }
            billVOList.add(billVO);
        }
        map.put("billVOList",billVOList);
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean putInStorage(Long merchant, GoodsApplyDTO goodsApplyDTO) {
        GoodsApply goodsApply=new GoodsApply();
        BeanUtils.copyProperties(goodsApplyDTO,goodsApply);
        goodsApply.setMerchantId(merchant);
        goodsApply.setApplyTime(new Date());
        goodsApply.setFinished(false);
        goodsApply.setIsTake(false);
        goodsApply.setPublish(false);
        return goodsApplyMapper.insertSelective(goodsApply)>0;
    }
}
