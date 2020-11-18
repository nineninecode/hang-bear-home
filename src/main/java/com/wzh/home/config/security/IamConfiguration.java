package com.wzh.home.config.security;

import lombok.extern.slf4j.Slf4j;

import java.util.Properties;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.foresealife.iam.client.api.IamServiceFactory;
import com.foresealife.iam.client.config.IamConfig;
import com.foresealife.iam.client.config.IamConfigFactory;
import com.foresealife.iam.client.util.PropsUtils;

@Configuration
@Slf4j
public class IamConfiguration {


    @Autowired
    private IamProperties iamProperties;

    @Bean
    public IamServiceFactory iamServiceFactory() {

        String env = iamProperties.getEnv();
        Properties properties = null;
        if(StringUtils.equals("test",env)) {
             log.info("load properties from test env");
             properties = PropsUtils.loadProps("iam-client-test.properties");
        }else {
            log.info("load properties from prd env");
             properties = PropsUtils.loadProps("iam-client-prd.properties");
        }
        IamConfig iamConfig =  IamConfigFactory.loadConfig(properties);
        return  IamServiceFactory.getInstance(iamConfig);
    }



}
