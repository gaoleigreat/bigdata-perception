package com.lego.perception.template.service;
import com.lego.framework.template.model.entity.DataTemplateItem;

import java.util.List;

/**
 * 高级查询条件
 * weihao 2019-04-17
 */
public interface ISearchConditionService {

    /**
     * 根据模板查询 高级查询条件
     * @param templateCode
     * @return
     */
    List<DataTemplateItem> findSearchCondition(String templateCode);
}
