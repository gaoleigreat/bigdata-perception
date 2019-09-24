package com.lego.equipment.service.service.impl;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.mybatis.utils.PageUtil;
import com.lego.equipment.service.mapper.EquipmentMapper;
import com.lego.equipment.service.service.IEquipmentService;
import com.lego.framework.equipment.model.entity.Equipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-09-24 07:43:07
 * @since jdk 1.8
 */
@Service
public class EquipmentServiceImpl implements IEquipmentService {
    /*<AUTOGEN--BEGIN>*/

    @Autowired
    public EquipmentMapper equipmentMapper;


    @Override
    public PagedResult<Equipment> selectPaged(Equipment equipment, Page page) {
        return PageUtil.queryPaged(page,equipment,equipmentMapper);
    }

    @Override
    public Equipment selectByPrimaryKey(Long id) {
        return equipmentMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer deleteByPrimaryKey(Long id) {
        return equipmentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer insert(Equipment tplEquipment) {
        return equipmentMapper.insert(tplEquipment);
    }

    @Override
    public Integer insertSelective(Equipment equipment) {
        return equipmentMapper.insertSelective(equipment);
    }

    @Override
    public Integer insertSelectiveIgnore(Equipment tplEquipment) {
        return equipmentMapper.insertSelectiveIgnore(tplEquipment);
    }

    @Override
    public Integer updateByPrimaryKeySelective(Equipment tplEquipment) {
        return equipmentMapper.updateByPrimaryKeySelective(tplEquipment);
    }

    @Override
    public Integer updateByPrimaryKey(Equipment tplEquipment) {
        return equipmentMapper.updateByPrimaryKey(tplEquipment);
    }

    @Override
    public Integer batchInsert(List<Equipment> list) {
        return equipmentMapper.batchInsert(list);
    }

    @Override
    public Integer batchUpdate(List<Equipment> list) {
        return equipmentMapper.batchUpdate(list);
    }

    /**
     * 存在即更新
     *
     * @return
     */
    @Override
    public Integer upsert(Equipment tplEquipment) {
        return equipmentMapper.upsert(tplEquipment);
    }

    /**
     * 存在即更新，可选择具体属性
     *
     * @return
     */
    @Override
    public Integer upsertSelective(Equipment tplEquipment) {
        return equipmentMapper.upsertSelective(tplEquipment);
    }

    @Override
    public List<Equipment> query(Equipment tplEquipment) {
        return equipmentMapper.query(tplEquipment);
    }

    @Override
    public Long queryTotal() {
        return equipmentMapper.queryTotal();
    }

    @Override
    public Integer deleteBatch(List<Long> list) {
        return equipmentMapper.deleteBatch(list);
    }

}
