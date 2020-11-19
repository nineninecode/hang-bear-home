package com.wzh.home.config.security;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;


import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 自定义登录验证
 * 
 * @author chihl
 * @since 2020-10-28
 */
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

    /**
     * 执行与以下合同相同的身份验证 {@link AuthenticationManager＃authenticate（Authentication）} 。
     *
     * @返回包含凭证的经过完全认证的对象。 可能会回来 <code> null </ code>（如果<code> AuthenticationProvider </ code>无法支持） 对传递的<code>
     * Authentication </ code>对象的身份验证。 在这种情况下， 支持所提供的下一个<code> AuthenticationProvider </ code> 将尝试<code> Authentication
     * </ code>类。
     *
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取认证的用户名 & 密码
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        if (StringUtils.isBlank(name)) {
            throw new UsernameNotFoundException("用户名不能为空");
        }

        if (StringUtils.isBlank(password)) {
            throw new AuthenticationCredentialsNotFoundException("密码不能为空");
        }

        // IAM 认证
        log.debug("IAM response {}", "");

        if (true) {
            throw new BadCredentialsException("response.getMsg()");
        } else {
            try {
                // 获取IAM信息用户信息
                log.debug("login IamUserInfo is {}", "user");
            } catch (Exception e) {
                throw new LockedException("IAM 异常");
            }

            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("", null));
            Authentication auth = new UsernamePasswordAuthenticationToken(name, password, Collections.emptyList());
            return auth;
        }

    }

    /**
     * 是否可以提供输入类型的认证服务
     * 
     * @param authentication
     *            认证类
     * @return 布尔值
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}