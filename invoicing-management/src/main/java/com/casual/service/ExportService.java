package com.casual.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casual.entity.Export;
import com.baomidou.mybatisplus.extension.service.IService;
import com.casual.query.ExportQuery;
import com.casual.vo.ExportVo;

import java.util.List;

/**
 * <p>
 * 出货表 服务类
 * </p>
 *
 * @author Mousse
 * @since 2020-05-17
 */
public interface ExportService extends IService<Export> {
    void pageQuery(Page<Export> pageParam, ExportQuery exportQuery);
    List<ExportVo> getExportList(Long page, Long limit, ExportQuery exportQuery);
}
