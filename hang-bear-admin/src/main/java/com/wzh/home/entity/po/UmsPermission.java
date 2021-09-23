package com.wzh.home.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

    private static final long serialVersionUID = -6951144528252057815L;

    /**
     * 启用标记:0-禁用，1-启用
     */
    private Integer enabledFlag;

    /**
     * 备注
     */
    private String remark;

    /**
     * 权限编码
     */
    private String permissionCode;

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 权限uri
     */
    private String permissionUri;

    /**
     * 权限类型： 0->目录；1->菜单；2->按钮（接口绑定权限）
     */
    private Integer permissionType;

    /**
     * 权限排序
     */
    private Integer permissionSort;

    /**
     * 访问路径，数据库表字段不存在
     */
    @TableField(exist = false)
    private String path;
}
