package com.lego.perception.template.service;
import com.alibaba.fastjson.JSONObject;
import com.framework.common.sdto.RespVO;
import com.lego.framework.template.model.entity.FormTemplate;
import java.util.Map;

/**
 * @author yanglf
 */
public interface IDataConvertService {

    /**
     * 档案数据转为表单模板数据
     * @param formTemplate 表单模板
     * @param jsonObject 档案数据
     * @return 表单模板数据
     */
    RespVO<Map<String, Object>> convertData2Template(FormTemplate formTemplate, JSONObject jsonObject);

    /**
     * 表单模板数据转为档案数据
     * @param formTemplate 表单模板
     * @param param 表单模板数据
     * @return 档案数据
     */
    RespVO<JSONObject> convertTemplate2Data(FormTemplate formTemplate, JSONObject param);
}
