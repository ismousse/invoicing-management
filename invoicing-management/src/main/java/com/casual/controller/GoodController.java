package com.casual.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.casual.entity.Unit;
import com.casual.query.GoodQuery;
import com.casual.service.UnitService;
import com.casual.vo.GoodVo;
import com.casual.entity.Good;
import com.casual.service.GoodService;
import com.casual.util.R;
import com.casual.vo.UnitVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author Mousse
 * @since 2020-05-17
 */
@Api(description = "商品管理")
@CrossOrigin
@RestController
@RequestMapping("/good")
public class GoodController {
    @Autowired
    private GoodService goodService;
    @Autowired
    private UnitService unitService;

    @ApiOperation(value = "商品列表")
    @GetMapping("{page}/{limit}")
    public R list(@ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Long page,
                  @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Long limit,
                  @ApiParam(name = "goodQuery", value = "查询对象", required = false) GoodQuery goodQuery){
        Integer total = null;
        if(goodQuery == null) {
            List<Good> goodlist = goodService.list(null);
            total = goodlist.size();
        } else {
            total = goodService.getTotal(goodQuery);
        }
        List<GoodVo> list =  goodService.getGoodList(page, limit, goodQuery);
        //List<Good> list = goodService.list(null);
        return R.ok().data("goods", list).data("total", total);
    }

    @ApiOperation(value = "根据id删除商品")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "商品id", required = true)
            @PathVariable String id){
        goodService.removeById(id);
        return R.ok();
    }

    /*@ApiOperation(value = "分页商品列表")
    @GetMapping("{page}/{limit}")
    public R pageQuery(
            @ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Long limit,
            @ApiParam(name = "goodQuery", value = "查询对象", required = false) GoodQuery goodQuery
    ){
        Page<Good> pageParam = new Page<>(page, limit);
        if(page <= 0 || limit <= 0){
            throw new MyException(ResultCodeEnum.PARAM_ERROR);
        }
        goodService.pageQuery(pageParam, goodQuery);
        List<Good> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return  R.ok().data("total", total).data("rows", records);
    }*/

    @ApiOperation(value = "新增商品")
    @PostMapping
    public R save(@ApiParam(name = "good", value = "商品", required = true)
                  @RequestBody GoodVo goodvo){
        Good good = new Good();
        if(null == goodvo.getUnitid()){
            Unit unit = new Unit();
            unit.setName(goodvo.getUnitname());
            unitService.save(unit);
            QueryWrapper<Unit> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name", goodvo.getUnitname());
            Unit unit2 = unitService.getOne(queryWrapper);
            good.setUnitid(unit2.getId());
            good.setName(goodvo.getGoodname());
            goodService.save(good);
            return R.ok();
        }
        good.setUnitid(goodvo.getUnitid());
        good.setName(goodvo.getGoodname());
        goodService.save(good);
        return R.ok();
    }

    @ApiOperation(value = "根据id查询商品")
    @GetMapping("{id}")
    public R getById(@ApiParam(name = "id", value = "商品id", required = true)
                     @PathVariable String id){
        Good good = goodService.getById(id);
        return R.ok().data("good", good);
    }

    @ApiOperation(value = "根据id修改商品")
    @PutMapping("{id}/{unitname}")
    public R updateById(
            @ApiParam(name = "id", value = "商品id", required = true)
            @PathVariable String id,
            @ApiParam(name = "good", value = "商品对象", required = true)
            @RequestBody Good good,
            @ApiParam(name = "unitname", value = "单位名称", required = true)
            @PathVariable String unitname
    ){
        Unit unit = unitService.getById(good.getUnitid());
        if(null == unit){
            Unit unit2 = new Unit();
            unit2.setName(unitname);
            unitService.save(unit2);
            QueryWrapper<Unit> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name", unitname);
            Unit unitId = unitService.getOne(queryWrapper);
            good.setId(id);
            good.setUnitid(unitId.getId());
            goodService.updateById(good);
            return R.ok();

        }
        good.setId(id);
        goodService.updateById(good);
        return R.ok();
    }

    @ApiOperation(value = "单位选择器")
    @GetMapping
    public R getUnit(){
        List<UnitVo> list = unitService.getUnit();//封装成value:id label:name
        return R.ok().data("options", list);
    }
}

