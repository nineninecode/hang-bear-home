package com.wzh.home.config.security;

import java.util.Arrays;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.foresealife.iam.client.api.IamServiceFactory;
import com.foresealife.iam.client.filter.security.RoleBasedAclFilter;

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
    private IamServiceFactory iamServiceFactory;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    @Autowired
    private CustomAccessDeniedHandlerImpl customAccessDeniedHandler;

    /**
     * 需要放行的URL
     */
    private static final String[] AUTH_WHITELIST = {"/v2/api-docs-ext", "/v2/api-docs", "/webjars/**",
        "/swagger-resources/**", "/doc.html", "/user-info/get", "hgmc-store-product/publish", "hgmc-store-sku/*",
        // 类目导出
        "/hgmc-front-category/getFirstCategory", "/hgmc-front-category/getSecondCategory/*","/error"
        // other public endpoints of your API may be appended to this array
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 获取iam中配置的白名单
        List<String> urlList = iamServiceFactory.getUnitService().getCasIgnoreUrlFromApi();
        urlList.addAll(Arrays.asList(AUTH_WHITELIST));
        String[] withoutAuthUrlList = new String[urlList.size()];
        urlList.toArray(withoutAuthUrlList);

        http.cors().and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().authorizeRequests()
            // 所有请求需要身份认证
            .antMatchers(withoutAuthUrlList).permitAll().anyRequest().authenticated().and().exceptionHandling()
            // 自定义认证失败处理器
            .authenticationEntryPoint(customAuthenticationEntryPoint).and()
            .addFilter(new JWTLoginFilter(authenticationManager()))
            .addFilter(new JWTAuthenticationFilter(authenticationManager(),customAuthenticationEntryPoint)).logout()
            // 默认注销行为为logout，可以通过下面的方式来修改
            // .logoutUrl("/logout")
            // 设置注销成功后跳转页面，默认是跳转到登录页面;
            .logoutSuccessUrl("/login")
            // .logoutSuccessHandler(customLogoutSuccessHandler)
            .permitAll();
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
        auth.authenticationProvider(new CustomAuthenticationProvider(iamServiceFactory));
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
}