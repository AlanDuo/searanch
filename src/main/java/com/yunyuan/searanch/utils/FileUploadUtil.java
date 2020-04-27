package com.yunyuan.searanch.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件上传工具类
 *
 * @author alan
 * @date 2020/3/19
 */
public class FileUploadUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadUtil.class);
    private static final long FILE_SIZE=(long)1024*1024*5;
    private static final String REAL_FILE="C://image/";
    private static String url="";
    private static List<String> urls=new ArrayList<>();

    private FileUploadUtil(){}

    public static boolean uploadFile(MultipartFile file, HttpServletRequest request){
        if(file.getSize()>FILE_SIZE || file.isEmpty()){
            return false;
        }
        String path=request.getContextPath();
        String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;

        Long time=System.currentTimeMillis();
        String originalFileName=time.toString().substring(time.toString().length()-8,time.toString().length());
        originalFileName=originalFileName.concat("."+ FilenameUtils.getExtension(file.getOriginalFilename()));
        url=basePath+"/static/image/"+originalFileName;
        LOGGER.info(url);
        try{
            FileUtils.copyInputStreamToFile(file.getInputStream(),new File(REAL_FILE,originalFileName));
            return true;
        }catch (Exception e){
            LOGGER.info(e.getMessage());
            return false;
        }

    }
    public static boolean uploadFile(MultipartFile[] files,HttpServletRequest request){
        try {
            for (MultipartFile file : files) {
                uploadFile(file,request);
            }
            urls.add(url);
            return true;
        }catch (Exception e){
            LOGGER.info(e.getMessage());
            return false;
        }
    }
    public static String getUrl(){
        return url;
    }
    public static List<String> getUrls(){
        return urls;
    }
}
