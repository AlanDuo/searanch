package com.yunyuan.searanch.controller;

import com.yunyuan.searanch.service.DataAnalysisService;
import com.yunyuan.searanch.utils.DateUtil;
import com.yunyuan.searanch.utils.ResponseData;
import com.yunyuan.searanch.vo.OrderSalesAndQuantityVO;
import com.yunyuan.searanch.vo.ProfitVO;
import com.yunyuan.searanch.vo.SalesRankVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author alan
 * @date 2020/5/3
 */
@Api(value = "数据分析",tags = "对数据的分析")
@RestController
public class DataAnalysisController {
    private DataAnalysisService dataAnalysisService;
    @Autowired
    public DataAnalysisController(DataAnalysisService dataAnalysisService){
        this.dataAnalysisService=dataAnalysisService;
    }

    @ApiOperation(value="该年全年全国的销售额与订单总数")
    @GetMapping("/yearSales")
    public ResponseData thisYearSales(@RequestParam(value = "year",defaultValue = "2020")Integer year){
        Date date1= DateUtil.parseDate(year);
        Date date2= DateUtil.parseDate(year+1);
        Integer amount=dataAnalysisService.getOrderAmountByTime(date1,date2);
        BigDecimal volume=dataAnalysisService.getSalesVolumeByTime(date1,date2);
        OrderSalesAndQuantityVO salesAndQuantityVO=new OrderSalesAndQuantityVO(volume,amount);
        return ResponseData.ok().putDataValue(salesAndQuantityVO);
    }
    @ApiOperation(value = "该年每个月的销售额与订单数量")
    @GetMapping("/monthlySales/{year}")
    public ResponseData eachYearSales(@PathVariable("year")Integer year){
        Map<Integer, OrderSalesAndQuantityVO> map=dataAnalysisService.monthlySales(year);
        return ResponseData.ok().putDataValue(map);
    }
    @ApiOperation(value = "该月各类商品的销售额")
    @GetMapping("/monthlyTypeSales/{year}/{month}")
    public ResponseData monthlyGoodsTypeSales(@PathVariable("year") Integer year,@PathVariable("month") Integer month){
        Map<String,BigDecimal> map=dataAnalysisService.monthlyGoodsTypeSales(year,month);
        return ResponseData.ok().putDataValue(map);
    }
    @ApiOperation(value="该类商品所有商品的销售额")
    @GetMapping("/goodsSales/{year}/{month}/{type}")
    public ResponseData goodsSalesOfType(@PathVariable("year") Integer year
            ,@PathVariable("month") Integer month
            ,@PathVariable("type") String type){
        Map<String,BigDecimal> map=dataAnalysisService.goodsSalesOfType(year, month, type);
        return ResponseData.ok().putDataValue(map);
    }
    @ApiOperation(value="该年每个季度销售额占比")
    @GetMapping("/quarterRatio/{year}")
    public ResponseData quarterOrderRatio(@PathVariable("year") Integer year){
        Map<Integer,Float> map=dataAnalysisService.quarterOrderRatio(year);
        return ResponseData.ok().putDataValue(map);
    }
    @ApiOperation(value = "该年该季度各类商品的销售额占比")
    @GetMapping("/goodsTypeRatio/{year}/{quarter}")
    public ResponseData quarterGoodsTypeOrderRatio(@PathVariable("year") Integer year,@PathVariable("quarter")Integer quarter){
        Map<String,Float> map=dataAnalysisService.quarterGoodsTypeOrderRatio(year,quarter);
        return ResponseData.ok().putDataValue(map);
    }
    @ApiOperation(value="该年该季度该类商品的各种商品的销售额占比")
    @GetMapping("/goodsRatio/{year}/{quarter}/{type}")
    public ResponseData quarterOrderRatioOfType(@PathVariable("year") Integer year,
                                                @PathVariable("quarter")Integer quarter,
                                                @PathVariable("type")String type){
        Map<String,Float> map=dataAnalysisService.quarterOrderRatioOfType(year, quarter, type);
        return ResponseData.ok().putDataValue(map);
    }
    @ApiOperation(value = "该年各个省份订单总额占比")
    @GetMapping("/ratioOfProvince/{year}")
    public ResponseData ratioOfProvince(@PathVariable("year")Integer year){
        Map<String,BigDecimal> map=dataAnalysisService.ratioOfProvince(year);
        return ResponseData.ok().putDataValue(map);
    }
    @ApiOperation(value = "该年该省份各个城市订单总额占比")
    @GetMapping("/ratioOfCity/{year}/{province}")
    public ResponseData ratioOfCity(@PathVariable("year")Integer year,@PathVariable("province")String province){
        Map<String,BigDecimal> map=dataAnalysisService.ratioOfCity(year,province);
        return ResponseData.ok().putDataValue(map);
    }
    @ApiOperation(value="该年所有省份的订单总量与销售额")
    @GetMapping("/provinceSales")
    public ResponseData thisYearProvinceSales(@RequestParam(value = "year",defaultValue = "2020")Integer year){
        Map<String,OrderSalesAndQuantityVO> map=dataAnalysisService.eachProvinceSalesAndQuantity(year);
        return ResponseData.ok().putDataValue(map);
    }
    @ApiOperation(value="该年该省份的所有有消费记录的订单总量与销售额")
    @GetMapping("/citysSales/{province}")
    public ResponseData thisProvinceAllCitySales(@PathVariable("province")String province,
                                                 @RequestParam(value = "year",defaultValue = "2020")Integer year){
        Map<String,OrderSalesAndQuantityVO> map=dataAnalysisService.cityOfProvinceSalesAndQuantity(province, year);
        return ResponseData.ok().putDataValue(map);
    }
    @ApiOperation(value = "该年每月的利润")
    @GetMapping("/monthProfit/{year}")
    public ResponseData monthProfit(@PathVariable("year")Integer year){
        Map<Integer, ProfitVO> map=dataAnalysisService.getEachMonthProfit(year);
        return ResponseData.ok().putDataValue(map);
    }
    @ApiOperation(value="月销量排行前十条")
    @GetMapping("/monthlyRank/{year}/{month}")
    public ResponseData monthlySalesRank(@RequestParam(name = "year",defaultValue = "2020") @PathVariable("year")Integer year,
                                         @PathVariable("month")Integer month){
        List<SalesRankVO> salesRankVOS=dataAnalysisService.getMonthlySalesRank(year,month);

        return ResponseData.ok().putDataValue(salesRankVOS);
    }
}
