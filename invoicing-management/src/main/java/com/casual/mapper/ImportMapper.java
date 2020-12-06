package com.casual.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.casual.entity.Import;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.casual.vo.ImportVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 进货表 Mapper 接口
 * </p>
 *
 * @author Mousse
 * @since 2020-05-17
 */
public interface ImportMapper extends BaseMapper<Import> {
    @Select("select i.id importid, g.id goodid, g.name goodname, i.count count, i.created created, i.username username from import i left join good g on i.goodid = g.id limit #{page}, #{limit}")
    List<ImportVo> getImportList1(@Param("page")long page, @Param("limit")Long limit);
    @Select("select i.id importid, g.id goodid, g.name goodname, i.count count, i.created created, i.username username from import i left join good g on i.goodid = g.id where ${ew.sqlSegment} limit #{page}, #{limit}")
    List<ImportVo> getImportList2(@Param("page")long page, @Param("limit")Long limit, @Param(Constants.WRAPPER) QueryWrapper<Import> queryWrapper);
}
