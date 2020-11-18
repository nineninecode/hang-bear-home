package com.wzh.home.config.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = IamProperties.IAM_CONFIG_PREFIX)
public class IamProperties {


    public static final String IAM_CONFIG_PREFIX = "iam.config";

    private String env = "test";


    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }
}
