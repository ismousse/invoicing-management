package com.casual.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casual.vo.GoodVo;
import com.casual.entity.Good;
import com.casual.mapper.GoodMapper;
import com.casual.query.GoodQuery;
import com.casual.service.GoodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author Mousse
 * @since 2020-05-17
 */
@Service
public class GoodServiceImpl extends ServiceImpl<GoodMapper, Good> implements GoodService {

    @Autowired
    private GoodMapper goodMapper;

    @Override
    public void pageQuery(Page<Good> pageParam, GoodQuery goodQuery) {
        QueryWrapper<Good> queryWrapper = new QueryWrapper<>();
        if (goodQuery == null){
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }
        String id = goodQuery.getId();
        String name = goodQuery.getName();

        if (!StringUtils.isEmpty(id)) {
            queryWrapper.eq("id", id);
        }

        if (!StringUtils.isEmpty(name)) {
            queryWrapper.eq("name", name);
        }

        baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    public List<GoodVo> getGoodList(Long page, Long limit, GoodQuery goodQuery) {
        QueryWrapper<Good> queryWrapper = new QueryWrapper<>();
//        if (goodQuery == null){
//            System.out.println("=======");
//            return goodMapper.getGoodList1(page, limit);
//        }
        String id = goodQuery.getId();
        String name = goodQuery.getName();
        if(StringUtils.isEmpty(id) && StringUtils.isEmpty(name)){
            return goodMapper.getGoodList1((page - 1)*limit, limit);
        }
        if (!StringUtils.isEmpty(id)) {
            queryWrapper.eq("g.id", id);
        }
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.eq("g.name", name);
        }
        return goodMapper.getGoodList2((page - 1)*limit, limit, queryWrapper);
    }

    @Override
    public Integer getTotal(GoodQuery goodQuery) {
        QueryWrapper<Good> queryWrapper = new QueryWrapper<>();
        String id = goodQuery.getId();
        String name = goodQuery.getName();
        if(StringUtils.isEmpty(id) && StringUtils.isEmpty(name)){
            return goodMapper.getTotal1();
        }
        if (!StringUtils.isEmpty(id)) {
            queryWrapper.eq("g.id", id);
        }
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.eq("g.name", name);
        }
        return goodMapper.getTotal2(queryWrapper);
    }
}
