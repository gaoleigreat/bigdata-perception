package com.lego.perception.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.framework.common.consts.RespConsts;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.framework.excel.utils.ExcelUtil;
import com.framework.mybatis.utils.PageUtil;
import com.framework.mybatis.utils.TableUtils;
import com.framework.mybatis.utils.WrapperUtils;
import com.lego.framework.base.exception.ExceptionBuilder;
import com.lego.framework.business.model.entity.BusinessTable;
import com.lego.framework.template.model.entity.FormTemplate;
import com.lego.framework.template.model.entity.FormTemplateItem;
import com.lego.framework.template.model.entity.SearchParam;
import com.lego.perception.business.mapper.CrudMapper;
import com.lego.perception.business.service.ICrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.*;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/3 18:10
 * @desc :
 */
@Service
public class CrudServiceImpl implements ICrudService {

    @Autowired
    private CrudMapper businessMapper;

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
            String columnType = TableUtils.getColumnType(category);
            if (null == columnType) {
                continue;
            }
            sb.append(field + " ");
            sb.append(columnType + " ");
            String isNull = TableUtils.getIsNull(isRequired);
            if (isNull != null) {
                sb.append(isNull + " ");
            }
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
                                     List<Map<String, Object>> data) {
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
    public RespVO<RespDataVO<Map<String, Object>>> queryBusinessData(String tableName, List<SearchParam> params) {
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
        List<Map<String, Object>> data = businessMapper.queryBusinessData(tableName, wrapper);
        if (!CollectionUtils.isEmpty(data)) {
            for (Map datum : data) {
                datum.remove("fileId");
            }
        }
        return RespVOBuilder.success(data);
    }

    @Override
    public RespVO<PagedResult<Map>> queryBusinessDataPaged(String tableName, List<SearchParam> params, Page page) {
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

        IPage iPage = PageUtil.page2IPage(page);
        IPage<Map> data = businessMapper.queryBusinessData(tableName, wrapper, iPage);
        if (!CollectionUtils.isEmpty(data.getRecords())) {
            for (Map datum : data.getRecords()) {
                datum.remove("fileId");
            }
        }
        return RespVOBuilder.success(PageUtil.iPage2Result(data));
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

    @Override
    public RespVO uploadBusinessData(FormTemplate formTemplate, MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String type = originalFilename != null && originalFilename.lastIndexOf(".") > 0 ? originalFilename.substring(originalFilename.lastIndexOf(".") + 1) : "";
            List<Map<String, Object>> list = ExcelUtil.excelReader(inputStream, type.equals("xlsx") ? 0 : 1, null, null);
            Map<String, FormTemplateItem> itemMap = getTemplateItems(formTemplate);
            if (CollectionUtils.isEmpty(itemMap)) {
                return RespVOBuilder.failure("模板字段不存在");
            }
            if (CollectionUtils.isEmpty(list)) {
                return RespVOBuilder.failure("上传文件字段缺失");
            }
            List<Map<String, Object>> fields = new ArrayList<>();
            for (Map<String, Object> map : list) {
                fields.add(setFields(itemMap, map));
            }
            return insertBusinessData(formTemplate, fields);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public RespVO downloadBusinessData(FormTemplate formTemplate,
                                       List<SearchParam> searchParams,
                                       HttpServletResponse response) {
        String tableName = formTemplate.getDescription();
        RespVO<RespDataVO<Map<String, Object>>> respVO = queryBusinessData(tableName, searchParams);
        if (respVO.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
            return RespVOBuilder.failure();
        }
        Map<String, String> templateItems = getTemplateItemsFields(formTemplate);
        List<Map<String, Object>> mapList = respVO.getInfo().getList();
        List<Map<String, String>> dataList = getItemsStrData(mapList, formTemplate);
        try {
            ExcelUtil.excelWriter(dataList, templateItems, "sheet", formTemplate.getTemplateName(), 0, response);
            return RespVOBuilder.success();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespVOBuilder.failure();
    }

    private List<Map<String, String>> getItemsStrData(List<Map<String, Object>> mapList, FormTemplate formTemplate) {
        List<Map<String, String>> dataList = new ArrayList<>();
        List<FormTemplateItem> items = formTemplate.getItems();
        for (Map<String, Object> map : mapList) {
            Map<String, String> fieldMap = new HashMap<>(16);
            for (FormTemplateItem templateItem : items) {
                String field = templateItem.getField();
                Integer category = templateItem.getCategory();
                String columnValueStr = TableUtils.getColumnValueStr(category, map.get(field));
                fieldMap.put(field, columnValueStr);
            }
            dataList.add(fieldMap);
        }

        return dataList;
    }

    private Map<String, String> getTemplateItemsFields(FormTemplate formTemplate) {
        Map<String, String> itemMap = new LinkedHashMap<>();
        List<FormTemplateItem> items = formTemplate.getItems();
        for (FormTemplateItem item : items) {
            itemMap.put(item.getField(), item.getTitle());
        }
        return itemMap;
    }

    private Map<String, Object> setFields(Map<String, FormTemplateItem> itemMap, Map<String, Object> map) {
        Map<String, Object> field = new LinkedHashMap<>();
        try {
            for (Map.Entry<String, Object> m : map.entrySet()) {
                FormTemplateItem item = itemMap.get(m.getKey());
                Integer category = item.getCategory();
                String f = item.getField();
                field.put(f, TableUtils.getColumnValue(category, m.getValue() + ""));
            }
        } catch (Exception e) {
            ExceptionBuilder.operateFailException("数据类型错误:" + e.getMessage());
        }
        return field;
    }

    private Map<String, FormTemplateItem> getTemplateItems(FormTemplate formTemplate) {
        Map<String, FormTemplateItem> itemMap = new LinkedHashMap<>();
        List<FormTemplateItem> items = formTemplate.getItems();
        for (FormTemplateItem item : items) {
            itemMap.put(item.getTitle(), item);
        }
        return itemMap;
    }


}
