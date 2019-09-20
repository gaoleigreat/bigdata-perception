package com.lego.perception.data.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lego.framework.template.model.entity.FormTemplate;
import com.lego.framework.template.model.entity.FormTemplateItem;

import java.util.List;

/**
 * @auther xiaodao
 * @date 2019/9/19 21:33
 */
public class TemplateDataValidataUtil {
    public JSONObject transformFormDataItem(FormTemplate formTemplate, JSONObject jsonObject) {
        JSONObject toJsonObj = new JSONObject();
        List<FormTemplateItem> items = formTemplate.getItems();
        for (FormTemplateItem item : items) {
            String field = item.getField();
            Integer category = item.getCategory();
            Object o = jsonObject.get(field);
            if (category != 10 && category != 11 && o == null) {
                continue;
            }
            if (category == 10) {
                JSONObject object = new JSONObject();
                toJsonObj.put(field, object);
                transformFormObjectData(item.getItems(), JSONObject.parseObject(JSONObject.toJSONString(o)), object, false);
                continue;
            }

            if (category == 11) {
                JSONArray array = new JSONArray();
                toJsonObj.put(field, array);
                transformFormArrayData(item.getItems(), JSONObject.parseArray(JSONObject.toJSONString(o)), array, false);
                continue;
            }
            toJsonObj.put(field, o);
        }
        return toJsonObj;
    }


    private void transformFormObjectData(List<FormTemplateItem> formTemplateItems, JSONObject jsonObject, JSONObject toJsonObject, boolean isTransformValue) {
        for (FormTemplateItem formTemplateItem : formTemplateItems) {
            if (jsonObject == null) {
                return;
            }
            String field = formTemplateItem.getField();
            Integer category = formTemplateItem.getCategory();
            Object o = jsonObject.get(field);
            if (category != 10 && category != 11 && o == null) {
                continue;
            }
            if (isTransformValue) {


                if (category == 9) {
                    if (o instanceof Integer) {
                        Integer num = (Integer) o;
                        toJsonObject.put(field, num + "");
                        continue;
                    }
                }

            }
            if (category == 10) {
                JSONObject object = new JSONObject();
                toJsonObject.put(field, object);
                transformFormObjectData(formTemplateItem.getItems(), JSONObject.parseObject(JSONObject.toJSONString(o)), object, isTransformValue);
                continue;
            }
            if (category == 11 || category == 4) {
                JSONArray array = new JSONArray();
                toJsonObject.put(field, array);
                transformFormArrayData(formTemplateItem.getItems(), JSONObject.parseArray(JSONObject.toJSONString(o)), array, isTransformValue);
                continue;
            }

            toJsonObject.put(field, o);
        }

    }

    private void transformFormArrayData(List<FormTemplateItem> formTemplateItems, JSONArray jsonArray, JSONArray toJsonArray, boolean isTransformValue) {
        if (jsonArray == null || formTemplateItems == null) {
            return;
        }
        for (Object obj : jsonArray) {
            if (obj instanceof JSONObject) {
                JSONObject jsonObject = (JSONObject) obj;
                JSONObject object = new JSONObject();
                for (FormTemplateItem templateItem : formTemplateItems) {
                    String field = templateItem.getField();
                    Integer category = templateItem.getCategory();
                    Object o = jsonObject.get(field);
                    if (o == null) {
                        continue;
                    }
                    if (isTransformValue) {

                        if (category == 9) {
                            if (o instanceof Integer) {
                                Integer num = (Integer) o;
                                object.put(field, num + "");
                                continue;
                            }
                        }


                    }
                    if (category == 10) {
                        JSONObject obj1 = new JSONObject();
                        object.put(field, obj1);
                        transformFormObjectData(templateItem.getItems(), JSONObject.parseObject(JSONObject.toJSONString(o)), obj1, isTransformValue);
                        continue;
                    }
                    if (category == 11 || category == 4) {
                        JSONArray array = new JSONArray();
                        object.put(field, array);
                        transformFormArrayData(templateItem.getItems(), JSONObject.parseArray(JSONObject.toJSONString(o)), array, isTransformValue);
                        continue;
                    }
                    object.put(field, o);
                }
            }
            toJsonArray.add(obj);
        }
    }
}
