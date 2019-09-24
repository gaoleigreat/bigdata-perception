package com.lego.equipment.service.service;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.lego.framework.equipment.model.entity.Equipment;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * service层
 *
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-09-24 07:43:07
 * @since jdk 1.8
 */
public interface IEquipmentService {


    PagedResult<Equipment> selectPaged(Equipment equipment, Page page);

    Equipment selectByPrimaryKey(Long id);

    Integer deleteByPrimaryKey(Long id);

    Integer insert(Equipment tplEquipment);

    Integer insertSelective(Equipment tplEquipment);

    Integer insertSelectiveIgnore(Equipment tplEquipment);

    Integer updateByPrimaryKeySelective(Equipment tplEquipment);

    Integer updateByPrimaryKey(Equipment tplEquipment);

    Integer batchInsert(List<Equipment> list);

    Integer batchUpdate(List<Equipment> list);

    /**
     * 存在即更新
     * @return
     */
    Integer upsert(Equipment tplEquipment);

    /**
     * 存在即更新，可选择具体属性
     * @return
     */
    Integer upsertSelective(Equipment tplEquipment);

    List<Equipment> query(Equipment tplEquipment);

    Long queryTotal();

    Integer deleteBatch(List<Long> list);


}
