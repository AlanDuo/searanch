package com.yunyuan.searanch.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yunyuan.searanch.entity.Goods;
import com.yunyuan.searanch.entity.GoodsApply;
import com.yunyuan.searanch.entity.User;
import com.yunyuan.searanch.service.GoodsManagerService;
import com.yunyuan.searanch.utils.ResponseData;
import com.yunyuan.searanch.vo.AdminGoodsVO;
import com.yunyuan.searanch.vo.GoodsAppliedVO;
import com.yunyuan.searanch.vo.GoodsApplyVO;
import com.yunyuan.searanch.vo.TableVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author alan
 * @date 2020/4/3
 */
@Api(value = "商品管理",tags = "管理员商品管理")
@RestController
@RequestMapping("/goods/manager")
public class GoodsManagerController {
    private static final String PAGE_INFO="pageInfo";
    private GoodsManagerService goodsManagerService;
    @Autowired
    public GoodsManagerController(GoodsManagerService goodsManagerService){
        this.goodsManagerService=goodsManagerService;
    }

    @ApiOperation(value = "商品申请列表")
    @GetMapping("/applyList")
    public TableVO goodsApplyList(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                  @RequestParam(value = "limit",defaultValue = "10") Integer limit){
        PageHelper.startPage(page,limit);
        Map<String,Object> map=goodsManagerService.goodsWaitToTake();
        List<GoodsApply> goodsApplies=(List<GoodsApply>) map.get(PAGE_INFO);
        PageInfo pageInfo=new PageInfo<>(goodsApplies);
        return new TableVO(pageInfo,(List<GoodsApplyVO>)map.get("applyVOList"));
    }
    @ApiOperation(value="确认收购商品")
    @ApiImplicitParam(name = "isTake",value = "是否收购商品（true或者false）",dataType = "Boolean")
    @PostMapping("/takeGoods")
    @RequiresRoles("admin")
    public ResponseData takeGoods(Long applyId,Boolean isTake,String remarks){
        Subject subject= SecurityUtils.getSubject();
        User user=(User)subject.getPrincipal();
        goodsManagerService.sureTakeGoods(applyId,isTake,remarks,user.getUserId());
        return ResponseData.ok();
    }
    @ApiOperation(value="已收购商品列表")
    @GetMapping("/goodsApplied")
    public TableVO goodsAppliedList(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                    @RequestParam(value = "limit",defaultValue = "10") Integer limit) {
        PageHelper.startPage(page, limit);
        Map<String,Object> map=goodsManagerService.getGoodsApplied();
        List<GoodsApply> goodsApplies=(List<GoodsApply>) map.get(PAGE_INFO);
        PageInfo pageInfo=new PageInfo<>(goodsApplies);
        return new TableVO(pageInfo,(List< GoodsAppliedVO>)map.get("applyVOList"));
    }
    @ApiOperation(value="发布商品")
    @PostMapping("/publish")
    @RequiresRoles("admin")
    public ResponseData publishGoods(Long applyId, BigDecimal price){
        goodsManagerService.publishGoods(applyId,price);
        return ResponseData.ok();
    }
    @ApiOperation(value = "已经发布商品列表")
    @GetMapping("/published")
    public TableVO publishedGoods(String goodsName,
                                  @RequestParam(value = "page",defaultValue = "1") Integer page,
                                  @RequestParam(value = "limit",defaultValue = "10") Integer limit) {
        PageHelper.startPage(page, limit);
        Map<String,Object> map=goodsManagerService.getPublishedGoods(goodsName);
        List<Goods> goodsList=(List<Goods>)map.get(PAGE_INFO);
        PageInfo pageInfo=new PageInfo<>(goodsList);
        return new TableVO<>(pageInfo,(List<AdminGoodsVO>)map.get("goodsVOList"));
    }
}
