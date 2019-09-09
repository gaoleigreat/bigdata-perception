package com.lego.perception.business.service.impl;

import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.exception.ExceptionBuilder;
import com.lego.framework.business.model.entity.BusinessTable;
import com.lego.framework.template.model.entity.FormTemplate;
import com.lego.framework.template.model.entity.FormTemplateItem;
import com.lego.perception.business.mapper.BusinessMapper;
import com.lego.perception.business.service.IBusinessService;
import com.lego.perception.business.utils.TableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/3 18:10
 * @desc :
 */
@Service(value = "mySqlBusinessServiceImpl")
public class MySqlBusinessServiceImpl implements IBusinessService {

    @Autowired
    private BusinessMapper businessMapper;

    @Override
    public RespVO createBusinessTable(FormTemplate formTemplate) {
        String tableName = formTemplate.getDescription();
        List<FormTemplateItem> list = formTemplate.getItems();
        if (CollectionUtils.isEmpty(list)) {
            return RespVOBuilder.failure("字段不能为空");
        }
        StringBuilder sb = new StringBuilder();
        for (FormTemplateItem templateItem : list) {
            // 字段名称
            String field = templateItem.getField();
            // 类型
            Integer category = templateItem.getCategory();
            // 描述
            String title = templateItem.getTitle();
            // 默认值
            String defaultValue = templateItem.getDefaultValue();
            // 是否必填，1：是，2：否
            Integer isRequired = templateItem.getIsRequired();
            sb.append(field + " ");
            sb.append(TableUtils.getColumnType(category) + " ");
            sb.append(TableUtils.getIsNull(isRequired) + " ");
            sb.append(TableUtils.getDefaultValue(defaultValue) + " ");
            sb.append(TableUtils.getComment(title));
            sb.append(",");
        }
        sb.replace(sb.length() - 1, sb.length(), "");
        businessMapper.createBusinessTable(tableName, sb.toString());
        Integer existTable = businessMapper.existTable(tableName);
        if (existTable != null) {
            return RespVOBuilder.success();
        }
        return RespVOBuilder.failure();
    }


    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public RespVO insertBusinessData(FormTemplate formTemplate, List<Map<String, Object>> data) {
        String tableName = formTemplate.getDescription();
        // 参数校验
        for (Map<String, Object> objectMap : data) {
            BusinessTable businessTable = new BusinessTable(null, tableName, objectMap);
            Integer insertBusinessData = businessMapper.insertBusinessData(businessTable);
            if (insertBusinessData <= 0) {
                ExceptionBuilder.operateFailException("新增数据失败");
            }
        }
        return RespVOBuilder.success();
    }


    @Override
    public RespVO queryBusinessData(String tableName, Map<String, Object> param) {
        BusinessTable businessTable = new BusinessTable(null, tableName, param);
        List<Map<String, Object>> data = businessMapper.queryBusinessData(businessTable);
        return RespVOBuilder.success(data);
    }

    @Override
    public RespVO updateBusinessData(String tableName, Map<String, Object> data) {
        if (!data.containsKey("id")) {
            // 防止数据全部删除
            return RespVOBuilder.failure("修改条件缺失");
        }
        Long id = (Long) data.get("id");
        BusinessTable business = new BusinessTable(id, tableName, null);
        Integer update = businessMapper.updateByID(business);
        if (update > 0) {
            return RespVOBuilder.success();
        }
        return RespVOBuilder.failure();
    }

    @Override
    public RespVO delBusinessData(String tableName, Map<String, Object> data) {
        if (!data.containsKey("id")) {
            return RespVOBuilder.failure("删除条件缺失");
        }
        Long id = (Long) data.get("id");
        BusinessTable business = new BusinessTable(id, tableName, null);
        Integer delBusinessData = businessMapper.delBusinessData(business);
        if (delBusinessData > 0) {
            return RespVOBuilder.success();
        }
        return RespVOBuilder.failure();
    }


}
