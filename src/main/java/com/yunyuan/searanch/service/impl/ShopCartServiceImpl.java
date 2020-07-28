package com.yunyuan.searanch.service.impl;

import com.yunyuan.searanch.dao.DiscountMapper;
import com.yunyuan.searanch.dao.GoodsMapper;
import com.yunyuan.searanch.dao.GoodsTypeMapper;
import com.yunyuan.searanch.dao.ShopCartMapper;
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
    public boolean addShopCart(Long userId,Long goodsId,Integer amount) {
        ShopCartExample shopCartExample=new ShopCartExample();
        ShopCartExample.Criteria shopCartCriteria=shopCartExample.createCriteria();
        shopCartCriteria.andUserIdEqualTo(userId);
        shopCartCriteria.andGoodsEqualTo(goodsId);
        List<ShopCart> shopCartList=shopCartMapper.selectByExample(shopCartExample);
        if(shopCartList.size()==0) {
            ShopCart shopCart = new ShopCart();
            shopCart.setUserId(userId);
            shopCart.setGoods(goodsId);
            shopCart.setAmount(amount);
            BigDecimal price= goodsMapper.selectByPrimaryKey(goodsId).getPrice();
            shopCart.setAddPrice(price);
            shopCart.setAddTime(new Date());
            shopCart.setInvalid(true);
            shopCart.setTypeId(0L);
            return shopCartMapper.insertSelective(shopCart) > 0;
        }
        ShopCart shopCart=shopCartList.get(0);
        shopCart.setAmount(shopCart.getAmount()+amount);
        return shopCartMapper.updateByPrimaryKeySelective(shopCart)>0;
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
            if(null==goods){
                continue;
            }
            BeanUtils.copyProperties(goods,shopCartVO);
            shopCartVO.setDesc(goods.getGoodsDesc());

            GoodsType goodsType=goodsTypeMapper.selectByPrimaryKey(typeId);
            if(null!=goodsType) {
                BeanUtils.copyProperties(goodsType, shopCartVO);
            }

            DiscountExample discountExample=new DiscountExample();
            DiscountExample.Criteria discountCriteria=discountExample.createCriteria();
            discountCriteria.andWorkEqualTo(true);
            discountCriteria.andGoodsIdEqualTo(goodsId);
            List<Discount> discountList=discountMapper.selectByExample(discountExample);
            if(discountList.size()!=0) {
                Discount discount=discountList.get(0);
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
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value="shopCart",key = "#userId")
    public boolean updateShopCart(Long cartId, Integer amount,Long userId) {
        ShopCart shopCart=shopCartMapper.selectByPrimaryKey(cartId);
        shopCart.setAmount(amount);
        return shopCartMapper.updateByPrimaryKey(shopCart)>0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value="shopCart",key = "#userId")
    public boolean deleteShopCart(Long cartId,Long userId) {
        return shopCartMapper.deleteByPrimaryKey(cartId)>0;
    }
}
