package com.lego.equipment.service.mapper;
import com.framework.mybatis.mapper.Mapper;
import com.lego.framework.equipment.model.entity.EquipmentType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/24 10:40
 * @desc :
 */
@Repository
public interface EquipmentTypeMapper extends Mapper<EquipmentType> {

    EquipmentType selectByPrimaryKey(Long id);

    Integer deleteByPrimaryKey(Long id);


    int insert(EquipmentType equipmentType);

    Integer insertSelective(EquipmentType equipmentType);

    Integer insertSelectiveIgnore(EquipmentType equipmentType);

    Integer updateByPrimaryKeySelective(EquipmentType equipmentType);

    Integer updateByPrimaryKey(EquipmentType equipmentType);

    Integer batchInsert(List<EquipmentType> list);

    Integer batchUpdate(List<EquipmentType> list);

    /**
     * 存在即更新
     *
     * @return
     */
    Integer upsert(EquipmentType equipmentType);

    /**
     * 存在即更新，可选择具体属性
     *
     * @return
     */
    Integer upsertSelective(EquipmentType equipmentType);

    List<EquipmentType> query(EquipmentType equipmentType);

    Long queryTotal();

    Integer deleteBatch(List<Long> list);

}
