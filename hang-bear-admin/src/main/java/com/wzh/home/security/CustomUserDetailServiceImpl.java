package com.wzh.home.security;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Objects;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzh.home.entity.po.UmsUser;
import com.wzh.home.service.IUmsUserService;

/**
 * <p>
 * spring security 获取登录用户服务类
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/6 16:21
 */
@Slf4j
@Service
public class CustomUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private IUmsUserService iUmsUserService;

    /**
     * Locates the user based on the username. In the actual implementation, the search may possibly be case sensitive,
     * or case insensitive depending on how the implementation instance is configured. In this case, the
     * <code>UserDetails</code> object that comes back may have a username that is of a different case than what was
     * actually requested..
     *
     * @param username
     *            the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException
     *             if the user could not be found or the user has no GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<UmsUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        UmsUser umsUser = iUmsUserService.getOne(queryWrapper);
        if (Objects.isNull(umsUser)) {
            throw new UsernameNotFoundException("该用户不存在！");
        }
        UserDetails userDetails = new User(umsUser.getUsername(), umsUser.getPassword(), Collections.emptyList());
        return userDetails;
    }
}
