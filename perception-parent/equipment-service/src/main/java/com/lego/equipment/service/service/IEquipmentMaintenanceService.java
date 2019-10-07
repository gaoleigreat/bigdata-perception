package com.lego.equipment.service.service;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.lego.framework.equipment.model.entity.EquipmentMaintenance;
import java.util.List;
/**
 * service层
 *
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-10-06 09:29:09
 * @since jdk 1.8
 */
public interface IEquipmentMaintenanceService {

    PagedResult<EquipmentMaintenance> selectPaged(EquipmentMaintenance equipmentMaintenance, Page page);

    EquipmentMaintenance selectByPrimaryKey(Long id);

    Integer deleteByPrimaryKey(Long id);

    Integer insert(EquipmentMaintenance equipmentMaintenance);

    Integer insertSelective(EquipmentMaintenance equipmentMaintenance);

    Integer insertSelectiveIgnore(EquipmentMaintenance equipmentMaintenance);

    Integer updateByPrimaryKeySelective(EquipmentMaintenance equipmentMaintenance);

    Integer updateByPrimaryKey(EquipmentMaintenance equipmentMaintenance);

    Integer batchInsert(List<EquipmentMaintenance> list);

    Integer batchUpdate(List<EquipmentMaintenance> list);

    /**
     * 存在即更新
     *
     * @return
     */
    Integer upsert(EquipmentMaintenance equipmentMaintenance);

    /**
     * 存在即更新，可选择具体属性
     * @return
     */
    Integer upsertSelective(EquipmentMaintenance equipmentMaintenance);

    List<EquipmentMaintenance> query(EquipmentMaintenance equipmentMaintenance);

    Long queryTotal();

    Integer deleteBatch(List<Integer> list);

}
