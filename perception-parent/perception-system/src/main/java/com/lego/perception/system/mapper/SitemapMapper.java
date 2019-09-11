package com.lego.perception.system.mapper;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.mybatis.mapper.Mapper;
import com.lego.framework.system.model.entity.Sitemap;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SitemapMapper extends Mapper<Sitemap> {

    /**
     * 查询列表
     *
     * @param sitemap
     * @return
     */
    List<Sitemap> findList(Sitemap sitemap);

    /**
     * 分页查询
     *
     * @param sitemap
     * @param page
     * @return
     */
    PagedResult<Sitemap> findPagedList(Sitemap sitemap, Page page);

    /**
     * 新增
     *
     * @param sitemap
     * @return
     */
    Integer save(Sitemap sitemap);

    /**
     * 批量新增
     *
     * @param sitemaps
     * @return
     */
    Integer insertList(List<Sitemap> sitemaps);

    /**
     * 更新
     *
     * @param sitemap
     * @return
     */
    Integer update(Sitemap sitemap);

    /**
     * 批量更新
     *
     * @param sitemaps
     * @return
     */
    Integer updateList(List<Sitemap> sitemaps);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    Integer delete(Long id);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    Integer deleteList(List<Long> ids);
}
