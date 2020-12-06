package com.casual.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casual.entity.Unit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.casual.query.UnitQuery;
import com.casual.vo.UnitVo;

import java.util.List;

/**
 * <p>
 * 单位表 服务类
 * </p>
 *
 * @author Mousse
 * @since 2020-05-17
 */
public interface UnitService extends IService<Unit> {
    void pageQuery(Page<Unit> pageParam, UnitQuery unitQuery);
    List<UnitVo> getUnit();
}
