package com.wzh.home.entity.po;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 用户信息类
 * </p>
 *
 * @author weizhuohang
 * @since 2020/11/19 22:28
 */
@Data
public class UmsUser implements Serializable {

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 名称
     */
    private String name;

}
