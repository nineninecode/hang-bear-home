package com.wzh.home.config.properties;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 自定义配置属性
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/7 15:28
 */
@Data
@Component
@ConfigurationProperties(prefix = CustomProperties.CUSTOM)
public class CustomProperties {
    public static final String CUSTOM = "custom";

    /**
     * 默认密码
     */
    private String defaultPassword;
}
