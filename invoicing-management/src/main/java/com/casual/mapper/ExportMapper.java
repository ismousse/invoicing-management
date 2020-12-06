package com.casual.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.casual.entity.Export;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.casual.vo.ExportVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 出货表 Mapper 接口
 * </p>
 *
 * @author Mousse
 * @since 2020-05-17
 */
public interface ExportMapper extends BaseMapper<Export> {
    @Select("select e.id exportid, g.id goodid, g.name goodname, e.count count, e.created created, e.username username from export e left join good g on e.goodid = g.id limit #{page}, #{limit}")
    List<ExportVo> getExportList1(@Param("page")long page, @Param("limit")Long limit);
    @Select("select e.id exportid, g.id goodid, g.name goodname, e.count count, e.created created, e.username username from export e left join good g on e.goodid = g.id where ${ew.sqlSegment} limit #{page}, #{limit}")
    List<ExportVo> getExportList2(@Param("page")long page, @Param("limit")Long limit, @Param(Constants.WRAPPER)QueryWrapper<Export> queryWrapper);
}
