package com.wzh.home.security.intercept;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import com.wzh.home.service.ResourceService;

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
    private ResourceService resourceService;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 白名单
     */
    private List<String> ignoreRequestUrls = new ArrayList<>();

    /**
     * 每个资源（url）所需要的权限（角色）集合
     */
    private HashMap<String, Collection<ConfigAttribute>> map = null;

    /**
     * 加载权限
     */
    public void loadResourceDefine() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 实时获取白名单
        ignoreRequestUrls = resourceService.getIgnoreUrlList();

        map = new HashMap<>(64);
        Collection<ConfigAttribute> array;
        ConfigAttribute cfg;
        List<String> permissions = new ArrayList<>();
        permissions.add("/hgmc-channel-product/**");
        permissions.add("/hgmc-channel-product/*");
        permissions.add("/hello/*");
        for (String permission : permissions) {
            array = new ArrayList<>();
            cfg = new SecurityConfig("lixj007");
            // 此处只添加了用户的名字，其实还可以添加更多权限的信息，
            // 例如请求方法到ConfigAttribute的集合中去。此处添加的信息将会作为MyAccessDecisionManager类的decide的第三个参数。
            array.add(cfg);
            // 用权限的getUrl() 作为map的key，用ConfigAttribute的集合作为 value，
            map.put(permission, array);
        }
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

        if (map == null) {
            loadResourceDefine();
        }
        // object 中包含用户请求的request 信息
        HttpServletRequest request = ((FilterInvocation)object).getHttpRequest();

        String loginUserId = request.getRemoteUser();
        log.info("request.getRemoteUser: {}", loginUserId);

        if (isIgnoreRequest(request)) {
            return null;
        }

        // String resUrl;
        // for (Iterator<String> inter = map.keySet().iterator(); inter.hasNext();) {
        // resUrl = inter.next();
        // matcher = new AntPathRequestMatcher(resUrl);
        // if (matcher.matches(request)) {
        // return map.get(resUrl);
        // }
        // }
        return null;
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
}
