package com.yunyuan.searanch.controller;

import com.yunyuan.searanch.service.SuperManagerService;
import com.yunyuan.searanch.utils.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author alan
 * @date 2020/4/20
 */
@Api(value="超级管理员",tags = "超级管理员特有的权限")
@RestController
@RequestMapping("/superAdmin")
public class SuperManagerController {
    private SuperManagerService superManagerService;
    @Autowired
    public SuperManagerController(SuperManagerService superManagerService){
        this.superManagerService=superManagerService;
    }

    @ApiOperation(value = "添加管理员")
    @PostMapping("/addAdmin")
    public ResponseData addAdmin(){
        superManagerService.addAdmin();
        return ResponseData.ok();
    }
}
