package com.casual.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casual.entity.Stock;
import com.casual.mapper.StockMapper;
import com.casual.query.StockQuery;
import com.casual.service.StockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.casual.vo.StockVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 库存表 服务实现类
 * </p>
 *
 * @author Mousse
 * @since 2020-05-17
 */
@Service
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock> implements StockService {

    @Autowired
    private StockMapper stockMapper;

    @Override
    public void pageQuery(Page<Stock> pageParam, StockQuery stockQuery) {
        QueryWrapper<Stock> queryWrapper = new QueryWrapper<>();
        if (stockQuery == null){
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }
        String goodid = stockQuery.getGoodid();
        String name = stockQuery.getName();

        if (!StringUtils.isEmpty(goodid)) {
            queryWrapper.like("goodid", goodid);
        }

        if (!StringUtils.isEmpty(name) ) {
            queryWrapper.eq("name", name);
        }

        baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    public List<StockVo> getStockList(Long page, Long limit, StockQuery stockQuery) {
        QueryWrapper<Stock> queryWrapper = new QueryWrapper<>();
        String goodid = stockQuery.getGoodid();
        String name = stockQuery.getName();
        Integer count = stockQuery.getCount();
        if(StringUtils.isEmpty(goodid) && StringUtils.isEmpty(name) && count == null){
            return stockMapper.getStockList1(page - 1, limit);
        }
        if (!StringUtils.isEmpty(goodid)) {
            queryWrapper.eq("s.goodid", goodid);
        }
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.eq("g.name", name);
        }
        if (count != null) {
            queryWrapper.eq("s.count", count);
        }
        return stockMapper.getStockList2(page - 1, limit, queryWrapper);
    }
}
