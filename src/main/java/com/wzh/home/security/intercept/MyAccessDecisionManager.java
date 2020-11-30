package com.wzh.home.security.intercept;

import lombok.extern.slf4j.Slf4j;

import java.util.Collection;


import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 自定义 security 权限控制类
 * </p>
 *
 * @author weizhuohang
 * @since 2020/11/3 15:27
 */
@Slf4j
@Component
public class MyAccessDecisionManager implements AccessDecisionManager {

    /**
     * 判定是否拥有权限的决策方法
     * 
     * @param authentication
     *            登录验证中循环添加的角色集合.
     * @param object
     *            包含客户端发起的请求的request信息
     * @param configAttributes
     *            权限表
     * @throws AccessDeniedException
     *             if access is denied as the authentication does not hold a required authority or ACL privilege
     * @throws InsufficientAuthenticationException
     *             if access is denied as the authentication does not provide a sufficient level of trust
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
        throws AccessDeniedException, InsufficientAuthenticationException {

        log.info("configAttributes {}", configAttributes);
        if (null == configAttributes || configAttributes.size() <= 0) {
            // 放行
            return;
        }
        for (ConfigAttribute configAttribute : configAttributes) {
            // authentication对象中的权限信息集合
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority ga : authorities) {
                if (configAttribute.getAttribute().trim().equals(ga.getAuthority())) {
                    // 放行
                    return;
                }
            }

        }

        throw new AccessDeniedException("no role!");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
