package com.wzh.home.service.impl;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzh.home.config.properties.CustomProperties;
import com.wzh.home.entity.form.UserEditForm;
import com.wzh.home.entity.form.UserPasswordForm;
import com.wzh.home.entity.po.UmsUser;
import com.wzh.home.exception.BizException;
import com.wzh.home.mapper.UmsUserMapper;
import com.wzh.home.service.IUmsUserService;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author wzh
 * @since 2021-01-06
 */
@Slf4j
@Service
public class IUmsUserServiceImpl extends ServiceImpl<UmsUserMapper, UmsUser> implements IUmsUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomProperties customProperties;

    /**
     * 新增注册用户信息
     *
     * @param userEditForm
     *            用户信息表单
     * @return 注册结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addUmsUser(UserEditForm userEditForm) {

        QueryWrapper<UmsUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userEditForm.getUsername());
        UmsUser one = this.getOne(queryWrapper);
        if (Objects.nonNull(one)) {
            throw new BizException("该账号已经存在！");
        }

        UmsUser umsUser = new UmsUser();
        BeanUtils.copyProperties(userEditForm, umsUser);
        umsUser.setPassword(passwordEncoder.encode(customProperties.getDefaultPassword()));
        Boolean saveBoolean = this.save(umsUser);
        return saveBoolean;
    }

    /**
     * 修改密码
     *
     * @param userPasswordForm
     *            用户密码表单
     * @return 修改结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateUserPassword(UserPasswordForm userPasswordForm) {
        QueryWrapper<UmsUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userPasswordForm.getUsername());
        UmsUser one = this.getOne(queryWrapper);
        if (Objects.isNull(one)) {
            throw new BizException("该账号不存在！");
        }
        if (StringUtils.isBlank(userPasswordForm.getPassword())) {
            throw new BizException("密码不能为空！");
        }
        if (!passwordEncoder.matches(userPasswordForm.getOldPassword(), one.getPassword())) {
            throw new BizException("原密码不正确！");
        }
        one.setPassword(passwordEncoder.encode(userPasswordForm.getPassword()));
        return this.updateById(one);
    }

    /**
     * 重置密码
     *
     * @param username
     *            用户名
     * @return 重置结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean resetUserPassword(String username) {

        QueryWrapper<UmsUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        UmsUser one = this.getOne(queryWrapper);
        if (Objects.isNull(one)) {
            throw new BizException("该账号不存在！");
        }
        one.setPassword(passwordEncoder.encode(customProperties.getDefaultPassword()));
        return this.updateById(one);
    }
}
