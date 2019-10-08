package com.lego.equipment.service.service.impl;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.mybatis.utils.PageUtil;
import com.lego.equipment.service.mapper.FittingMapper;
import com.lego.equipment.service.service.IFittingService;
import com.lego.framework.equipment.model.entity.Fitting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-10-08 06:16:21
 * @since jdk 1.8
 */
@Service
public class FittingServiceImpl implements IFittingService {

    @Autowired
    public FittingMapper fittingMapper;


    @Override
    public PagedResult<Fitting> selectPaged(Fitting fitting, Page page) {
        return PageUtil.queryPaged(page, fitting, fittingMapper);
    }

    @Override
    public Fitting selectByPrimaryKey(Long id) {
        return fittingMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer deleteByPrimaryKey(Long id) {
        return fittingMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer insert(Fitting fitting) {
        return fittingMapper.insert(fitting);
    }

    @Override
    public Integer insertSelective(Fitting fitting) {
        return fittingMapper.insertSelective(fitting);
    }

    @Override
    public Integer insertSelectiveIgnore(Fitting fitting) {
        return fittingMapper.insertSelectiveIgnore(fitting);
    }

    @Override
    public Integer updateByPrimaryKeySelective(Fitting fitting) {
        return fittingMapper.updateByPrimaryKeySelective(fitting);
    }

    @Override
    public Integer updateByPrimaryKey(Fitting fitting) {
        return fittingMapper.updateByPrimaryKey(fitting);
    }

    @Override
    public Integer batchInsert(List<Fitting> list) {
        return fittingMapper.batchInsert(list);
    }

    @Override
    public Integer batchUpdate(List<Fitting> list) {
        return fittingMapper.batchUpdate(list);
    }

    /**
     * 存在即更新
     * @return
     */
    @Override
    public Integer upsert(Fitting fitting) {
        return fittingMapper.upsert(fitting);
    }

    /**
     * 存在即更新，可选择具体属性
     * @return
     */
    @Override
    public Integer upsertSelective(Fitting fitting) {
        return fittingMapper.upsertSelective(fitting);
    }

    @Override
    public List<Fitting> query(Fitting fitting) {
        return fittingMapper.query(fitting);
    }

    @Override
    public Long queryTotal() {
        return fittingMapper.queryTotal();
    }

    @Override
    public Integer deleteBatch(List<Long> list) {
        return fittingMapper.deleteBatch(list);
    }

}
