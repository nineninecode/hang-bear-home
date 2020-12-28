package com.wzh.home.service.impl;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Service;

import com.wzh.home.entity.bo.PermissionBo;
import com.wzh.home.service.UmsPermissionService;

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
public class UmsPermissionServiceImpl implements UmsPermissionService {

    /**
     * 获取所有的权限访问资源
     *
     * @return 权限访问资源列表
     */
    @Override
    public List<PermissionBo> getAllPermissionBos() {
        List<PermissionBo> permissionBos = new ArrayList<>();
        PermissionBo permissionBo = new PermissionBo();
        permissionBo.setPath("/hello/say");
        permissionBo.setRequestMethod("*");
        permissionBo.setRoleCode("R_ADMIN");
        permissionBos.add(permissionBo);
        return permissionBos;
    }

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
