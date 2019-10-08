package com.lego.equipment.service.service.impl;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.mybatis.utils.PageUtil;
import com.lego.equipment.service.mapper.EquipmentMaintenanceMapper;
import com.lego.equipment.service.service.IEquipmentMaintenanceService;
import com.lego.framework.equipment.model.entity.EquipmentMaintenance;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-10-06 09:29:09
 * @since jdk 1.8
 */
@Service
public class EquipmentMaintenanceServiceImpl implements IEquipmentMaintenanceService {

    @Autowired
    public EquipmentMaintenanceMapper equipmentMaintenanceMapper;


    @Override
    public PagedResult<EquipmentMaintenance> selectPaged(EquipmentMaintenance equipmentMaintenance, Page page) {
        return PageUtil.queryPaged(page, equipmentMaintenance, equipmentMaintenanceMapper);
    }

    @Override
    public EquipmentMaintenance selectByPrimaryKey(Long id) {
        return equipmentMaintenanceMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer deleteByPrimaryKey(Long id) {
        return equipmentMaintenanceMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer insert(EquipmentMaintenance equipmentMaintenance) {
        return equipmentMaintenanceMapper.insert(equipmentMaintenance);
    }

    @Override
    public Integer insertSelective(EquipmentMaintenance equipmentMaintenance) {
        return equipmentMaintenanceMapper.insertSelective(equipmentMaintenance);
    }

    @Override
    public Integer insertSelectiveIgnore(EquipmentMaintenance equipmentMaintenance) {
        return equipmentMaintenanceMapper.insertSelectiveIgnore(equipmentMaintenance);
    }

    @Override
    public Integer updateByPrimaryKeySelective(EquipmentMaintenance equipmentMaintenance) {
        return equipmentMaintenanceMapper.updateByPrimaryKeySelective(equipmentMaintenance);
    }

    @Override
    public Integer updateByPrimaryKey(EquipmentMaintenance equipmentMaintenance) {
        return equipmentMaintenanceMapper.updateByPrimaryKey(equipmentMaintenance);
    }

    @Override
    public Integer batchInsert(List<EquipmentMaintenance> list) {
        return equipmentMaintenanceMapper.batchInsert(list);
    }

    @Override
    public Integer batchUpdate(List<EquipmentMaintenance> list) {
        return equipmentMaintenanceMapper.batchUpdate(list);
    }

    /**
     * 存在即更新
     *
     * @return
     */
    @Override
    public Integer upsert(EquipmentMaintenance equipmentMaintenance) {
        return equipmentMaintenanceMapper.upsert(equipmentMaintenance);
    }

    /**
     * 存在即更新，可选择具体属性
     *
     * @return
     */
    @Override
    public Integer upsertSelective(EquipmentMaintenance equipmentMaintenance) {
        return equipmentMaintenanceMapper.upsertSelective(equipmentMaintenance);
    }

    @Override
    public List<EquipmentMaintenance> query(EquipmentMaintenance equipmentMaintenance) {
        return equipmentMaintenanceMapper.query(equipmentMaintenance);
    }

    @Override
    public Long queryTotal() {
        return equipmentMaintenanceMapper.queryTotal();
    }

    @Override
    public Integer deleteBatch(List<Integer> list) {
        return equipmentMaintenanceMapper.deleteBatch(list);
    }

}
