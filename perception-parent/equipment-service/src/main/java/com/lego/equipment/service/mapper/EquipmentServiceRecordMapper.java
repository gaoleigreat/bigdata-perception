package com.lego.equipment.service.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.framework.mybatis.mapper.Mapper;
import com.lego.framework.equipment.model.entity.EquipmentServiceRecord;
import org.apache.ibatis.annotations.Param;
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


    /**
     * @param page
     * @param wrapper
     * @return
     */
    Page<EquipmentServiceRecord> queryList(@Param(value = "page") IPage<EquipmentServiceRecord> page,
                                           @Param(value = "ew") QueryWrapper<EquipmentServiceRecord> wrapper);
}