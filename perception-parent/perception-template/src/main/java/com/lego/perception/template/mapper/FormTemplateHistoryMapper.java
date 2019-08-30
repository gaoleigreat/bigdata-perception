package com.lego.perception.template.mapper;

import com.framework.mybatis.mapper.Mapper;
import com.lego.framework.template.model.entity.FormTemplateHistory;
import com.lego.framework.template.model.entity.FormTemplateItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormTemplateHistoryMapper extends Mapper<FormTemplateHistory> {

    /**
     * 查询列表
     * @param dataTemplateHistory
     * @return
     */
    List<FormTemplateHistory> findList(FormTemplateHistory dataTemplateHistory);

    /**
     * 新增
     * @param dataTemplateHistory
     * @return
     */
    Integer save(FormTemplateHistory dataTemplateHistory);

    /**
     * 查询模板项
     * @param formTemplateItem
     * @return
     */
    List<FormTemplateItem> findItems(FormTemplateItem formTemplateItem);

    /**
     * 新增模板项
     * @param formTemplateItemList
     * @return
     */
    Integer insertItems(List<FormTemplateItem> formTemplateItemList);
}
