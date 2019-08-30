package com.lego.perception.template.service.impl;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.template.model.entity.DataTemplateItem;
import com.lego.framework.template.model.entity.TemplateItem;
import com.lego.framework.template.model.entity.ValidateResult;
import com.lego.perception.template.mapper.DataTemplateItemMapper;
import com.lego.perception.template.service.ITemplateItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Service(value = "dataTemplateItemService")
@Slf4j
public class DataTemplateItemService implements ITemplateItemService<DataTemplateItem> {

    private static final String DATA_TEMPLATE_ITEM_ID_PREFIX = "fupin.dataTemplateItemId";

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DataTemplateItemMapper dataTemplateItemMapper;
    
    @Override
    public List<DataTemplateItem> findList(DataTemplateItem item) {
        if(null == item ){
            return Collections.EMPTY_LIST;
        }
        return dataTemplateItemMapper.findList(item);
    }

    @Override
    public RespVO insertList(List<DataTemplateItem> items) {
        if(CollectionUtils.isEmpty(items)){
            return RespVOBuilder.failure("参数缺失");
        }
        //设置id, parentId
        List<Long> parentIds = new ArrayList<>();
        for(DataTemplateItem item : items){
            if(null != item && null != item.getParentId()){
                parentIds.add(item.getParentId());
            }
        }
        DataTemplateItem query = new DataTemplateItem();
        query.setIds(parentIds);
        List<DataTemplateItem> parentItems = null;
        if(!CollectionUtils.isEmpty(parentIds)){
            parentItems =  findList(query);
        }

        Map<Long, DataTemplateItem> map = new HashMap<>();
        if(!CollectionUtils.isEmpty(parentItems)){
            for(DataTemplateItem item : parentItems){
                map.put(item.getId(), item);
            }
        }

        Long itemId = createDataItemId(Long.valueOf(items.size())) - items.size() + 1;
        for(DataTemplateItem item : items){
            if(null == item){
                continue;
            }
            if(null == item.getParentId()){
                item.setParentId(-1L);
                item.setAbsoluteField(item.getField());
            }else{
                if(map.containsKey(item.getParentId())){
                    item.setAbsoluteField(map.get(item.getParentId()).getAbsoluteField()+"."+item.getField());
                }
            }
            item.setId(itemId++);
        }
        return insert(items);
    }

    private RespVO insert(List<DataTemplateItem> items){
        for(DataTemplateItem item : items){
            ValidateResult v = validateNull(item);
            if(!v.getResult()){
                return RespVOBuilder.failure(v.getMsg());
            }

            v = validateDataNull(item);
            if(!v.getResult()){
                return RespVOBuilder.failure(v.getMsg());
            }
        }
        dataTemplateItemMapper.insertList(items);
        return RespVOBuilder.success();
    }

    @Override
    public RespVO insertTree(Long templateId, List<DataTemplateItem> items) {
        if(CollectionUtils.isEmpty(items)){
            return RespVOBuilder.success();
        }

        //树形数据项，设置id，parentId，并转为平行结构
        List<DataTemplateItem> insertItemList = convertTree2List(templateId, items);

        if(!CollectionUtils.isEmpty(insertItemList)){
            return insert(insertItemList);
        }
        return RespVOBuilder.failure("参数缺失");
    }

    @Override
    public List<DataTemplateItem> convertList2Tree(List<DataTemplateItem> itemList) {
        Map<Long, List<DataTemplateItem>> map = new HashMap<>();
        List<DataTemplateItem> items = new ArrayList<>();
        if(!CollectionUtils.isEmpty(itemList)){
            for(DataTemplateItem dataTemplateItem : itemList){
                if(null == dataTemplateItem || null == dataTemplateItem.getParentId()){
                    continue;
                }
                if(map.containsKey(dataTemplateItem.getParentId())){
                    map.get(dataTemplateItem.getParentId()).add(dataTemplateItem);
                }else{
                    List<DataTemplateItem> lst = new ArrayList<>();
                    lst.add(dataTemplateItem);
                    map.put(dataTemplateItem.getParentId(), lst);
                }
            }

            for(DataTemplateItem dataTemplateItem : itemList){
                if(map.containsKey(dataTemplateItem.getId())){
                    dataTemplateItem.setItems(map.get(dataTemplateItem.getId()));
                }
            }
        }
        items = map.get(-1L);
        return items;
    }

    @Override
    public List<DataTemplateItem> convertTree2List(Long templateId, List<DataTemplateItem> items) {
        List<DataTemplateItem> targetList = new ArrayList<>();
        convertTree2List(targetList, items, -1L, templateId);
        return targetList;
    }

