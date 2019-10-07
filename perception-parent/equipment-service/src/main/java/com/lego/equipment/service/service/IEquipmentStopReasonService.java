package com.lego.equipment.service.service;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.lego.framework.equipment.model.entity.EquipmentStopReason;
import java.util.List;
/**
 * service层
 *
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-10-06 09:29:16
 * @since jdk 1.8
 */
public interface IEquipmentStopReasonService {

    PagedResult<EquipmentStopReason> selectPaged(EquipmentStopReason equipmentStopReason, Page page);

    EquipmentStopReason selectByPrimaryKey(Long id);

    Integer deleteByPrimaryKey(Long id);

    Integer insert(EquipmentStopReason equipmentStopReason);

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
