package com.wzh.home.service;

import java.util.List;


import com.wzh.home.entity.bo.PermissionBo;
import org.springframework.context.annotation.Primary;

/**
 * <p>
 * 资源服务--接口类
 * </p>
 *
 * @author weizhuohang
 * @since 2020/11/30 11:20
 */
@Primary
public interface UmsPermissionService {

    /**
     * 获取所有的权限访问资源
     * 
     * @return 权限访问资源列表
     */
    List<PermissionBo> getAllPermissionBos();

    /**
     * 获取访问白名单
     * 
     * @return 白名单列表
     */
    List<String> getIgnoreUrlList();
}
