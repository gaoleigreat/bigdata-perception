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
import com.lego.framework.template.feign.TemplateFeignClient;
import com.lego.framework.template.model.entity.EnumerationItem;
import com.lego.framework.template.model.entity.FormTemplate;
import com.lego.framework.template.model.entity.FormTemplateItem;
import com.lego.framework.template.model.entity.SearchParam;
import com.lego.perception.business.mapper.CrudMapper;
import com.lego.perception.business.service.ICrudService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class CrudServiceImpl implements ICrudService {

    @Autowired
    private CrudMapper crudMapper;

    @Autowired
    private TemplateFeignClient templateFeignClient;

    @Override
    public RespVO createBusinessTable(FormTemplate formTemplate) {
        String tableName = formTemplate.getDescription();
        List<FormTemplateItem> list = formTemplate.getItems();
        if (CollectionUtils.isEmpty(list)) {
            return RespVOBuilder.failure("该模板子字段不能为空");
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
        sb.replace(sb.length() - 1, sb.length(), "");
        crudMapper.createBusinessTable(tableName, sb.toString());
        Integer existTable = crudMapper.existTable(tableName);
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
            Integer insertBusinessData = crudMapper.insertBusinessData(businessTable);
            if (insertBusinessData <= 0) {
                ExceptionBuilder.operateFailException("新增数据失败");
            }
        }
        return RespVOBuilder.success();
    }


    @Override
    public RespVO<RespDataVO<Map<String, Object>>> queryBusinessData(String templateCode, List<SearchParam> params) {
        RespVO<FormTemplate> respVO = templateFeignClient.findFormTemplateByCode(templateCode);
        if (respVO.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
            return RespVOBuilder.failure("获取模板信息失败");
        }
        FormTemplate formTemplate = respVO.getInfo();
        if (formTemplate == null) {
            return RespVOBuilder.failure("找不到对应模板信息");
        }
        StringBuilder sb = getColumns(formTemplate);
        QueryWrapper wrapper = getQueryWrapper(params);
        List<Map<String, Object>> data = crudMapper.queryBusinessData(formTemplate.getDescription(), wrapper, sb.toString());
        if (!CollectionUtils.isEmpty(data)) {
            for (Map datum : data) {
                datum.remove("file_id");
            }
        }
        return RespVOBuilder.success(data);
    }

    private QueryWrapper getQueryWrapper(List<SearchParam> params) {
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
        return wrapper;
    }

    @Override
    public RespVO<PagedResult<Map<String, Object>>> queryBusinessDataPaged(String templateCode, List<SearchParam> params, Page page) {
        RespVO<FormTemplate> respVO = templateFeignClient.findFormTemplateByCode(templateCode);
        if (respVO.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
            return RespVOBuilder.failure("获取模板信息失败");
        }
        FormTemplate formTemplate = respVO.getInfo();
        if (formTemplate == null) {
            return RespVOBuilder.failure("找不到对应模板信息");
        }
        StringBuilder sb = getColumns(formTemplate);
        sb.replace(sb.length() - 1, sb.length(), "");
        QueryWrapper wrapper = getQueryWrapper(params);

        IPage iPage = PageUtil.page2IPage(page);
        IPage<Map<String, Object>> data = crudMapper.queryBusinessData(iPage, formTemplate.getDescription(), wrapper, sb.toString());
        if (!CollectionUtils.isEmpty(data.getRecords())) {
            for (Map datum : data.getRecords()) {
                datum.remove("file_id");
            }
        }
        return RespVOBuilder.success(PageUtil.iPage2Result(data));
    }

    private StringBuilder getColumns(FormTemplate formTemplate) {
        List<FormTemplateItem> items = formTemplate.getItems();
        StringBuilder sb = new StringBuilder();
        if (CollectionUtils.isEmpty(items)) {
            ExceptionBuilder.operateFailException("获取模板字段失败");
        }
        for (FormTemplateItem item : items) {
            sb.append("IFNULL(" + item.getField() + ",'') as " + item.getField() + ",");
        }
        return sb;
    }

    @Override
    public RespVO updateBusinessData(String tableName, Map<String, Object> data) {
        log.info("data:{},tableName:{}", data, tableName);
        if (!data.containsKey("id")) {
            // 防止数据全部删除
            return RespVOBuilder.failure("修改条件缺失");
        }
        Long id = Long.valueOf(data.get("id") + "");
        BusinessTable business = new BusinessTable(id, tableName, data);
        Integer update = crudMapper.updateByID(business);
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
        Long id = Long.valueOf(data.get("id") + "");
        BusinessTable business = new BusinessTable(id, tableName, null);
        Integer delBusinessData = crudMapper.delBusinessData(business);
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
    public void downloadBusinessData(FormTemplate formTemplate,
                                     List<SearchParam> searchParams,
                                     HttpServletResponse response) {
        String tableName = formTemplate.getDescription();
        RespVO<RespDataVO<Map<String, Object>>> respVO = queryBusinessData(tableName, searchParams);
        if (respVO.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
            ExceptionBuilder.operateFailException("下载失败");
        }
        Map<String, String> templateItems = getTemplateItemsFields(formTemplate);
        List<Map<String, Object>> mapList = respVO.getInfo().getList();
        List<Map<String, String>> dataList = getItemsStrData(mapList, formTemplate);
        try {
            ExcelUtil.excelWriter(dataList, templateItems, "sheet", formTemplate.getTemplateName(), 0, response);
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionBuilder.operateFailException("下载失败");
        }
    }

    @Override
    public RespVO<Map<String, Object>> queryBusinessDataByCode(String templateCode, String equipmentCode) {
        RespVO<FormTemplate> respVO = templateFeignClient.findFormTemplateByCode(templateCode);
        if (respVO.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
            return RespVOBuilder.failure("获取模板信息失败");
        }
        FormTemplate formTemplate = respVO.getInfo();
        if (formTemplate == null) {
            return RespVOBuilder.failure("找不到对应模板信息");
        }
        List<FormTemplateItem> items = formTemplate.getItems();
        if (CollectionUtils.isEmpty(items)) {
            return RespVOBuilder.failure("获取不到模板字段");
        }
        StringBuilder sb = new StringBuilder();
        for (FormTemplateItem item : items) {
            sb.append("IFNULL(" + item.getField() + ",'') as " + item.getField() + ",");
        }
        sb.replace(sb.length() - 1, sb.length(), "");
        Map<String, Object> data = crudMapper.queryByCode(formTemplate.getDescription(), equipmentCode, sb.toString());
        return RespVOBuilder.success(data);
    }

    @Override
    public Integer queryCount(String description, List<SearchParam> searchParams) {
        QueryWrapper<BusinessTable> wrapper = getQueryWrapper(searchParams);
        return crudMapper.findCountByCondition(description, wrapper);
    }

    @Override
    public List<Map<String, String>> findSumExcavationByCondition(String description, List<SearchParam> searchParams, Integer type) {
        QueryWrapper<BusinessTable> wrapper = getQueryWrapper(searchParams);
        return crudMapper.findSumExcavationByCondition(description, wrapper, type);
    }

    private List<Map<String, String>> getItemsStrData(List<Map<String, Object>> mapList, FormTemplate formTemplate) {
        List<Map<String, String>> dataList = new ArrayList<>();
        List<FormTemplateItem> items = formTemplate.getItems();
        for (Map<String, Object> map : mapList) {
            Map<String, String> fieldMap = new HashMap<>(16);
            for (FormTemplateItem templateItem : items) {
                String field = templateItem.getField();
                Integer category = templateItem.getCategory();
                Object o = map.get(field);
                if (o == null) {
                    fieldMap.put(field, null);
                    continue;
                }
                String columnValueStr;
                if (category == 6) {
                    Long enumId = templateItem.getEnumId();
                    RespVO<EnumerationItem> respVO = templateFeignClient.findItem(enumId, Integer.valueOf(o + ""));
                    if (respVO.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
                        continue;
                    }
                    EnumerationItem info = respVO.getInfo();
                    if (null == info) {
                        continue;
                    }
                    columnValueStr = info.getLabel();
                } else {
                    columnValueStr = TableUtils.getColumnValueStr(category, o);
                }
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
