package com.yunyuan.searanch.service.impl;

import com.yunyuan.searanch.dao.*;
import com.yunyuan.searanch.dto.GoodsApplyDTO;
import com.yunyuan.searanch.entity.*;
import com.yunyuan.searanch.service.MerchantService;
import com.yunyuan.searanch.vo.BillVO;
import com.yunyuan.searanch.vo.StockVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
    private GoodsMapper goodsMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private MerchantBillMapper billMapper;
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
    public Map<String,Object> getStockDetails(Long merchantId,String goodsName) {
        GoodsExample goodsExample=new GoodsExample();
        GoodsExample.Criteria goodsCriteria=goodsExample.createCriteria();
        goodsCriteria.andBusinessEqualTo(merchantId);
        if(goodsName!=null && "".equals(goodsName.trim())){
            goodsCriteria.andGoodsNameLike("%"+goodsName+"%");
        }
        List<Goods> goodsList=goodsMapper.selectByExample(goodsExample);
        Map<String,Object> map=new HashMap<>(2);
        map.put("pageInfo",goodsList);
        List<StockVO> stockVOList=new ArrayList<>();
        for(Goods goods:goodsList){
            StockVO stockVO=new StockVO();
            BeanUtils.copyProperties(goods,stockVO);
            stockVOList.add(stockVO);
        }
        map.put("stockVOList",stockVOList);
        return map;
    }

    @Override
    public Map<String, Object> getBill(Long merchantId, Date date) {
        MerchantBillExample billExample=new MerchantBillExample();
        MerchantBillExample.Criteria billCriteria=billExample.createCriteria();
        billCriteria.andMerchantIdEqualTo(merchantId);
        if(null!=date){
            billCriteria.andRecordDateEqualTo(date);
        }
        List<MerchantBill> merchantBills=billMapper.selectByExample(billExample);
        Map<String,Object> map=new HashMap<>(2);
        map.put("pageInfo",merchantBills);
        Collections.sort(merchantBills,((o1, o2) -> o1.getRecordDate().compareTo(o2.getRecordDate())));
        List<BillVO> billVOList=new ArrayList<>();
        for(MerchantBill bill:merchantBills){
            Goods goods=goodsMapper.selectByPrimaryKey(bill.getGoodsId());
            BillVO billVO=new BillVO();
            BeanUtils.copyProperties(billVO,goods);
            BeanUtils.copyProperties(bill,billVO);
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
        return goodsApplyMapper.insertSelective(goodsApply)>0;
    }
}
