package com.yunyuan.searanch.service.impl;

import com.yunyuan.searanch.dao.GoodsMapper;
import com.yunyuan.searanch.dao.OrderMapper;
import com.yunyuan.searanch.entity.Goods;
import com.yunyuan.searanch.entity.Order;
import com.yunyuan.searanch.entity.OrderExample;
import com.yunyuan.searanch.service.DataAnalysisService;
import com.yunyuan.searanch.utils.DateUtil;
import com.yunyuan.searanch.vo.OrderSalesAndQuantityVO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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

    @Override
    public List<Order> getOrdersByTime(Date date1,Date date2){
        OrderExample orderExample=new OrderExample();
        OrderExample.Criteria orderCriteria=orderExample.createCriteria();
        orderCriteria.andFinishTimeGreaterThanOrEqualTo(date1);
        orderCriteria.andFinishTimeLessThan(date2);
        return orderMapper.selectByExample(orderExample);
    }

    @Override
    public int getOrderAmountByTime(Date date1,Date date2){
        OrderExample orderExample=new OrderExample();
        OrderExample.Criteria orderCriteria=orderExample.createCriteria();
        orderCriteria.andFinishTimeGreaterThanOrEqualTo(date1);
        orderCriteria.andFinishTimeLessThan(date2);
        return orderMapper.countByExample(orderExample);
    }

    @Override
    public BigDecimal getSalesVolumeByTime(Date date1, Date date2) {
        BigDecimal volume=BigDecimal.ZERO;
        OrderExample orderExample=new OrderExample();
        OrderExample.Criteria orderCriteria=orderExample.createCriteria();
        orderCriteria.andFinishTimeGreaterThanOrEqualTo(date1);
        orderCriteria.andFinishTimeLessThan(date2);
        List<Order> orderList=orderMapper.selectByExample(orderExample);
        for(Order order:orderList){
            volume=volume.add(order.getPrice().multiply(new BigDecimal(order.getAmount())));
        }
        return volume;
    }

    @Override
    public Map<Integer, OrderSalesAndQuantityVO> monthlySales(Integer year) {
        Map<Integer,OrderSalesAndQuantityVO> map=new HashMap<>(12);
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
            OrderSalesAndQuantityVO salesAndQuantityVO=new OrderSalesAndQuantityVO();
            salesAndQuantityVO.setOrderQuantity(orders.size());
            salesAndQuantityVO.setSalesVolume(count);
            map.put(i,salesAndQuantityVO);
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
    public Map<Integer, Float> quarterOrderRatio(Integer year) {
        Map<Integer,Float> map=new HashMap<>();
        Map<Integer,Integer> quarterMap=new HashMap<>();
        int k=1;
        int sum=0;
        for(int i=1;i<=MONTHS;i=i+3){
            Date date1=DateUtil.parseDate(year,i);
            Date date2=DateUtil.parseDate(year,i+3);
            int orderAmount=getOrderAmountByTime(date1,date2);
            sum+=orderAmount;
            quarterMap.put(k,orderAmount);
            k++;
        }
        DecimalFormat df=new DecimalFormat("0.00");
        for(Map.Entry<Integer,Integer> entry:quarterMap.entrySet()){
            if(sum!=0) {
                float proportion = Float.parseFloat(df.format((float) entry.getValue() / sum));
                map.put(entry.getKey(), proportion);
            }else{
                map.put(entry.getKey(),(float)0);
            }
        }
        return map;
    }

    @Override
    public Map<String, Float> quarterGoodsTypeOrderRatio(Integer year, Integer quarter) {
        Map<String,Float> map=new HashMap<>();
        Map<String,Integer> typeMap=new HashMap<>();
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
            if (typeMap.containsKey(goods.getType())) {
                k=typeMap.get(goods.getType());
                typeMap.put(goods.getType(),k+1);

            }else{
                typeMap.put(goods.getType(),k);
            }
            k=1;
        }
        DecimalFormat df=new DecimalFormat("0.00");
        int len=orderList.size();
        for(Map.Entry<String,Integer> entry:typeMap.entrySet()){
            float proportion=Float.parseFloat(df.format((float)entry.getValue()/len));
            map.put(entry.getKey(),proportion);
        }
        return map;
    }

    @Override
    public Map<String, Float> quarterOrderRatioOfType(Integer year, Integer quarter, String type) {
        Map<String,Float> map=new HashMap<>();
        Map<String,Integer> goodsMap=new HashMap<>();
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
        int thisType=0;
        for(Order order:orderList){
            Goods goods=goodsMapper.selectByPrimaryKey(order.getGoodsId());

            if(!goods.getType().equals(type)){
                continue;
            }
            if(goodsMap.containsKey(goods.getGoodsName())){
                k=goodsMap.get(goods.getGoodsName());
                goodsMap.put(goods.getGoodsName(),k+1);
            }else{
                goodsMap.put(goods.getGoodsName(),k);
            }
            k=1;
            thisType++;
        }
        DecimalFormat df=new DecimalFormat("0.00");
        for(Map.Entry<String,Integer> entry:goodsMap.entrySet()){
            if(thisType!=0) {
                float proportion = Float.parseFloat(df.format((float) entry.getValue() / thisType));
                map.put(entry.getKey(),proportion);
            }else {
                map.put(entry.getKey(), (float)0);
            }
        }
        return map;
    }

    @Override
    public Map<String, BigDecimal> ratioOfProvince(Integer year) {
        Map<String,BigDecimal> map=new HashMap<>();
        Map<String,BigDecimal> provinceMap=new HashMap<>();
        Date date1=DateUtil.parseDate(year);
        Date date2=DateUtil.parseDate(year+1);
        List<Order> orderList=getOrdersByTime(date1,date2);
        BigDecimal thisProvince;
        BigDecimal salesVolume=BigDecimal.ZERO;
        for(Order order:orderList){
            salesVolume=salesVolume.add(order.getPrice().multiply(new BigDecimal(order.getAmount())));
            if (provinceMap.containsKey(order.getProvince())) {
                thisProvince=provinceMap.get(order.getProvince());
                provinceMap.put(order.getProvince(),thisProvince.add(order.getPrice().multiply(new BigDecimal(order.getAmount()))));
            }else{
                provinceMap.put(order.getProvince(),order.getPrice().multiply(new BigDecimal(order.getAmount())));
            }
        }
        for(Map.Entry<String,BigDecimal> entry:provinceMap.entrySet()){
            BigDecimal ratio=entry.getValue().divide(salesVolume,2,BigDecimal.ROUND_HALF_UP);
            map.put(entry.getKey(),ratio);
        }
        return map;
    }

    @Override
    public Map<String, BigDecimal> ratioOfCity(Integer year, String province) {
        Map<String,BigDecimal> map=new HashMap<>();
        Date date1=DateUtil.parseDate(year);
        Date date2=DateUtil.parseDate(year+1);
        OrderExample orderExample=new OrderExample();
        OrderExample.Criteria orderCriteria=orderExample.createCriteria();
        orderCriteria.andFinishTimeGreaterThanOrEqualTo(date1);
        orderCriteria.andFinishTimeLessThan(date2);
        orderCriteria.andProvinceEqualTo(province);
        List<Order> orderList=orderMapper.selectByExample(orderExample);
        Map<String,BigDecimal> cityMap=new HashMap<>();
        BigDecimal salesVolume=BigDecimal.ZERO;
        BigDecimal thisCity;
        for(Order order:orderList){
            salesVolume=salesVolume.add(order.getPrice().multiply(new BigDecimal(order.getAmount())));
            if(cityMap.containsKey(order.getCity())){
                thisCity=map.get(order.getCity());
                cityMap.put(order.getCity(),thisCity.add(order.getPrice().multiply(new BigDecimal(order.getAmount()))));
            }else{
                cityMap.put(order.getCity(),order.getPrice().multiply(new BigDecimal(order.getAmount())));
            }
        }
        for(Map.Entry<String,BigDecimal> entry:cityMap.entrySet()){
            BigDecimal ratio=entry.getValue().divide(salesVolume,2,BigDecimal.ROUND_HALF_UP);
            map.put(entry.getKey(),ratio);
        }
        return map;
    }

    @Override
    public OrderSalesAndQuantityVO citySalesAndQuantity(Integer year,String city) {
        Date date1=DateUtil.parseDate(year);
        Date date2=DateUtil.parseDate(year+1);
        OrderExample orderExample=new OrderExample();
        OrderExample.Criteria orderCriteria=orderExample.createCriteria();
        orderCriteria.andFinishTimeGreaterThanOrEqualTo(date1);
        orderCriteria.andFinishTimeLessThan(date2);
        orderCriteria.andCityEqualTo(city);
        List<Order> orderList=orderMapper.selectByExample(orderExample);
        OrderSalesAndQuantityVO salesAndQuantityVO=new OrderSalesAndQuantityVO();
        salesAndQuantityVO.setOrderQuantity(orderList.size());
        BigDecimal count=BigDecimal.ZERO;
        for(Order order:orderList){
            count=count.add(order.getPrice().multiply(new BigDecimal(order.getAmount())));
        }
        salesAndQuantityVO.setSalesVolume(count);
        return salesAndQuantityVO;
    }

    @Override
    public Map<String, OrderSalesAndQuantityVO> eachProvinceSalesAndQuantity(Integer year) {
        Map<String,OrderSalesAndQuantityVO> map=new HashMap<>();
        Date date1=DateUtil.parseDate(year);
        Date date2=DateUtil.parseDate(year+1);
        List<Order> orderList=getOrdersByTime(date1,date2);
        BigDecimal thisProvinceVolume;
        Integer thisProvinceAmount=0;
        for(Order order:orderList){
            if (map.containsKey(order.getProvince())) {
                thisProvinceVolume=map.get(order.getProvince()).getSalesVolume();
                thisProvinceAmount=map.get(order.getProvince()).getOrderQuantity();
                thisProvinceVolume=thisProvinceVolume.add(order.getPrice().multiply(new BigDecimal(order.getAmount())));
                OrderSalesAndQuantityVO salesAndQuantityVO=new OrderSalesAndQuantityVO(thisProvinceVolume,thisProvinceAmount+1);
                map.put(order.getProvince(),salesAndQuantityVO);
            }else{
                thisProvinceVolume=order.getPrice().multiply(new BigDecimal(order.getAmount()));
                thisProvinceAmount=1;
                OrderSalesAndQuantityVO salesAndQuantityVO=new OrderSalesAndQuantityVO(thisProvinceVolume,thisProvinceAmount);
                map.put(order.getProvince(),salesAndQuantityVO);
            }
        }
        return map;
    }

    @Override
    @Cacheable(value = "citySales")
    public Map<String, OrderSalesAndQuantityVO> cityOfProvinceSalesAndQuantity(String province, Integer year) {
        Map<String,OrderSalesAndQuantityVO> map=new HashMap<>();
        Date date1=DateUtil.parseDate(year);
        Date date2=DateUtil.parseDate(year+1);
        OrderExample orderExample=new OrderExample();
        OrderExample.Criteria orderCriteria=orderExample.createCriteria();
        orderCriteria.andFinishTimeGreaterThanOrEqualTo(date1);
        orderCriteria.andFinishTimeLessThan(date2);
        orderCriteria.andProvinceEqualTo("%"+province+"%");
        List<Order> orderList=orderMapper.selectByExample(orderExample);
        BigDecimal thisCityVolume;
        Integer thisCityAmount=0;
        for(Order order:orderList){
            if (map.containsKey(order.getCity())) {
                thisCityVolume=map.get(order.getCity()).getSalesVolume();
                thisCityAmount=map.get(order.getCity()).getOrderQuantity();
                thisCityVolume=thisCityVolume.add(order.getPrice().multiply(new BigDecimal(order.getAmount())));
                OrderSalesAndQuantityVO salesAndQuantityVO=new OrderSalesAndQuantityVO(thisCityVolume,thisCityAmount+1);
                map.put(order.getCity(),salesAndQuantityVO);
            }else{
                thisCityVolume=order.getPrice().multiply(new BigDecimal(order.getAmount()));
                thisCityAmount=1;
                OrderSalesAndQuantityVO salesAndQuantityVO=new OrderSalesAndQuantityVO(thisCityVolume,thisCityAmount);
                map.put(order.getCity(),salesAndQuantityVO);
            }
        }
        return map;
    }
}
