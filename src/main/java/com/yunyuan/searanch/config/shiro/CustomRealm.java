package com.yunyuan.searanch.config.shiro;

import com.yunyuan.searanch.entity.User;
import com.yunyuan.searanch.service.ShiroService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author alan
 * @date 2020/2/20
 */
@Component
public class CustomRealm extends AuthorizingRealm {
    private ShiroService shiroService;
    @Autowired
    @Lazy
    public CustomRealm(ShiroService shiroService){
        this.shiroService=shiroService;
    }
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomRealm.class);

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) throws AuthorizationException {
        LOGGER.info("执行了授权----》doGetAuthorizationInfo");
        User user=(User)principalCollection.getPrimaryPrincipal();
        Long userId=user.getUserId();
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        info.addRole(shiroService.getRole(userId).getRole());
        Set<String> permission=new HashSet<>(Arrays.asList(shiroService.getPermission(userId).getPermission().split(",")));
        info.addStringPermissions(permission);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        LOGGER.info("执行了认证----》doGetAuthenticationInfo");
        UsernamePasswordToken token=(UsernamePasswordToken)authenticationToken;
        String phoneNumber=token.getUsername();
        if(null==phoneNumber || "".equals(phoneNumber)){
            throw new AuthenticationException("token invalid!");
        }
        User user=shiroService.getUser(phoneNumber);
        String password=new String(token.getPassword());
        if(null!=user && user.getPassword().equals(password)) {
            return new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
        }
        return null;
    }
}
