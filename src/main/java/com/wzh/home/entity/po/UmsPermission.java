package com.wzh.home.entity.po;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2020/11/30 21:36
 */
@Data
public class UmsPermission implements Serializable {

    /**
     * 访问路径
     */
    private String path;

    /**
     * 请求方法
     */
    private String requestMethod;
}
