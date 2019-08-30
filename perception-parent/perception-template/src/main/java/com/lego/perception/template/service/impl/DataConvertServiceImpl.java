package com.lego.perception.template.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.template.model.entity.FormTemplate;
import com.lego.framework.template.model.entity.FormTemplateItem;
import com.lego.framework.template.model.entity.ValidateResult;
import com.lego.perception.template.service.IDataConvertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@Slf4j
public class DataConvertServiceImpl implements IDataConvertService {


    @Override
    public RespVO<Map<String, Object>> convertData2Template(FormTemplate formTemplate, JSONObject
            jsonObject) {
        if (null == formTemplate || null == jsonObject || CollectionUtils.isEmpty(formTemplate.getItems())) {
            return RespVOBuilder.failure("参数错误");
        }

        Map<String, Object> resultMap = new HashMap<>();
        List<FormTemplateItem> formItems = formTemplate.getItems();
        getTemplateData(jsonObject, resultMap, formItems);
        return RespVOBuilder.success(resultMap);
    }

    private void getTemplateData(JSONObject jsonObject, Map<String, Object> resultMap, List<FormTemplateItem> formItems) {
        if (CollectionUtils.isEmpty(formItems) || null == jsonObject) {
            return;
        }

        for (FormTemplateItem formItem : formItems) {
            if (null == formItem || null == formItem.getCategory() || StringUtils.isEmpty(formItem.getDataField())) {
                continue;
            }
            switch (formItem.getCategory()) {
                case 1:
                    //text
                    String v1 = getValue(formItem, jsonObject, String.class);
                    if (null != v1) {
                        resultMap.put(formItem.getField(), v1);
                    }
                    break;
                case 2:
                    //textarea
                    String v2 = getValue(formItem, jsonObject, String.class);
                    if (null != v2) {
                        resultMap.put(formItem.getField(), v2);
                    }
                    break;
                case 3:
                    //date
                    Date v3 = getValue(formItem, jsonObject, Date.class);
                    if (null != v3) {
                        resultMap.put(formItem.getField(), v3);
                    }
                    break;
                case 4:
                    //textarea
                    String v4 = getValue(formItem, jsonObject, String.class);
                    if (null != v4) {
                        resultMap.put(formItem.getField(), v4);
                    }
                    break;
                case 5:
                    //attachement
                    String v5 = getValue(formItem, jsonObject, String.class);
                    if (null != v5) {
                        resultMap.put(formItem.getField(), v5);
                    }
                    break;
                case 6:
                    //单选
                    Long enumId = formItem.getEnumId();
                    if (enumId > 0) {
                        Integer v6 = getValue(formItem, jsonObject, Integer.class);
                        if (null != v6) {
                            resultMap.put(formItem.getField(), v6);
                        }
                    } else {
                        String v6 = getValue(formItem, jsonObject, String.class);
                        if (null != v6) {
                            resultMap.put(formItem.getField(), v6);
                        }
                    }
                    break;
                case 7:
                    //多选
                    String v7 = getValue(formItem, jsonObject, String.class);
                    if (null != v7) {
                        try {
                            resultMap.put(formItem.getField(), JSONArray.parseArray(v7));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 8:
                    //gps
                    JSONArray arr = new JSONArray();
                    JSONArray v8 = getValue(formItem, jsonObject, JSONArray.class);
                    if (v8 != null) {
                        for (Object o : v8) {
                            arr.add(Double.valueOf(o+""));
                        }
                        resultMap.put(formItem.getField(), arr);
                    }
                    break;
                case 9:
                    //整数
                    Long v9 = getValue(formItem, jsonObject, Long.class);
                    if (null != v9) {
                        resultMap.put(formItem.getField(), v9);
                    }
                    break;
                case 10:
                    //object
                    Map<String, Object> map = new HashMap<>();
                    List<FormTemplateItem> items = formItem.getItems();
                    getTemplateData(jsonObject, map, items);
                    if (!CollectionUtils.isEmpty(map)) {
                        resultMap.put(formItem.getField(), map);
                    }
                    break;
                case 11:
                    //listObject
                    //获取对象列表数据
                    /*List<Map<String, Object>> lst = new ArrayList<>();
                    JSONArray jsonArray = getValue(formItem, jsonObject, JSONArray.class);
                    if (null == jsonArray) {
                        resultMap.put(formItem.getField(), lst);
                        break;
                    }
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject object=jsonArray.getJSONObject(i);
                        Map<String, Object> hashMap = new HashMap<>();
                        getTemplateData(object, hashMap, formItem.getItems());
                        if (!CollectionUtils.isEmpty(hashMap)) {
                            lst.add(hashMap);
                        }
                    }
                    resultMap.put(formItem.getField(), lst);*/
                    // 模板项分类，分为 listObject 和 object
                    List<Map<String, Object>> lst = new ArrayList<>();
                    JSONArray jsonArray = getValue(formItem, jsonObject, JSONArray.class);
                    if (null == jsonArray) {
                        resultMap.put(formItem.getField(), lst);
                        break;
                    }
                    // 模板项分类，分为 listObject 和 object
                    List<FormTemplateItem> aryItems = new ArrayList<>();
                    List<FormTemplateItem> objItems = new ArrayList<>();
                    classfyFormItems(formItem, aryItems, objItems);

                    Iterator<Object> iterator = jsonArray.iterator();
                    while (iterator.hasNext()) {
                        JSONObject jo = (JSONObject) iterator.next();
                        Map<String, Object> m = new HashMap<>();
                        if (!CollectionUtils.isEmpty(aryItems)) {
                            getTemplateData(jo, m, aryItems);
                        }
                        if (!CollectionUtils.isEmpty(objItems)) {
                            getTemplateData(jsonObject, m, objItems);
                        }
                        lst.add(m);
                    }
                    resultMap.put(formItem.getField(), lst);
                    break;
                case 12:
                    //time
                    Date v13 = getValue(formItem, jsonObject, Date.class);
                    if (null != v13) {
                        resultMap.put(formItem.getField(), v13);
                    }
                    break;
                case 13:
                    //boolean
                    String v12 = getValue(formItem, jsonObject, String.class);
                    if (null != v12) {
                        resultMap.put(formItem.getField(), v12);
                    }
                    break;
                case 14:
                    //float
                    String v14 = getValue(formItem, jsonObject, String.class);
                    if (null != v14) {
                        resultMap.put(formItem.getField(), v14);
                    }
                    break;
                case 15:
                    String v15 = getValue(formItem, jsonObject, String.class);
                    if (null != v15) {
                        resultMap.put(formItem.getField(), v15);
                    }
                    break;
                default:
                    String v16 = getValue(formItem, jsonObject, String.class);
                    if (null != v16) {
                        resultMap.put(formItem.getField(), v16);
                    }
                    break;
            }
        }
    }

    private <T> T getValue(FormTemplateItem formTemplateItem, JSONObject jsonObject, Class<T> c) {
        if (null == jsonObject || StringUtils.isEmpty(formTemplateItem)) {
            return null;
        }
        String dataField = formTemplateItem.getDataField();
        if (dataField.contains(".")) {
            String[] ary = dataField.split("\\.");

            //第一层元素
            int index = 0;


            String s = ary[index];
            JSONObject jo = null;
            Object o = jsonObject.get(s);
            if (o instanceof Map) {
                jo = JSONObject.parseObject(JSONObject.toJSONString(o));
            } else if (o instanceof List) {
                JSONArray array = JSON.parseArray(JSONObject.toJSONString(o));
                for (int i = 0; i < array.size(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    getValue(formTemplateItem, object, JSONObject.class);
                }
            }

            if (null == jo) {
                return null;
            }


            index++;

            //第二层 到 倒数第二层
            for (; index < ary.length - 1; ) {
                index++;
                String s1 = ary[index];
                jo = jo.getJSONObject(s1);
                if (null == jo) {
                    return null;
                }
            }
            //值
            return jo.getObject(ary[index], c);
        } else {
            return jsonObject.getObject(dataField, c);
        }
    }

    @Override
    public RespVO<JSONObject> convertTemplate2Data(FormTemplate formTemplate, JSONObject param) {

        if (null == formTemplate || CollectionUtils.isEmpty(param) || CollectionUtils.isEmpty(formTemplate.getItems())) {
            return RespVOBuilder.failure("参数错误");
        }
        List<ValidateResult> validateResults = new ArrayList<>();

        JSONObject jsonObject = new JSONObject();

        List<FormTemplateItem> formItems = formTemplate.getItems();

        setTemplateData(jsonObject, param, validateResults, formItems);

        return RespVOBuilder.success(jsonObject);
    }

    public void setTemplateData(JSONObject jsonObject, JSONObject param, List<ValidateResult> validateResults, List<FormTemplateItem> formItems) {
        if (null == jsonObject || null == param || null == validateResults || CollectionUtils.isEmpty(formItems)) {
            return;
        }
        for (FormTemplateItem formItem : formItems) {
            String field = formItem.getField();
            String dataField = formItem.getDataField();
            if (null == formItem || null == formItem.getCategory() ||
                    StringUtils.isEmpty(formItem.getField()) || StringUtils.isEmpty(formItem.getDataField())) {
                //必要参数为空是 不做处理
                continue;
            }
            switch (formItem.getCategory()) {
                case 1:
                    //input
                    setData2JSONObject(param, validateResults, jsonObject, formItem, String.class);
                    break;
                case 2:
                    //textarea
                    setData2JSONObject(param, validateResults, jsonObject, formItem, String.class);
                    break;
                case 3:
                    //date
                    setData2JSONObject(param, validateResults, jsonObject, formItem, Date.class);
                    break;
                case 4:
                    //图片
                    setData2JSONObject(param, validateResults, jsonObject, formItem, String.class);
                    break;
                case 5:
                    //附件
                    setData2JSONObject(param, validateResults, jsonObject, formItem, String.class);
                    break;
                case 6:
                    //单选
                    setData2JSONObject(param, validateResults, jsonObject, formItem, Integer.class);
                    break;
                case 7:
                    //多选
                    setData2JSONObject(param, validateResults, jsonObject, formItem, String.class);
                    break;
                case 8:
                    //gps
                    setData2JSONObject(param, validateResults, jsonObject, formItem, String.class);
                    break;
                case 9:
                    //整数
                    setData2JSONObject(param, validateResults, jsonObject, formItem, Long.class);
                    break;
                case 10:
                    //object
                    List<FormTemplateItem> childItems = formItem.getItems();
                    JSONObject childParam = param.getJSONObject(field);
                    setTemplateData(jsonObject, childParam, validateResults, childItems);
                    break;
                case 11:
                    //listobject
                    JSONArray ary = param.getJSONArray(field);
                    if (null == ary) {
                        continue;
                    }

                    // 模板项分类，分为 listObject 和 object
                    List<FormTemplateItem> aryItems = new ArrayList<>();
                    List<FormTemplateItem> objItems = new ArrayList<>();
                    classfyFormItems(formItem, aryItems, objItems);

                    JSONArray jsonArray = new JSONArray();
                    Iterator<Object> it = ary.iterator();
                    while (it.hasNext()) {
                        Object o = it.next();
                        if (null == o) {
                            continue;
                        }
                        JSONObject jo = (JSONObject) o;
                        if (!CollectionUtils.isEmpty(aryItems)) {
                            JSONObject j = new JSONObject();
                            setTemplateData(j, jo, validateResults, aryItems);
                            jsonArray.add(j);
                        }
                        if (!CollectionUtils.isEmpty(objItems)) {
                            setTemplateData(jsonObject, jo, validateResults, objItems);
                        }
                    }
                    if (!jsonArray.isEmpty()) {
                        setValue(formItem.getDataField(), jsonObject, jsonArray);
                    }
                    break;
                case 12:
                    //time
                    setData2JSONObject(param, validateResults, jsonObject, formItem, Date.class);
                    break;
                case 13:
                    //boolean
                    setData2JSONObject(param, validateResults, jsonObject, formItem, Boolean.class);
                    break;
                case 14:
                    //float
                    setData2JSONObject(param, validateResults, jsonObject, formItem, String.class);
                    break;
                case 15:
                    setData2JSONObject(param, validateResults, jsonObject, formItem, String.class);
                    break;
            }
        }
    }

    private void classfyFormItems(FormTemplateItem formItem, List<FormTemplateItem> aryItems, List<FormTemplateItem> objItems) {
        if (null == formItem || CollectionUtils.isEmpty(formItem.getItems())) {
            return;
        }
        for (FormTemplateItem item : formItem.getItems()) {
            String innerDataField = item.getDataField();
            if (innerDataField.startsWith(formItem.getDataField())) {
                item.setDataField(innerDataField.replace(formItem.getDataField() + ".", ""));
                aryItems.add(item);
            } else {
                objItems.add(item);
            }
        }
    }

    /**
     * 将上传的表单模板数据，增加到数据模板数据中
     *
     * @param param
     * @param validateResults
     * @param jsonObject
     * @param formItem
     */
    private <T> void setData2JSONObject(JSONObject param, List<ValidateResult> validateResults, JSONObject jsonObject,
                                        FormTemplateItem formItem, Class<T> c) {
        if (null == formItem || StringUtils.isEmpty(formItem.getField()) || StringUtils.isEmpty(formItem.getDataField())) {
            return;
        }

        T t = parseValue(formItem.getField(), param, c);
        if (null != formItem.getIsRequired() && 1 == formItem.getIsRequired() && null == t) {
            add2Result(validateResults, formItem.getTitle() + "不能为空");
            return;
        }
        if (!StringUtils.isEmpty(formItem.getValidStr()) && (null != t || t.toString().matches(formItem.getValidStr()))) {

            add2Result(validateResults, formItem.getTitle() + "正则校验不通过");
            return;
        }
        setValue(formItem.getDataField(), jsonObject, t);
    }

    private void add2Result(List<ValidateResult> validateResults, String msg) {
        ValidateResult v = new ValidateResult();
        v.setResult(false);
        v.setMsg(msg);
        validateResults.add(v);
    }


    public <T> T parseValue(String field, JSONObject param, Class<T> type) {
        if (null == param || StringUtils.isEmpty(field)) {
            return null;
        }

        return param.getObject(field, type);
    }

    public <T> void setValue(String dataField, JSONObject jsonObject, T t) {
        if (StringUtils.isEmpty(dataField) || null == jsonObject) {
            return;
        }

        if (dataField.contains(".")) {
            String[] ary = dataField.split("\\.");

            int index = 0;
            JSONObject jo = null;
            if (jsonObject.containsKey(ary[index])) {
                jo = jsonObject.getJSONObject(ary[index]);
            } else {
                jo = new JSONObject();
                jsonObject.put(ary[index], jo);
            }

            index++;

            for (; index < ary.length - 1; ) {
                index++;
                if (jsonObject.containsKey(ary[index])) {
                    jo = jsonObject.getJSONObject(ary[index]);
                } else {
                    jo = new JSONObject();
                    jsonObject.put(ary[index], jo);
                }
            }
            jo.put(ary[index], t);
        } else {
            jsonObject.put(dataField, t);
        }
    }
}
