package com.lego.equipment.service.mapper;

import com.framework.mybatis.mapper.Mapper;
import com.lego.framework.equipment.model.entity.EquipmentBusiness;
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
public interface EquipmentBusinessMapper extends Mapper<EquipmentBusiness> {

    EquipmentBusiness selectByPrimaryKey(Long id);

    Integer deleteByPrimaryKey(Long id);

    @Override
    int insert(EquipmentBusiness equipmentBusiness);

    Integer insertSelective(EquipmentBusiness equipmentBusiness);

    Integer insertSelectiveIgnore(EquipmentBusiness equipmentBusiness);

    Integer updateByPrimaryKeySelective(EquipmentBusiness equipmentBusiness);

    Integer updateByPrimaryKey(EquipmentBusiness equipmentBusiness);

    Integer batchInsert(List<EquipmentBusiness> list);

    Integer batchUpdate(List<EquipmentBusiness> list);

    /**
     * 存在即更新
     *
     * @return
     */
    Integer upsert(EquipmentBusiness equipmentBusiness);

    /**
     * 存在即更新，可选择具体属性
     *
     * @return
     */
    Integer upsertSelective(EquipmentBusiness equipmentBusiness);

    List<EquipmentBusiness> query(EquipmentBusiness equipmentBusiness);

    Long queryTotal();

    Integer deleteBatch(List<Long> list);


}