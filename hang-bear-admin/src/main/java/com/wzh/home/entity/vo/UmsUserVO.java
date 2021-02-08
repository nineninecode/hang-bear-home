package com.wzh.home.entity.vo;

import lombok.Data;

/**
 * <p>
 * 用户信息-VO类
 * </p>
 *
 * @author weizhuohang
 * @since 2021/2/3 16:37
 */
@Data
public class UmsUserVO {

    /**
     * 登录账号
     */
    private String username;

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
