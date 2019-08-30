package com.lego.perception.template.service.impl;

import com.lego.framework.template.model.entity.DataTemplate;
import com.lego.framework.template.model.entity.DataTemplateItem;
import com.lego.perception.template.service.IDataTemplateService;
import com.lego.perception.template.service.ISearchConditionService;
import com.lego.perception.template.service.ITemplateItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
public class SearchConditionServiceImpl implements ISearchConditionService {


    @Autowired
    @Qualifier(value = "dataTemplateItemService")
    private ITemplateItemService dataTemplateItemService;

    @Autowired
    private IDataTemplateService dataTemplateService;

    @Override
    public List<DataTemplateItem> findSearchCondition(String templateCode) {
        DataTemplate queryParam = new DataTemplate();
        queryParam.setTemplateCode(templateCode);
        List<DataTemplate> dataTemplateList = dataTemplateService.findList(queryParam);
        if (CollectionUtils.isEmpty(dataTemplateList)) {
            return Collections.EMPTY_LIST;
        }

        DataTemplate dataTemplate = dataTemplateList.get(0);
        DataTemplateItem itemQueryParam = new DataTemplateItem();
        itemQueryParam.setTemplateId(dataTemplate.getId());
        itemQueryParam.setIsSearch(1);
        List<DataTemplateItem> itemList = dataTemplateItemService.findList(itemQueryParam);
        if (CollectionUtils.isEmpty(itemList)) {
            return Collections.EMPTY_LIST;
        }
        List<DataTemplateItem> resultList = dataTemplateItemService.convertList2Tree(itemList);
        return resultList;
    }
}
