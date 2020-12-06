package com.casual.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casual.entity.Unit;
import com.casual.mapper.UnitMapper;
import com.casual.query.UnitQuery;
import com.casual.service.UnitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.casual.vo.UnitVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 单位表 服务实现类
 * </p>
 *
 * @author Mousse
 * @since 2020-05-17
 */
@Service
public class UnitServiceImpl extends ServiceImpl<UnitMapper, Unit> implements UnitService {

    @Autowired
    private UnitMapper unitMapper;

    @Override
    public void pageQuery(Page<Unit> pageParam, UnitQuery unitQuery) {
        QueryWrapper<Unit> queryWrapper = new QueryWrapper<>();
        baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    public List<UnitVo> getUnit() {
        return unitMapper.getUnit();
    }
}