    private void convertTree2List(List<DataTemplateItem> targetList, List<DataTemplateItem> dataTemplateItems, Long parentId, Long templateId){
        if(!CollectionUtils.isEmpty(dataTemplateItems)){
            Long itemId = createDataItemId(Long.valueOf(dataTemplateItems.size())) - dataTemplateItems.size() + 1;

            //设置id
            for(DataTemplateItem item : dataTemplateItems){
                item.setTemplateId(templateId);
                item.setId(itemId++);
                item.setParentId(parentId);
                targetList.add(item);
            }

            //递归 处理子节点
            for(DataTemplateItem item : dataTemplateItems){
                convertTree2List(targetList, item.getItems(), item.getId(), templateId);
            }
        }
    }

    @Override
    public RespVO updateList(List<DataTemplateItem> items) {
        if(CollectionUtils.isEmpty(items)){
            return RespVOBuilder.failure("参数缺失");
        }

        for(DataTemplateItem item : items){
            if(null == item || null == item.getId()){
                return RespVOBuilder.failure("参数缺失");
            }
            item.setUpdateInfo();
        }
        dataTemplateItemMapper.updateList(items);
        return RespVOBuilder.success();
    }

    private ValidateResult validateDataNull(DataTemplateItem item) {
        ValidateResult v = new ValidateResult();
        if(null == item.getSource()){
            v.setResult(false);
            v.setMsg("不能为空");
            return v;
        }else{
            //来源不为空，暂不校验
        }
        v.setResult(true);
        return v;
    }

    @Override
    public RespVO deleteList(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)){
            return RespVOBuilder.failure("参数缺失");
        }
        dataTemplateItemMapper.deleteList(ids);
        return RespVOBuilder.success();
    }

    @Override
    public ValidateResult validateNull(TemplateItem item) {
        ValidateResult v = new ValidateResult();

        if(null == item){
            v.setResult(false);
            v.setMsg("参数错误");
            return v;
        }

        if(StringUtils.isEmpty(item.getField())){
            v.setResult(false);
            v.setMsg("字段不能为空");
            return v;
        }
        if(StringUtils.isEmpty(item.getTitle())){
            v.setResult(false);
            v.setMsg("字段名称不能为空");
            return v;
        }

        if(StringUtils.isEmpty(item.getParentId())){
            v.setResult(false);
            v.setMsg("父字段不能为空");
            return v;
        }

        if(StringUtils.isEmpty(item.getTemplateId())){
            v.setResult(false);
            v.setMsg("模板ID不能为空");
            return v;
        }

        if(StringUtils.isEmpty(item.getCategory())){
            v.setResult(false);
            v.setMsg("字段类型不能为空");
            return v;
        }

        v.setResult(true);
        return v;
    }

    @Override
    public Map<String, Object> findFieldTree(DataTemplateItem item) {
        List<DataTemplateItem> itemList = findList(item);
        List<DataTemplateItem> items = convertList2Tree(itemList);
        Map<String, Object> map = new HashMap<>();
        getFieldTree(items, map);
        return map;
    }

    @Override
    public Map<String, DataTemplateItem> findFieldNameMap(DataTemplateItem item) {
        List<DataTemplateItem> itemList = findList(item);
        Map<String, DataTemplateItem> map = new HashMap<>();
        if(!CollectionUtils.isEmpty(itemList)){
            for(DataTemplateItem dataTemplateItem : itemList){
                map.put(dataTemplateItem.getTitle(), dataTemplateItem);
            }
        }
        return map;
    }

    @Override
    public Map<String, String> findFieldName(DataTemplateItem item) {
        List<DataTemplateItem> itemList = findList(item);
        Map<String, String> map = new HashMap<>();
        if(!CollectionUtils.isEmpty(itemList)){
            for(DataTemplateItem dataTemplateItem : itemList){
                map.put(dataTemplateItem.getTitle(), dataTemplateItem.getField());
            }
        }
        return map;
    }

    public void getFieldTree(List<DataTemplateItem> itemList, Map<String, Object> map){
        for(DataTemplateItem it : itemList){
            if(null == it || null == it.getCategory()){
                continue;
            }
            if(10 == it.getCategory()){
                Map<String, Object> m = new HashMap<>();
                getFieldTree(it.getItems(), m);
                map.put(it.getField(), m);
            }else if(11 == it.getCategory()){
                List<Map<String, Object>> lst = new ArrayList<>();
                Map<String, Object> m = new HashMap<>();
                getFieldTree(it.getItems(), m);
                lst.add(m);
                map.put(it.getField(), lst);
            }else{
                map.put(it.getField(), "");
            }
        }
    }



    private Long createDataItemId(Long count) {
        Long value = redisTemplate.opsForValue().increment(DATA_TEMPLATE_ITEM_ID_PREFIX, count);
        return value;
    }
}
