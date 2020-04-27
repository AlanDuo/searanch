package com.yunyuan.searanch.service.impl;

import com.yunyuan.searanch.dao.DiscountMapper;
import com.yunyuan.searanch.dao.GoodsMapper;
import com.yunyuan.searanch.dao.GoodsTypeMapper;
import com.yunyuan.searanch.dao.ShopCartMapper;
import com.yunyuan.searanch.dto.ShopCartDTO;
import com.yunyuan.searanch.entity.*;
import com.yunyuan.searanch.service.ShopCartService;
import com.yunyuan.searanch.vo.ShopCartVO;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author alan
 * @date 2020/3/28
 */
@Service
public class ShopCartServiceImpl implements ShopCartService {
    @Resource
    private ShopCartMapper shopCartMapper;
    @Resource
    private GoodsTypeMapper goodsTypeMapper;
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private DiscountMapper discountMapper;

    @Override
    @CacheEvict(value="shopCart",key = "#userId")
    @Transactional(rollbackFor = Exception.class)
    public boolean addShopCart(Long userId,Long goodsId,Long typeId,Integer amount) {
        ShopCart shopCart=new ShopCart();
        shopCart.setUserId(userId);
        shopCart.setGoods(goodsId);
        shopCart.setTypeId(typeId);
        shopCart.setAmount(amount);
        GoodsType goodsType=goodsTypeMapper.selectByPrimaryKey(typeId);
        BigDecimal price;
        if(goodsType!=null) {
            price=goodsType.getPrice();
        }else{
            price=goodsMapper.selectByPrimaryKey(goodsId).getPrice();
        }
        shopCart.setAddPrice(price);
        shopCart.setAddTime(new Date());
        shopCart.setInvalid(true);
        return shopCartMapper.insertSelective(shopCart)>0;
    }

    @Override
    @Cacheable(value="shopCart",key = "#userId")
    public List<ShopCartVO> getShopCart(Long userId) {
        ShopCartExample shopCartExample=new ShopCartExample();
        ShopCartExample.Criteria cartCriteria=shopCartExample.createCriteria();
        cartCriteria.andUserIdEqualTo(userId);
        List<ShopCart> shopCartList=shopCartMapper.selectByExample(shopCartExample);
        List<ShopCartVO> shopCartVOList=new ArrayList<>();
        for(ShopCart shopCart:shopCartList){
            Long goodsId=shopCart.getGoods();
            Long typeId=shopCart.getTypeId();
            ShopCartVO shopCartVO=new ShopCartVO();
            BeanUtils.copyProperties(shopCart,shopCartVO);
            Goods goods=goodsMapper.selectByPrimaryKey(goodsId);
            BeanUtils.copyProperties(goods,shopCartVO);
            GoodsType goodsType=goodsTypeMapper.selectByPrimaryKey(typeId);
            BeanUtils.copyProperties(goodsType,shopCartVO);
            DiscountExample discountExample=new DiscountExample();
            DiscountExample.Criteria discountCriteria=discountExample.createCriteria();
            discountCriteria.andWorkEqualTo(true);
            discountCriteria.andGoodsIdEqualTo(goodsId);
            Discount discount=discountMapper.selectByExample(discountExample).get(0);
            if(null!=discount) {
                shopCartVO.setDiscount(discount.getDiscountDesc());
                if (discount.getDiscountType() == 1 && shopCart.getAddPrice().compareTo(discount.getDiscountTerm())>=0) {
                    //优惠条件为满减
                    shopCartVO.setPrice(shopCart.getAddPrice().subtract(discount.getDiscountAmount()));
                } else if (discount.getDiscountType() == 2 && shopCart.getAddPrice().compareTo(discount.getDiscountTerm())>=0) {
                    //优惠条件为折扣
                   shopCartVO.setPrice(shopCart.getAddPrice().multiply(discount.getDiscountAmount()));
                }
            }
            shopCartVOList.add(shopCartVO);
        }
        return shopCartVOList;
    }

    @Override
    @CacheEvict(value="shopCart",key = "#userId")
    public boolean updateShopCart(Long cartId, Integer amount) {
        ShopCart shopCart=shopCartMapper.selectByPrimaryKey(cartId);
        shopCart.setAmount(amount);
        return shopCartMapper.updateByPrimaryKey(shopCart)>0;
    }

    @Override
    @CacheEvict(value="shopCart",key = "#userId")
    public boolean deleteShopCart(Long cartId) {
        return shopCartMapper.deleteByPrimaryKey(cartId)>0;
    }
}
