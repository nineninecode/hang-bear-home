package com.wzh.home.service.impl;

import com.wzh.home.service.IUmsUserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>
 *
 * </p>
 *
 * @author weizhuohang
 * @since 2021/07/08 16:30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IUmsUserServiceImplTest {

    @Autowired
    IUmsUserService umsUserService;

    @Test
    public void resetUserPassword() {
        umsUserService.getUserById(99L);
        //umsUserService.resetUserPassword("wzh");
    }
}