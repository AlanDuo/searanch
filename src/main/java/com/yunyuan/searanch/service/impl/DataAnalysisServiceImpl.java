package com.yunyuan.searanch.service.impl;

import com.yunyuan.searanch.dao.GoodsMapper;
import com.yunyuan.searanch.dao.OrderMapper;
import com.yunyuan.searanch.entity.Goods;
import com.yunyuan.searanch.entity.Order;
import com.yunyuan.searanch.entity.OrderExample;
import com.yunyuan.searanch.service.DataAnalysisService;
import com.yunyuan.searanch.utils.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author alan
 * @date 2020/5/3
 */
@Service
public class DataAnalysisServiceImpl implements DataAnalysisService {
    private static final Integer DIVIDE_DAY=1;
    private static final Integer MONTHS=12;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private GoodsMapper goodsMapper;

    /**
     * 根据时间区间查询订单
     *
     * @param date1
     * @param date2
     * @return
     */
    public List<Order> getOrdersByTime(Date date1,Date date2){
        OrderExample orderExample=new OrderExample();
        OrderExample.Criteria orderCriteria=orderExample.createCriteria();
        orderCriteria.andFinishTimeGreaterThanOrEqualTo(date1);
        orderCriteria.andFinishTimeLessThan(date2);
        return orderMapper.selectByExample(orderExample);
    }
    /**
     * 根据时间区间查询订单的数量
     *
     * @param date1
     * @param date2
     * @return
     */
    public int getOrderAmountByTime(Date date1,Date date2){
        OrderExample orderExample=new OrderExample();
        OrderExample.Criteria orderCriteria=orderExample.createCriteria();
        orderCriteria.andFinishTimeGreaterThanOrEqualTo(date1);
        orderCriteria.andFinishTimeLessThan(date2);
        return orderMapper.countByExample(orderExample);
    }
    @Override
    public Map<Integer, BigDecimal> monthlySales(Integer year) {
        Map<Integer,BigDecimal> map=new HashMap<>(12);
        Date dateStart=null;
        Date dateEnd=null;
        for(int i=1;i<=MONTHS;i++){
            BigDecimal count=BigDecimal.ZERO;
            dateStart=DateUtil.parseDate(year,i,DIVIDE_DAY);
            dateEnd=DateUtil.parseDate(year,i+1,DIVIDE_DAY);
            List<Order> orders=getOrdersByTime(dateStart,dateEnd);
            for(Order order:orders){
                count=count.add(order.getPrice().multiply(new BigDecimal(order.getAmount())));
            }
            map.put(i,count);
        }
        return map;
    }

    @Override
    public Map<String, BigDecimal> monthlyGoodsTypeSales(Integer year,Integer month) {
        Map<String,BigDecimal> map=new HashMap<>();
        Date date1= DateUtil.parseDate(year,month);
        Date date2=DateUtil.parseDate(year,month+1);
        List<Order> orderList=getOrdersByTime(date1,date2);
        for(Order order:orderList){
            Goods goods=goodsMapper.selectByPrimaryKey(order.getGoodsId());
            String type=goods.getType();
            if(map.containsKey(type)){
                BigDecimal sum=map.get(type);
                map.put(type,sum.add(order.getPrice().multiply(new BigDecimal(order.getAmount()))));
            }else{
                map.put(type,order.getPrice().multiply(new BigDecimal(order.getAmount())));
            }
        }
        return map;
    }

    @Override
    public Map<String, BigDecimal> goodsSalesOfType(Integer year, Integer month, String type) {
        Map<String,BigDecimal> map=new HashMap<>();
        Date date1= DateUtil.parseDate(year,month);
        Date date2=DateUtil.parseDate(year,month+1);
        List<Order> orderList=getOrdersByTime(date1,date2);
        for(Order order:orderList){
           Goods goods=goodsMapper.selectByPrimaryKey(order.getGoodsId());
           if(!goods.getType().equals(type)){
               continue;
           }
           if(map.containsKey(goods.getGoodsName())){
               BigDecimal sum=map.get(goods.getGoodsName());
               map.put(goods.getGoodsName(),sum.add(order.getPrice().multiply(new BigDecimal(order.getAmount()))));
           }else{
               map.put(goods.getGoodsName(),order.getPrice().multiply(new BigDecimal(order.getAmount())));
           }
        }
        return map;
    }

    @Override
    public Map<Integer, Integer> quarterOrderAmount(Integer year) {
        Map<Integer,Integer> map=new HashMap<>();
        int k=1;
        for(int i=1;i<=MONTHS;i=i+3){
            Date date1=DateUtil.parseDate(year,i);
            Date date2=DateUtil.parseDate(year,i+3);
            int orderAmount=getOrderAmountByTime(date1,date2);
            map.put(k,orderAmount);
            k++;
        }
        return map;
    }

    @Override
    public Map<String, Integer> quarterGoodsTypeOrderAmount(Integer year, Integer quarter) {
        Map<String,Integer> map=new HashMap<>();
        Date date1=null;
        Date date2=null;
        switch (quarter){
            case 1:
                date1=DateUtil.parseDate(year,1);
                date2=DateUtil.parseDate(year,4);
                break;
            case 2:
                date1=DateUtil.parseDate(year,4);
                date2=DateUtil.parseDate(year,7);
                break;
            case 3:
                date1=DateUtil.parseDate(year,7);
                date2=DateUtil.parseDate(year,10);
                break;
            case 4:
                date1=DateUtil.parseDate(year,10);
                date2=DateUtil.parseDate(year,13);
                break;
            default:return null;
        }
        List<Order> orderList=getOrdersByTime(date1,date2);
        int k=1;
        for(Order order:orderList){
            Goods goods=goodsMapper.selectByPrimaryKey(order.getGoodsId());
            if (map.containsKey(goods.getType())) {
                k=map.get(goods.getType());
                map.put(goods.getType(),k+1);

            }else{
                map.put(goods.getType(),k);
            }
            k=1;
        }
        return map;
    }

    @Override
    public Map<String, Integer> quarterOrderAmountOfType(Integer year, Integer quarter, String type) {
        Map<String,Integer> map=new HashMap<>();
        Date date1=null;
        Date date2=null;
        switch (quarter){
            case 1:
                date1=DateUtil.parseDate(year,1);
                date2=DateUtil.parseDate(year,4);
                break;
            case 2:
                date1=DateUtil.parseDate(year,4);
                date2=DateUtil.parseDate(year,7);
                break;
            case 3:
                date1=DateUtil.parseDate(year,7);
                date2=DateUtil.parseDate(year,10);
                break;
            case 4:
                date1=DateUtil.parseDate(year,10);
                date2=DateUtil.parseDate(year,13);
                break;
            default:return null;
        }
        List<Order> orderList=getOrdersByTime(date1,date2);
        int k=1;
        for(Order order:orderList){
            Goods goods=goodsMapper.selectByPrimaryKey(order.getGoodsId());
            if(!goods.getType().equals(type)){
                continue;
            }
            if(map.containsKey(goods.getGoodsName())){
                k=map.get(goods.getGoodsName());
                map.put(goods.getGoodsName(),k+1);
            }else{
                map.put(goods.getGoodsName(),k);
            }
            k=1;
        }
        return map;
    }
}
