package com.lego.perception.template.mapper;
import com.framework.mybatis.mapper.Mapper;
import com.lego.framework.template.model.entity.DataTemplateHistory;
import com.lego.framework.template.model.entity.DataTemplateItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataTemplateHistoryMapper extends Mapper<DataTemplateHistory> {

    /**
     * 查询列表
     * @param dataTemplateHistory
     * @return
     */
    List<DataTemplateHistory> findList(DataTemplateHistory dataTemplateHistory);

    /**
     * 新增
     * @param dataTemplateHistory
     * @return
     */
    Integer save(DataTemplateHistory dataTemplateHistory);

    /**
     * 查询模板项
     * @param dataTemplateItem
     * @return
     */
    List<DataTemplateItem> findItems(DataTemplateItem dataTemplateItem);

    /**
     * 新增模板项
     * @param dataTemplateItemList
     * @return
     */
    Integer insertItems(List<DataTemplateItem> dataTemplateItemList);
}
