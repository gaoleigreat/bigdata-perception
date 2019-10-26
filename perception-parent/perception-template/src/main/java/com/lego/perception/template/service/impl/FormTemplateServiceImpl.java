package com.lego.perception.template.service.impl;

import com.framework.common.consts.RespConsts;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.framework.mybatis.utils.PageUtil;
import com.lego.framework.base.exception.ExceptionBuilder;
import com.lego.framework.business.feign.CrudClient;
import com.lego.framework.data.feign.DataClient;
import com.lego.framework.event.template.TemplateProcessorSender;
import com.lego.framework.template.model.entity.*;
import com.lego.perception.template.init.EnumerationInit;
import com.lego.perception.template.mapper.FormTemplateMapper;
import com.lego.perception.template.service.IFormTemplateService;
import com.lego.perception.template.service.ITemplateItemService;
import com.lego.perception.template.service.ITemplateValidateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class FormTemplateServiceImpl implements IFormTemplateService {

    @Autowired
    private FormTemplateMapper formTemplateMapper;

    @Autowired
    private ITemplateValidateService templateValidateService;


    private Map<Integer, String> tags = new HashMap<>();

    @Autowired
    private DataClient dataClient;


    @Autowired
    @Qualifier(value = "formTemplateItemServiceImpl")
    private ITemplateItemService formTemplateItemService;

    @Autowired
    private EnumerationInit enumerationInit;

    @Override
    public PagedResult<FormTemplate> findPagedList(FormTemplate template, Page page) {
        return PageUtil.queryPaged(page, template, formTemplateMapper);
    }

    @Override
    public List<FormTemplate> findAll() {
        return formTemplateMapper.findList(null);
    }

    @Override
    public List<FormTemplate> findList(FormTemplate template) {


        return formTemplateMapper.findList(template);
    }

    @Override
    public FormTemplate find(FormTemplate formTemplate) {
        FormTemplate template = null;
        List<FormTemplate> templateList = formTemplateMapper.findList(formTemplate);
        if (!CollectionUtils.isEmpty(templateList)) {

            template = templateList.get(0);

            FormTemplateItem queryParam = new FormTemplateItem();
            queryParam.setTemplateId(template.getId());

            Map<Long, Enumeration> enumerationMap = enumerationInit.getEnumerationIdMap();
            //查询数据项
            List<FormTemplateItem> itemList = formTemplateItemService.findList(queryParam);

          /*  DataTemplateItem query = new DataTemplateItem();
            query.setTemplateId(template.getDataTemplateId());
            List<DataTemplateItem> dataTemplateItems = dataTemplateItemService.findList(query);
            Map<String, String> map = new HashMap<>();
            if (!CollectionUtils.isEmpty(dataTemplateItems)) {
                for (DataTemplateItem item : dataTemplateItems) {
                    map.put(item.getAbsoluteField(), item.getTitle());
                }
            }*/

           /* if (!CollectionUtils.isEmpty(itemList)) {
                for (FormTemplateItem item : itemList) {
                    item.setDataFieldName(map.get(item.getDataField()));
                    if (null != item.getEnumId()) {
                        item.setEnumName(enumerationMap.containsKey(item.getEnumId()) ? enumerationMap.get(item.getEnumId()).getEnumName() : "");
                    }
                }
            }*/

            //转为树形结构
            List<FormTemplateItem> itemTree = formTemplateItemService.convertList2Tree(itemList);

            template.setItems(itemTree);
        }
        return template;
    }

    @Override
    public RespVO insert(FormTemplate template) {
        if (null == template) {
            return RespVOBuilder.failure("参数缺失");
        }

        ValidateResult v = validateTemplate(template);
        if (!v.getResult()) {
            return RespVOBuilder.failure(v.getMsg());
        }

        template.setCreateInfo();

        //新增模板数据项
        formTemplateMapper.save(template);

        //新增模板数据项
        if (!CollectionUtils.isEmpty(template.getItems())) {
            RespVO r = formTemplateItemService.insertTree(template.getId(), template.getItems());
            if (r.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
                return r;
            }
        }
        if (tags.containsKey(template.getDataType())) {
            RespVO respVO = dataClient.createDataTable(template.getTemplateCode());
            if (respVO.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
                ExceptionBuilder.operateFailException("创建模板业务表失败");
            }
        }
        return RespVOBuilder.success();
    }


    @PostConstruct
    public void initTags() {
        tags.put(1, "地形地貌");
        tags.put(2, "水文环境");
        tags.put(3, "市政管线");
        tags.put(4, "勘探设计");
        tags.put(5, "工程施工");
        tags.put(6, "装备运行");
        tags.put(7, "运营维护");
        tags.put(8, "其他");
    }

    @PreDestroy
    public void destroy() {
        tags.clear();
        tags = null;
    }


    private ValidateResult validateTemplate(FormTemplate template) {
        ValidateResult v = templateValidateService.validateTemplate(template);
        if (!v.getResult()) {
            return v;
        }
        v = isDuplicate(template);
        if (!v.getResult()) {
            return v;
        }

        v.setResult(true);
        return v;
    }

    private ValidateResult isDuplicate(FormTemplate dataTemplate) {
        ValidateResult v = new ValidateResult();
        FormTemplate templateParam = new FormTemplate();
        templateParam.setTemplateCode(dataTemplate.getTemplateCode());
        List<FormTemplate> lst = formTemplateMapper.findList(templateParam);
        if (!CollectionUtils.isEmpty(lst)) {
            v.setResult(false);
            v.setMsg("模板编码重复");
            return v;
        }
        v.setResult(true);
        return v;
    }

    private ValidateResult isDuplicate(FormTemplate dataTemplate, Long id) {
        ValidateResult v = new ValidateResult();
        FormTemplate templateParam = new FormTemplate();
        templateParam.setTemplateCode(dataTemplate.getTemplateCode());
        List<FormTemplate> lst = formTemplateMapper.findList(templateParam);
        if (!CollectionUtils.isEmpty(lst) && !id.equals(lst.get(0).getId())) {
            v.setResult(false);
            v.setMsg("模板编码重复");
            return v;
        }
        v.setResult(true);
        return v;
    }

    @Override
    public RespVO update(FormTemplate formTemplate) {
        if (null == formTemplate || null == formTemplate.getId()) {
            return RespVOBuilder.failure("参数缺失");
        }

        if (null != formTemplate.getTemplateCode()) {
            ValidateResult v = isDuplicate(formTemplate, formTemplate.getId());
            if (!v.getResult()) {
                return RespVOBuilder.failure(v.getMsg());
            }
        }
        formTemplate.setUpdateInfo();
        formTemplateMapper.update(formTemplate);
        return RespVOBuilder.success();
    }

    @Override
    public RespVO delete(Long id) {
        if (null == id) {
            return RespVOBuilder.failure("参数缺失");
        }
        formTemplateMapper.delete(id);
        return RespVOBuilder.success();
    }

    @Override
    public FormTemplate findById(Long id) {
        FormTemplate formTemplate = new FormTemplate();
        formTemplate.setId(id);
        List<FormTemplate> list = formTemplateMapper.findList(formTemplate);
        return list != null && list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public Map<String, List<FormTemplateItem>> queryFields(String code) {
        Map<String, List<FormTemplateItem>> map = new HashMap<>();
        FormTemplate queryTemplate = new FormTemplate();
        queryTemplate.setTemplateCode(code);
        FormTemplate template = find(queryTemplate);
        if (template == null) {
            return map;
        }
        List<FormTemplateItem> itemList = template.getItems();
        if (CollectionUtils.isEmpty(itemList)) {
            return map;
        }
        map.put(template.getTemplateName(), itemList);
        return map;
    }
}
