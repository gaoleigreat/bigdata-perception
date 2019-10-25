package com.lego.perception.data.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lego.framework.template.model.entity.FormTemplate;
import com.lego.framework.template.model.entity.FormTemplateItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther xiaodao
 * @date 2019/9/19 21:33
 */
public class TemplateDataValidataUtil {
    public static JSONObject transformFormDataItem(FormTemplate formTemplate, JSONObject jsonObject) {
        JSONObject toJsonObj = new JSONObject();
        List<FormTemplateItem> items = formTemplate.getItems();
        for (FormTemplateItem item : items) {
            String field = item.getField();
            Integer category = item.getCategory();
            Object o = jsonObject.get(field);
            if (o == null) {
                continue;
            }
            if (category < 9) {
                toJsonObj.put(field, o.toString());
                continue;
            } else if (category == 9) {
                toJsonObj.put(field, Integer.valueOf(o.toString()));
                continue;
            } else if (category == 12) {
                toJsonObj.put(field, o.toString());
                continue;
            } else if (category == 13) {
                toJsonObj.put(field, Boolean.valueOf(o.toString()));
                continue;
            } else if (category == 14) {
                toJsonObj.put(field, Double.parseDouble(o.toString()));
                continue;
            } else if (category == 10) {
                JSONObject object = new JSONObject();
                toJsonObj.put(field, object);
                transformFormObjectData(item.getItems(), JSONObject.parseObject(JSONObject.toJSONString(o)), object);
                continue;
            } else if (category == 11) {
                JSONArray array = new JSONArray();
                toJsonObj.put(field, array);
                transformFormArrayData(item.getItems(), JSONObject.parseArray(JSONObject.toJSONString(o)), array);
                continue;
            } else {
                toJsonObj.put(field, o);
                continue;
            }
        }
        return toJsonObj;
    }


    private static void transformFormObjectData(List<FormTemplateItem> formTemplateItems, JSONObject jsonObject, JSONObject toJsonObject) {
        for (FormTemplateItem formTemplateItem : formTemplateItems) {
            if (jsonObject == null) {
                return;
            }
            String field = formTemplateItem.getField();
            Integer category = formTemplateItem.getCategory();
            Object o = jsonObject.get(field);
            if (category < 9) {
                toJsonObject.put(field, o.toString());
                continue;
            } else if (category == 9) {
                toJsonObject.put(field, Integer.valueOf(o.toString()));
                continue;
            } else if (category == 12) {
                toJsonObject.put(field, o.toString());
                continue;
            } else if (category == 13) {
                toJsonObject.put(field, Boolean.valueOf(o.toString()));
                continue;
            } else if (category == 14) {
                toJsonObject.put(field, Double.parseDouble(o.toString()));
                continue;
            } else if (category == 10) {
                JSONObject object = new JSONObject();
                toJsonObject.put(field, object);
                transformFormObjectData(formTemplateItem.getItems(), JSONObject.parseObject(JSONObject.toJSONString(o)), object);
                continue;
            } else if (category == 11) {
                JSONArray array = new JSONArray();
                toJsonObject.put(field, array);
                transformFormArrayData(formTemplateItem.getItems(), JSONObject.parseArray(JSONObject.toJSONString(o)), array);
                continue;
            } else {
                toJsonObject.put(field, o);
            }

        }

    }

    private static void transformFormArrayData(List<FormTemplateItem> formTemplateItems, JSONArray jsonArray, JSONArray toJsonArray) {
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
                    if (category < 9) {
                        object.put(field, o.toString());
                        continue;
                    } else if (category == 9) {
                        object.put(field, Integer.valueOf(o.toString()));
                        continue;
                    } else if (category == 12) {
                        object.put(field, o.toString());
                        continue;
                    } else if (category == 13) {
                        object.put(field, Boolean.valueOf(o.toString()));
                        continue;
                    } else if (category == 14) {
                        object.put(field, Double.parseDouble(o.toString()));
                        continue;
                    } else if (category == 10) {
                        JSONObject objectNew = new JSONObject();
                        object.put(field, object);
                        transformFormObjectData(templateItem.getItems(), JSONObject.parseObject(JSONObject.toJSONString(o)), objectNew);
                        continue;
                    } else if (category == 11) {
                        JSONArray array = new JSONArray();
                        object.put(field, array);
                        transformFormArrayData(templateItem.getItems(), JSONObject.parseArray(JSONObject.toJSONString(o)), array);
                        continue;
                    } else {
                        object.put(field, o);
                        continue;
                    }
                }
                toJsonArray.add(object);
            } else {
                toJsonArray.add(obj);
            }

        }
    }


    public static void main(String[] args) {
        FormTemplate formTemplate = new FormTemplate();
        formTemplate.setCategory(1L);
        FormTemplateItem formTemplateItem = new FormTemplateItem();
        FormTemplateItem formTemplateItem1 = new FormTemplateItem();
        FormTemplateItem formTemplateItem10 = new FormTemplateItem();
        FormTemplateItem formTemplateItem20 = new FormTemplateItem();
        FormTemplateItem formTemplateItem2 = new FormTemplateItem();
        formTemplateItem.setField("project");
        formTemplateItem.setCategory(14);
        formTemplateItem1.setField("json");
        formTemplateItem1.setCategory(10);
        formTemplateItem10.setField("lalalla");
        formTemplateItem10.setCategory(14);

        formTemplateItem2.setField("array");
        formTemplateItem2.setCategory(11);

        formTemplateItem20.setField("lalalla");
        formTemplateItem20.setCategory(14);


        List<FormTemplateItem> formTemplateItems = new ArrayList<>();

        List<FormTemplateItem> formTemplateItems1 = new ArrayList<>();
        List<FormTemplateItem> formTemplateItems2 = new ArrayList<>();
        formTemplateItems1.add(formTemplateItem10);
        formTemplateItem1.setItems(formTemplateItems1);

        formTemplateItems2.add(formTemplateItem20);
        formTemplateItem2.setItems(formTemplateItems2);

        formTemplateItems.add(formTemplateItem);
        formTemplateItems.add(formTemplateItem1);
        formTemplateItems.add(formTemplateItem2);

        formTemplate.setItems(formTemplateItems);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObjectnew = new JSONObject();
        JSONObject jsonObjectnew1 = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(jsonObjectnew);

        jsonObjectnew.put("lalalla", "5.6");
        jsonObjectnew1.put("test", "test1");
        jsonObject.put("json", jsonObjectnew);
        jsonObject.put("project", 9.1);
        jsonObject.put("array", jsonArray);

        JSONObject jsonObject1 = TemplateDataValidataUtil.transformFormDataItem(formTemplate, jsonObject);
        System.out.println(jsonObject1);


    }
}
