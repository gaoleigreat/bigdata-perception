package com.lego.perception.template.service.impl;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.template.model.entity.FormTemplateHistory;
import com.lego.framework.template.model.entity.FormTemplateItem;
import com.lego.perception.template.mapper.FormTemplateHistoryMapper;
import com.lego.perception.template.service.IHistoryTemplateService;
import com.lego.perception.template.service.ITemplateItemService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.List;

@Service(value = "formTemplateHistoryServiceImpl")
@Slf4j
public class FormTemplateHistoryServiceImpl implements IHistoryTemplateService<FormTemplateHistory> {


    @Autowired
    @Qualifier(value = "formTemplateItemServiceImpl")
    private ITemplateItemService formTemplateItemService;

    @Autowired
    private FormTemplateHistoryMapper formTemplateHistoryMapper;

    @Override
    public FormTemplateHistory find(String code, String tag) {
        FormTemplateHistory result = null;
        if(StringUtils.isEmpty(code) || StringUtils.isEmpty(tag)){
            return result;
        }

        FormTemplateHistory queryParam = new FormTemplateHistory();
        queryParam.setTemplateCode(code);
        queryParam.setTag(tag);

        List<FormTemplateHistory> templateHistoryList = formTemplateHistoryMapper.findList(queryParam);
        if(!CollectionUtils.isEmpty(templateHistoryList)){

            result = templateHistoryList.get(0);

            FormTemplateItem itemQuery = new FormTemplateItem();
            itemQuery.setTemplateId(result.getId());

            List<FormTemplateItem> itemList = formTemplateHistoryMapper.findItems(itemQuery);

            List<FormTemplateItem> items = formTemplateItemService.convertList2Tree(itemList);

            result.setItems(items);
        }

        return result;
    }

    @Override
    public RespVO insert(FormTemplateHistory formTemplateHistory) {
        if(StringUtils.isEmpty(formTemplateHistory.getTemplateCode())){
            return RespVOBuilder.failure("模板编码不能为空");
        }

        if(StringUtils.isEmpty(formTemplateHistory.getTag())){
            return RespVOBuilder.failure("标签不能为空");
        }

        if(StringUtils.isEmpty(formTemplateHistory.getTemplateName())){
            return RespVOBuilder.failure("模板名称不能为空");
        }

        formTemplateHistoryMapper.save(formTemplateHistory);

        if(CollectionUtils.isEmpty(formTemplateHistory.getItems())){
            List<FormTemplateItem> items = formTemplateItemService.convertTree2List(formTemplateHistory.getId(), formTemplateHistory.getItems());
            formTemplateHistoryMapper.insertItems(items);
        }

        return RespVOBuilder.success();
    }
}
