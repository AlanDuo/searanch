package com.yunyuan.searanch.controller;

import com.github.pagehelper.PageInfo;
import com.yunyuan.searanch.entity.Role;
import com.yunyuan.searanch.service.SuperManagerService;
import com.yunyuan.searanch.utils.ResponseData;
import com.yunyuan.searanch.vo.AdminListVO;
import com.yunyuan.searanch.vo.TableVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author alan
 * @date 2020/4/20
 */
@Api(value="超级管理员",tags = "超级管理员特有的权限")
@RestController
@RequestMapping("/superAdmin")
public class SuperManagerController {
    private static final String PAGE_INFO="pageInfo";
    private SuperManagerService superManagerService;
    @Autowired
    public SuperManagerController(SuperManagerService superManagerService){
        this.superManagerService=superManagerService;
    }

    @ApiOperation(value = "管理员列表")
    @ApiImplicitParam(name = "check",value = "管理员状态（true已审核，false未审核）",dataType = "Boolean")
    @GetMapping("/admins")
    @RequiresRoles("superAdmin")
    public TableVO adminList(Boolean check,@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "limit",defaultValue = "10") Integer limit){
        Map<String,Object> map=superManagerService.adminList(check);
        PageInfo pageInfo=new PageInfo<>((List<Role>)map.get(PAGE_INFO));
        return new TableVO<>(pageInfo,(List<AdminListVO>)map.get("adminListVOList"));
    }

    @ApiOperation(value="管理员搜索")
    @ApiImplicitParam(name = "check",value = "管理员状态（true已审核，false未审核）",dataType = "Boolean")
    @GetMapping("/searchAdmin")
    public ResponseData searchAdmin(Boolean check,Long userId,String username,String phoneNumber){
        AdminListVO adminVO=superManagerService.searchAdmin(check, userId, username, phoneNumber);
        return ResponseData.ok().putDataValue(adminVO);
    }

    @ApiOperation(value="审核管理员")
    @PostMapping("/examine")
    public ResponseData examineAdmin(Long userId,Boolean check){
        superManagerService.examineAdmin(userId,check);
        return ResponseData.ok();
    }
    @ApiOperation(value="删除管理员")
    @DeleteMapping("/delete/{userId}")
    public ResponseData deleteAdmin(@PathVariable("userId")Long userId){
        superManagerService.deleteAdmin(userId);
        return ResponseData.ok();
    }
}
