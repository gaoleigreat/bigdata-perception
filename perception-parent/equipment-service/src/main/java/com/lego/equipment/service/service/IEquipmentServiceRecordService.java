package com.lego.equipment.service.service;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.lego.framework.equipment.model.entity.EquipmentServiceRecord;

import java.util.List;

/**
 * service层
 *
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-09-24 10:07:49
 * @since jdk 1.8
 */
public interface IEquipmentServiceRecordService {


    PagedResult<EquipmentServiceRecord> selectPaged(EquipmentServiceRecord equipmentServiceRecord, Page page);

    EquipmentServiceRecord selectByPrimaryKey(Long id);

    List<EquipmentServiceRecord> selectByEquipmentid(Long equipmentId);

    Integer deleteByPrimaryKey(Long id);

    Integer insert(EquipmentServiceRecord equipmentServiceRecord);

    Integer insertSelective(EquipmentServiceRecord equipmentServiceRecord);

    Integer insertSelectiveIgnore(EquipmentServiceRecord equipmentServiceRecord);

    Integer updateByPrimaryKeySelective(EquipmentServiceRecord equipmentServiceRecord);

    Integer updateByPrimaryKey(EquipmentServiceRecord equipmentServiceRecord);

    Integer update(EquipmentServiceRecord equipmentServiceRecord);


    Integer batchInsert(List<EquipmentServiceRecord> list);

    Integer batchUpdate(List<EquipmentServiceRecord> list);

    /**
     * 存在即更新
     *
     * @return
     */
    Integer upsert(EquipmentServiceRecord equipmentServiceRecord);

    /**
     * 存在即更新，可选择具体属性
     *
     * @return
     */
    Integer upsertSelective(EquipmentServiceRecord equipmentServiceRecord);

    List<EquipmentServiceRecord> query(EquipmentServiceRecord equipmentServiceRecord);

    Long queryTotal();

    Integer deleteBatch(List<Long> list);

}
