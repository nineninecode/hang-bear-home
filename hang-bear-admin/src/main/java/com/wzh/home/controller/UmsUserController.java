package com.wzh.home.controller;

import com.wzh.home.entity.form.UserEditForm;
import com.wzh.home.entity.form.UserPasswordForm;
import com.wzh.home.entity.result.BaseResult;
import com.wzh.home.entity.vo.ItemVo;
import com.wzh.home.entity.vo.UmsUserVO;
import com.wzh.home.service.impl.IUmsUserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户信息相关--前端控制器
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/7 15:39
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UmsUserController implements BeanNameAware {
    @Autowired
    private IUmsUserServiceImpl iUmsUserService;

    private String name;

    /**
     * 获取用户信息
     *
     * @param id
     *            用户id
     * @return 注册结果
     */
    @GetMapping("/get/{id}")
    public BaseResult<UmsUserVO> get(@PathVariable Long id) {
        log.info("user get id:{}", id);
        log.info("iUmsUserService beanId {}", iUmsUserService.getBeanName());
        log.info("controller beanId {}", this.name);
        return BaseResult.success(iUmsUserService.getUserById(id));
    }

    /**
     * 新增注册用户
     *
     * @param userEditForm
     *            注册用户信息
     * @return 注册结果
     */
    @PostMapping("/add")
    public BaseResult<Boolean> add(@Validated @RequestBody UserEditForm userEditForm) {
        log.info("userEditForm:{}", userEditForm);
        return BaseResult.success(iUmsUserService.addUmsUser(userEditForm));
    }

    /**
     * 修改密码
     *
     * @param userPasswordForm
     *            密码信息表单
     * @return 修改结果
     */
    @PutMapping("/update-password")
    public BaseResult<Boolean> updatePassword(@Validated @RequestBody UserPasswordForm userPasswordForm) {
        log.info("userPasswordForm:{}", userPasswordForm);
        return BaseResult.success(iUmsUserService.updateUserPassword(userPasswordForm));
    }

    /**
     * 重置密码
     *
     * @param userPasswordForm
     *            密码信息表单
     * @return 重置结果
     */
    @PutMapping("/reset-password")
    public BaseResult<Boolean> resetPassword(@Validated @RequestBody UserPasswordForm userPasswordForm) {
        log.info("userPasswordForm:{}", userPasswordForm);
        return BaseResult.success(iUmsUserService.resetUserPassword(userPasswordForm.getUsername()));
    }

    @Override
    public void setBeanName(String s) {
        this.name = s;
    }

    @GetMapping("/mongo-test")
    public BaseResult<List<ItemVo>> mongoTest() {
        return BaseResult.success(iUmsUserService.mongoDBTest());
    }

    @GetMapping("/save-test")
    public BaseResult<Boolean> saveTest() {
        Boolean aBoolean = iUmsUserService.updateUserBatch();
        return BaseResult.success(aBoolean);
    }

    @GetMapping("/update")
    public void updateCount(@RequestParam Integer num) {
        iUmsUserService.updateCount(num);
    }
}
