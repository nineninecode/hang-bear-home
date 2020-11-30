package com.wzh.home.service;

import java.util.List;

/**
 * <p>
 * 资源服务--接口类
 * </p>
 *
 * @author weizhuohang
 * @since 2020/11/30 11:20
 */
public interface ResourceService {

    /**
     * 获取访问白名单
     * 
     * @return 白名单列表
     */
    List<String> getIgnoreUrlList();
}
