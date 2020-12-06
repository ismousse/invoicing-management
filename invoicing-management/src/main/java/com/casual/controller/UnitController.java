package com.casual.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casual.entity.Unit;
import com.casual.query.UnitQuery;
import com.casual.service.UnitService;
import com.casual.util.MyException;
import com.casual.util.R;
import com.casual.util.ResultCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 单位表 前端控制器
 * </p>
 *
 * @author Mousse
 * @since 2020-05-17
 */
@Api(description = "单位管理")
@CrossOrigin
@RestController
@RequestMapping("/unit")
public class UnitController {

    @Autowired
    private UnitService unitService;

    @ApiOperation(value = "单位列表")
    @GetMapping
    public R list(){
        List<Unit> list = unitService.list(null);
        return R.ok().data("units", list);
    }

    @ApiOperation(value = "根据id删除单位")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "单位id", required = true)
            @PathVariable String id){
        unitService.removeById(id);
        return R.ok();
    }

    @ApiOperation(value = "分页单位列表")
    @GetMapping("{page}/{limit}")
    public R pageQuery(
            @ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Long limit,
            @ApiParam(name = "unitQuery", value = "查询对象", required = false) UnitQuery unitQuery
    ){
        Page<Unit> pageParam = new Page<>(page, limit);
        if(page <= 0 || limit <= 0){
            throw new MyException(ResultCodeEnum.PARAM_ERROR);
        }
        unitService.pageQuery(pageParam, unitQuery);
        List<Unit> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return  R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "新增单位")
    @PostMapping
    public R save(@ApiParam(name = "unit", value = "单位", required = true)
                  @RequestBody Unit unit){
        unitService.save(unit);
        return R.ok();
    }

    @ApiOperation(value = "根据id查询单位")
    @GetMapping("{id}")
    public R getById(@ApiParam(name = "id", value = "单位id", required = true)
                     @PathVariable String id){
        Unit unit = unitService.getById(id);
        return R.ok().data("unit", unit);
    }

    @ApiOperation(value = "根据id修改单位")
    @PutMapping("{id}")
    public R updateById(
            @ApiParam(name = "id", value = "单位id", required = true)
            @PathVariable String id,
            @ApiParam(name = "unit", value = "单位对象", required = true)
            @RequestBody Unit unit
    ){
        unit.setId(id);
        unitService.updateById(unit);
        return R.ok();
    }
}

