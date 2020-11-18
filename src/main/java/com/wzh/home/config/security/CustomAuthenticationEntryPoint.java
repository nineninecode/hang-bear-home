package com.wzh.home.config.security;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

//import com.bn.boot.base.result.BaseResult;

/**
 * <p>
 * 权限相关错误自定义处理类(401认证相关错误)
 * </p>
 *
 * @author weizhuohang
 * @since 2020/11/9 9:10
 */
@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException, ServletException {
        log.info("权限处理报错 {}", authException.toString());

        //BaseResult<String> result = BaseResult.success(authException.getMessage());
        String result = authException.getMessage();
        log.info("权限处理报错 {}", result);
        // 设置返回值
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter printWriter = response.getWriter();
        printWriter.print(JSONObject.toJSON(result));
        printWriter.flush();
        printWriter.close();
    }
}
