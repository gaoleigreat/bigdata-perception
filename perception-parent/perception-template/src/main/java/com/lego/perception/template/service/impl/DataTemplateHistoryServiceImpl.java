package com.lego.perception.template.service.impl;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.template.model.entity.DataTemplateHistory;
import com.lego.framework.template.model.entity.DataTemplateItem;
import com.lego.perception.template.mapper.DataTemplateHistoryMapper;
import com.lego.perception.template.service.IHistoryTemplateService;
import com.lego.perception.template.service.ITemplateItemService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author yanglf
 */
@Service(value = "dataTemplateHistoryServiceImpl")
@Slf4j
public class DataTemplateHistoryServiceImpl implements IHistoryTemplateService<DataTemplateHistory> {

    @Autowired
    @Qualifier(value = "dataTemplateItemService")
    private ITemplateItemService<DataTemplateItem> dataTemplateItemService;

    @Autowired
    private DataTemplateHistoryMapper dataTemplateHistoryMapper;

    @Override
    public DataTemplateHistory find(String code, String tag) {
        DataTemplateHistory result = null;
        if(StringUtils.isEmpty(code) || StringUtils.isEmpty(tag)){
            return null;
        }

        DataTemplateHistory queryParam = new DataTemplateHistory();
        queryParam.setTemplateCode(code);
        queryParam.setTag(tag);

        List<DataTemplateHistory> templateHistoryList = dataTemplateHistoryMapper.findList(queryParam);
        if(!CollectionUtils.isEmpty(templateHistoryList)){

            result = templateHistoryList.get(0);

            DataTemplateItem itemQuery = new DataTemplateItem();
            itemQuery.setTemplateId(result.getId());

            List<DataTemplateItem> itemList = dataTemplateHistoryMapper.findItems(itemQuery);

            List<DataTemplateItem> items = dataTemplateItemService.convertList2Tree(itemList);

            result.setItems(items);
        }

        return result;
    }

    @Override
    public RespVO insert(DataTemplateHistory dataTemplateHistory) {
        if(StringUtils.isEmpty(dataTemplateHistory.getTemplateCode())){
            return RespVOBuilder.failure("模板编码不能为空");
        }

        if(StringUtils.isEmpty(dataTemplateHistory.getTag())){
            return RespVOBuilder.failure("标签不能为空");
        }

        if(StringUtils.isEmpty(dataTemplateHistory.getTemplateName())){
            return RespVOBuilder.failure("模板名称不能为空");
        }

        dataTemplateHistoryMapper.save(dataTemplateHistory);

        if(!CollectionUtils.isEmpty(dataTemplateHistory.getItems())){
            List<DataTemplateItem> items = dataTemplateItemService.convertTree2List(dataTemplateHistory.getId(), dataTemplateHistory.getItems());
            dataTemplateHistoryMapper.insertItems(items);
        }

        return RespVOBuilder.success();
    }


}
