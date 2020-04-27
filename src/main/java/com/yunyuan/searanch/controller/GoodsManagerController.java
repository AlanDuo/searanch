package com.yunyuan.searanch.controller;

import com.yunyuan.searanch.dto.GoodsAddDTO;
import com.yunyuan.searanch.service.GoodsManagerService;
import com.yunyuan.searanch.utils.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author alan
 * @date 2020/4/3
 */
@Api(value = "商品管理",tags = "商品管理接口")
@RestController
@RequestMapping("/goods/manager")
public class GoodsManagerController {
    private GoodsManagerService goodsManagerService;
    @Autowired
    public GoodsManagerController(GoodsManagerService goodsManagerService){
        this.goodsManagerService=goodsManagerService;
    }

    @ApiOperation(value="增加商品")
    @PostMapping("/addGoods")
    public ResponseData addGoods(GoodsAddDTO goodsAddDTO, MultipartFile file, HttpServletRequest request){
        goodsManagerService.addGoods(goodsAddDTO,file,request);
        return ResponseData.ok();
    }

    @ApiOperation(value="更新商品")
    @PostMapping("/updateGoods")
    public ResponseData update(){

        return ResponseData.ok();
    }
    @ApiOperation(value="下架商品")
    @PostMapping("/downShelf")
    public ResponseData downShelfGoods(){

        return ResponseData.ok();
    }
    @ApiOperation(value="查询商品")
    @GetMapping("/searchGoods")
    public ResponseData searchGoods(){

        return ResponseData.ok();
    }
}
