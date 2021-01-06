package com.wzh.home.entity.po;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

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

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 创建人名称
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新人名称
     */
    private String updatedBy;

    /**
     * 更新时间
     */
    private String updatedTime;

    /**
     * 创建人id
     */
    private Long createdById;

    /**
     * 更新人id
     */
    private Long updatedById;
}
