//package com.wzh.home.config.security;
//
//import org.springframework.beans.factory.annotation.Configurable;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
///**
// * <p>
// * web security config
// * </p>
// *
// * @author weizhuohang
// * @since 2020/11/12 23:16
// */
//@Configurable
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//            // 表单方式
//            .formLogin().loginPage("/login").and()
//            // 授权配置
//            .authorizeRequests()
//            // 所有请求
//            .anyRequest()
//            // 需要认证
//            .authenticated();
//
//    }
//
//    /**
//     * 注入BCrypt密码编码器
//     *
//     * @return bean
//     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
