package com.lego.perception.business.controller;

import com.framework.common.consts.RespConsts;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.base.exception.ExceptionBuilder;
import com.lego.framework.business.model.entity.Business;
import com.lego.framework.system.feign.SitemapClient;
import com.lego.framework.system.model.entity.Sitemap;
import com.lego.framework.template.feign.TemplateFeignClient;
import com.lego.framework.template.model.entity.FormTemplate;
import com.lego.perception.business.service.IBusinessService;
import com.lego.perception.business.service.ICrudService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-09-17 10:04:50
 * @since jdk 1.8
 */
@RestController
@RequestMapping("/business")
@Api(value = "business", description = "业务管理")
@Resource(value = "business", desc = "业务管理")
public class BusinessController {


    @Autowired
    private IBusinessService iBusinessService;

    @Autowired
    private ICrudService iCrudService;

    @Autowired
    private TemplateFeignClient templateFeignClient;

    @Autowired
    private SitemapClient sitemapClient;

    /**
     * 分页查询数据
     */
    @Operation(value = "select_paged", desc = "查询业务")
    @ApiOperation(value = "查询业务", httpMethod = "GET")
    @RequestMapping(value = "/select_paged/{pageSize}/{pageIndex}", method = RequestMethod.GET)
    public RespVO<PagedResult<Business>> selectPaged(@ModelAttribute Business business,
                                                     @PathParam(value = "") Page page) {
        return RespVOBuilder.success(iBusinessService.selectPaged(business, page));
    }

    /**
     * 通过id查询
     *
     * @return
     */
    @Operation(value = "select_by_id", desc = "查询业务")
    @ApiOperation(value = "查询业务", httpMethod = "GET")
    @RequestMapping(value = "/select_by_id", method = RequestMethod.GET)
    public RespVO<Business> selectByPrimaryKey(@RequestParam Long id) {
        Business business = iBusinessService.selectByPrimaryKey(id);
        return RespVOBuilder.success(business);
    }

    /**
     * 通过ID删除
     *
     * @return
     */
    @Operation(value = "delete_by_id", desc = "删除业务")
    @ApiOperation(value = "通过ID删除", httpMethod = "DELETE")
    @RequestMapping(value = "/delete_by_id", method = RequestMethod.DELETE)
    public RespVO deleteByPrimaryKey(Long id) {
        Integer num = iBusinessService.deleteByPrimaryKey(id);
        if (num > 0) {
            return RespVOBuilder.success();
        }
        return RespVOBuilder.failure();
    }

    /**
     * 新增数据
     *
     * @return
     */
    @Operation(value = "save_tplBusiness", desc = "新增业务")
    @ApiOperation(value = "新增业务", httpMethod = "POST")
    @RequestMapping(value = "/save_tplBusiness", method = RequestMethod.POST)
    @Transactional(rollbackFor = RuntimeException.class)
    public RespVO insert(@RequestBody Business business,@RequestParam(value = "parentId") Long parentId) {
        String templateCode = business.getTemplateCode();
        Business queryBusiness = new Business();
        queryBusiness.setTemplateCode(templateCode);
        List<Business> queryBusinessList = iBusinessService.query(queryBusiness);
        if (!CollectionUtils.isEmpty(queryBusinessList)) {
            return RespVOBuilder.failure("模板对应业务已经存在");
        }
        RespVO<FormTemplate> respVO = templateFeignClient.findFormTemplateByCode(templateCode);
        if (respVO.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
            ExceptionBuilder.operateFailException(respVO.getMsg());
        }
        FormTemplate template = respVO.getInfo();
        if (template == null) {
            ExceptionBuilder.operateFailException("模板不存在");
        }
        business.setType(template.getType());
        business.setTableName(template.getDescription());
        Integer num = iBusinessService.insertSelective(business);
        if (num <= 0) {
            ExceptionBuilder.operateFailException("业务表创建失败");
        }
        RespVO vo = iCrudService.createBusinessTable(template);
        if (vo.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
            ExceptionBuilder.operateFailException(vo.getMsg());
        }
        Sitemap querySitemap = new Sitemap();
        if (parentId ==null){
            querySitemap.setName("业务管理");
            querySitemap.setSubSystem("basicManage");
            querySitemap.setType(1);
        }else{
            querySitemap.setId(parentId);
        }
        List<Sitemap> list = sitemapClient.list(querySitemap);
        if (CollectionUtils.isEmpty(list)) {
            // 创建业务菜单
            ExceptionBuilder.operateFailException("请先创建业务管理菜单");
        }
        querySitemap = list.get(0);
        Sitemap sitemap = new Sitemap();
        sitemap.setName(business.getName());
        sitemap.setType(2);
        sitemap.setStatus("1");
        sitemap.setParentId(querySitemap.getId());
        sitemap.setSubSystem(querySitemap.getName());
        sitemap.setUrl("templatePage" + "?" + business.getTemplateCode());
        return sitemapClient.insert(sitemap);
    }

    /**
     * 修改数据
     *
     * @return
     */
    @Operation(value = "update_tplBusiness", desc = "修改业务")
    @ApiOperation(value = "修改业务", httpMethod = "PUT")
    @RequestMapping(value = "/update_tplBusiness", method = RequestMethod.PUT)
    public RespVO updateByPrimaryKeySelective(Business business) {
        Integer num = iBusinessService.updateByPrimaryKeySelective(business);
        if (num > 0) {
            return RespVOBuilder.success();
        }
        return RespVOBuilder.failure();
    }


    /**
     * 查询列表
     *
     * @return
     */
    @Operation(value = "query_list", desc = "查询业务")
    @ApiOperation(value = "查询业务", httpMethod = "GET")
    @RequestMapping(value = "/query_list", method = RequestMethod.GET)
    public RespVO<RespDataVO<Business>> queryByCondition(Business business) {
        List<Business> list = iBusinessService.query(business);
        return RespVOBuilder.success(list);
    }

}
