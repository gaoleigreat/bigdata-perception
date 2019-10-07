package com.lego.equipment.service.mapper;
import com.framework.mybatis.mapper.Mapper;
import com.lego.framework.equipment.model.entity.EquipmentStopReason;
import org.springframework.stereotype.Repository;
import java.util.List;
/**
 * 的dao层
 *
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-10-06 09:29:16
 * @since jdk1.8
 */
@Repository
public interface EquipmentStopReasonMapper extends Mapper<EquipmentStopReason> {

    EquipmentStopReason selectByPrimaryKey(Long id);

    Integer deleteByPrimaryKey(Long id);

    @Override
    int insert(EquipmentStopReason equipmentStopReason);

    Integer insertSelective(EquipmentStopReason equipmentStopReason);

    Integer insertSelectiveIgnore(EquipmentStopReason equipmentStopReason);

    Integer updateByPrimaryKeySelective(EquipmentStopReason equipmentStopReason);

    Integer updateByPrimaryKey(EquipmentStopReason equipmentStopReason);

    Integer batchInsert(List<EquipmentStopReason> list);

    Integer batchUpdate(List<EquipmentStopReason> list);
    /**
     * 存在即更新
     * @return
     */
    Integer upsert(EquipmentStopReason equipmentStopReason);

    /**
     * 存在即更新，可选择具体属性
     * @return
     */
    Integer upsertSelective(EquipmentStopReason equipmentStopReason);

    List<EquipmentStopReason> query(EquipmentStopReason equipmentStopReason);

    Long queryTotal();

    Integer deleteBatch(List<Long> list);

}