package com.casual.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casual.entity.Stock;
import com.casual.query.StockQuery;
import com.casual.service.StockService;
import com.casual.util.MyException;
import com.casual.util.R;
import com.casual.util.ResultCodeEnum;
import com.casual.vo.StockVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 库存表 前端控制器
 * </p>
 *
 * @author Mousse
 * @since 2020-05-17
 */
@Api(description = "库存管理")
@CrossOrigin
@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @ApiOperation(value = "库存列表")
    @GetMapping("{page}/{limit}")
    public R list(@ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Long page,
                  @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Long limit,
                  @ApiParam(name = "stockQuery", value = "查询对象", required = false) StockQuery stockQuery){
        List<StockVo> list =  stockService.getStockList(page, limit, stockQuery);
        return R.ok().data("rows", list).data("total", list.size());
    }

    @ApiOperation(value = "根据id删除库存")
    @DeleteMapping("{id}")
    public R removeById(@ApiParam(name = "id", value = "库存id", required = true) @PathVariable String id){
        stockService.removeById(id);
        return R.ok();
    }

    /*@ApiOperation(value = "分页列表")
    @GetMapping("{page}/{limit}")
    public R pageQuery(
            @ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Long limit,
            @ApiParam(name = "stockQuery", value = "查询对象", required = false) StockQuery stockQuery){
        Page<Stock> pageParam = new Page<>(page, limit);
        if(page <= 0 || limit <= 0){
            throw new MyException(ResultCodeEnum.PARAM_ERROR);
        }
        stockService.pageQuery(pageParam, stockQuery);
        List<Stock> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return  R.ok().data("total", total).data("rows", records);
    }*/

    @ApiOperation(value = "新增库存")
    @PostMapping
    public R save(@ApiParam(name = "stock", value = "库存", required = true)
                  @RequestBody Stock stock){
        stockService.save(stock);
        return R.ok();
    }

    @ApiOperation(value = "根据id查询库存")
    @GetMapping("{id}")
    public R getById(@ApiParam(name = "id", value = "库存id", required = true)
                     @PathVariable String id){
        Stock stock = stockService.getById(id);
        return R.ok().data("stock", stock);
    }

    @ApiOperation(value = "根据id修改库存")
    @PutMapping("{id}")
    public R updateById(
            @ApiParam(name = "id", value = "库存id", required = true)
            @PathVariable String id,
            @ApiParam(name = "stock", value = "库存对象", required = true)
            @RequestBody Stock stock
    ){
        stock.setId(id);
        stockService.updateById(stock);
        return R.ok();
    }
}

