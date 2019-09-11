package com.lego.perception.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.CurrentVo;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.framework.mybatis.tool.WhereEntityTool;
import com.framework.mybatis.utils.PageUtil;
import com.lego.framework.base.context.RequestContext;
import com.lego.framework.system.model.entity.Sitemap;
import com.lego.perception.system.mapper.SitemapMapper;
import com.lego.perception.system.service.ISitemapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@Slf4j
public class SitemapServiceImpl implements ISitemapService {


    @Autowired
    private SitemapMapper sitemapMapper;

    @Override
    public List<Sitemap> findList(Sitemap sitemap) {
        List<Sitemap> result = new ArrayList<>();
        List<Sitemap> list = sitemapMapper.findList(sitemap);
        if (CollectionUtils.isEmpty(list)) {
            return result;
        }

        result = convertList2Tree(list);

        return result;
    }

    private List<Sitemap> convertList2Tree(List<Sitemap> list) {
        List<Sitemap> result;
        Map<Long, List<Sitemap>> map = new HashMap<>();
        for (Sitemap d : list) {
            if (map.containsKey(d.getParentId())) {
                map.get(d.getParentId()).add(d);
            } else {
                List<Sitemap> l = new ArrayList<>();
                l.add(d);
                map.put(d.getParentId(), l);
            }
        }
        result = map.get(-1L);
        if (!CollectionUtils.isEmpty(result)) {
            setChildren(result, map);
        }
        return result;
    }

    @Override
    public List<Sitemap> findUserSitemap(String subSystem) {
        Sitemap queryParam = new Sitemap();
        queryParam.setSubSystem(subSystem);
        List<Sitemap> sitemaps = sitemapMapper.findList(queryParam);
        CurrentVo currentVo = RequestContext.getCurrent();
        if (currentVo != null) {
            Set<String> permissions = currentVo.getPermissions();
            List<Sitemap> result = new ArrayList<>();
            for (Sitemap sitemap : sitemaps) {
                if (StringUtils.isEmpty(sitemap.getPermission()) ||
                        permissions.contains(sitemap.getPermission())) {
                    result.add(sitemap);
                }
            }
            if (!CollectionUtils.isEmpty(result)) {
                result = convertList2Tree(result);
            }
            return result;
        }
        return null;
    }

    public void setChildren(List<Sitemap> sitemaps, Map<Long, List<Sitemap>> map) {
        if (CollectionUtils.isEmpty(sitemaps)) {
            return;
        }
        for (Sitemap sitemap : sitemaps) {
            sitemap.setChildren(map.get(sitemap.getId()));
            setChildren(sitemap.getChildren(), map);
        }
    }

    @Override
    public PagedResult<Sitemap> findPagedList(Sitemap sitemap, Page page) {
        return PageUtil.queryPaged(page, sitemap, sitemapMapper);
    }

    @Override
    public RespVO insert(Sitemap sitemap) {
        if (null == sitemap) {
            return RespVOBuilder.failure("参数错误");
        }
        sitemap.setCreateInfo();
        if (null == sitemap.getParentId()) {
            sitemap.setParentId(-1L);
        }
        sitemapMapper.insert(sitemap);
        return RespVOBuilder.success();
    }

    @Override
    public RespVO insertList(List<Sitemap> sitemaps) {
        if (CollectionUtils.isEmpty(sitemaps)) {
            return RespVOBuilder.failure("参数错误");
        }
        for (Sitemap sitemap : sitemaps) {
            sitemap.setCreateInfo();
            if (null == sitemap.getParentId()) {
                sitemap.setParentId(-1L);
            }
        }
        sitemapMapper.insertList(sitemaps);
        return RespVOBuilder.success();
    }

    @Override
    public RespVO update(Sitemap sitemap) {
        if (null == sitemap) {
            return RespVOBuilder.failure("参数错误");
        }
        sitemap.setUpdateInfo();
        sitemapMapper.update(sitemap);
        return RespVOBuilder.success();
    }

    @Override
    public RespVO updateList(List<Sitemap> sitemaps) {
        if (CollectionUtils.isEmpty(sitemaps)) {
            return RespVOBuilder.failure("参数错误");
        }
        for (Sitemap sitemap : sitemaps) {
            sitemap.setUpdateInfo();
        }
        sitemapMapper.updateList(sitemaps);
        return RespVOBuilder.success();
    }

    @Override
    public RespVO delete(Long id) {
        if (null == id) {
            return RespVOBuilder.failure("参数错误");
        }
        sitemapMapper.delete(id);
        return RespVOBuilder.success();
    }

    @Override
    public RespVO deleteList(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return RespVOBuilder.failure("参数错误");
        }
        sitemapMapper.deleteList(ids);
        return RespVOBuilder.success();
    }
}
