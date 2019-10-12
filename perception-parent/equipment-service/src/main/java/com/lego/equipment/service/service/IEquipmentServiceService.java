package com.lego.equipment.service.service;

import com.lego.framework.equipment.model.entity.EquipmentService;

import java.util.List;

public interface IEquipmentServiceService {
    /*<AUTOGEN--BEGIN>*/

  
    EquipmentService selectByPrimaryKey(Long id);

    Integer deleteByPrimaryKey(Long id);

    Integer insert(EquipmentService tplEquipmentService);

    Integer insertSelective(EquipmentService tplEquipmentService);

    Integer insertSelectiveIgnore(EquipmentService tplEquipmentService);

    Integer updateByPrimaryKeySelective(EquipmentService tplEquipmentService);

    Integer updateByPrimaryKey(EquipmentService tplEquipmentService);

    Integer batchInsert(List<EquipmentService> list);

    Integer batchUpdate(List<EquipmentService> list);

    /**
     * 存在即更新
     *
     * @return
     */
    Integer upsert(EquipmentService tplEquipmentService);

    /**
     * 存在即更新，可选择具体属性
     *
     * @return
     */
    Integer upsertSelective(EquipmentService tplEquipmentService);

    List<EquipmentService> query(EquipmentService tplEquipmentService);

    Long queryTotal();

    Integer deleteBatch(List<Long> list);

    /*<AUTOGEN--END>*/
}
