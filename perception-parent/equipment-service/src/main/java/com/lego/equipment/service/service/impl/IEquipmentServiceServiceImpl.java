package com.lego.equipment.service.service.impl;

import com.lego.equipment.service.mapper.EquipmentServiceMapper;
import com.lego.equipment.service.service.IEquipmentServiceService;
import com.lego.framework.equipment.model.entity.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IEquipmentServiceServiceImpl implements IEquipmentServiceService {
    /*<AUTOGEN--BEGIN>*/

    @Autowired
    public EquipmentServiceMapper equipmentService;

    

    @Override
    public EquipmentService selectByPrimaryKey(Long id) {
        return equipmentService.selectByPrimaryKey(id);
    }

    @Override
    public Integer deleteByPrimaryKey(Long id) {
        return equipmentService.deleteByPrimaryKey(id);
    }

    @Override
    public Integer insert(EquipmentService tplEquipmentService) {
        return equipmentService.insert(tplEquipmentService);
    }

    @Override
    public Integer insertSelective(EquipmentService tplEquipmentService) {
        return equipmentService.insertSelective(tplEquipmentService);
    }

    @Override
    public Integer insertSelectiveIgnore(EquipmentService tplEquipmentService) {
        return equipmentService.insertSelectiveIgnore(tplEquipmentService);
    }

    @Override
    public Integer updateByPrimaryKeySelective(EquipmentService tplEquipmentService) {
        return equipmentService.updateByPrimaryKeySelective(tplEquipmentService);
    }

    @Override
    public Integer updateByPrimaryKey(EquipmentService tplEquipmentService) {
        return equipmentService.updateByPrimaryKey(tplEquipmentService);
    }

    @Override
    public Integer batchInsert(List<EquipmentService> list) {
        return equipmentService.batchInsert(list);
    }

    @Override
    public Integer batchUpdate(List<EquipmentService> list) {
        return equipmentService.batchUpdate(list);
    }

    /**
     * 存在即更新
     *
     * @return
     */
    @Override
    public Integer upsert(EquipmentService tplEquipmentService) {
        return equipmentService.upsert(tplEquipmentService);
    }

    /**
     * 存在即更新，可选择具体属性
     *
     * @return
     */
    @Override
    public Integer upsertSelective(EquipmentService tplEquipmentService) {
        return equipmentService.upsertSelective(tplEquipmentService);
    }

    @Override
    public List<EquipmentService> query(EquipmentService tplEquipmentService) {
        return equipmentService.query(tplEquipmentService);
    }

    @Override
    public Long queryTotal() {
        return equipmentService.queryTotal();
    }

    @Override
    public Integer deleteBatch(List<Long> list) {
        return equipmentService.deleteBatch(list);
    }

    /*<AUTOGEN--END>*/

}
