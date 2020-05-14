package com.yunyuan.searanch.service.impl;

import com.yunyuan.searanch.dao.GoodsApplyMapper;
import com.yunyuan.searanch.dao.GoodsMapper;
import com.yunyuan.searanch.dao.MerchantRegisterMapper;
import com.yunyuan.searanch.dao.OrderMapper;
import com.yunyuan.searanch.entity.*;
import com.yunyuan.searanch.service.GoodsManagerService;
import com.yunyuan.searanch.vo.AdminGoodsVO;
import com.yunyuan.searanch.vo.GoodsAppliedVO;
import com.yunyuan.searanch.vo.GoodsApplyVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author alan
 * @date 2020/4/3
 */
@Service
public class GoodsManagerServiceImpl implements GoodsManagerService {
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private GoodsApplyMapper goodsApplyMapper;
    @Resource
    private MerchantRegisterMapper merchantMapper;
    @Resource
    private OrderMapper orderMapper;

    @Override
    public Map<String, Object> goodsWaitToTake() {
        Map<String,Object> map=new HashMap<>(2);
        GoodsApplyExample goodsApplyExample=new GoodsApplyExample();
        GoodsApplyExample.Criteria applyCriteria=goodsApplyExample.createCriteria();
        applyCriteria.andIsTakeEqualTo(false);
        applyCriteria.andFinishedEqualTo(false);
        List<GoodsApply> applyList=goodsApplyMapper.selectByExample(goodsApplyExample);
        map.put("pageInfo",applyList);
        List<GoodsApplyVO> applyVOList=new ArrayList<>();
        for(GoodsApply apply:applyList){
            GoodsApplyVO applyVO=new GoodsApplyVO();
            BeanUtils.copyProperties(apply,applyVO);
            MerchantRegister merchant=merchantMapper.selectByPrimaryKey(apply.getMerchantId());
            applyVO.setMerchant(merchant.getMerchantName());
            applyVOList.add(applyVO);
        }
        map.put("applyVOList",applyVOList);
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean sureTakeGoods(Long applyId, boolean isTake,String remarks,Long handler) {
        GoodsApply goodsApply=goodsApplyMapper.selectByPrimaryKey(applyId);
        goodsApply.setIsTake(isTake);
        goodsApply.setFinished(true);
        goodsApply.setFinishTime(new Date());
        if(null!=remarks && !"".equals(remarks.trim())){
            goodsApply.setRemarks(remarks);
        }
        goodsApply.setHandler(handler);
        goodsApply.setPublish(false);
        return goodsApplyMapper.updateByPrimaryKeySelective(goodsApply)>0;
    }

    @Override
    public Map<String, Object> getGoodsApplied() {
        Map<String,Object> map=new HashMap<>(2);
        GoodsApplyExample goodsApplyExample=new GoodsApplyExample();
        GoodsApplyExample.Criteria applyCriteria=goodsApplyExample.createCriteria();
        applyCriteria.andIsTakeEqualTo(true);
        applyCriteria.andPublishEqualTo(false);
        List<GoodsApply> applyList=goodsApplyMapper.selectByExample(goodsApplyExample);
        map.put("pageInfo",applyList);
        List<GoodsAppliedVO> applyVOList=new ArrayList<>();
        for(GoodsApply apply:applyList){
            GoodsAppliedVO applyVO=new GoodsAppliedVO();
            BeanUtils.copyProperties(apply,applyVO);
            MerchantRegister merchant=merchantMapper.selectByPrimaryKey(apply.getMerchantId());
            applyVO.setMerchant(merchant.getMerchantName());
            applyVOList.add(applyVO);
        }
        map.put("applyVOList",applyVOList);
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean publishGoods(Long applyId, BigDecimal price) {
        GoodsApply goodsApply=goodsApplyMapper.selectByPrimaryKey(applyId);
        goodsApply.setPrice(price.multiply(BigDecimal.valueOf(0.6)));
        goodsApplyMapper.updateByPrimaryKeySelective(goodsApply);
        Goods goods=new Goods();
        BeanUtils.copyProperties(goodsApply,goods);
        goods.setType(goodsApply.getGoodsType());
        goods.setProcessMode("鲜活");
        goods.setPrice(price);
        goods.setUpShelf(true);
        goods.setUpTime(new Date());
        goods.setStock(goodsApply.getAmount());
        goods.setBusiness(goodsApply.getMerchantId());
        return goodsMapper.insertSelective(goods)>0;
    }

    @Override
    public Map<String, Object> getPublishedGoods(String goodsName) {
        Map<String,Object> map=new HashMap<>(2);
        GoodsExample goodsExample=new GoodsExample();
        GoodsExample.Criteria goodsCriteria=goodsExample.createCriteria();
        if(null!=goodsName && !"".equals(goodsName)) {
            goodsCriteria.andGoodsNameLike("%" + goodsName + "%");
        }
        List<Goods> goodsList=goodsMapper.selectByExample(goodsExample);
        map.put("pageInfo",goodsList);
        List<AdminGoodsVO> goodsVOList=new ArrayList<>();
        for(Goods goods:goodsList){
            AdminGoodsVO goodsVO=new AdminGoodsVO();
            BeanUtils.copyProperties(goods,goodsVO);
            OrderExample orderExample=new OrderExample();
            OrderExample.Criteria orderCriteria=orderExample.createCriteria();
            orderCriteria.andGoodsIdEqualTo(goods.getGoodsId());
            List<Order> orders=orderMapper.selectByExample(orderExample);
            int count=0;
            for(Order order:orders){
                count+=order.getAmount();
            }
            goodsVO.setSalesAmount(count);
            goodsVOList.add(goodsVO);
        }
        map.put("goodsVOList",goodsVOList);
        return map;
    }
}
