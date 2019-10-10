package com.lego.perception.template.service.impl;

import com.lego.framework.template.model.entity.DataTemplate;
import com.lego.framework.template.model.entity.DataTemplateItem;
import com.lego.framework.template.model.entity.FormTemplate;
import com.lego.framework.template.model.entity.FormTemplateItem;
import com.lego.perception.template.service.IDataTemplateService;
import com.lego.perception.template.service.IFormTemplateService;
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
    @Qualifier(value = "formTemplateItemServiceImpl")
    private ITemplateItemService templateItemService;

    @Autowired
    private IFormTemplateService formTemplateService;

    @Override
    public List<FormTemplateItem> findSearchCondition(String templateCode) {
        FormTemplate queryParam = new FormTemplate();
        queryParam.setTemplateCode(templateCode);
        List<FormTemplate> templateList = formTemplateService.findList(queryParam);
        if (CollectionUtils.isEmpty(templateList)) {
            return Collections.EMPTY_LIST;
        }

        FormTemplate formTemplate = templateList.get(0);
        FormTemplateItem itemQueryParam = new FormTemplateItem();
        itemQueryParam.setTemplateId(formTemplate.getId());
        itemQueryParam.setIsSearch(1);
        List<FormTemplateItem> itemList = templateItemService.findList(itemQueryParam);
        if (CollectionUtils.isEmpty(itemList)) {
            return Collections.EMPTY_LIST;
        }
        List<FormTemplateItem> resultList = templateItemService.convertList2Tree(itemList);
        return resultList;
    }
}
