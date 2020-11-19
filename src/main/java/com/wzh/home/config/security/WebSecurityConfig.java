package com.wzh.home.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * <p>
 * security web config
 * </p>
 *
 * @author qujunjie
 * @since 2020-10-12
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    @Autowired
    private CustomAccessDeniedHandlerImpl customAccessDeniedHandler;

    /**
     * 需要放行的URL
     */
    private static final String[] AUTH_WHITELIST = {/*"/error"*/};

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable()
            // 不需要session
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            // 访问资源开启身份验证
            .and().authorizeRequests()
            // 放行的资源
            .antMatchers(AUTH_WHITELIST).permitAll()
            // 所有请求需要身份认证
            .anyRequest().authenticated().and()
            // 添加自定义filter
            .addFilter(jwtLoginFilter()).addFilter(jwtAuthenticationFilter())
            // 注册自定义异常处理器
            .exceptionHandling()
            // 自定义认证失败处理器
            .authenticationEntryPoint(customAuthenticationEntryPoint);
    }

    /**
     * 自定义登录验证，该方法是登录的时候会进入
     *
     * @param auth
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 使用自定义身份验证组件
        auth.authenticationProvider(new CustomAuthenticationProvider());
    }

    /**
     * 注册自定义权限filter,IAM的权限过滤器
     * <p>
     * 放行的三种情况1：iam中白名单；2：iam中没有配置的url；3：iam配置且用户已经登录且角色符合
     * <p/>
     *
     * @return RoleBasedAclFilter
     */
    @Bean
    RoleBasedAclFilter roleBasedAclFilter() {
        RoleBasedAclFilter roleBasedAclFilter = new RoleBasedAclFilter();
        return roleBasedAclFilter;
    }

    @Autowired
    private LoginFailHandler loginFailHandler;

    @Bean
    public JwtLoginFilter jwtLoginFilter() throws Exception {
        JwtLoginFilter filter = new JwtLoginFilter();
        filter.setAuthenticationManager(authenticationManager());
        // filter.setAuthenticationSuccessHandler(loginSuccessConfig);
        filter.setAuthenticationFailureHandler(loginFailHandler);
        // filter.setRequiresAuthenticationRequestMatcher(new RegexRequestMatcher("/login", "POST"));
        return filter;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter =
            new JwtAuthenticationFilter(authenticationManager(), customAuthenticationEntryPoint);
        return filter;
    }
}