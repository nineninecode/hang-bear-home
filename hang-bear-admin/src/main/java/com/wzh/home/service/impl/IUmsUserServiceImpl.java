package com.wzh.home.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzh.home.config.properties.CustomProperties;
import com.wzh.home.entity.form.UserEditForm;
import com.wzh.home.entity.form.UserPasswordForm;
import com.wzh.home.entity.po.UmsPermission;
import com.wzh.home.entity.po.UmsUser;
import com.wzh.home.entity.vo.ItemVo;
import com.wzh.home.entity.vo.UmsUserVO;
import com.wzh.home.exception.BizException;
import com.wzh.home.mapper.UmsPermissionMapper;
import com.wzh.home.mapper.UmsUserMapper;
import com.wzh.home.service.IUmsUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author wzh
 * @since 2021-01-06
 */
@Slf4j
@Service
public class IUmsUserServiceImpl extends ServiceImpl<UmsUserMapper, UmsUser> implements IUmsUserService, BeanNameAware {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomProperties customProperties;

    //@Autowired
    //private MongoTemplate mongoTemplate;

    private String beanName;

    @Autowired
    private UmsPermissionMapper umsPermissionMapper;

    /**
     * 根据用户id获取用户信息
     *
     * @param id
     *            用户id
     * @return 用户信息
     */
    @Override
    public UmsUserVO getUserById(Long id) {
        UmsUserVO umsUserVO = new UmsUserVO();
        UmsUser byId = this.getById(id);
        if (Objects.isNull(byId)) {
            throw new BizException("用户不存在！");
        }
        BeanUtils.copyProperties(byId, umsUserVO);
        String idStr = byId.getId().toString();
        synchronized (this) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(new Date());
        }
        return umsUserVO;
    }

    /**
     * 新增注册用户信息
     *
     * @param userEditForm
     *            用户信息表单
     * @return 注册结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addUmsUser(UserEditForm userEditForm) {

        LambdaQueryWrapper<UmsUser> queryWrapper = Wrappers.lambdaQuery(UmsUser.class);
        queryWrapper.eq(UmsUser::getUsername, userEditForm.getUsername());
        UmsUser one = this.getOne(queryWrapper);
        if (Objects.nonNull(one)) {
            throw new BizException("该账号已经存在！");
        }

        UmsUser umsUser = new UmsUser();
        BeanUtils.copyProperties(userEditForm, umsUser);
        umsUser.setPassword(passwordEncoder.encode(customProperties.getDefaultPassword()));
        return this.save(umsUser);
    }

    /**
     * 修改密码
     *
     * @param userPasswordForm
     *            用户密码表单
     * @return 修改结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateUserPassword(UserPasswordForm userPasswordForm) {
        LambdaQueryWrapper<UmsUser> queryWrapper = Wrappers.lambdaQuery(UmsUser.class);
        queryWrapper.eq(UmsUser::getUsername, userPasswordForm.getUsername());
        UmsUser one = this.getOne(queryWrapper);
        if (Objects.isNull(one)) {
            throw new BizException("该账号不存在！");
        }
        if (StringUtils.isBlank(userPasswordForm.getPassword())) {
            throw new BizException("密码不能为空！");
        }
        if (!passwordEncoder.matches(userPasswordForm.getOldPassword(), one.getPassword())) {
            throw new BizException("原密码不正确！");
        }
        one.setPassword(passwordEncoder.encode(userPasswordForm.getPassword()));
        return this.updateById(one);
    }

    /**
     * 重置密码
     *
     * @param username
     *            用户名
     * @return 重置结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean resetUserPassword(String username) {

        QueryWrapper<UmsUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        UmsUser one = this.getOne(queryWrapper);
        if (Objects.isNull(one)) {
            throw new BizException("该账号不存在！");
        }
        one.setPassword(passwordEncoder.encode(customProperties.getDefaultPassword()));
        one.setUpdatedBy(null);
        return this.updateById(one);
    }


    /**
     * MP updateBatch测试
     *
     * @return 结果
     */
    @Override
    public Boolean updateUserBatch() {
        // Wrapper<UmsUser> updateWrapper = new UpdateWrapper<>();
        // List<UmsUser> userList = new ArrayList<>();
        // BiConsumer<SqlSession, UmsUser> consumer = new BiConsumer<SqlSession, UmsUser>() {
        // /**
        // * Performs this operation on the given arguments.
        // *
        // * @param sqlSession the first input argument
        // * @param umsUser the second input argument
        // */
        // @Override
        // public void accept(SqlSession sqlSession, UmsUser umsUser) {
        // sqlSession.update("", umsUser);
        // }
        //
        // };
        //// updateWrapper.
        // this.executeBatch(userList, consumer);
        // UmsUser umsUser = new UmsUser();
        // QueryWrapper<UmsUser> queryWrapper = new QueryWrapper<>();
        // this.update(umsUser,queryWrapper);
        UmsUser umsUser = new UmsUser();
        // umsUser.setId(1L);
        umsUser.setNickName("韦卓航");
        umsUser.setUsername("wzh");
        UmsUser umsUser2 = new UmsUser();
        // umsUser2.setId(1347103549580058625L);
        umsUser2.setNickName("熊欢欢");
        umsUser2.setUsername("xhh");
        UpdateWrapper<UmsUser> updateWrapper2 = new UpdateWrapper<>(umsUser);
        String customSqlSegment = updateWrapper2.getCustomSqlSegment();
        QueryWrapper<UmsUser> queryWrapper = new QueryWrapper<>();
        String customSqlSegment1 = queryWrapper.getCustomSqlSegment();
        // this.update(umsUser2, updateWrapper2);
        List<UmsUser> userList = new ArrayList<>();
        userList.add(umsUser);
        userList.add(umsUser2);
        String sqlStatement = getSqlStatement(SqlMethod.UPDATE);
        // this.updateBatchById(userList);
        executeBatch(userList, (sqlSession, entity) -> {
            MapperMethod.ParamMap<Object> param = new MapperMethod.ParamMap<>();
            QueryWrapper<UmsUser> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("username", entity.getUsername());
            LambdaQueryWrapper<UmsUser> queryWrapper3 = new QueryWrapper<UmsUser>().lambda();
            param.put(Constants.ENTITY, entity);
            param.put(Constants.WRAPPER, queryWrapper2);
            sqlSession.update(sqlStatement, param);
        });
        return Boolean.TRUE;
    }

    /**
     * 更新数量
     *
     * @param num
     *            数量
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCount(Integer num) {
        LambdaQueryWrapper<UmsUser> queryWrapper = Wrappers.lambdaQuery(UmsUser.class);
        queryWrapper.eq(UmsUser::getUsername, "wzh");
        UmsUser one = this.getOne(queryWrapper);
        log.info("num：{},wzh:{}", num, JSON.toJSONString(one));

        LambdaQueryWrapper<UmsPermission> permissionQueryWrapper = Wrappers.lambdaQuery(UmsPermission.class);
        permissionQueryWrapper.eq(UmsPermission::getPermissionCode, "CODE001");
        UmsPermission umsPermission = umsPermissionMapper.selectOne(permissionQueryWrapper);

        int update = umsPermissionMapper.updateById(umsPermission);
        log.info("permission update count：{}", update);
        log.info("num：{},sleep 5 seconds start！", num);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("num：{},sleep 5 seconds end！", num);
        Integer updateCount = one.getUpdateCount();
        one.setUpdateCount(updateCount + num);
        this.updateById(one);
    }

    @Override
    public void setBeanName(String s) {
        this.beanName = s;
    }

    public String getBeanName() {
        return this.beanName;
    }
}
