package com.lego.equipment.service.mapper;

import com.framework.mybatis.mapper.Mapper;
import com.lego.framework.equipment.model.entity.EquipmentServiceRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 的dao层
 *
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-09-24 10:07:49
 * @since jdk1.8
 */
@Repository
public interface EquipmentServiceRecordMapper extends Mapper<EquipmentServiceRecord> {

    EquipmentServiceRecord selectByPrimaryKey(Long id);

    Integer deleteByPrimaryKey(Long id);

    @Override
    int insert(EquipmentServiceRecord equipmentServiceRecord);

    Integer insertSelective(EquipmentServiceRecord equipmentServiceRecord);

    Integer insertSelectiveIgnore(EquipmentServiceRecord equipmentServiceRecord);

    Integer updateByPrimaryKeySelective(EquipmentServiceRecord equipmentServiceRecord);

    Integer updateByPrimaryKey(EquipmentServiceRecord equipmentServiceRecord);

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