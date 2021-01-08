package com.wzh.home.security.handler;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.wzh.home.entity.result.BaseResult;

/**
 * <p>
 * 登录失败自定义处理器
 * </p>
 *
 * @author weizhuohang
 * @since 2020/11/19 23:27
 */
@Slf4j
@Component
public class LoginFailHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException exception) throws IOException, ServletException {

        BaseResult<String> fail = BaseResult.fail(exception.getMessage());
        log.info("登录验证失败 {}", exception.getMessage());
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().print(JSONObject.toJSON(fail));
        response.getWriter().close();
    }
}
