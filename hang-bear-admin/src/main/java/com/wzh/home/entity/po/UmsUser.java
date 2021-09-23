package com.wzh.home.entity.po;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author weizhuohang
 * @since 2020/11/19 22:28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ums_user")
public class UmsUser extends BasePo {

    private static final long serialVersionUID = 3157772192041986654L;

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

    /**
     * 数量
     */
    private Integer updateCount;

}
