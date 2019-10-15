package com.lego.equipment.service.service;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.lego.framework.equipment.model.entity.EquipmentCost;

import java.util.List;

public interface EquipmentCostService {
    PagedResult<EquipmentCost> selectPaged(EquipmentCost equipmentCost, Page page);

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