package com.wzh.home.entity.form;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/7 16:13
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class UserPasswordForm extends BaseForm {

    /**
     * 用户名
     */
    private String username;

    /**
     * 新密码
     */
    private String password;

    /**
     * 原密码
     */
    private String oldPassword;

}
