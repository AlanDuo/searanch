package com.yunyuan.searanch.config.shiro;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * @author alan
 * @date 2020/2/20
 */
public class CustomSessionManager extends DefaultWebSessionManager {
    private static final String AUTHORIZATION="Authorization";
    private static final String REFERENCED_SESSION_ID_SOURCE="Stateless request";

    public CustomSessionManager(){
        super();
        setGlobalSessionTimeout(DEFAULT_GLOBAL_SESSION_TIMEOUT);
    }
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response){
        String sessionId= WebUtils.toHttp(request).getHeader(AUTHORIZATION);
        if(StringUtils.isEmpty(sessionId)){
            return super.getSessionId(request,response);
        }
        else{
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID,sessionId);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID,Boolean.TRUE);
            return sessionId;
        }
    }
}
