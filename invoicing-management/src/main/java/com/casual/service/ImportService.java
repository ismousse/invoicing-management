package com.casual.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casual.entity.Import;
import com.baomidou.mybatisplus.extension.service.IService;
import com.casual.entity.Stock;
import com.casual.query.ImportQuery;
import com.casual.vo.ImportVo;

import java.util.List;

/**
 * <p>
 * 进货表 服务类
 * </p>
 *
 * @author Mousse
 * @since 2020-05-17
 */
public interface ImportService extends IService<Import> {

    void pageQuery(Page<Import> pageParam, ImportQuery importQuery);

    List<ImportVo> getImportList(Long page, Long limit, ImportQuery importQuery);
}
