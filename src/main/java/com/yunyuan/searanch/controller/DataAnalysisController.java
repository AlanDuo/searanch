package com.yunyuan.searanch.controller;

import com.yunyuan.searanch.service.DataAnalysisService;
import com.yunyuan.searanch.utils.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
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
    @ApiOperation(value = "该年每个月的销售额")
    @GetMapping("/monthlySales/{year}")
    public ResponseData eachYearSales(@PathVariable("year")Integer year){
        Map<Integer, BigDecimal> map=dataAnalysisService.monthlySales(year);
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
    @ApiOperation(value = "该年各个省份订单总额占比")
    @GetMapping("/ratioOfCity/{year}/{province}")
    public ResponseData ratioOfCity(@PathVariable("year")Integer year,@PathVariable("province")String province){
        Map<String,BigDecimal> map=dataAnalysisService.ratioOfCity(year,province);
        return ResponseData.ok().putDataValue(map);
    }
}
