package com.casual.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casual.entity.Stock;
import com.baomidou.mybatisplus.extension.service.IService;
import com.casual.query.StockQuery;
import com.casual.vo.StockVo;

import java.util.List;

/**
 * <p>
 * 库存表 服务类
 * </p>
 *
 * @author Mousse
 * @since 2020-05-17
 */
public interface StockService extends IService<Stock> {

    void pageQuery(Page<Stock> pageParam, StockQuery stockQuery);

    List<StockVo> getStockList(Long page, Long limit, StockQuery stockQuery);
}
