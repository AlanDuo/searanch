package com.yunyuan.searanch.config.shiro;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author alan
 * @date 2020/2/22
 */
public class CorsAuthenticationFilter extends FormAuthenticationFilter {
    private static final String OPTIONS="OPTIONS";
    public CorsAuthenticationFilter(){
        super();
    }
    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue){
        if((request instanceof HttpServletRequest)&&((HttpServletRequest)request).getMethod().toUpperCase().equals(OPTIONS)){
                return true;
        }
        return super.isAccessAllowed(request,response,mappedValue);
    }
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse res=(HttpServletResponse)response;
        res.setHeader("Access-Control-Allow-Origin","*");
        res.setStatus(HttpServletResponse.SC_OK);
        res.setCharacterEncoding("UTF-8");
        PrintWriter writer=res.getWriter();
        Map<String,Object> map=new HashMap<>(3);
        map.put("code",401);
        map.put("msg","please login");
        writer.write(JSON.toJSONString(map));
        writer.close();
        return false;
    }
}
