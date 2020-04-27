package com.yunyuan.searanch.utils;


import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author alan
 * @date 2020/4/15
 */
public class MessageCodeUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageCodeUtil.class);
    private MessageCodeUtil(){}
    public static void senMessage(String phoneNumber,String code){
        String host = "https://feginesms.market.alicloudapi.com";
        String path = "/codeNotice";
        String method = "GET";
        String appCode = "d05b490b8054490d9dcd0bc1868230d3";
        Map<String, String> headers = new HashMap<>(2);
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appCode);
        Map<String, String> querys = new HashMap<>(4);
        querys.put("param", code);
        querys.put("phone", phoneNumber);
        querys.put("sign", "175622");
        querys.put("skin", "1");
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            //System.out.println(response.toString());如不输出json, 请打开这行代码，打印调试头部状态码。
            //状态码: 200 正常；400 URL无效；401 appCode错误； 403 次数用完； 500 API网管错误
            //获取response的body
            String responseBody=EntityUtils.toString(response.getEntity());
            LOGGER.info(responseBody);
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }
    }
}
