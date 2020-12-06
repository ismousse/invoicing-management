package com.casual.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casual.entity.Export;
import com.casual.entity.Good;
import com.casual.entity.Stock;
import com.casual.entity.User;
import com.casual.query.ExportQuery;
import com.casual.service.ExportService;
import com.casual.service.GoodService;
import com.casual.service.StockService;
import com.casual.util.MyException;
import com.casual.util.R;
import com.casual.util.ResultCodeEnum;
import com.casual.util.WebUtils;
import com.casual.vo.ExportVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 出货表 前端控制器
 * </p>
 *
 * @author Mousse
 * @since 2020-05-17
 */
@Api(description = "出货管理")
@CrossOrigin
@RestController
@RequestMapping("/export")
public class ExportController {
    @Autowired
    private ExportService exportService;
    @Autowired
    private StockService stockService;
    @Autowired
    private GoodService goodService;

    @ApiOperation(value = "出货列表")
    @GetMapping("{page}/{limit}")
    public R list(@ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Long page,
                  @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Long limit,
                  @ApiParam(name = "exportQuery", value = "查询对象", required = false) ExportQuery exportQuery){
        List<ExportVo> list =  exportService.getExportList(page, limit, exportQuery);
        return R.ok().data("rows", list).data("total", list.size());
    }

    @ApiOperation(value = "根据id删除出货")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "出货id", required = true)
            @PathVariable String id){
        exportService.removeById(id);
        return R.ok();
    }

    /*@ApiOperation(value = "分页出货列表")
    @GetMapping("{page}/{limit}")
    public R pageQuery(
            @ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Long limit,
            @ApiParam(name = "exportQuery", value = "查询对象", required = false) ExportQuery exportQuery
    ){
        Page<Export> pageParam = new Page<>(page, limit);
        if(page <= 0 || limit <= 0){
            throw new MyException(ResultCodeEnum.PARAM_ERROR);
        }
        exportService.pageQuery(pageParam, exportQuery);
        List<Export> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return  R.ok().data("total", total).data("rows", records);
    }*/

    @ApiOperation(value = "新增出货")
    @PostMapping
    public R save(@ApiParam(name = "export", value = "出货", required = true) @RequestBody Export export){
//        Export export = new Export();
//        export.setCount(exportVo.getCount());
//        export.setGoodid(exportVo.getGoodid());
//        Cookie[] cookies =  request.getCookies();
//        String username = null;
//        if(cookies != null){
//            for(Cookie cookie : cookies){
//                if(cookie.getName().equals("user")){
//                    username = cookie.getValue();
//                }
//            }
//        }
//        System.out.println(username);
        // 同IP不同端口导致session冲突 sessionid会变 但是这个什么垃圾Cookie居然不区分端口的
        // 改变他们的sessionCookieName改为不同值。Tomcat下修改方式如下：
        // 在conf/context.xml文件中Context节点上增加 sessionCookieName="xxx" <Context sessionCookieName="JsessionId_XXX">
        // 官方文档对于sessionCookieName
        // The name to be used for all session cookies created for this context. If set,
        // this overrides any name set by the web application. If not set,
        // the value specified by the web application, if any, will be used,
        // or the name JSESSIONID if the web application does not explicitly set one.
        // 或者去修改IP映射，一个IP/域名对应一个应用，头疼
        User user = (User) WebUtils.getHttpSession().getAttribute("user");//就怕tm跨域问题
        export.setUsername(user.getUsername());
//        export.setUsername("admin");
        exportService.save(export);
        //修改库存
        QueryWrapper<Stock> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goodid", export.getGoodid());
        Stock stock = stockService.getOne(queryWrapper);
        Integer count = stock.getCount() - export.getCount();
        if(count < 0){
            return R.error();
        }
        stock.setCount(count);
        UpdateWrapper<Stock> updateWrapper = new UpdateWrapper<Stock>();
        updateWrapper.eq("goodid", export.getGoodid());
        stockService.update(stock, updateWrapper);
        return R.ok();
    }

    /*@ApiOperation(value = "根据id查询出货")
    @GetMapping("{id}")
    public R getById(@ApiParam(name = "id", value = "出货id", required = true)
                     @PathVariable String id){
        Export export = exportService.getById(id);
        return R.ok().data("export", export);
    }*/

    @ApiOperation(value = "根据id修改出货")
    @PutMapping("{id}")
    public R updateById(
            @ApiParam(name = "id", value = "出货id", required = true)
            @PathVariable String id,
            @ApiParam(name = "export", value = "出货对象", required = true)
            @RequestBody Export export
    ){
        export.setId(id);
        exportService.updateById(export);
        return R.ok();
    }

    @ApiOperation(value = "商品选择器")
    @GetMapping
    public R getGood(){
        //出货从库存去填充Selector
        List<Good> goodlist = new ArrayList<>();
        QueryWrapper<Stock> stockQueryWrapper = new QueryWrapper<>();
        stockQueryWrapper.ge("count", 0);
        List<Stock> stocklist = stockService.list(stockQueryWrapper);
        for(int i = 0; i < stocklist.size(); i++) {
            Stock stock = stocklist.get(i);
            QueryWrapper<Good> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", stock.getGoodid());
            Good good = goodService.getOne(queryWrapper);
            if(good != null) {
                goodlist.add(good);
            }
        }
        return R.ok().data("options", goodlist);
    }

    @ApiOperation(value = "根据商品id获取库存数量")
    @GetMapping("{goodid}")
    public R getCountByGoodId(
            @ApiParam(name = "goodid", value = "商品id", required = true)
            @PathVariable String goodid
    ){
        QueryWrapper<Stock> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goodid", goodid);
        Stock stock = stockService.getOne(queryWrapper);
        return R.ok().data("count", stock.getCount());
    }

}

