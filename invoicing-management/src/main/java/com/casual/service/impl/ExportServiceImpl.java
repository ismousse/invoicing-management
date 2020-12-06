package com.casual.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casual.entity.Export;
import com.casual.entity.Stock;
import com.casual.mapper.ExportMapper;
import com.casual.query.ExportQuery;
import com.casual.service.ExportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.casual.vo.ExportVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 出货表 服务实现类
 * </p>
 *
 * @author Mousse
 * @since 2020-05-17
 */
@Service
public class ExportServiceImpl extends ServiceImpl<ExportMapper, Export> implements ExportService {

    @Autowired
    private ExportMapper exportMapper;

    @Override
    public void pageQuery(Page<Export> pageParam, ExportQuery exportQuery) {
        QueryWrapper<Export> queryWrapper = new QueryWrapper<>();
        if (exportQuery == null){
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }
        String goodid = exportQuery.getGoodid();
        String name = exportQuery.getName();
        String username = exportQuery.getUsername();
        String begin = exportQuery.getBegin();
        String end = exportQuery.getEnd();

        if (!StringUtils.isEmpty(goodid)) {
            queryWrapper.eq("goodid", goodid);
        }

        if (!StringUtils.isEmpty(name) ) {
            queryWrapper.eq("name", name);
        }

        if (!StringUtils.isEmpty(username) ) {
            queryWrapper.eq("username", username);
        }

        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("created", begin);
        }

        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("modified", end);
        }

        baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    public List<ExportVo> getExportList(Long page, Long limit, ExportQuery exportQuery) {
        QueryWrapper<Export> queryWrapper = new QueryWrapper<>();
//        if (goodQuery == null){
//            System.out.println("=======");
//            return goodMapper.getGoodList1(page, limit);
//        }
        String goodid = exportQuery.getGoodid();
        String name = exportQuery.getName();
        String begin = exportQuery.getBegin();
        String end = exportQuery.getEnd();
        String username = exportQuery.getUsername();
        if(StringUtils.isEmpty(goodid) && StringUtils.isEmpty(name) && StringUtils.isEmpty(begin)
            && StringUtils.isEmpty(end) && StringUtils.isEmpty(username)){
            return exportMapper.getExportList1(page - 1, limit);
        }
        if (!StringUtils.isEmpty(goodid)) {
            queryWrapper.eq("g.id", goodid);
        }
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.eq("g.name", name);
        }
        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("e.created", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("e.created", end);
        }
        if (!StringUtils.isEmpty(username)) {
            queryWrapper.eq("e.username", username);
        }
        return exportMapper.getExportList2(page - 1, limit, queryWrapper);
    }
}
