package com.wzh.home.security.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wzh.home.entity.po.UmsUser;
import com.wzh.home.utils.JwtUtil;

/**
 * 自定义登录验证
 *
 * @author weizhuohang
 * @since 2020-10-28
 */
@Slf4j
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * 尝试身份认证(接收并解析用户凭证)
     *
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
        throws AuthenticationException {
        try {
            JSONObject user = new ObjectMapper().readValue(req.getInputStream(), JSONObject.class);
            return super.getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
                user.getString("username"), user.getString("password"), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 认证成功(用户成功登录后，这个方法会被调用，我们在这个方法里生成token)
     * 
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
        Authentication auth) throws IOException, ServletException {
        // builder the token
        String token = null;
        try {

            log.info("principal {}", JSON.toJSONString(auth.getPrincipal()));
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            log.info("authentication {}", JSON.toJSONString(authentication));
            UmsUser userInfo = (UmsUser)auth.getPrincipal();
            log.info("user info is {}", JSON.toJSONString(userInfo));
            token = JwtUtil.generateToken(userInfo.getUsername(), userInfo.getNickName());
            log.info("token {}", token);

            // 登录成功后，返回token到header里面
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().print(JSONObject.toJSON(token));
            response.getWriter().close();
        } catch (Exception e) {
            throw new AuthenticationServiceException("登录出错！", e);
        }
    }

}