# invoicing-management
进销存系统（前后端分离）前端项目：invoicing&nbsp;&nbsp;后端项目：invoicing-management
### 1.进销存系统要求实现以下功能：
1、用户登陆<br/>
2、主页<br/>
3、库存管理模块<br/>
4、进货管理模块<br/>
5、商品管理模块<br/>
6、出货管理模块<br/>
7、用户管理模块<br/>
8、单位管理模块<br/>
9、修改密码<br/>
### 2.后端springboot项目
```
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.3.0.RELEASE</version>
    <relativePath/>
</parent>
```
### 3.使用技术
```
<mybatis-plus.version>3.0.5</mybatis-plus.version>
<swagger.version>2.7.0</swagger.version>
```
>mybatis-plus

Mybatis-Plus（简称MP）是一个 Mybatis 的增强工具，在 Mybatis 的基础上只做增强不做改变，为简化开发、提高效率而生<br/>
[官方文档](https://mybatis.plus/guide/crud-interface.html)

>swagger

由于API 接口众多，细节复杂，需要考虑不同的HTTP请求类型、HTTP头部信息、HTTP请求内容等，想要高质量的完成这份文档需要耗费大量的精力；难以维护。
随着需求的变更和项目的优化、推进，接口的细节在不断地演变，接口描述文档也需要同步修订，可是文档和代码处于两个不同的媒介，除非有严格的管理机制，否则很容易出现文档、接口不一致的情况。<br/>
Swagger2 的出现就是为了从根本上解决上述问题。它作为一个规范和完整的框架，可以用于生成、描述、调用和可视化 RESTful 风格的 Web 服务：接口文档在线自动生成，文档随接口变动实时更新，节省维护成本
支持在线接口测试，不依赖第三方工具<br/>
- @Api：修饰整个类，描述Controller的作用
- @ApiOperation：描述一个类的一个方法，或者说一个接口
- @ApiParam：单个参数描述
- @ApiModel：用对象来接收参数
- @ApiProperty：用对象接收参数时，描述对象的一个字段
- @ApiResponse：HTTP响应其中1个描述
- @ApiResponses：HTTP响应整体描述
- @ApiIgnore：使用该注解忽略这个API
- @ApiError ：发生错误返回的信息
- @ApiImplicitParam：描述一个请求参数，可以配置参数的中文含义，还可以给参数设置默认值
- @ApiImplicitParams：描述由多个 @ApiImplicitParam 注解的参数组成的请求参数列表<br/>
SpringBoot 启动成功后，访问 http://localhost:8080/swagger-ui.html<br/>
[官方文档](https://swagger.io/)
```
//Spring Boot 项目中如果集成了 Spring Security，在不做额外配置的情况下，Swagger2 文档会被拦截。解决方法是在 Security 的配置类中重写 configure 方法添加白名单即可
@Override
public void configure ( WebSecurity web) throws Exception {
    web.ignoring()
      .antMatchers("/swagger-ui.html")
      .antMatchers("/v2/**")
      .antMatchers("/swagger-resources/**");
} 
```

### 4.前端使用vue
[官方文档](https://cn.vuejs.org/v2/guide/)

### 参考
[使用git命令行将本地仓库代码上传到github或gitlab远程仓库](https://www.cnblogs.com/jpfss/p/10925852.html)<br/>
[IDEA工程代码提交到Github](https://www.cnblogs.com/ye-feng-yu/p/11108696.html)
