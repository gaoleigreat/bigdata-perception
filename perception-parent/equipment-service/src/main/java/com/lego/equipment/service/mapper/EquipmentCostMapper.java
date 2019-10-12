package com.lego.equipment.service.mapper;

import com.framework.mybatis.mapper.Mapper;
import com.lego.framework.equipment.model.entity.EquipmentCost;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentCostMapper extends Mapper<EquipmentCost> {

    EquipmentCost selectByPrimaryKey(Long id);

    Integer deleteByPrimaryKey(Long id);

    @Override
    int insert(EquipmentCost tplEquipmentCost);

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
