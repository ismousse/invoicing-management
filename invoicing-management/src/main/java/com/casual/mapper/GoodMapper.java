package com.casual.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.casual.vo.GoodVo;
import com.casual.entity.Good;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author Mousse
 * @since 2020-05-17
 */
public interface GoodMapper extends BaseMapper<Good> {
//    @Select({"<script>"
//            +"select g.id goodid, unitid, g.name goodname, u.name unitname from good g left join unit u on g.unitid = u.id "
//            +"<when test='flase' >where</when>"
//            +" ${ew.sqlSegment} limit #{page}, #{limit}"
//            +"</script>"})
    @Select("select g.id goodid, unitid, g.name goodname, u.name unitname from good g left join unit u on g.unitid = u.id where g.is_deleted=0 limit #{page}, #{limit}")
    List<GoodVo> getGoodList1(@Param("page")Long page, @Param("limit")Long limit);

    @Select("select g.id goodid, unitid, g.name goodname, u.name unitname from good g left join unit u on g.unitid = u.id where ${ew.sqlSegment} and g.is_deleted=0 limit #{page}, #{limit}")
    List<GoodVo> getGoodList2(@Param("page")Long page, @Param("limit")Long limit, @Param(Constants.WRAPPER)QueryWrapper<Good> queryWrapper);

    @Select("select count(*) from good g left join unit u on g.unitid = u.id where g.is_deleted=0")
    Integer getTotal1();

    @Select("select count(*) from good g left join unit u on g.unitid = u.id where ${ew.sqlSegment} and g.is_deleted=0")
    Integer getTotal2(@Param(Constants.WRAPPER)QueryWrapper<Good> queryWrapper);

}
