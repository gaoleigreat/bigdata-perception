package com.lego.perception.template.service;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespVO;
import com.lego.framework.template.model.entity.DataTemplate;

import java.util.List;

/**
 * 数据模板
 * weihao 2019-02-26
 */
public interface IDataTemplateService {

    /**
     * 分页查询
     * @param template
     * @param page
     * @return
     */
    PagedResult<DataTemplate> findPagedList(DataTemplate template, Page page);


    /**
     * @return
     */
    List<DataTemplate>  findAll();


    /**
     * 查询列表
     * @param dataTemplate
     * @return
     */
    List<DataTemplate> findList(DataTemplate dataTemplate);

    /**
     * 查询详情
     * @param dataTemplate
     * @return
     */
    DataTemplate find(DataTemplate dataTemplate);

    /**
     * 新增
     * @param dataTemplate
     * @return
     */
    RespVO insert(DataTemplate dataTemplate);

    /**
     * 更新
     * @param dataTemplate
     * @return
     */
    RespVO update(DataTemplate dataTemplate);

    /**
     * 删除
     * @param id
     * @return
     */
    RespVO delete(Long id);

    /**
     * @param templateId
     * @return
     */
    DataTemplate findById(Long templateId);
}
