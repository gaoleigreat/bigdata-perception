package com.lego.perception.business.service.impl;

import com.lego.framework.equipment.model.entity.EquipmentCost;
import com.lego.perception.business.mapper.CostMapper;
import com.lego.perception.business.service.ICostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CostImpl implements ICostService {

    @Autowired
    public CostMapper costMapper;

    @Override
    public EquipmentCost selectByPrimaryKey(Long id) {
        return costMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer deleteByPrimaryKey(Long id) {
        return costMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer insert(EquipmentCost tplEquipmentCost) {
        return costMapper.insert(tplEquipmentCost);
    }

    @Override
    public Integer insertSelective(EquipmentCost tplEquipmentCost) {
        return costMapper.insertSelective(tplEquipmentCost);
    }

    @Override
    public Integer insertSelectiveIgnore(EquipmentCost tplEquipmentCost) {
        return costMapper.insertSelectiveIgnore(tplEquipmentCost);
    }

    @Override
    public Integer updateByPrimaryKeySelective(EquipmentCost tplEquipmentCost) {
        return costMapper.updateByPrimaryKeySelective(tplEquipmentCost);
    }

    @Override
    public Integer updateByPrimaryKey(EquipmentCost tplEquipmentCost) {
        return costMapper.updateByPrimaryKey(tplEquipmentCost);
    }

    @Override
    public Integer batchInsert(List<EquipmentCost> list) {
        return costMapper.batchInsert(list);
    }

    @Override
    public Integer batchUpdate(List<EquipmentCost> list) {
        return costMapper.batchUpdate(list);
    }

    /**
     * 存在即更新
     *
     * @return
     */
    @Override
    public Integer upsert(EquipmentCost tplEquipmentCost) {
        return costMapper.upsert(tplEquipmentCost);
    }

    /**
     * 存在即更新，可选择具体属性
     *
     * @return
     */
    @Override
    public Integer upsertSelective(EquipmentCost tplEquipmentCost) {
        return costMapper.upsertSelective(tplEquipmentCost);
    }

    @Override
    public List<EquipmentCost> query(EquipmentCost tplEquipmentCost) {
        return costMapper.query(tplEquipmentCost);
    }

    @Override
    public Long queryTotal() {
        return costMapper.queryTotal();
    }

    @Override
    public Integer deleteBatch(List<Long> list) {
        return costMapper.deleteBatch(list);
    }
}
