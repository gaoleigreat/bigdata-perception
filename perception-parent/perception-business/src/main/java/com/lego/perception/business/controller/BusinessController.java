package com.lego.perception.business.controller;

import com.framework.common.consts.RespConsts;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.annotation.Resource;
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
    @ApiOperation(value = "通过ID删除", httpMethod = "DELETE")
    @RequestMapping(value = "/delete_by_id", method = RequestMethod.DELETE)
    public RespVO deleteByPrimaryKey(Long id) {
        Integer num = iBusinessService.deleteByPrimaryKey(id);
        return RespVOBuilder.success();
    }

    /**
     * 新增数据
     *
     * @return
     */
    @ApiOperation(value = "新增业务", httpMethod = "POST")
    @RequestMapping(value = "/save_tplBusiness", method = RequestMethod.POST)
    public RespVO insert(@RequestBody Business business) {
        Integer num = iBusinessService.insertSelective(business);
        if (num <= 0) {
            return RespVOBuilder.failure();
        }
        RespVO<FormTemplate> respVO = templateFeignClient.findFormTemplateByCode(business.getTemplateCode());
        if (respVO.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
            return RespVOBuilder.failure();
        }
        FormTemplate template = respVO.getInfo();
        if (template == null) {
            return RespVOBuilder.failure();
        }
        RespVO vo = iCrudService.createBusinessTable(template);
        if (vo.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
            return RespVOBuilder.failure();
        }
        //TODO  处理菜单
        Sitemap sitemap = new Sitemap();
        sitemap.setName(business.getName());
        sitemap.setType(2);
        sitemap.setStatus("1");
        sitemap.setUrl(business.getTemplateCode());
        return sitemapClient.insert(sitemap);
    }

    /**
     * 修改数据
     *
     * @return
     */
    @ApiOperation(value = "修改业务", httpMethod = "PUT")
    @RequestMapping(value = "/update_tplBusiness", method = RequestMethod.PUT)
    public RespVO updateByPrimaryKeySelective(Business business) {
        Integer num = iBusinessService.updateByPrimaryKeySelective(business);
        return RespVOBuilder.success();
    }


    /**
     * 查询列表
     *
     * @return
     */
    @ApiOperation(value = "查询业务", httpMethod = "PUT")
    @RequestMapping(value = "/query_list", method = RequestMethod.GET)
    public RespVO<RespDataVO<Business>> queryByCondition(Business business) {
        List<Business> list = iBusinessService.query(business);
        return RespVOBuilder.success(list);
    }

}
