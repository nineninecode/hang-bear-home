package com.wzh.home.config.security;

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
import java.util.Collections;
import java.util.Map;


import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.wzh.home.utils.JwtUtil;

/**
 * 鉴权过滤器
 * 
 * @author chihl
 * @since 2020-10-28
 */
@Slf4j
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        String header = request.getHeader(JwtUtil.AUTH_HEADER);
        if (header == null || !header.startsWith(JwtUtil.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request, response);
        SecurityContextHolder.getContext().setAuthentication(authentication);
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

            // 鉴权时设置用户信息，将用户信息设置到当前线程，结合mybatis plus食用
            // UserInfoBo userInfoBo = new UserInfoBo();
            // userInfoBo.setUserName(resultMap.get("name"));
            // userInfoBo.setUserAccount(user);
            // LoginUtils.set(userInfoBo);
            // log.info("userInfo {}", JSON.toJSONString(userInfoBo));

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
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