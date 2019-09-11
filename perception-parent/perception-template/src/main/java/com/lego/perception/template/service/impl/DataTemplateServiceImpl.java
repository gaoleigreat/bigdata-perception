package com.lego.perception.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.framework.common.consts.RespConsts;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.framework.mybatis.utils.PageUtil;
import com.lego.framework.template.model.entity.DataTemplate;
import com.lego.framework.template.model.entity.DataTemplateItem;
import com.lego.framework.template.model.entity.Enumeration;
import com.lego.framework.template.model.entity.ValidateResult;
import com.lego.perception.template.init.EnumerationInit;
import com.lego.perception.template.mapper.DataTemplateMapper;
import com.lego.perception.template.service.IDataTemplateService;
import com.lego.perception.template.service.ITemplateItemService;
import com.lego.perception.template.service.ITemplateValidateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class DataTemplateServiceImpl implements IDataTemplateService {


    @Autowired
    private DataTemplateMapper dataTemplateMapper;

    @Autowired
    private ITemplateValidateService templateValidateService;

    @Autowired
    @Qualifier(value = "dataTemplateItemService")
    private ITemplateItemService dataTemplateItemService;

    @Autowired
    private EnumerationInit enumerationInit;

    @Override
    public PagedResult<DataTemplate> findPagedList(DataTemplate template, Page page) {
        return PageUtil.queryPaged(page,template,dataTemplateMapper);
    }

    @Override
    public List<DataTemplate> findAll() {
        return dataTemplateMapper.findList(null);
    }

    @Override
    public List<DataTemplate> findList(DataTemplate dataTemplate) {


        return dataTemplateMapper.findList(dataTemplate);
    }

    @Override
    public DataTemplate find(DataTemplate dataTemplate) {
        DataTemplate template = null;
        List<DataTemplate> templateList = dataTemplateMapper.findList(dataTemplate);
        if (!CollectionUtils.isEmpty(templateList)) {

            template = templateList.get(0);

            DataTemplateItem queryParam = new DataTemplateItem();
            queryParam.setTemplateId(template.getId());

            //查询数据项
            List<DataTemplateItem> itemList = dataTemplateItemService.findList(queryParam);

            Map<Long, Enumeration> enumerationMap = enumerationInit.getEnumerationIdMap();
            if (!CollectionUtils.isEmpty(itemList)) {
                for (DataTemplateItem item : itemList) {
                    if (null != item.getEnumId()) {
                        item.setEnumName(enumerationMap.containsKey(item.getEnumId()) ? enumerationMap.get(item.getEnumId()).getEnumName() : "");
                    }
                }
            }

            //转为树形结构
            List<DataTemplateItem> itemTree = dataTemplateItemService.convertList2Tree(itemList);

            template.setItems(itemTree);
        }
        return template;
    }

    @Override
    public RespVO insert(DataTemplate dataTemplate) {
        if (null == dataTemplate) {
            return RespVOBuilder.failure("参数缺失");
        }

        ValidateResult v = validateTemplate(dataTemplate);
        if (!v.getResult()) {
            return RespVOBuilder.failure(v.getMsg());
        }

        dataTemplate.setCreateInfo();

        //新增模板
        dataTemplateMapper.save(dataTemplate);

        //新增模板数据项
        if (!CollectionUtils.isEmpty(dataTemplate.getItems())) {

            RespVO r = dataTemplateItemService.insertTree(dataTemplate.getId(), dataTemplate.getItems());
            if (r.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
                return r;
            }
        }

        return RespVOBuilder.success();
    }

    private ValidateResult validateTemplate(DataTemplate dataTemplate) {
        ValidateResult v = templateValidateService.validateTemplate(dataTemplate);
        if (!v.getResult()) {
            return v;
        }
        v = isDuplicate(dataTemplate);
        if (!v.getResult()) {
            return v;
        }

        v.setResult(true);
        return v;
    }

    private ValidateResult isDuplicate(DataTemplate dataTemplate) {
        ValidateResult v = new ValidateResult();
        DataTemplate templateParam = new DataTemplate();
        templateParam.setTemplateCode(dataTemplate.getTemplateCode());
        List<DataTemplate> lst = dataTemplateMapper.findList(templateParam);
        if (!CollectionUtils.isEmpty(lst)) {
            v.setResult(false);
            v.setMsg("模板编码重复");
            return v;
        }
        v.setResult(true);
        return v;
    }

    private ValidateResult isDuplicate(DataTemplate dataTemplate, Long id) {
        ValidateResult v = new ValidateResult();
        DataTemplate templateParam = new DataTemplate();
        templateParam.setTemplateCode(dataTemplate.getTemplateCode());
        List<DataTemplate> lst = dataTemplateMapper.findList(templateParam);
        if (!CollectionUtils.isEmpty(lst) && !id.equals(lst.get(0).getId())) {
            v.setResult(false);
            v.setMsg("模板编码重复");
            return v;
        }
        v.setResult(true);
        return v;
    }

    @Override
    public RespVO update(DataTemplate dataTemplate) {
        if (null == dataTemplate) {
            return RespVOBuilder.failure("参数缺失");
        }

        if (null != dataTemplate.getTemplateCode()) {
            ValidateResult v = isDuplicate(dataTemplate, dataTemplate.getId());
            if (!v.getResult()) {
                return RespVOBuilder.failure(v.getMsg());
            }
        }

        dataTemplate.setUpdateInfo();
        dataTemplateMapper.update(dataTemplate);
        return RespVOBuilder.success();
    }

    @Override
    public RespVO delete(Long id) {
        if (null == id) {
            return RespVOBuilder.failure("参数缺失");
        }
        dataTemplateMapper.delete(id);
        return RespVOBuilder.success();
    }

    @Override
    public DataTemplate findById(Long templateId) {
        DataTemplate dataTemplate = new DataTemplate();
        dataTemplate.setId(templateId);
        List<DataTemplate> list = dataTemplateMapper.findList(dataTemplate);
        return list != null && list.size() > 0 ? list.get(0) : null;
    }
}
