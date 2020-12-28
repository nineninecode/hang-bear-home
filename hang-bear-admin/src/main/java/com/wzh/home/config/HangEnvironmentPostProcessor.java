package com.wzh.home.config;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.util.ResourceUtils;

/**
 * <p>
 * 加载自定义名称目录的配置文件，需要在spring.factories中配置，在IOC之前
 * </p>
 *
 * @author weizhuohang
 * @since 2020/11/29 16:47
 */
@Slf4j
public class HangEnvironmentPostProcessor implements EnvironmentPostProcessor {

    /**
     * 文件加载类
     */
    private final ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

    /**
     * TODO 为什么用final好
     */
    private final List<PropertySourceLoader> propertySourceLoaders;

    public HangEnvironmentPostProcessor() {
        super();
        this.propertySourceLoaders =
            SpringFactoriesLoader.loadFactories(PropertySourceLoader.class, getClass().getClassLoader());
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String[] activeProfiles = environment.getActiveProfiles();
        System.out.println("active profiles " + Arrays.toString(activeProfiles));

        for (String activeProfile : activeProfiles) {
            for (PropertySourceLoader loader : propertySourceLoaders) {
                for (String extension : loader.getFileExtensions()) {
                    String location = ResourceUtils.CLASSPATH_URL_PREFIX + activeProfile + "/*." + extension;
                    try {
                        Resource[] resources = resourcePatternResolver.getResources(location);
                        for (Resource resource : resources) {
                            List<PropertySource<?>> propertySources = loader.load(resource.getFilename(), resource);
                            if (null != propertySources && !propertySources.isEmpty()) {
                                propertySources.forEach(
                                    propertySource -> environment.getPropertySources().addLast(propertySource));
                            }
                        }
                    } catch (IOException e) {
                        log.error("配置文件加载失败，{}", e.getMessage(), e);
                    }
                }

            }
        }
    }
}
