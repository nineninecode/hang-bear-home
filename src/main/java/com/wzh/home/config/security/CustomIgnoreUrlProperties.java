package com.wzh.home.config.security;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 文件上传配置
 * </p>
 *
 * @author weizhuohang
 * @since 2020/10/23 15:35
 */
@Data
@Component
@ConfigurationProperties(prefix = CustomIgnoreUrlProperties.CUSTOM_SECURITY_IGNORE)
public class CustomIgnoreUrlProperties {
    public static final String CUSTOM_SECURITY_IGNORE = "custom.security.ignore";

    /**
     * security 访问忽略
     */
    private String url;

}
