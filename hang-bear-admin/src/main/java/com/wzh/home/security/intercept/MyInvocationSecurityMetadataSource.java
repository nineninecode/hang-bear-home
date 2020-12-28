package com.wzh.home.security.intercept;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import com.wzh.home.entity.bo.PermissionBo;
import com.wzh.home.service.UmsPermissionService;

/**
 * <p>
 * 自定义 security 权限元数据
 * </p>
 *
 * @author weizhuohang
 * @since 2020/11/3 15:09
 */
@Slf4j
@Component
public class MyInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private UmsPermissionService umsPermissionService;

    private static final String ASTERISK_CHAR = "*";

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 白名单
     */
    private List<String> ignoreRequestUrls = new ArrayList<>();

    /**
     * 访问权限列表
     */
    private List<PermissionBo> permissionBos = new ArrayList<>();

    /**
     * 加载权限
     */
    public void loadResourceDefine() {

        // 实时获取白名单
        ignoreRequestUrls = umsPermissionService.getIgnoreUrlList();
        // 实时获取所有权限
        permissionBos = umsPermissionService.getAllPermissionBos();

    }

    /**
     * Accesses the {@code ConfigAttribute}s that apply to a given secure object.
     * <p>
     * 判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行。
     * </p>
     * 
     * @param object
     *            the object being secured
     * @return the attributes that apply to the passed in secured object. Should return an empty collection if there are
     *         no applicable attributes.
     * @throws IllegalArgumentException
     *             if the passed object is not of a type supported by the <code>SecurityMetadataSource</code>
     *             implementation
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        loadResourceDefine();

        // object 中包含用户请求的request 信息
        HttpServletRequest request = ((FilterInvocation)object).getHttpRequest();

        String loginUserId = request.getRemoteUser();
        log.info("request.getRemoteUser: {}", loginUserId);
        request.getMethod();

        if (isIgnoreRequest(request)) {
            return null;
        }
        Collection<ConfigAttribute> configAttributes = getRoles(request);
        log.info("need roles: {}", configAttributes);
        return configAttributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    /**
     * 是否在白名单中
     * 
     * @param request
     *            访问请求
     * @return 布尔结果
     */
    private boolean isIgnoreRequest(HttpServletRequest request) {
        boolean result = false;
        String url = request.getRequestURI();
        for (String path : ignoreRequestUrls) {
            if (path.equals(url)) {
                result = true;
                break;
            }
            // 判断是否符合antPathMatcher的规则
            else if (this.pathMatcher.isPattern(path)) {
                // 判断是否匹配
                if (this.pathMatcher.match(path, url)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 获取权限角色
     * 
     * @param request
     *            请求
     * @return 角色权限
     */
    private Collection<ConfigAttribute> getRoles(HttpServletRequest request) {
        Collection<ConfigAttribute> configAttributes = new ArrayList<>();
        for (PermissionBo permissionBo : permissionBos) {
            String url = request.getRequestURI();
            String path = permissionBo.getPath();
            String requestMethod = permissionBo.getRequestMethod();
            String roleCode = permissionBo.getRoleCode();
            // 请求方法控制为【*】，或者与request请求方法一致
            if (!ASTERISK_CHAR.equals(requestMethod) && !requestMethod.equals(request.getMethod())) {
                continue;
            }
            if (url.equals(path)) {
                configAttributes.add(new SecurityConfig(roleCode));
            }
            // 判断是否符合antPathMatcher的规则
            else if (this.pathMatcher.isPattern(path)) {
                // 判断是否匹配
                if (this.pathMatcher.match(path, url)) {
                    configAttributes.add(new SecurityConfig(roleCode));
                }
            }
        }

        return configAttributes;
    }
}
