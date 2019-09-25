package com.lego.equipment.service.mapper;
import com.framework.mybatis.mapper.Mapper;
import com.lego.framework.equipment.model.entity.Equipment;
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
public interface EquipmentMapper extends Mapper<Equipment> {

    Equipment selectByPrimaryKey(Long id);

    Integer deleteByPrimaryKey(Long id);


    int insert(Equipment tplEquipment);

    Integer insertSelective(Equipment tplEquipment);

    Integer insertSelectiveIgnore(Equipment tplEquipment);

    Integer updateByPrimaryKeySelective(Equipment tplEquipment);

    Integer updateByPrimaryKey(Equipment tplEquipment);

    Integer batchInsert(List<Equipment> list);

    Integer batchUpdate(List<Equipment> list);

    /**
     * 存在即更新
     *
     * @return
     */
    Integer upsert(Equipment tplEquipment);

    /**
     * 存在即更新，可选择具体属性
     *
     * @return
     */
    Integer upsertSelective(Equipment tplEquipment);

    List<Equipment> query(Equipment tplEquipment);

    Long queryTotal();

    Integer deleteBatch(List<Long> list);

}