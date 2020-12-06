package com.casual.mapper;

import com.casual.entity.Unit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.casual.vo.UnitVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 单位表 Mapper 接口
 * </p>
 *
 * @author Mousse
 * @since 2020-05-17
 */
public interface UnitMapper extends BaseMapper<Unit> {
    @Select("select id value, name label from unit")
    List<UnitVo> getUnit();
}
