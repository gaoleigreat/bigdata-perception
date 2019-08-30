package com.lego.perception.template.mapper;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.mybatis.mapper.Mapper;
import com.lego.framework.template.model.entity.FormTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormTemplateMapper extends Mapper<FormTemplate> {

    /**
     * 分页查询
     * @param template
     * @param page
     * @return
     */
    PagedResult<FormTemplate> findPagedList(FormTemplate template, Page page);

    /**
     * 查询列表
     * @param template
     * @return
     */
    List<FormTemplate> findList(FormTemplate template);

    /**
     * 新增
     * @param template
     * @return
     */
    Integer save(FormTemplate template);

    /**
     * 更新
     * @param template
     * @return
     */
    Integer update(FormTemplate template);

    /**
     * 删除
     * @param id
     * @return
     */
    Integer delete(Long id);
}
