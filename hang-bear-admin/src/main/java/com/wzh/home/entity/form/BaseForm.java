package com.wzh.home.entity.form;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/7 15:19
 */
@Data
public class BaseForm implements Serializable {

    private static final long serialVersionUID = 1L;

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
