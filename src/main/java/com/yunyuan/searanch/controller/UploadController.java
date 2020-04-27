package com.yunyuan.searanch.controller;

import com.yunyuan.searanch.utils.FileUploadUtil;
import com.yunyuan.searanch.utils.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author alan
 * @date 2020/4/27
 */
@Api(value = "文件上传",tags = "文件（图片）上传")
@RestController
@RequestMapping("/upload")
public class UploadController {
    @ApiOperation(value = "上传图片")
    @PostMapping("/uploadImag")
    public ResponseData uploadImage(MultipartFile image, HttpServletRequest request){
        FileUploadUtil.uploadFile(image,request);
        String url=FileUploadUtil.getUrl();
        return ResponseData.ok().putDataValue(url);
    }
}
