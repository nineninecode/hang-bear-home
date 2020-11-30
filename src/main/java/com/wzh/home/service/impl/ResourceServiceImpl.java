package com.wzh.home.service.impl;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wzh.home.service.ResourceService;

/**
 * <p>
 * 资源服务--实现类
 * </p>
 *
 * @author weizhuohang
 * @since 2020/11/30 11:20
 */
@Slf4j
@Service
public class ResourceServiceImpl implements ResourceService {
    /**
     * 获取访问白名单
     *
     * @return 白名单列表
     */
    @Override
    public List<String> getIgnoreUrlList() {
        List<String> ignoreUrls = new ArrayList<>();
        ignoreUrls.add("/hello/say");
        return ignoreUrls;
    }
}
