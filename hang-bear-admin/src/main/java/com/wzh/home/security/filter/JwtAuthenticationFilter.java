package com.wzh.home.security.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.wzh.home.entity.po.UmsUser;
import com.wzh.home.utils.JwtUtil;

/**
 * 鉴权过滤器
 * 
 * @author weizhuohang
 * @since 2020-10-28
 */
@Slf4j
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
        AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        String header = request.getHeader(JwtUtil.AUTH_HEADER);
        if (header == null || !header.startsWith(JwtUtil.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        try {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(request, response);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (AuthenticationException failed) {
            SecurityContextHolder.clearContext();
            log.debug("Authentication request for failed!", failed);
            onUnsuccessfulAuthentication(request, response, failed);
            super.getAuthenticationEntryPoint().commence(request, response, failed);
            return;
        }
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {
        long start = System.currentTimeMillis();
        String token = request.getHeader(JwtUtil.AUTH_HEADER);
        if (StringUtils.isBlank(token)) {
            throw new AuthenticationServiceException("Token为空");
        }
        String user = null;
        try {

            Map<String, String> resultMap = JwtUtil.parseAndRefreshToken(token);
            String refreshToken = resultMap.get("token");
            if (StringUtils.isNotBlank(refreshToken)) {
                // 主动刷新token，并返回给前端
                response.addHeader("refreshToken", refreshToken);
            }

            long end = System.currentTimeMillis();
            log.info("执行时间: {}", (end - start) + " 毫秒");
            user = resultMap.get("account");
            log.info("user account: {}", user);

            // 获取用户角色列表，放入authentication中
            UmsUser loginUser = new UmsUser();
            loginUser.setNickName("韦卓航");
            loginUser.setUsername("wzh");
            loginUser.setPassword("123456");

            // 获取用户的角色信息
            List<String> roles = new ArrayList<>();
            roles.add("R_ADMIN");

            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            for (String role : roles) {
                if (role != null) {
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
                    // 此处将权限信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象。
                    grantedAuthorities.add(grantedAuthority);
                }
            }

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(loginUser, null, grantedAuthorities);
            }
        } catch (ExpiredJwtException e) {
            throw new AuthenticationServiceException("Token已过期", e);
        } catch (UnsupportedJwtException e) {
            throw new AuthenticationServiceException("Token格式错误", e);
        } catch (MalformedJwtException e) {
            throw new AuthenticationServiceException("Token没有被正确构造", e);
        } catch (SignatureException e) {
            throw new AuthenticationServiceException("签名失败", e);
        } catch (IllegalArgumentException e) {
            throw new AuthenticationServiceException("非法参数异常", e);
        }

        return null;
    }

}