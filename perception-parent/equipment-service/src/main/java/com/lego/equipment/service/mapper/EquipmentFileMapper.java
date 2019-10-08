package com.lego.equipment.service.mapper;


import com.lego.framework.equipment.model.entity.EquipmentFile;
import com.framework.mybatis.mapper.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 的dao层
 *
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-10-08 08:42:13
 * @since jdk1.8
 */
@Repository
public interface EquipmentFileMapper extends Mapper<EquipmentFile> {

    /*<AUTOGEN--BEGIN>*/


    EquipmentFile selectByPrimaryKey(Long fileId);

    Integer deleteByPrimaryKey(Long fileId);

    @Override
    int insert(EquipmentFile equipmentFile);

    Integer insertSelective(EquipmentFile equipmentFile);

    Integer insertSelectiveIgnore(EquipmentFile equipmentFile);

    Integer updateByPrimaryKeySelective(EquipmentFile equipmentFile);

    Integer updateByPrimaryKey(EquipmentFile equipmentFile);

    Integer batchInsert(List<EquipmentFile> list);

    Integer batchUpdate(List<EquipmentFile> list);

    /**
     * 存在即更新
     *
     * @return
     */
    Integer upsert(EquipmentFile equipmentFile);

    /**
     * 存在即更新，可选择具体属性
     *
     * @return
     */
    Integer upsertSelective(EquipmentFile equipmentFile);

    List<EquipmentFile> query(EquipmentFile equipmentFile);

    Long queryTotal();

    Integer deleteBatch(List<Long> list);

    /*<AUTOGEN--END>*/

}