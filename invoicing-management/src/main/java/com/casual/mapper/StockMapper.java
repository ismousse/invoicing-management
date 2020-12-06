package com.casual.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.casual.entity.Good;
import com.casual.entity.Stock;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.casual.vo.GoodVo;
import com.casual.vo.StockVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 库存表 Mapper 接口
 * </p>
 *
 * @author Mousse
 * @since 2020-05-17
 */
public interface StockMapper extends BaseMapper<Stock> {

    @Select("select s.id stockid, s.goodid goodid, g.name goodname, s.count count from stock s left join good g on s.goodid = g.id limit #{page}, #{limit}")
    List<StockVo> getStockList1(@Param("page")Long page, @Param("limit")Long limit);

    @Select("select s.id stockid, s.goodid goodid, g.name goodname, s.count count from stock s left join good g on s.goodid = g.id where ${ew.sqlSegment} limit #{page}, #{limit}")
    List<StockVo> getStockList2(@Param("page")Long page, @Param("limit")Long limit, @Param(Constants.WRAPPER) QueryWrapper<Stock> queryWrapper);

}
