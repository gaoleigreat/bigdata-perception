package com.lego.equipment.service.service;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.lego.framework.equipment.model.entity.EquipmentMaintenanceDoc;
import java.util.List;
/**
 * service层
 *
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-10-06 09:29:13
 * @since jdk 1.8
 */
public interface IEquipmentMaintenanceDocService {

    PagedResult<EquipmentMaintenanceDoc> selectPaged(EquipmentMaintenanceDoc equipmentMaintenanceDoc, Page page);

    EquipmentMaintenanceDoc selectByPrimaryKey(Long id);

    Integer deleteByPrimaryKey(Long id);

    Integer insert(EquipmentMaintenanceDoc equipmentMaintenanceDoc);

    Integer insertSelective(EquipmentMaintenanceDoc equipmentMaintenanceDoc);

    Integer insertSelectiveIgnore(EquipmentMaintenanceDoc equipmentMaintenanceDoc);

    Integer updateByPrimaryKeySelective(EquipmentMaintenanceDoc equipmentMaintenanceDoc);

    Integer updateByPrimaryKey(EquipmentMaintenanceDoc equipmentMaintenanceDoc);

    Integer batchInsert(List<EquipmentMaintenanceDoc> list);

    Integer batchUpdate(List<EquipmentMaintenanceDoc> list);

    /**
     * 存在即更新
     *
     * @return
     */
    Integer upsert(EquipmentMaintenanceDoc equipmentMaintenanceDoc);

    /**
     * 存在即更新，可选择具体属性
     *
     * @return
     */
    Integer upsertSelective(EquipmentMaintenanceDoc equipmentMaintenanceDoc);

    List<EquipmentMaintenanceDoc> query(EquipmentMaintenanceDoc equipmentMaintenanceDoc);

    Long queryTotal();

    Integer deleteBatch(List<Long> list);

}
