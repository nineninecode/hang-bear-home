package com.wzh.home.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.wzh.home.constant.JwtConstantKey;

/**
 * <p>
 * Jwt工具类
 * </p>
 *
 * @author weizhuohang
 * @since 2020/11/5 14:48
 */
@Slf4j
public class JwtUtil {

    /**
     * 认证头key
     */
    public static final String AUTH_HEADER = "Authorization";

    /**
     * token 前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 刷新频率
     */
    public static final Integer REFRESH_RATE = 2;

    /**
     * token 有效时间(minute)
     */
    public static final Integer VALID_MIN = 90;

    /**
     * token 姓名 key
     */
    public static final String CLAIM_NAME = "name";

    /**
     * token 账号 key
     */
    public static final String CLAIM_ACCOUNT = "username";

    /**
     * 根据账户信息生成token
     * 
     * @param username
     *            账户
     * @param name
     *            名称
     * @return token
     */
    public static String generateToken(String username, String name) {

        String token;
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        // 设置签发时间
        calendar.setTime(new Date());
        // 设置过期时间
        calendar.add(Calendar.MINUTE, VALID_MIN);
        Date time = calendar.getTime();
        token = Jwts.builder().setSubject(username)
            // 签发时间
            .setIssuedAt(now)
            // 过期时间
            .setExpiration(time).claim(CLAIM_NAME, name).claim(CLAIM_ACCOUNT, username)
            .signWith(SignatureAlgorithm.HS512, JwtConstantKey.SIGNING_KEY).compact();
        return token;
    }

    /**
     * 分析并刷新token，返回新token（不刷新为null）和账户
     * 
     * @param token
     *            原来token
     * @return 结果map
     */
    public static Map<String, String> parseAndRefreshToken(String token) {
        Map<String, String> resultMap = new HashMap<>(8);
        String refreshToken = null;
        Claims claims = Jwts.parser().setSigningKey(JwtConstantKey.SIGNING_KEY)
            .parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();
        // token签发时间
        long issuedAt = claims.getIssuedAt().getTime();
        // 当前时间
        long currentTimeMillis = System.currentTimeMillis();
        // token过期时间
        long expirationTime = claims.getExpiration().getTime();
        String userName = claims.get(CLAIM_NAME, String.class);
        String userAccount = claims.get(CLAIM_ACCOUNT, String.class);

        // 1. 签发时间 < 当前时间 < (签发时间+((token过期时间-token签发时间)/2)) 不刷新token
        // 2. (签发时间+((token过期时间-token签发时间)/2)) < 当前时间 < token过期时间 刷新token并返回给前端
        // 3. token过期时间 < 当前时间 跳转登录，重新登录获取token
        // 验证token时间有效性
        if ((issuedAt + ((expirationTime - issuedAt) / REFRESH_RATE)) < currentTimeMillis
            && currentTimeMillis < expirationTime) {

            // 重新生成token start
            Calendar calendar = Calendar.getInstance();
            Date now = calendar.getTime();
            // 设置签发时间
            calendar.setTime(new Date());
            // 设置过期时间
            calendar.add(Calendar.MINUTE, VALID_MIN);
            Date time = calendar.getTime();
            refreshToken = Jwts.builder().setSubject(userAccount)
                // 签发时间
                .setIssuedAt(now)
                // 过期时间
                .setExpiration(time).claim(CLAIM_NAME, userName).claim(CLAIM_ACCOUNT, userAccount)
                .signWith(SignatureAlgorithm.HS512, JwtConstantKey.SIGNING_KEY).compact();

        }
        resultMap.put("name", userName);
        resultMap.put("account", userAccount);
        resultMap.put("token", refreshToken);
        return resultMap;
    }

}
