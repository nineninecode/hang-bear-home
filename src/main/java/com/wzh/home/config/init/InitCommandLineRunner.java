package com.wzh.home.config.init;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.wzh.home.config.security.CustomIgnoreUrlProperties;

/**
 * <p>
 * spring boot 初始化完毕后init
 * </p>
 *
 * @author weizhuohang
 * @since 2020/11/27 16:47
 */
@Slf4j
@Component
public class InitCommandLineRunner implements CommandLineRunner {

    @Autowired
    private CustomIgnoreUrlProperties customIgnoreUrlProperties;

    @Override
    public void run(String... args) {
        log.info("url {}", customIgnoreUrlProperties.getUrl());
        log.info("url-array {}", Arrays.toString(customIgnoreUrlProperties.getUrlArray()));
        log.info("url-list {}", customIgnoreUrlProperties.getUrlList());
    }
}
