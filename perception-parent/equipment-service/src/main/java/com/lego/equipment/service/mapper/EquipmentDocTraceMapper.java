package com.lego.equipment.service.mapper;
import com.framework.mybatis.mapper.Mapper;
import com.lego.framework.equipment.model.entity.EquipmentDocTrace;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-10-06 03:57:48
 * @since jdk1.8
 */
@Repository
public interface EquipmentDocTraceMapper extends Mapper<EquipmentDocTrace> {


    EquipmentDocTrace selectByPrimaryKey(Long id);

    Integer deleteByPrimaryKey(Long id);

    @Override
    int insert(EquipmentDocTrace equipmentDocTrace);

    Integer insertSelective(EquipmentDocTrace equipmentDocTrace);

    Integer insertSelectiveIgnore(EquipmentDocTrace equipmentDocTrace);

    Integer updateByPrimaryKeySelective(EquipmentDocTrace equipmentDocTrace);

    Integer updateByPrimaryKey(EquipmentDocTrace equipmentDocTrace);

    Integer batchInsert(List<EquipmentDocTrace> list);

    Integer batchUpdate(List<EquipmentDocTrace> list);

    /**
     * 存在即更新
     * @return
     */
    Integer upsert(EquipmentDocTrace equipmentDocTrace);

    /**
     * 存在即更新，可选择具体属性
     * @return
     */
    Integer upsertSelective(EquipmentDocTrace equipmentDocTrace);

    List<EquipmentDocTrace> query(EquipmentDocTrace equipmentDocTrace);

    Long queryTotal();

    Integer deleteBatch(List<Long> list);

}