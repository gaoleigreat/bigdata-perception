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
import org.springframework.web.bind.annotation.*;

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
                                                             @PathParam(value = "") Page page) {
        PagedResult<BusinessTemplate> pagedResult = tplBusinessTemplateService.selectPaged(businessTemplate, page);
        return RespVOBuilder.success(pagedResult);
    }

    /**
     * 通过id查询
     *
     * @return
     */
    @RequestMapping(value = "/select_by_id", method = RequestMethod.GET)
    public RespVO<BusinessTemplate> selectByPrimaryKey(@RequestParam Long id) {
        BusinessTemplate po = tplBusinessTemplateService.selectByPrimaryKey(id);
        return RespVOBuilder.success(po);
    }

    /**
     * 通过ID删除
     *
     * @return
     */
    @RequestMapping(value = "/delete_by_id", method = RequestMethod.DELETE)
    public RespVO deleteByPrimaryKey(@RequestParam Long id) {
        Integer num = tplBusinessTemplateService.deleteByPrimaryKey(id);
        return RespVOBuilder.success();
    }

    /**
     * 新增数据
     *
     * @return
     */
    @RequestMapping(value = "/save_tplBusinessTemplate", method = RequestMethod.PUT)
    public RespVO insert(@RequestBody BusinessTemplate businessTemplate) {
        Integer num = tplBusinessTemplateService.insertSelective(businessTemplate);
        return RespVOBuilder.success();
    }

    /**
     * 修改数据
     *
     * @return
     */
    @RequestMapping(value = "/update_tplBusinessTemplate", method = RequestMethod.PUT)
    public RespVO updateByPrimaryKeySelective(@RequestBody BusinessTemplate tplBusinessTemplate) {
        Integer num = tplBusinessTemplateService.updateByPrimaryKeySelective(tplBusinessTemplate);
        return RespVOBuilder.success();
    }


    /**
     * 查询列表
     *
     * @return
     */
    @RequestMapping(value = "/query_list",method = RequestMethod.GET)
    public RespVO<RespDataVO<BusinessTemplate>> queryByCondition(BusinessTemplate businessTemplate) {
        List<BusinessTemplate> list = tplBusinessTemplateService.query(businessTemplate);
        return RespVOBuilder.success(list);
    }

}
