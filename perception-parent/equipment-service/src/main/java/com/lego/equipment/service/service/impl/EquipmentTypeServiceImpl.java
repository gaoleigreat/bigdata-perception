package com.lego.equipment.service.service.impl;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.mybatis.utils.PageUtil;
import com.lego.equipment.service.mapper.EquipmentTypeMapper;
import com.lego.equipment.service.service.IEquipmentTypeService;
import com.lego.framework.config.BaseModel;
import com.lego.framework.equipment.model.entity.EquipmentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;


/**
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-09-24 07:43:07
 * @since jdk 1.8
 */
@Service
public class EquipmentTypeServiceImpl implements IEquipmentTypeService {

    @Autowired
    public EquipmentTypeMapper equipmentTypeMapper;


    @Override
    public PagedResult<EquipmentType> selectPaged(EquipmentType equipmentType, Page page) {
        return PageUtil.queryPaged(page, equipmentType, equipmentTypeMapper);
    }

    @Override
    public EquipmentType selectByPrimaryKey(Long id) {
        return equipmentTypeMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer deleteByPrimaryKey(Long id) {
        return equipmentTypeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer insert(EquipmentType equipmentType) {
        equipmentType.setCreateInfo();
        equipmentType.setUpdateInfo();
        return equipmentTypeMapper.insert(equipmentType);
    }

    @Override
    public Integer insertSelective(EquipmentType equipmentType) {
        equipmentType.setCreateInfo();
        equipmentType.setUpdateInfo();
        return equipmentTypeMapper.insertSelective(equipmentType);
    }

    @Override
    public Integer insertSelectiveIgnore(EquipmentType equipmentType) {
        equipmentType.setCreateInfo();
        equipmentType.setUpdateInfo();
        return equipmentTypeMapper.insertSelectiveIgnore(equipmentType);
    }

    @Override
    public Integer updateByPrimaryKeySelective(EquipmentType equipmentType) {
        equipmentType.setUpdateInfo();
        return equipmentTypeMapper.updateByPrimaryKeySelective(equipmentType);
    }

    @Override
    public Integer updateByPrimaryKey(EquipmentType equipmentType) {
        equipmentType.setUpdateInfo();
        return equipmentTypeMapper.updateByPrimaryKey(equipmentType);
    }

    @Override
    public Integer batchInsert(List<EquipmentType> list) {
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(l -> {
                l.setCreateInfo();
                l.setUpdateInfo();
            });
        }
        return equipmentTypeMapper.batchInsert(list);
    }

    @Override
    public Integer batchUpdate(List<EquipmentType> list) {
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(BaseModel::setUpdateInfo);
        }
        return equipmentTypeMapper.batchUpdate(list);
    }

    /**
     * 存在即更新
     *
     * @return
     */
    @Override
    public Integer upsert(EquipmentType equipmentType) {
        return equipmentTypeMapper.upsert(equipmentType);
    }

    /**
     * 存在即更新，可选择具体属性
     *
     * @return
     */
    @Override
    public Integer upsertSelective(EquipmentType equipmentType) {
        return equipmentTypeMapper.upsertSelective(equipmentType);
    }

    @Override
    public List<EquipmentType> query(EquipmentType equipmentType) {
        return equipmentTypeMapper.query(equipmentType);
    }

    @Override
    public Long queryTotal() {
        return equipmentTypeMapper.queryTotal();
    }

    @Override
    public Integer deleteBatch(List<Long> list) {
        return equipmentTypeMapper.deleteBatch(list);
    }

}
