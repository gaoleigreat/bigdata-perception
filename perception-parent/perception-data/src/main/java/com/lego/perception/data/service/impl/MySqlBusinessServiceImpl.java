package com.lego.perception.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.framework.mybatis.utils.TableUtils;
import com.framework.mybatis.utils.WrapperUtils;
import com.lego.framework.base.exception.ExceptionBuilder;
import com.lego.framework.business.model.entity.BusinessTable;
import com.lego.framework.template.model.entity.FormTemplate;
import com.lego.framework.template.model.entity.FormTemplateItem;
import com.lego.framework.template.model.entity.SearchParam;
import com.lego.perception.data.mapper.DataMapper;
import com.lego.perception.data.service.IBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
    private DataMapper businessMapper;

    @Override
    public RespVO createBusinessTable(FormTemplate formTemplate) {
        String tableName = formTemplate.getDescription();
        List<FormTemplateItem> list = formTemplate.getItems();
        if (CollectionUtils.isEmpty(list)) {
            return RespVOBuilder.failure("字段不能为空");
        }
        StringBuilder sb = new StringBuilder(" id BIGINT NOT NULL PRIMARY KEY UNIQUE AUTO_INCREMENT COMMENT 'ID', ");
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
        sb.append(" file_id BIGINT NOT NULL COMMENT '文件id '");
        businessMapper.createBusinessTable(tableName, sb.toString());
        Integer existTable = businessMapper.existTable(tableName);
        if (existTable != null) {
            return RespVOBuilder.success();
        }
        return RespVOBuilder.failure();
    }


    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public RespVO insertBusinessData(FormTemplate formTemplate,
                                     List<Map<String, Object>> data,
                                     Long fileId) {
        String tableName = formTemplate.getDescription();
        // 参数校验
        for (Map<String, Object> objectMap : data) {
            objectMap.put("fileId", fileId);
            BusinessTable businessTable = new BusinessTable(null, tableName, objectMap);
            Integer insertBusinessData = businessMapper.insertBusinessData(businessTable);
            if (insertBusinessData <= 0) {
                ExceptionBuilder.operateFailException("新增数据失败");
            }
        }
        return RespVOBuilder.success();
    }


    @Override
    public RespVO<RespDataVO<Map>> queryBusinessData(String tableName, List<SearchParam> params) {
        QueryWrapper wrapper = new QueryWrapper();
        if (!CollectionUtils.isEmpty(params)) {
            for (SearchParam param : params) {
                String symbol = param.getSymbol();
                String absoluteField = param.getAbsoluteField();
                String value = param.getValue();
                Integer dataType = param.getDataType();
                if (null == symbol || null == absoluteField || null == value || null == dataType) {
                    continue;
                }
                WrapperUtils.addAdvancedCondition(wrapper, symbol, absoluteField, value);
            }
        }
        List<Map> data = businessMapper.queryBusinessData(tableName, wrapper);
        if (!CollectionUtils.isEmpty(data)) {
            for (Map datum : data) {
                datum.remove("fileId");
            }
        }
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
