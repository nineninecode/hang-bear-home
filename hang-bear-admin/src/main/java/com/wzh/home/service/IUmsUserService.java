package com.wzh.home.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzh.home.entity.form.UserEditForm;
import com.wzh.home.entity.form.UserPasswordForm;
import com.wzh.home.entity.po.UmsUser;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author wzh
 * @since 2021-01-06
 */
public interface IUmsUserService extends IService<UmsUser> {

    /**
     * 新增注册用户信息
     * 
     * @param userEditForm
     *            用户信息表单
     * @return 注册结果
     */
    Boolean addUmsUser(UserEditForm userEditForm);

    /**
     * 修改密码
     * 
     * @param userPasswordForm
     *            用户密码表单
     * @return 修改结果
     */
    Boolean updateUserPassword(UserPasswordForm userPasswordForm);

    /**
     * 重置密码
     * 
     * @param username
     *            用户名
     * @return 重置结果
     */
    Boolean resetUserPassword(String username);

}
