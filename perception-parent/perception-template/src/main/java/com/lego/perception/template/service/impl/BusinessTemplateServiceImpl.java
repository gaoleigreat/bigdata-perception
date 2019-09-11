package com.lego.perception.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.mybatis.tool.WhereEntityTool;
import com.framework.mybatis.utils.PageUtil;
import com.lego.framework.template.model.entity.BusinessTemplate;
import com.lego.perception.template.mapper.BusinessTemplateMapper;
import com.lego.perception.template.service.IBusinessTemplateService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-09-03 03:40:16
 * @since jdk 1.8
 */
@Service("tplBusinessTemplateService")
public class BusinessTemplateServiceImpl implements IBusinessTemplateService {

    @Autowired
    public BusinessTemplateMapper businessTemplateMapper;


    @Override
    public PagedResult<BusinessTemplate> selectPaged(BusinessTemplate businessTemplate,
                                                     Page page) {
        return PageUtil.queryPaged(page, businessTemplate, businessTemplateMapper);
    }

    @Override
    public BusinessTemplate selectByPrimaryKey(Long id) {
        return businessTemplateMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer deleteByPrimaryKey(Long id) {
        return businessTemplateMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer insert(BusinessTemplate businessTemplate) {
        return businessTemplateMapper.insert(businessTemplate);
    }

    @Override
    public Integer insertSelective(BusinessTemplate businessTemplate) {
        return businessTemplateMapper.insertSelective(businessTemplate);
    }

    @Override
    public Integer insertSelectiveIgnore(BusinessTemplate businessTemplate) {
        return businessTemplateMapper.insertSelectiveIgnore(businessTemplate);
    }

    @Override
    public Integer updateByPrimaryKeySelective(BusinessTemplate businessTemplate) {
        return businessTemplateMapper.updateByPrimaryKeySelective(businessTemplate);
    }

    @Override
    public Integer updateByPrimaryKey(BusinessTemplate businessTemplate) {
        return businessTemplateMapper.updateByPrimaryKey(businessTemplate);
    }

    @Override
    public Integer batchInsert(List<BusinessTemplate> list) {
        return businessTemplateMapper.batchInsert(list);
    }

    @Override
    public Integer batchUpdate(List<BusinessTemplate> list) {
        return businessTemplateMapper.batchUpdate(list);
    }

    /**
     * 存在即更新
     *
     * @param businessTemplate
     * @return
     */
    @Override
    public Integer upsert(BusinessTemplate businessTemplate) {
        return businessTemplateMapper.upsert(businessTemplate);
    }

    /**
     * 存在即更新，可选择具体属性
     *
     * @param businessTemplate
     * @return
     */
    @Override
    public Integer upsertSelective(BusinessTemplate businessTemplate) {
        return businessTemplateMapper.upsertSelective(businessTemplate);
    }

    @Override
    public List<BusinessTemplate> query(BusinessTemplate businessTemplate) {
        return businessTemplateMapper.query(businessTemplate);
    }

    @Override
    public Long queryTotal() {
        return businessTemplateMapper.queryTotal();
    }

    @Override
    public Integer deleteBatch(List<Long> list) {
        return businessTemplateMapper.deleteBatch(list);
    }

}
