package com.casual.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casual.entity.User;
import com.casual.query.UserQuery;
import com.casual.service.UserService;
import com.casual.util.MyException;
import com.casual.util.R;
import com.casual.util.ResultCodeEnum;
import com.casual.util.WebUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author Mousse
 * @since 2020-05-17
 */
@Api(description = "用户管理")
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户登录")
    @PostMapping("login")
    public R login(
            @ApiParam(name = "user", value = "用户对象", required = true)
            @RequestBody User user){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername()).eq("passwd", user.getPassword());
        User user2 = userService.getOne(queryWrapper);
        if(user2 != null) {
            WebUtils.getHttpSession().setAttribute("user", user);
            return R.ok().data("user", user2);
        }
        return R.error();
    }

    @ApiOperation(value = "用户列表")
    @GetMapping
    public R list(){
        List<User> list = userService.list(null);
        return R.ok().data("users", list);
    }

    @ApiOperation(value = "根据id删除用户")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "用户id", required = true)
            @PathVariable String id){
        userService.removeById(id);
        return R.ok();
    }

    @ApiOperation(value = "分页讲师列表")
    @GetMapping("{page}/{limit}")
    public R pageQuery(
        @ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Long page,
        @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Long limit,
        @ApiParam(name = "userQuery", value = "查询对象", required = false) UserQuery userQuery){
        Page<User> pageParam = new Page<>(page, limit);
        if(page <= 0 || limit <= 0){
            throw new MyException(ResultCodeEnum.PARAM_ERROR);
        }
        userService.pageQuery(pageParam, userQuery);
        List<User> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return  R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "新增用户")
    @PostMapping
    public R save(@ApiParam(name = "user", value = "用户", required = true)
                  @RequestBody User user){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername()).or().eq("mobile", user.getMobile());
        User condition = userService.getOne(queryWrapper);
        if(null == condition){
            userService.save(user);
            return R.ok();
        }else {
            return R.error();//用户名/电话重复
        }
    }

    @ApiOperation(value = "根据id查询用户")
    @GetMapping("{id}")
    public R getById(@ApiParam(name = "id", value = "用户id", required = true)
                     @PathVariable String id){
        User user = userService.getById(id);
        return R.ok().data("user", user);
    }

    @ApiOperation(value = "根据id修改用户")
    @PutMapping("{id}")
    public R updateById(
            @ApiParam(name = "id", value = "用户id", required = true)
            @PathVariable String id,
            @ApiParam(name = "user", value = "用户对象", required = true)
            @RequestBody User user
    ){
        user.setId(id);
        userService.updateById(user);
        return R.ok();
    }

    @ApiOperation(value = "修改密码")
    @RequestMapping("updatePwd/{password}")
    public R updatePwd(
            @ApiParam(name = "password", value = "新密码", required = true)
            @PathVariable String password
    ){
        User user = (User) WebUtils.getHttpSession().getAttribute("user");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());
        User newuser = userService.getOne(queryWrapper);
        newuser.setPassword(password);
        userService.saveOrUpdate(newuser);
        return R.ok();
    }

}

