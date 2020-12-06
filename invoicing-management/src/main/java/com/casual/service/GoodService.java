package com.casual.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casual.vo.GoodVo;
import com.casual.entity.Good;
import com.baomidou.mybatisplus.extension.service.IService;
import com.casual.query.GoodQuery;

import java.util.List;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author Mousse
 * @since 2020-05-17
 */
public interface GoodService extends IService<Good> {

    void pageQuery(Page<Good> pageParam, GoodQuery goodQuery);

    List<GoodVo> getGoodList(Long page, Long limit, GoodQuery goodQuery);

    Integer getTotal(GoodQuery goodQuery);
}
