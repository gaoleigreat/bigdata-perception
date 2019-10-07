package com.lego.equipment.service.mapper;

import com.framework.mybatis.mapper.Mapper;
import com.lego.framework.equipment.model.entity.EquipmentMaintenance;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * 的dao层
 *
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-10-06 09:29:09
 * @since jdk1.8
 */
@Repository
public interface EquipmentMaintenanceMapper extends Mapper<EquipmentMaintenance> {

    EquipmentMaintenance selectByPrimaryKey(Long id);

    Integer deleteByPrimaryKey(Long id);

    @Override
    int insert(EquipmentMaintenance equipmentMaintenance);

    Integer insertSelective(EquipmentMaintenance equipmentMaintenance);

    Integer insertSelectiveIgnore(EquipmentMaintenance equipmentMaintenance);

    Integer updateByPrimaryKeySelective(EquipmentMaintenance equipmentMaintenance);

    Integer updateByPrimaryKey(EquipmentMaintenance equipmentMaintenance);

    Integer batchInsert(List<EquipmentMaintenance> list);

    Integer batchUpdate(List<EquipmentMaintenance> list);

    /**
     * 存在即更新
     * @return
     */
    Integer upsert(EquipmentMaintenance equipmentMaintenance);

    /**
     * 存在即更新，可选择具体属性
     *
     * @return
     */
    Integer upsertSelective(EquipmentMaintenance equipmentMaintenance);

    List<EquipmentMaintenance> query(EquipmentMaintenance equipmentMaintenance);

    Long queryTotal();

    Integer deleteBatch(List<Integer> list);

}