package com.lego.perception.system.service;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespVO;
import com.lego.framework.system.model.entity.Sitemap;
import java.util.List;
import java.util.Map;

public interface ISitemapService {

    /**
     * 查询列表
     * @param sitemap
     * @return
     */
    List<Sitemap> findList(Sitemap sitemap);

    /**
     * 查询用户菜单
     * @return
     */
    List<Sitemap> findUserSitemap(String subSystem);

    /**
     * 分页查询
     * @param sitemap
     * @param page
     * @return
     */
    PagedResult<Sitemap> findPagedList(Sitemap sitemap, Page page);

    /**
     * 新增
     * @param sitemap
     * @return
     */
    RespVO insert(Sitemap sitemap);

    /**
     * 批量新增
     * @param sitemaps
     * @return
     */
    RespVO insertList(List<Sitemap> sitemaps);

    /**
     * 更新
     * @param sitemap
     * @return
     */
    RespVO update(Sitemap sitemap);

    /**
     * 批量更新
     * @param sitemaps
     * @return
     */
    RespVO updateList(List<Sitemap> sitemaps);

    /**
     * 删除
     * @param id
     * @return
     */
    RespVO delete(Long id);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    RespVO deleteList(List<Long> ids);

    /**
     * @param sitemap
     * @return
     */
    List<Sitemap> findAllList(Sitemap sitemap);
}
