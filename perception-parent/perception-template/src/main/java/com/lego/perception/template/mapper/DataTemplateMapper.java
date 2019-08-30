package com.lego.perception.template.mapper;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.mybatis.mapper.Mapper;
import com.lego.framework.template.model.entity.DataTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataTemplateMapper extends Mapper<DataTemplate> {

    /**
     * 分页查询
     * @param template
     * @param page
     * @return
     */
    PagedResult<DataTemplate> findPagedList(DataTemplate template, Page page);

    /**
     * 查询列表
     * @param template
     * @return
     */
    List<DataTemplate> findList(DataTemplate template);

    /**
     * 新增
     * @param template
     * @return
     */
    Integer save(DataTemplate template);

    /**
     * 更新
     * @param template
     * @return
     */
    Integer update(DataTemplate template);

    /**
     * 删除
     * @param id
     * @return
     */
    Integer delete(Long id);
}
