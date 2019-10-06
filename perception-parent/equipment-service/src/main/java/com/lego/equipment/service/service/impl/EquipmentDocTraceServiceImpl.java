package com.lego.equipment.service.service.impl;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.mybatis.utils.PageUtil;
import com.lego.equipment.service.mapper.EquipmentDocTraceMapper;
import com.lego.equipment.service.service.IEquipmentDocTraceService;
import com.lego.framework.equipment.model.entity.EquipmentDocTrace;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-10-06 03:57:48
 * @since jdk 1.8
 */
@Service
public class EquipmentDocTraceServiceImpl implements IEquipmentDocTraceService {
    /*<AUTOGEN--BEGIN>*/

    @Autowired
    public EquipmentDocTraceMapper equipmentDocTraceMapper;


    @Override
    public PagedResult<EquipmentDocTrace> selectPaged(EquipmentDocTrace equipmentDocTrace, Page page) {
        return PageUtil.queryPaged(page,equipmentDocTrace,equipmentDocTraceMapper);
    }

    @Override
    public EquipmentDocTrace selectByPrimaryKey(Long id) {
        return equipmentDocTraceMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer deleteByPrimaryKey(Long id) {
        return equipmentDocTraceMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer insert(EquipmentDocTrace equipmentDocTrace) {
        return equipmentDocTraceMapper.insert(equipmentDocTrace);
    }

    @Override
    public Integer insertSelective(EquipmentDocTrace equipmentDocTrace) {
        return equipmentDocTraceMapper.insertSelective(equipmentDocTrace);
    }

    @Override
    public Integer insertSelectiveIgnore(EquipmentDocTrace equipmentDocTrace) {
        return equipmentDocTraceMapper.insertSelectiveIgnore(equipmentDocTrace);
    }

    @Override
    public Integer updateByPrimaryKeySelective(EquipmentDocTrace equipmentDocTrace) {
        return equipmentDocTraceMapper.updateByPrimaryKeySelective(equipmentDocTrace);
    }

    @Override
    public Integer updateByPrimaryKey(EquipmentDocTrace equipmentDocTrace) {
        return equipmentDocTraceMapper.updateByPrimaryKey(equipmentDocTrace);
    }

    @Override
    public Integer batchInsert(List<EquipmentDocTrace> list) {
        return equipmentDocTraceMapper.batchInsert(list);
    }

    @Override
    public Integer batchUpdate(List<EquipmentDocTrace> list) {
        return equipmentDocTraceMapper.batchUpdate(list);
    }

    /**
     * 存在即更新
     * @return
     */
    @Override
    public Integer upsert(EquipmentDocTrace equipmentDocTrace) {
        return equipmentDocTraceMapper.upsert(equipmentDocTrace);
    }

    /**
     * 存在即更新，可选择具体属性
     *
     * @return
     */
    @Override
    public Integer upsertSelective(EquipmentDocTrace equipmentDocTrace) {
        return equipmentDocTraceMapper.upsertSelective(equipmentDocTrace);
    }

    @Override
    public List<EquipmentDocTrace> query(EquipmentDocTrace equipmentDocTrace) {
        return equipmentDocTraceMapper.query(equipmentDocTrace);
    }

    @Override
    public Long queryTotal() {
        return equipmentDocTraceMapper.queryTotal();
    }

    @Override
    public Integer deleteBatch(List<Long> list) {
        return equipmentDocTraceMapper.deleteBatch(list);
    }
}
