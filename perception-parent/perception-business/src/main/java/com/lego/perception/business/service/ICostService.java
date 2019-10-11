package com.lego.perception.business.service;

import com.lego.framework.equipment.model.entity.EquipmentCost;

import java.util.List;

public interface ICostService {

    EquipmentCost selectByPrimaryKey(Long id);

    Integer deleteByPrimaryKey(Long id);

    Integer insert(EquipmentCost tplEquipmentCost);

    Integer insertSelective(EquipmentCost tplEquipmentCost);

    Integer insertSelectiveIgnore(EquipmentCost tplEquipmentCost);

    Integer updateByPrimaryKeySelective(EquipmentCost tplEquipmentCost);

    Integer updateByPrimaryKey(EquipmentCost tplEquipmentCost);

    Integer batchInsert(List<EquipmentCost> list);

    Integer batchUpdate(List<EquipmentCost> list);

    /**
     * 存在即更新
     *
     * @return
     */
    Integer upsert(EquipmentCost tplEquipmentCost);

    /**
     * 存在即更新，可选择具体属性
     *
     * @return
     */
    Integer upsertSelective(EquipmentCost tplEquipmentCost);

    List<EquipmentCost> query(EquipmentCost tplEquipmentCost);

    Long queryTotal();

    Integer deleteBatch(List<Long> list);
}
