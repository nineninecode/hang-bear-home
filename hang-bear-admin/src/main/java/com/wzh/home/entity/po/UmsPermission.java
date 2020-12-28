package com.wzh.home.entity.po;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author weizhuohang
 * @since 2020/11/30 21:36
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ums_permission")
public class UmsPermission extends BasePo {

    /**
     * 访问路径
     */
    private String path;

    /**
     * 请求方法
     */
    private String requestMethod;
}
