package com.lego.perception.template.controller;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.template.model.entity.BusinessTemplate;
import com.lego.perception.template.service.IBusinessTemplateService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-09-03 03:40:16
 * @since jdk 1.8
 */
@RestController
@RequestMapping("/tplbusinesstemplate")
public class BusinessTemplateController {

    @Autowired
    private IBusinessTemplateService tplBusinessTemplateService;

    /**
     * 分页查询数据
     */
    @RequestMapping("/select_paged/{pageSize}/{pageIndex}")
    public RespVO<PagedResult<BusinessTemplate>> selectPaged(@ModelAttribute BusinessTemplate businessTemplate,
                                                             @PathParam(value = "")Page page) {
        PagedResult<BusinessTemplate> pagedResult = tplBusinessTemplateService.selectPaged(businessTemplate,page);
        return RespVOBuilder.success(pagedResult);
    }

    /**
     * 通过id查询
     *
     * @return
     */
    @RequestMapping("/select_by_id")
    public RespVO<BusinessTemplate> selectByPrimaryKey(Long id) {
        BusinessTemplate po = tplBusinessTemplateService.selectByPrimaryKey(id);
        return RespVOBuilder.success(po);
    }

    /**
     * 通过ID删除
     *
     * @return
     */
    @RequestMapping("/delete_by_id")
    public RespVO deleteByPrimaryKey(Long id) {
        Integer num = tplBusinessTemplateService.deleteByPrimaryKey(id);
        return RespVOBuilder.success();
    }

    /**
     * 新增数据
     *
     * @return
     */
    @RequestMapping("/save_tplBusinessTemplate")
    public RespVO insert(BusinessTemplate businessTemplate) {
        Integer num = tplBusinessTemplateService.insertSelective(businessTemplate);
        return RespVOBuilder.success();
    }

    /**
     * 修改数据
     *
     * @return
     */
    @RequestMapping("/update_tplBusinessTemplate")
    public RespVO updateByPrimaryKeySelective(BusinessTemplate tplBusinessTemplate) {
        Integer num = tplBusinessTemplateService.updateByPrimaryKeySelective(tplBusinessTemplate);
        return RespVOBuilder.success();
    }


    /**
     * 查询列表
     * @return
     */
    @RequestMapping("/query_list")
    public RespVO<RespDataVO<BusinessTemplate>> queryByCondition(BusinessTemplate businessTemplate) {
        List<BusinessTemplate> list = tplBusinessTemplateService.query(businessTemplate);
        return RespVOBuilder.success(list);
    }

}
