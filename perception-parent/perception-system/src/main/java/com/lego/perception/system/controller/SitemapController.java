package com.lego.perception.system.controller;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespVO;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.system.model.entity.Sitemap;
import com.lego.perception.system.service.ISitemapService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/sitemap")
@Resource(value = "sitemap", desc = "菜单管理")
@Api(value = "sitemap", description = "菜单管理")
@Slf4j
public class SitemapController {

    @Autowired
    private ISitemapService sitemapService;

    @ApiOperation(value = "查询用户菜单", httpMethod = "GET")
    @RequestMapping(value = "/findUserSitemap", method = RequestMethod.GET)
    public List<Sitemap> findUserSitemap(@RequestParam String subSystem) {

        return sitemapService.findUserSitemap(subSystem);
    }

    @ApiOperation(value = "查询菜单", httpMethod = "GET")
    @RequestMapping(value = "/findList", method = RequestMethod.GET)
    @Operation(value = "find", desc = "查询")
    public List<Sitemap> findList(@ModelAttribute Sitemap sitemap) {

        return sitemapService.findList(sitemap);
    }


    @ApiOperation(value = "查询菜单", httpMethod = "POST")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @Operation(value = "find", desc = "查询")
    public List<Sitemap> list(@RequestBody Sitemap sitemap) {
        return sitemapService.findAllList(sitemap);
    }


    @ApiOperation(value = "查询", httpMethod = "GET")
    @RequestMapping(value = "/findPagedList/{pageSize}/{pageIndex}", method = RequestMethod.GET)
    @Operation(value = "find", desc = "查询")
    public PagedResult<Sitemap> findPagedList(@ModelAttribute Sitemap sitemap, @PathParam("") Page page) {

        return sitemapService.findPagedList(sitemap, page);
    }

    @ApiOperation(value = "新增", httpMethod = "POST")
    @RequestMapping(value = "/insertList", method = RequestMethod.POST)
    @Operation(value = "add", desc = "新增")
    public RespVO insertList(@RequestBody List<Sitemap> sitemaps) {

        return sitemapService.insertList(sitemaps);
    }

    @ApiOperation(value = "新增", httpMethod = "POST")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @Operation(value = "add", desc = "新增")
    public RespVO insert(@RequestBody Sitemap sitemap) {

        return sitemapService.insert(sitemap);
    }


    @ApiOperation(value = "更新", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Operation(value = "update", desc = "更新")
    public RespVO update(@RequestBody Sitemap sitemap) {

        return sitemapService.update(sitemap);
    }

    @ApiOperation(value = "更新", httpMethod = "POST")
    @RequestMapping(value = "/updateList", method = RequestMethod.POST)
    @Operation(value = "update", desc = "更新")
    public RespVO updateList(@RequestBody List<Sitemap> sitemaps) {

        return sitemapService.updateList(sitemaps);
    }

    @ApiOperation(value = "删除", httpMethod = "DELETE")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @Operation(value = "delete", desc = "删除")
    public RespVO deleteList(@RequestParam Long id) {

        return sitemapService.delete(id);
    }

    @ApiOperation(value = "删除", httpMethod = "POST")
    @RequestMapping(value = "/deleteList", method = RequestMethod.POST)
    @Operation(value = "delete", desc = "删除")
    public RespVO deleteList(@RequestBody List<Long> ids) {

        return sitemapService.deleteList(ids);
    }
}
