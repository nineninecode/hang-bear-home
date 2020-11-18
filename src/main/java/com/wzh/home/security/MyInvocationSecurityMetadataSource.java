package com.wzh.home.security;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;

import com.alibaba.fastjson.JSON;
import com.foresealife.iam.client.api.IamServiceFactory;
import com.foresealife.iam.client.bean.IamMenu;
import com.foresealife.iam.client.filter.security.AccessControl;

/**
 * <p>
 * 权限元数据
 * </p>
 *
 * @author weizhuohang
 * @since 2020/11/3 15:09
 */
//@Component
public class MyInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private HashMap<String, Collection<ConfigAttribute>> map = null;

    /**
     * 加载权限
     */
    public void loadResourceDefine() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        map = new HashMap<>(64);
        Collection<ConfigAttribute> array;
        ConfigAttribute cfg;
        List<String> permissions = new ArrayList<>();
        permissions.add("/hgmc-channel-product/**");
        permissions.add("/hgmc-channel-product/*");
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

        // 获取用户在当前系统的角色信息
        List<String> roleList = IamServiceFactory.getInstance().getUserService().getRoleList(loginUserId);

        List<IamMenu> menuList = IamServiceFactory.getInstance().getUserService().getMenuList(loginUserId);
        AccessControl aclFileFromNas =
            IamServiceFactory.getInstance().getAclService().getAclFileFromNas("QH001", "IAM_CONSOLE");

        // IamSubject subject = IamServiceFactory.getInstance().getUserService().getSubject(loginUserId,
        // PeopleType.STAFF);
        String str = JSON.toJSONString(menuList);
        AntPathRequestMatcher matcher;
        AntPathMatcher matcher2 = new AntPathMatcher();
        //matcher2.isPattern();
        String resUrl;
        for (Iterator<String> inter = map.keySet().iterator(); inter.hasNext();) {
            resUrl = inter.next();
            matcher = new AntPathRequestMatcher(resUrl);
            if (matcher.matches(request)) {
                return map.get(resUrl);
            }
        }
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
}
