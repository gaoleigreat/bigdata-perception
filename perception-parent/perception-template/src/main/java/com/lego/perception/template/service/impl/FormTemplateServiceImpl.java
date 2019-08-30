package com.lego.perception.template.service.impl;

import com.baomidou.mybatisplus.extension.api.R;
import com.framework.common.consts.RespConsts;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
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

    @Autowired
    @Qualifier(value = "formTemplateItemServiceImpl")
    private ITemplateItemService formTemplateItemService;

    @Autowired
    @Qualifier(value = "dataTemplateItemService")
    private ITemplateItemService dataTemplateItemService;

    @Autowired
    private EnumerationInit enumerationInit;

    @Override
    public PagedResult<FormTemplate> findPagedList(FormTemplate template, Page page) {

        return formTemplateMapper.findPagedList(template, page);
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
        if(!CollectionUtils.isEmpty(templateList)){

            template = templateList.get(0);

            FormTemplateItem queryParam = new FormTemplateItem();
            queryParam.setTemplateId(template.getId());

            Map<Long, Enumeration> enumerationMap = enumerationInit.getEnumerationIdMap();
            //查询数据项
            List<FormTemplateItem> itemList = formTemplateItemService.findList(queryParam);

            DataTemplateItem query = new DataTemplateItem();
            query.setTemplateId(template.getDataTemplateId());
            List<DataTemplateItem> dataTemplateItems = dataTemplateItemService.findList(query);
            Map<String, String> map = new HashMap<>();
            if(!CollectionUtils.isEmpty(dataTemplateItems)){
                for(DataTemplateItem item : dataTemplateItems){
                    map.put(item.getAbsoluteField(), item.getTitle());
                }
            }

            if(!CollectionUtils.isEmpty(itemList)){
                for(FormTemplateItem item : itemList){
                    item.setDataFieldName(map.get(item.getDataField()));
                    if(null != item.getEnumId()){
                        item.setEnumName(enumerationMap.containsKey(item.getEnumId()) ? enumerationMap.get(item.getEnumId()).getEnumName() : "");
                    }
                }
            }

            //转为树形结构
            List<FormTemplateItem> itemTree = formTemplateItemService.convertList2Tree(itemList);

            template.setItems(itemTree);
        }
        return template;
    }

    @Override
    public RespVO insert(FormTemplate template) {
        if(null == template){
            return RespVOBuilder.failure("参数缺失");
        }

        ValidateResult v = validateTemplate(template);
        if(!v.getResult()){
            return RespVOBuilder.failure(v.getMsg());
        }

        template.setCreateInfo();

        //新增模板数据项
        formTemplateMapper.save(template);

        //新增模板数据项
        if(!CollectionUtils.isEmpty(template.getItems())){
            RespVO r = formTemplateItemService.insertTree(template.getId(), template.getItems());
            if(r.getRetCode()!= RespConsts.SUCCESS_RESULT_CODE){
                return r;
            }
        }

        return RespVOBuilder.success();
    }


    private ValidateResult validateTemplate(FormTemplate template) {
        ValidateResult  v = templateValidateService.validateTemplate(template);
        if (!v.getResult()){
            return v;
        }
        v = isDuplicate(template);
        if (!v.getResult()){
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
        if(!CollectionUtils.isEmpty(lst)){
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
        if(!CollectionUtils.isEmpty(lst) && !id.equals(lst.get(0).getId())){
            v.setResult(false);
            v.setMsg("模板编码重复");
            return v;
        }
        v.setResult(true);
        return v;
    }

    @Override
    public RespVO update(FormTemplate formTemplate) {
        if(null == formTemplate || null == formTemplate.getId()){
            return RespVOBuilder.failure("参数缺失");
        }

        if(null != formTemplate.getTemplateCode()){
            ValidateResult v = isDuplicate(formTemplate, formTemplate.getId());
            if(!v.getResult()){
                return RespVOBuilder.failure(v.getMsg());
            }
        }
        formTemplate.setUpdateInfo();
        formTemplateMapper.update(formTemplate);
        return RespVOBuilder.success();
    }

    @Override
    public RespVO delete(Long id) {
        if(null == id){
            return RespVOBuilder.failure("参数缺失");
        }
        formTemplateMapper.delete(id);
        return RespVOBuilder.success();
    }

    @Override
    public FormTemplate findById(Long id) {
        FormTemplate formTemplate=new FormTemplate();
        formTemplate.setId(id);
        List<FormTemplate> list = formTemplateMapper.findList(formTemplate);
        return list!=null && list.size()>0 ? list.get(0) : null;
    }
}
