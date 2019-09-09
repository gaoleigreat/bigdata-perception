package com.lego.perception.template.service;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespVO;
import com.lego.framework.template.model.entity.FormTemplate;

import java.util.List;

/**
 * 数据模板
 * weihao 2019-02-26
 */
public interface IFormTemplateService {

    /**
     * 分页查询
     * @param template
     * @param page
     * @return
     */
    PagedResult<FormTemplate> findPagedList(FormTemplate template, Page page);


    /**
     * @return
     */
    List<FormTemplate>  findAll();

    /**
     * 查询列表
     * @param dataTemplate
     * @return
     */
    List<FormTemplate> findList(FormTemplate dataTemplate);

    /**
     * 查询详情
     * @param dataTemplate
     * @return
     */
    FormTemplate find(FormTemplate dataTemplate);

    /**
     * 新增
     * @param dataTemplate
     * @return
     */
    RespVO insert(FormTemplate dataTemplate,Integer sourceType);

    /**
     * 更新
     * @param dataTemplate
     * @return
     */
    RespVO update(FormTemplate dataTemplate);

    /**
     * 删除
     * @param id
     * @return
     */
    RespVO delete(Long id);

    /**
     * @param id
     * @return
     */
    FormTemplate findById(Long id);

    /**
     * 查询模板字段
     * @param code
     * @return
     */
    RespVO<List<String>> queryFields(String code);
}
