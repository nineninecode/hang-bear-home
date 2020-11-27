package com.wzh.home.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wzh.home.entity.po.User;

/**
 * <p>
 * 简单的前端控制器
 * </p>
 *
 * @author weizhuohang
 * @since 2020/10/30 15:14
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/say")
    public String sayHello() {
        return "hello";
    }

    @GetMapping("/hi")
    public User sayHi() {
        User user = new User();
        user.setUsername("wzh");
        user.setName("韦卓航");
        return user;
    }
}
