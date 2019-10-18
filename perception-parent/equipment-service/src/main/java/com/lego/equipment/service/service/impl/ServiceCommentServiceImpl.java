package com.lego.equipment.service.service.impl;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.mybatis.utils.PageUtil;
import com.lego.equipment.service.mapper.ServiceCommentMapper;
import com.lego.equipment.service.service.IServiceCommentService;
import com.lego.framework.equipment.model.entity.ServiceComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-10-18 03:51:45
 * @since jdk 1.8
 */
@Service
public class ServiceCommentServiceImpl implements IServiceCommentService {

    @Autowired
    public ServiceCommentMapper serviceCommentMapper;


    @Override
    public PagedResult<ServiceComment> selectPaged(Page page, ServiceComment serviceComment) {
        return PageUtil.queryPaged(page, serviceComment, serviceCommentMapper);
    }

    @Override
    public ServiceComment selectByPrimaryKey(Long id) {
        return serviceCommentMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer deleteByPrimaryKey(Long id) {
        return serviceCommentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer insert(ServiceComment serviceComment) {
        return serviceCommentMapper.insert(serviceComment);
    }

    @Override
    public Integer insertSelective(ServiceComment serviceComment) {
        return serviceCommentMapper.insertSelective(serviceComment);
    }

    @Override
    public Integer insertSelectiveIgnore(ServiceComment serviceComment) {
        return serviceCommentMapper.insertSelectiveIgnore(serviceComment);
    }

    @Override
    public Integer updateByPrimaryKeySelective(ServiceComment serviceComment) {
        return serviceCommentMapper.updateByPrimaryKeySelective(serviceComment);
    }

    @Override
    public Integer updateByPrimaryKey(ServiceComment serviceComment) {
        return serviceCommentMapper.updateByPrimaryKey(serviceComment);
    }

    @Override
    public Integer batchInsert(List<ServiceComment> list) {
        return serviceCommentMapper.batchInsert(list);
    }

    @Override
    public Integer batchUpdate(List<ServiceComment> list) {
        return serviceCommentMapper.batchUpdate(list);
    }

    /**
     * 存在即更新
     *
     * @return
     */
    @Override
    public Integer upsert(ServiceComment serviceComment) {
        return serviceCommentMapper.upsert(serviceComment);
    }

    /**
     * 存在即更新，可选择具体属性
     *
     * @return
     */
    @Override
    public Integer upsertSelective(ServiceComment serviceComment) {
        return serviceCommentMapper.upsertSelective(serviceComment);
    }

    @Override
    public List<ServiceComment> query(ServiceComment serviceComment) {
        return serviceCommentMapper.query(serviceComment);
    }

    @Override
    public Long queryTotal() {
        return serviceCommentMapper.queryTotal();
    }

    @Override
    public Integer deleteBatch(List<Long> list) {
        return serviceCommentMapper.deleteBatch(list);
    }

}
