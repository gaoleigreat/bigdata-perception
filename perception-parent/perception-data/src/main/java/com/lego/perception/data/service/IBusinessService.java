package com.lego.perception.data.service;

import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.lego.framework.template.model.entity.FormTemplate;
import com.lego.framework.template.model.entity.SearchParam;

import java.util.List;
import java.util.Map;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/3 18:05
 * @desc :
 */
public interface IBusinessService {

    /**
     * 创建业务表
     *
     * @param formTemplate
     * @return
     */
    RespVO createBusinessTable(FormTemplate formTemplate);


    /**
     * 新增业务数据
     *
     * @param formTemplate
     * @param data
     * @return
     */
    RespVO insertBusinessData(FormTemplate formTemplate, List<Map<String, Object>> data, Long fileId);


    /**
     * 查询业务数据
     *
     * @param tableName
     * @param params
     * @return
     */
    RespVO<RespDataVO<Map>> queryBusinessData(String tableName, List<SearchParam> params);


    /**
     * 更新业务数据
     *
     * @param tableName
     * @param data
     * @return
     */
    RespVO updateBusinessData(String tableName, Map<String, Object> data);


    /**
     * 删除业务数据
     *
     * @param tableName
     * @param data
     * @return
     */
    RespVO delBusinessData(String tableName, Map<String, Object> data);


}
