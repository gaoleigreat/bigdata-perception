package com.lego.perception.template.service.impl;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.template.model.entity.FormTemplateItem;
import com.lego.framework.template.model.entity.TemplateItem;
import com.lego.framework.template.model.entity.ValidateResult;
import com.lego.perception.template.mapper.FormTemplateItemMapper;
import com.lego.perception.template.service.ITemplateItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service(value = "formTemplateItemServiceImpl")
@Slf4j
public class FormTemplateItemServiceImpl implements ITemplateItemService<FormTemplateItem> {


    private static final String FORM_TEMPLATE_ITEM_ID_PREFIX = "fupin.formTemplateItemId";

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private FormTemplateItemMapper formTemplateItemMapper;

    @Autowired
    @Qualifier(value = "dataTemplateItemService")
    private ITemplateItemService dataTemplateItemService;

    @Override
    public List<FormTemplateItem> findList(FormTemplateItem item) {
        if(null == item || (null == item.getParentId() && null == item.getTemplateId())){
            return Collections.EMPTY_LIST;
        }
        return formTemplateItemMapper.findList(item);
    }

    @Override
    public RespVO insertList(List<FormTemplateItem> items) {
        if(CollectionUtils.isEmpty(items)){
            return RespVOBuilder.failure("参数缺失");
        }
        //设置id, parentId
        Long itemId = createFormItemId(Long.valueOf(items.size())) - items.size() + 1;
        for(FormTemplateItem item : items){
            if(null == item){
                continue;
            }
            if(null == item.getParentId()){
                item.setParentId(-1L);
            }
            item.setId(itemId++);
        }
        return insert(items);
    }

    private RespVO insert(List<FormTemplateItem> items) {
        for(FormTemplateItem item : items){
            ValidateResult v = validateNull(item);
            if(!v.getResult()){
                return RespVOBuilder.failure(v.getMsg());
            }
            v = validateFormNull(item);
            if(!v.getResult()){
                return RespVOBuilder.failure(v.getMsg());
            }
        }
        formTemplateItemMapper.insertList(items);
        return RespVOBuilder.success();
    }

    @Override
    public RespVO insertTree(Long templateId, List<FormTemplateItem> items) {
        if(CollectionUtils.isEmpty(items)){
            return RespVOBuilder.success();
        }
        //树形数据项，设置id，parentId，并转为平行结构
        List<FormTemplateItem> insertItemList = convertTree2List(templateId, items);

        if(!CollectionUtils.isEmpty(insertItemList)){
            return insert(insertItemList);
        }

        return RespVOBuilder.failure("参数缺失");
    }

    @Override
    public List<FormTemplateItem> convertList2Tree(List<FormTemplateItem> itemList) {
        Map<Long, List<FormTemplateItem>> map = new HashMap<>();
        List<FormTemplateItem> items = new ArrayList<>();
        if(!CollectionUtils.isEmpty(itemList)){
            for(FormTemplateItem dataTemplateItem : itemList){
                if(null == dataTemplateItem || null == dataTemplateItem.getParentId()){
                    continue;
                }

                if(map.containsKey(dataTemplateItem.getParentId())){
                    map.get(dataTemplateItem.getParentId()).add(dataTemplateItem);
                }else{
                    List<FormTemplateItem> lst = new ArrayList<>();
                    lst.add(dataTemplateItem);
                    map.put(dataTemplateItem.getParentId(), lst);
                }
            }

            for(FormTemplateItem dataTemplateItem : itemList){
                if(map.containsKey(dataTemplateItem.getId())){
                    dataTemplateItem.setItems(map.get(dataTemplateItem.getId()));
                }
            }
            items = map.get(-1L);
        }
        return items;
    }

    @Override
    public List<FormTemplateItem> convertTree2List(Long templateId, List<FormTemplateItem> itemList) {
        List<FormTemplateItem> targetList = new ArrayList<>();
        convertTree2List(targetList, itemList, -1L, templateId);
        return targetList;
    }

    private void convertTree2List(List<FormTemplateItem> targetList, List<FormTemplateItem> dataTemplateItems, Long parentId, Long templateId){
        if(!CollectionUtils.isEmpty(dataTemplateItems)){
            Long itemId = createFormItemId(Long.valueOf(dataTemplateItems.size())) - dataTemplateItems.size() + 1;

            //设置id
            for(FormTemplateItem item : dataTemplateItems){
                item.setTemplateId(templateId);
                item.setId(itemId++);
                item.setParentId(parentId);
                targetList.add(item);
            }

            //递归 处理子节点
            for(FormTemplateItem item : dataTemplateItems){
                convertTree2List(targetList, item.getItems(), item.getId(), templateId);
            }
        }
    }

    private ValidateResult validateFormNull(FormTemplateItem item){

        ValidateResult v = new ValidateResult();
        if(null == item.getField()){
            v.setResult(false);
            v.setMsg("数据字段不能为空");
            return v;
        }
        v.setResult(true);
        return v;
    }

    @Override
    public RespVO updateList(List<FormTemplateItem> items) {
        if(CollectionUtils.isEmpty(items)){
            return RespVOBuilder.failure("参数缺失");
        }

        for(FormTemplateItem item : items){
            if(null == item || null == item.getId()){
                return RespVOBuilder.failure("参数缺失");
            }
            item.setUpdateInfo();
        }
        formTemplateItemMapper.updateList(items);
        return RespVOBuilder.success();
    }

    @Override
    public RespVO deleteList(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)){
            return RespVOBuilder.failure("参数缺失");
        }
        formTemplateItemMapper.deleteList(ids);
        return RespVOBuilder.success();
    }

    @Override
    public ValidateResult validateNull(TemplateItem item) {

        return dataTemplateItemService.validateNull(item);
    }

    @Override
    public Map<String, Object> findFieldTree(FormTemplateItem item) {
        List<FormTemplateItem> itemList = findList(item);
        List<FormTemplateItem> items = convertList2Tree(itemList);
        Map<String, Object> map = new HashMap<>();
        getFieldTree(items, map);
        return map;
    }

    @Override
    public Map<String, FormTemplateItem> findFieldNameMap(FormTemplateItem item) {
        return null;
    }

    @Override
    public Map<String, String> findFieldName(FormTemplateItem item) {
        return null;
    }

    public void getFieldTree(List<FormTemplateItem> itemList, Map<String, Object> map){
        for(FormTemplateItem it : itemList){
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

    private Long createFormItemId(Long count) {
        Long value = redisTemplate.opsForValue().increment(FORM_TEMPLATE_ITEM_ID_PREFIX, count);
        return value;
    }
}
