package com.wzh.home;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * spring boot 启动类
 * </p>
 *
 * @author weizhuohang
 * @since 2020/10/30 15:14
 */
@SpringBootApplication
@MapperScan("com.wzh.home.mapper")
public class HangBearHomeApplication {

    public static void main(String[] args) {
        SpringApplication.run(HangBearHomeApplication.class, args);
    }

}
