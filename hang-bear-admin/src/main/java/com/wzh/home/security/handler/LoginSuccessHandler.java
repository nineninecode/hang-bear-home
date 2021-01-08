package com.wzh.home.security.handler;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wzh.home.entity.bo.SecurityUser;
import com.wzh.home.entity.result.BaseResult;
import com.wzh.home.utils.JwtUtil;

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

    /**
     * AbstractAuthenticationProcessingFilter#successfulAuthentication回调用successHandler
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {
        log.info("LoginSuccessHandler {}", authentication);
        // builder the token
        String token = null;
        try {

            log.info("principal {}", JSON.toJSONString(authentication.getPrincipal()));
            Authentication authenticationContext = SecurityContextHolder.getContext().getAuthentication();
            log.info("authenticationContext {}", JSON.toJSONString(authenticationContext));
            SecurityUser userInfo = (SecurityUser)authentication.getPrincipal();
            log.info("user info is {}", JSON.toJSONString(userInfo));
            token = JwtUtil.generateToken(userInfo.getUsername(), userInfo.getNickName());
            log.info("token {}", token);
            BaseResult<String> result = BaseResult.success(token);

            // 登录成功后，返回token到header里面
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().print(JSONObject.toJSON(result));
            response.getWriter().close();
        } catch (Exception e) {
            throw new AuthenticationServiceException("登录出错！", e);
        }
    }
}
