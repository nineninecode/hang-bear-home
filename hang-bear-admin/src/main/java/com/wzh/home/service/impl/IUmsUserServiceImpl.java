package com.wzh.home.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wzh.home.entity.po.LogInventory;
import com.wzh.home.entity.vo.ItemVo;
import com.wzh.home.redis.RedisTool;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.ibatis.io.ResolverUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzh.home.config.properties.CustomProperties;
import com.wzh.home.entity.form.UserEditForm;
import com.wzh.home.entity.form.UserPasswordForm;
import com.wzh.home.entity.po.UmsUser;
import com.wzh.home.entity.vo.UmsUserVO;
import com.wzh.home.exception.BizException;
import com.wzh.home.mapper.UmsUserMapper;
import com.wzh.home.service.IUmsUserService;
import redis.clients.jedis.Jedis;

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

    @Autowired
    private MongoTemplate mongoTemplate;

    private String beanName;

    /**
     * 根据用户id获取用户信息
     *
     * @param id 用户id
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
     * @param userEditForm 用户信息表单
     * @return 注册结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addUmsUser(UserEditForm userEditForm) {

        QueryWrapper<UmsUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userEditForm.getUsername());
        Jedis jedis = new Jedis();
//        RedisTool.tryGetDistributedLock();
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
     * @param userPasswordForm 用户密码表单
     * @return 修改结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateUserPassword(UserPasswordForm userPasswordForm) {
        QueryWrapper<UmsUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userPasswordForm.getUsername());
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
     * @param username 用户名
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
        return this.updateById(one);
    }

    @Override
    public List<ItemVo> mongoDBTest() {
        List<ItemVo> itemVoList = new ArrayList<>();
        Criteria criteria = Criteria.where("warehouseCode").is("WH0120");
        criteria.and("ownerCode").is("CS0022");
        criteria.and("actionType").is(19);
        List<Criteria> andCriteriaList = new ArrayList<>();
        LocalDate startDate = LocalDate.now();
        LocalDate localDate = startDate.minusDays(1L);
        LocalTime time = LocalTime.of(12, 12, 12);
        LocalTime time2 = LocalTime.of(15, 7, 8);
        LocalDateTime startDateTime = LocalDateTime.of(localDate, time);
        long until = time.until(time2, ChronoUnit.SECONDS);
        log.info("时间：{}", until);
        LocalDate endDate = localDate;
        if (until < 0) {
//            跨天
            endDate = endDate.plusDays(1L);
        }
        LocalDateTime endDateTime = LocalDateTime.of(endDate, time2);
        log.info("开始时间：{}", startDateTime);
        log.info("截止时间：{}", endDateTime);
        andCriteriaList.add(Criteria.where("changeTime").gte(startDateTime));//开始时间
        andCriteriaList.add(Criteria.where("changeTime").lte(endDateTime)); //结束时间
        criteria.andOperator(andCriteriaList.toArray(new Criteria[]{}));
        TypedAggregation<LogInventory> typedAggregation = TypedAggregation.newAggregation(LogInventory.class,
                TypedAggregation.match(criteria),
                TypedAggregation.group("warehouseCode", "ownerCode", "ownerItemCode")
                        .first("warehouseCode").as("warehouseCode")
                        .first("ownerCode").as("ownerCode")
                        .first("ownerItemCode").as("ownerItemCode")
                        .sum("changeStockQty").as("shippingQty"),
                TypedAggregation.project("warehouseCode", "ownerCode", "ownerItemCode", "shippingQty")
        );
        AggregationResults<ItemVo> aggregate = mongoTemplate.aggregate(typedAggregation, ItemVo.class);
        itemVoList = aggregate.getMappedResults();
        return itemVoList;
    }

    @Override
    public void setBeanName(String s) {
        this.beanName = s;
    }

    public String getBeanName() {
        return this.beanName;
    }
}
