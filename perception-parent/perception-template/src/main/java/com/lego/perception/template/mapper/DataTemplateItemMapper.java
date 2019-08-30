package com.lego.perception.template.mapper;

import com.framework.mybatis.mapper.Mapper;
import com.lego.framework.template.model.entity.DataTemplateItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataTemplateItemMapper extends Mapper<DataTemplateItem> {


    /**
     * 查询列表
     * @param item
     * @return
     */
    List<DataTemplateItem> findList(DataTemplateItem item);

    /**
     * 新增列表
     * @param items
     * @return
     */
    Integer insertList(List<DataTemplateItem> items);

    /**
     * 更新
     * @param items
     * @return
     */
    Integer updateList(List<DataTemplateItem> items);

    /**
     * 删除
     * @param ids
     * @return
     */
    Integer deleteList(List<Long> ids);
}
