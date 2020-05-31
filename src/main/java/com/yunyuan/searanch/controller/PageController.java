package com.yunyuan.searanch.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yunyuan.searanch.entity.Evaluate;
import com.yunyuan.searanch.entity.Goods;
import com.yunyuan.searanch.entity.User;
import com.yunyuan.searanch.service.PageService;
import com.yunyuan.searanch.utils.ResponseData;
import com.yunyuan.searanch.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author alan
 * @date 2020/3/21
 */
@RestController
@RequestMapping("/page")
@Api(value = "商品页面展示",tags = "商品页面展示接口")
public class PageController {
    private static final String PAGE_INFO="pageInfo";
    private PageService pageService;
    @Autowired
    public PageController(PageService pageService){
        this.pageService=pageService;
    }

    @ApiOperation(value = "首页推荐商品接口")
    @GetMapping("/recommendIndex")
    public TableVO recommendIndex(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                  @RequestParam(value = "limit",defaultValue = "10") Integer limit){
        Subject subject= SecurityUtils.getSubject();
        User user=(User)subject.getPrincipal();
        List<PageGoodsVO> pageGoodsVOS;
        Map<String,Object> map;
        if (null==user){
            //用户没有登陆，随便推荐
            map=pageService.recommendWithoutLogin(page,limit);
        }else{
            //用户登录，根据用户的习惯推荐
            map=pageService.recommendWithLogin(page,limit,user.getUserId());
        }
        pageGoodsVOS=(List<PageGoodsVO>)map.get("pageGoodsVOList");
        PageInfo pageInfo=new PageInfo<>((List<Goods>)map.get(PAGE_INFO));
        return new TableVO<>(pageInfo,pageGoodsVOS);
    }
    @ApiOperation(value="单个商品的具体信息")
    @GetMapping("/goodsInfo/{goodsId}")
    public ResponseData goodsInfo(@PathVariable("goodsId") Long goodsId){
        Subject subject= SecurityUtils.getSubject();
        User user=(User)subject.getPrincipal();
        GoodsInfoVO goodsInfoVO=pageService.getGoodsInfo(goodsId,user);
        return ResponseData.ok().putDataValue(goodsInfoVO);
    }
    @ApiOperation(value = "获取评论")
    @GetMapping("/goodsEvaluate/{goodsId}")
    public TableVO goodsEvaluate(@PathVariable("goodsId") Long goodsId,
                                 @RequestParam(value = "page",defaultValue = "1") Integer page,
                                 @RequestParam(value = "limit",defaultValue = "10") Integer limit){
        PageHelper.startPage(page,limit);
        Map<String,Object> map=pageService.evaluateInfo(goodsId);
        PageInfo pageInfo=new PageInfo<>((List< Evaluate>)map.get(PAGE_INFO));
        return new TableVO(pageInfo,(List<EvaluateVO>)map.get("evaluateVOList"));
    }
    @ApiOperation(value="商品搜索")
    @GetMapping("/search/{searchName}")
    public TableVO searchGoods(@RequestParam(value = "page",defaultValue = "1") Integer page,
                               @RequestParam(value = "limit",defaultValue = "10") Integer limit,
                               @PathVariable("searchName") String searchName){
        PageHelper.startPage(page,limit);
        Map<String,Object> map=pageService.searchGoods(searchName);
        PageInfo pageInfo=new PageInfo<>((List<Goods>)map.get(PAGE_INFO));
        return new TableVO(pageInfo,(List<PageGoodsVO>)map.get("pageGoodsVOList"));
    }
    @ApiOperation(value="底部商品推荐")
    @GetMapping("/bRecommend")
    @ApiImplicitParam(name = "goodsId",value = "商品id（根据这类商品推荐）",dataType = "Integer")
    public TableVO bottomRecommend(Integer goodsId,
                                   @RequestParam(value = "page",defaultValue = "1") Integer page,
                                   @RequestParam(value = "limit",defaultValue = "10") Integer limit){
        List<PageGoodsVO> pageGoodsVOS;
        Map<String,Object> map=pageService.bottomRecommend(page,limit,goodsId);
        pageGoodsVOS=(List<PageGoodsVO>)map.get("pageGoodsVOList");
        PageInfo pageInfo=new PageInfo<>((List<Goods>)map.get(PAGE_INFO));
        return new TableVO<>(pageInfo,pageGoodsVOS);
    }
    @ApiOperation(value="首页轮播图")
    @GetMapping("/adsRecommend")
    public ResponseData adsRecommend(){
        List<AdsVO> adsVOList=pageService.adsRecommend();
        return ResponseData.ok().putDataValue(adsVOList);
    }
    @ApiOperation(value = "点赞")
    @PostMapping("/like/{goodsId}")
    public ResponseData likeGoods(@PathVariable("goodsId")Long goodsId){
        Subject subject= SecurityUtils.getSubject();
        User user=(User)subject.getPrincipal();
        if(pageService.getLikeTimes(user.getUserId(),goodsId)>0){
            return new ResponseData(500,"你已经点过赞");
        }
        pageService.likeGoods(user.getUserId(),goodsId);
        return ResponseData.ok();
    }
}
