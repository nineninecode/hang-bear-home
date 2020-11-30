package com.wzh.home.entity.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2020/11/30 22:28
 */
@Data
public class PermissionBo implements Serializable {

    /**
     * 访问路径
     */
    private String path;

    /**
     * 请求方法
     */
    private String requestMethod;

    /**
     * 角色编码
     */
    private String roleCode;
}
