package com.wzh.home.config.mybatis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;

/**
 * <p>
 * 自定义spring boot 配置
 * </p>
 *
 * @author weizhuohang
 * @since 2021/08/11 17:10
 */
@Configuration
public class MybatisPlusBeanConfig {

    /**
     * 注册乐观锁拦截器
     * 
     * @return mybatis plus 拦截器
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }
}
