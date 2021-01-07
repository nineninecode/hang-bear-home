package com.wzh.home.entity.form;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/7 15:16
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserEditForm extends BaseForm {

    /**
     * 登录账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像
     */
    private String icon;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 昵称
     */
    private String nickName;
}
