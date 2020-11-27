package com.wzh.home.config.security;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 登录成功处理器
 * </p>
 *
 * @author weizhuohang
 * @since 2020/11/19 23:25
 */
@Slf4j
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {
        // 认证成功之后这个方法取出来就是你的UserDetailsService返回的那个UserDetails
        // User user = (User)authentication.getPrincipal();
        // try {
        // String token = JwtUtil.genToken(user);
        // response.addHeader(tokenHeader, tokenHead + " " + token);
        // ResponseUtil.requestSuccess("登录成功", response);
        // } catch (UnsupportedEncodingException e) {
        // throw new IOException(e.getCause());
        // }
    }
}
