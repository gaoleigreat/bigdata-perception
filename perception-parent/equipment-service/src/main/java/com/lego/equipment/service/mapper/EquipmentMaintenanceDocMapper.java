package com.lego.equipment.service.mapper;

import com.framework.mybatis.mapper.Mapper;
import com.lego.framework.equipment.model.entity.EquipmentMaintenanceDoc;
import org.springframework.stereotype.Repository;
import java.util.List;
/**
 * 的dao层
 *
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-10-06 09:29:13
 * @since jdk1.8
 */
@Repository
public interface EquipmentMaintenanceDocMapper extends Mapper<EquipmentMaintenanceDoc> {

    EquipmentMaintenanceDoc selectByPrimaryKey(Long id);

    Integer deleteByPrimaryKey(Long id);

    @Override
    int insert(EquipmentMaintenanceDoc equipmentMaintenanceDoc);

    Integer insertSelective(EquipmentMaintenanceDoc equipmentMaintenanceDoc);

    Integer insertSelectiveIgnore(EquipmentMaintenanceDoc equipmentMaintenanceDoc);

    Integer updateByPrimaryKeySelective(EquipmentMaintenanceDoc equipmentMaintenanceDoc);

    Integer updateByPrimaryKey(EquipmentMaintenanceDoc equipmentMaintenanceDoc);

    Integer batchInsert(List<EquipmentMaintenanceDoc> list);

    Integer batchUpdate(List<EquipmentMaintenanceDoc> list);

    /**
     * 存在即更新
     * @return
     */
    Integer upsert(EquipmentMaintenanceDoc equipmentMaintenanceDoc);

    /**
     * 存在即更新，可选择具体属性
     * @return
     */
    Integer upsertSelective(EquipmentMaintenanceDoc equipmentMaintenanceDoc);

    List<EquipmentMaintenanceDoc> query(EquipmentMaintenanceDoc equipmentMaintenanceDoc);

    Long queryTotal();

    Integer deleteBatch(List<Long> list);

}