package com.casual.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casual.entity.Import;
import com.casual.entity.Stock;
import com.casual.mapper.ImportMapper;
import com.casual.query.ImportQuery;
import com.casual.service.ImportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.casual.vo.ImportVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 进货表 服务实现类
 * </p>
 *
 * @author Mousse
 * @since 2020-05-17
 */
@Service
public class ImportServiceImpl extends ServiceImpl<ImportMapper, Import> implements ImportService {

    @Autowired
    private ImportMapper importMapper;

    @Override
    public void pageQuery(Page<Import> pageParam, ImportQuery importQuery) {
        QueryWrapper<Import> queryWrapper = new QueryWrapper<>();
        if (importQuery == null){
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }
        String goodid = importQuery.getGoodid();
        String name = importQuery.getName();
        String username = importQuery.getUsername();
        String begin = importQuery.getBegin();
        String end = importQuery.getEnd();

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
    public List<ImportVo> getImportList(Long page, Long limit, ImportQuery importQuery) {
        QueryWrapper<Import> queryWrapper = new QueryWrapper<>();
        String goodid = importQuery.getGoodid();
        String name = importQuery.getName();
        String begin = importQuery.getBegin();
        String end = importQuery.getEnd();
        String username = importQuery.getUsername();
        if(StringUtils.isEmpty(goodid) && StringUtils.isEmpty(name) && StringUtils.isEmpty(begin)
                && StringUtils.isEmpty(end) && StringUtils.isEmpty(username)){
            return importMapper.getImportList1(page - 1, limit);
        }
        if (!StringUtils.isEmpty(goodid)) {
            queryWrapper.eq("g.id", goodid);
        }
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.eq("g.name", name);
        }
        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("i.created", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("i.created", end);
        }
        if (!StringUtils.isEmpty(username)) {
            queryWrapper.eq("i.username", username);
        }
        return importMapper.getImportList2(page - 1, limit, queryWrapper);
    }
}
