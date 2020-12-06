package com.casual.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casual.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.casual.query.UserQuery;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Mousse
 * @since 2020-05-17
 */
public interface UserService extends IService<User> {
    void pageQuery(Page<User> pageParam, UserQuery userQuery);
}
