package com.casual.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.casual.entity.Good;
import com.casual.entity.Import;
import com.casual.entity.Stock;
import com.casual.entity.User;
import com.casual.query.ImportQuery;
import com.casual.service.GoodService;
import com.casual.service.ImportService;
import com.casual.service.StockService;
import com.casual.util.MyException;
import com.casual.util.R;
import com.casual.util.ResultCodeEnum;
import com.casual.util.WebUtils;
import com.casual.vo.ImportVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 进货表 前端控制器
 * </p>
 *
 * @author Mousse
 * @since 2020-05-17
 */
@Api(description = "进货管理")
@CrossOrigin
@RestController
@RequestMapping("/import")
public class ImportController {
    @Autowired
    private ImportService importService;
    @Autowired
    private StockService stockService;
    @Autowired
    private GoodService goodService;

    @ApiOperation(value = "进货列表")
    @GetMapping("{page}/{limit}")
    public R list(@ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Long page,
                  @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Long limit,
                  @ApiParam(name = "importQuery", value = "查询对象", required = false) ImportQuery importQuery){
        List<ImportVo> list =  importService.getImportList(page, limit, importQuery);
        return R.ok().data("rows", list).data("total", list.size());
    }

    @ApiOperation(value = "根据id删除进货")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "进货id", required = true)
            @PathVariable String id){
        importService.removeById(id);
        return R.ok();
    }

    @ApiOperation(value = "新增进货")
    @PostMapping
    public R save(@ApiParam(name = "import", value = "进货", required = true)
                  @RequestBody Import import2){
        import2.setUsername(((User)WebUtils.getHttpSession().getAttribute("user")).getUsername());
//        import2.setUsername("admin");
        importService.save(import2);
        //修改库存
        QueryWrapper<Stock> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goodid", import2.getGoodid());
        Stock stock = stockService.getOne(queryWrapper);
        //判断库存中是否存在
        if(null == stock){
            Stock stock1 = new Stock();
            stock1.setGoodid(import2.getGoodid());
            if(import2.getCount() < 0){
                return R.error();
            }
            stock1.setCount(import2.getCount());
            stockService.save(stock1);
        }else{
            Integer count = stock.getCount() + import2.getCount();
            if(count < 0){
                return R.error();
            }
            stock.setCount(count);
            UpdateWrapper<Stock> updateWrapper = new UpdateWrapper<Stock>();
            updateWrapper.eq("goodid", import2.getGoodid());
            stockService.update(stock, updateWrapper);
        }
        return R.ok();
    }

    @ApiOperation(value = "根据id修改进货")
    @PutMapping("{id}")
    public R updateById(
            @ApiParam(name = "id", value = "进货id", required = true)
            @PathVariable String id,
            @ApiParam(name = "import", value = "进货对象", required = true)
            @RequestBody Import import2
    ){
        import2.setId(id);
        importService.updateById(import2);
        return R.ok();
    }

    @ApiOperation(value = "商品选择器")
    @GetMapping
    public R getGood(){
        List<Good> list = goodService.list(null);//id:value name:label
        return R.ok().data("options", list);
    }

}

