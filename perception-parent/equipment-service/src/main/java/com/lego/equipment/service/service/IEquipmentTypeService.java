package com.lego.equipment.service.service;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.lego.framework.equipment.model.entity.EquipmentType;
import java.util.List;

/**
 * service层
 *
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-09-24 07:43:07
 * @since jdk 1.8
 */
public interface IEquipmentTypeService {


    PagedResult<EquipmentType> selectPaged(EquipmentType equipmentType, Page page);

    EquipmentType selectByPrimaryKey(Long id);

    Integer deleteByPrimaryKey(Long id);

    Integer insert(EquipmentType equipmentType);

    Integer insertSelective(EquipmentType equipmentType);

    Integer insertSelectiveIgnore(EquipmentType equipmentType);

    Integer updateByPrimaryKeySelective(EquipmentType equipmentType);

    Integer updateByPrimaryKey(EquipmentType equipmentType);

    Integer batchInsert(List<EquipmentType> list);

    Integer batchUpdate(List<EquipmentType> list);

    /**
     * 存在即更新
     * @return
     */
    Integer upsert(EquipmentType equipmentType);

    /**
     * 存在即更新，可选择具体属性
     * @return
     */
    Integer upsertSelective(EquipmentType equipmentType);

    List<EquipmentType> query(EquipmentType equipmentType);

    Long queryTotal();

    Integer deleteBatch(List<Long> list);


}
