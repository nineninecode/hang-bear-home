package com.wzh.home.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * po基础类
 * </p>
 *
 * @author weizhuohang
 * @since 2020/12/28 16:04
 */
@Data
public class BasePo implements Serializable {

    private static final long serialVersionUID = 1946624737813135560L;

    /**
     * 主键id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 创建人账户
     */
    @TableField(fill = FieldFill.INSERT)
    private String createdById;

    /**
     * 创建人名称
     */
    @TableField(fill = FieldFill.INSERT)
    private String createdBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    /**
     * 更新人账户
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updatedById;

    /**
     * 更新人名称
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updatedBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;

    /**
     * 逻辑删除，删除标记:0-未删除，1-删除
     */
    @TableLogic
    private Integer deleteFlag;

    /**
     * 乐观锁
     */
    @Version
    private Integer version;

}
