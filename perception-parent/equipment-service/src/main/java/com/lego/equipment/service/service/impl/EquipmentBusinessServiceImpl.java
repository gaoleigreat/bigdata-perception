package com.lego.equipment.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.mybatis.utils.PageUtil;
import com.lego.equipment.service.mapper.EquipmentBusinessMapper;
import com.lego.equipment.service.service.EquipmentBusinessService;
import com.lego.framework.config.BaseModel;
import com.lego.framework.equipment.model.entity.EquipmentBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-09-24 10:07:49
 * @since jdk 1.8
 */
@Service
public class EquipmentBusinessServiceImpl implements EquipmentBusinessService {
    /*<AUTOGEN--BEGIN>*/

    @Autowired
    public EquipmentBusinessMapper equipmentBusinessMapper;


    @Override
    public PagedResult<EquipmentBusiness> selectPaged(EquipmentBusiness equipmentBusiness, Page page) {
        return PageUtil.queryPaged(page, equipmentBusiness, equipmentBusinessMapper);
    }

    @Override
    public EquipmentBusiness selectByPrimaryKey(Long id) {
        return equipmentBusinessMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<EquipmentBusiness> selectByEquipmentid(Long equipmentId) {
        QueryWrapper<EquipmentBusiness> wrapper = new QueryWrapper<>();
        wrapper.eq("equipment_id", equipmentId);
        List<EquipmentBusiness> equipmentBusinesses = equipmentBusinessMapper.selectList(wrapper);
        return equipmentBusinesses;
    }

    @Override
    public Integer deleteByPrimaryKey(Long id) {
        return equipmentBusinessMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer insert(EquipmentBusiness equipmentBusiness) {
        equipmentBusiness.setCreateInfo();
        equipmentBusiness.setUpdateInfo();
        return equipmentBusinessMapper.insert(equipmentBusiness);
    }

    @Override
    public Integer insertSelective(EquipmentBusiness equipmentBusiness) {
        equipmentBusiness.setCreateInfo();
        equipmentBusiness.setUpdateInfo();
        return equipmentBusinessMapper.insertSelective(equipmentBusiness);
    }

    @Override
    public Integer insertSelectiveIgnore(EquipmentBusiness equipmentBusiness) {
        equipmentBusiness.setCreateInfo();
        equipmentBusiness.setUpdateInfo();
        return equipmentBusinessMapper.insertSelectiveIgnore(equipmentBusiness);
    }

    @Override
    public Integer updateByPrimaryKeySelective(EquipmentBusiness equipmentBusiness) {
        equipmentBusiness.setUpdateInfo();
        return equipmentBusinessMapper.updateByPrimaryKeySelective(equipmentBusiness);
    }

    @Override
    public Integer updateByPrimaryKey(EquipmentBusiness equipmentBusiness) {
        equipmentBusiness.setUpdateInfo();
        return equipmentBusinessMapper.updateByPrimaryKey(equipmentBusiness);
    }

    @Override
    public Integer batchInsert(List<EquipmentBusiness> list) {
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(l -> {
                l.setCreateInfo();
                l.setUpdateInfo();
            });
        }
        return equipmentBusinessMapper.batchInsert(list);
    }

    @Override
    public Integer batchUpdate(List<EquipmentBusiness> list) {
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(BaseModel::setUpdateInfo);
        }
        return equipmentBusinessMapper.batchUpdate(list);
    }

    /**
     * 存在即更新
     *
     * @return
     */
    @Override
    public Integer upsert(EquipmentBusiness equipmentBusiness) {
        return equipmentBusinessMapper.upsert(equipmentBusiness);
    }

    /**
     * 存在即更新，可选择具体属性
     *
     * @return
     */
    @Override
    public Integer upsertSelective(EquipmentBusiness equipmentBusiness) {
        return equipmentBusinessMapper.upsertSelective(equipmentBusiness);
    }

    @Override
    public List<EquipmentBusiness> query(EquipmentBusiness equipmentBusiness) {
        return equipmentBusinessMapper.query(equipmentBusiness);
    }

    @Override
    public Long queryTotal() {
        return equipmentBusinessMapper.queryTotal();
    }

    @Override
    public Integer deleteBatch(List<Long> list) {
        return equipmentBusinessMapper.deleteBatch(list);
    }

}
