package com.wzh.home.entity.form;

import lombok.Data;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>
 * json入参范例
 * </p>
 *
 * @author weizhuohang
 * @since 2020/11/27 15:16
 */
@Data
public class ExampleForm {

    /**
     * 序列化别名
     * jackson:@JsonProperty
     * alibaba.fastjson:@JSONField
     */
    @JsonProperty("first_name")
    private String param;
}
