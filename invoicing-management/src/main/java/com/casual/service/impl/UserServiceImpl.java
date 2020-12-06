package com.casual.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casual.entity.User;
import com.casual.mapper.UserMapper;
import com.casual.query.UserQuery;
import com.casual.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Mousse
 * @since 2020-05-17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public void pageQuery(Page<User> pageParam, UserQuery userQuery) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (userQuery == null){
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }
        String username = userQuery.getUsername();
        String mobile = userQuery.getMobile();

        if (!StringUtils.isEmpty(username)) {
            queryWrapper.like("username", username);
        }

        if (!StringUtils.isEmpty(mobile) ) {
            queryWrapper.eq("mobile", mobile);
        }

        baseMapper.selectPage(pageParam, queryWrapper);
    }
}
