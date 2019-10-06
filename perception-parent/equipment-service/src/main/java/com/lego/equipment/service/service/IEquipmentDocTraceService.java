package com.lego.equipment.service.service;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.lego.framework.equipment.model.entity.EquipmentDocTrace;
import java.util.List;
/**
 * service层
 *
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-10-06 03:57:48
 * @since jdk 1.8
 */
public interface IEquipmentDocTraceService {

    PagedResult<EquipmentDocTrace> selectPaged(EquipmentDocTrace equipmentDocTrace, Page page);

    EquipmentDocTrace selectByPrimaryKey(Long id);

    Integer deleteByPrimaryKey(Long id);

    Integer insert(EquipmentDocTrace equipmentDocTrace);

    Integer insertSelective(EquipmentDocTrace equipmentDocTrace);

    Integer insertSelectiveIgnore(EquipmentDocTrace equipmentDocTrace);

    Integer updateByPrimaryKeySelective(EquipmentDocTrace equipmentDocTrace);

    Integer updateByPrimaryKey(EquipmentDocTrace equipmentDocTrace);

    Integer batchInsert(List<EquipmentDocTrace> list);

    Integer batchUpdate(List<EquipmentDocTrace> list);

    /**
     * 存在即更新
     *
     * @return
     */
    Integer upsert(EquipmentDocTrace equipmentDocTrace);

    /**
     * 存在即更新，可选择具体属性
     *
     * @return
     */
    Integer upsertSelective(EquipmentDocTrace equipmentDocTrace);

    List<EquipmentDocTrace> query(EquipmentDocTrace equipmentDocTrace);

    Long queryTotal();

    Integer deleteBatch(List<Long> list);
}
