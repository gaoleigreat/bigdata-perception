package com.lego.perception.template.mapper;
import com.framework.mybatis.mapper.Mapper;
import com.lego.framework.template.model.entity.FormTemplateItem;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FormTemplateItemMapper extends Mapper<FormTemplateItem> {

    /**
     * 查询列表
     * @param item
     * @return
     */
    List<FormTemplateItem> findList(FormTemplateItem item);

    /**
     * 新增列表
     * @param items
     * @return
     */
    Integer insertList(List<FormTemplateItem> items);

    /**
     * 更新
     * @param items
     * @return
     */
    Integer updateList(List<FormTemplateItem> items);

    /**
     * 删除
     * @param ids
     * @return
     */
    Integer deleteList(List<Long> ids);
}
