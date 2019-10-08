package com.lego.equipment.service.service.impl;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.mybatis.utils.PageUtil;
import com.lego.equipment.service.mapper.EquipmentMaintenanceDocMapper;
import com.lego.equipment.service.service.IEquipmentMaintenanceDocService;
import com.lego.framework.equipment.model.entity.EquipmentMaintenanceDoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-10-06 09:29:13
 * @since jdk 1.8
 */
@Service
public class EquipmentMaintenanceDocServiceImpl implements IEquipmentMaintenanceDocService {

    @Autowired
    public EquipmentMaintenanceDocMapper equipmentMaintenanceDocMapper;


    @Override
    public PagedResult<EquipmentMaintenanceDoc> selectPaged(EquipmentMaintenanceDoc equipmentMaintenanceDoc, Page page) {
        return PageUtil.queryPaged(page,equipmentMaintenanceDoc,equipmentMaintenanceDocMapper);
    }

    @Override
    public EquipmentMaintenanceDoc selectByPrimaryKey(Long id) {
        return equipmentMaintenanceDocMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer deleteByPrimaryKey(Long id) {
        return equipmentMaintenanceDocMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer insert(EquipmentMaintenanceDoc equipmentMaintenanceDoc) {
        return equipmentMaintenanceDocMapper.insert(equipmentMaintenanceDoc);
    }

    @Override
    public Integer insertSelective(EquipmentMaintenanceDoc equipmentMaintenanceDoc) {
        return equipmentMaintenanceDocMapper.insertSelective(equipmentMaintenanceDoc);
    }

    @Override
    public Integer insertSelectiveIgnore(EquipmentMaintenanceDoc equipmentMaintenanceDoc) {
        return equipmentMaintenanceDocMapper.insertSelectiveIgnore(equipmentMaintenanceDoc);
    }

    @Override
    public Integer updateByPrimaryKeySelective(EquipmentMaintenanceDoc equipmentMaintenanceDoc) {
        return equipmentMaintenanceDocMapper.updateByPrimaryKeySelective(equipmentMaintenanceDoc);
    }

    @Override
    public Integer updateByPrimaryKey(EquipmentMaintenanceDoc equipmentMaintenanceDoc) {
        return equipmentMaintenanceDocMapper.updateByPrimaryKey(equipmentMaintenanceDoc);
    }

    @Override
    public Integer batchInsert(List<EquipmentMaintenanceDoc> list) {
        return equipmentMaintenanceDocMapper.batchInsert(list);
    }

    @Override
    public Integer batchUpdate(List<EquipmentMaintenanceDoc> list) {
        return equipmentMaintenanceDocMapper.batchUpdate(list);
    }

    /**
     * 存在即更新
     *
     * @return
     */
    @Override
    public Integer upsert(EquipmentMaintenanceDoc equipmentMaintenanceDoc) {
        return equipmentMaintenanceDocMapper.upsert(equipmentMaintenanceDoc);
    }

    /**
     * 存在即更新，可选择具体属性
     *
     * @return
     */
    @Override
    public Integer upsertSelective(EquipmentMaintenanceDoc equipmentMaintenanceDoc) {
        return equipmentMaintenanceDocMapper.upsertSelective(equipmentMaintenanceDoc);
    }

    @Override
    public List<EquipmentMaintenanceDoc> query(EquipmentMaintenanceDoc equipmentMaintenanceDoc) {
        return equipmentMaintenanceDocMapper.query(equipmentMaintenanceDoc);
    }

    @Override
    public Long queryTotal() {
        return equipmentMaintenanceDocMapper.queryTotal();
    }

    @Override
    public Integer deleteBatch(List<Long> list) {
        return equipmentMaintenanceDocMapper.deleteBatch(list);
    }


}
