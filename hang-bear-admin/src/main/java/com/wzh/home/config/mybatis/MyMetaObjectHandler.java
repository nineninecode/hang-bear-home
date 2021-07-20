package com.wzh.home.config.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.wzh.home.entity.bo.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 * mybatis plus 自动填充处理器
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/8 10:27
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 插入元对象字段填充（用于插入时对公共字段的填充）
     *
     * @param metaObject
     *            元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createdTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updatedTime", LocalDateTime.now(), metaObject);
        Object createdById = this.getFieldValByName("createdById", metaObject);
        if (Objects.isNull(createdById)) {
            // 更新人信息未设置，采用当前登录用户
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            SecurityUser userInfo = (SecurityUser)authentication.getPrincipal();
            if (Objects.isNull(userInfo)) {
                // 没有设置且当前登录人为空时设置默认值 system
                this.setFieldValByName("createdBy", "system", metaObject);
                this.setFieldValByName("createdById", "system", metaObject);
                this.setFieldValByName("updatedBy", "system", metaObject);
                this.setFieldValByName("updatedById", "system", metaObject);
            } else {
                this.setFieldValByName("createdBy", userInfo.getNickName(), metaObject);
                this.setFieldValByName("createdById", userInfo.getUsername(), metaObject);
                this.setFieldValByName("updatedBy", userInfo.getNickName(), metaObject);
                this.setFieldValByName("updatedById", userInfo.getUsername(), metaObject);
            }
        }
    }

    /**
     * 更新元对象字段填充（用于更新时对公共字段的填充）
     *
     * @param metaObject
     *            元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updatedTime", LocalDateTime.now(), metaObject);
        Object updatedById = this.getFieldValByName("updatedById", metaObject);
        if (Objects.isNull(updatedById)) {
            // 更新人信息未设置，采用当前登录用户
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            SecurityUser userInfo = (SecurityUser)authentication.getPrincipal();
            if (Objects.isNull(userInfo)) {
                // 没有设置且当前登录人为空时设置默认值 system
                this.setFieldValByName("updatedBy", "system", metaObject);
                this.setFieldValByName("updatedById", "system", metaObject);
            } else {
                this.setFieldValByName("updatedBy", userInfo.getNickName(), metaObject);
                this.setFieldValByName("updatedById", userInfo.getUsername(), metaObject);
            }
        }
    }
}
