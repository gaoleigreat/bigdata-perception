package com.lego.equipment.service.service.impl;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.mybatis.utils.PageUtil;
import com.lego.equipment.service.mapper.EquipmentStopReasonMapper;
import com.lego.equipment.service.service.IEquipmentStopReasonService;
import com.lego.framework.equipment.model.entity.EquipmentStopReason;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


/**
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-10-06 09:29:16
 * @since jdk 1.8
 */
@Service
public class EquipmentStopReasonServiceImpl implements IEquipmentStopReasonService {

    @Autowired
    public EquipmentStopReasonMapper equipmentStopReasonMapper;


    @Override
    public PagedResult<EquipmentStopReason> selectPaged(EquipmentStopReason equipmentStopReason, Page page) {
        return PageUtil.queryPaged(page,equipmentStopReason,equipmentStopReasonMapper);
    }

    @Override
    public EquipmentStopReason selectByPrimaryKey(Long id) {
        return equipmentStopReasonMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer deleteByPrimaryKey(Long id) {
        return equipmentStopReasonMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer insert(EquipmentStopReason equipmentStopReason) {
        return equipmentStopReasonMapper.insert(equipmentStopReason);
    }

    @Override
    public Integer insertSelective(EquipmentStopReason equipmentStopReason) {
        return equipmentStopReasonMapper.insertSelective(equipmentStopReason);
    }

    @Override
    public Integer insertSelectiveIgnore(EquipmentStopReason equipmentStopReason) {
        return equipmentStopReasonMapper.insertSelectiveIgnore(equipmentStopReason);
    }

    @Override
    public Integer updateByPrimaryKeySelective(EquipmentStopReason equipmentStopReason) {
        return equipmentStopReasonMapper.updateByPrimaryKeySelective(equipmentStopReason);
    }

    @Override
    public Integer updateByPrimaryKey(EquipmentStopReason equipmentStopReason) {
        return equipmentStopReasonMapper.updateByPrimaryKey(equipmentStopReason);
    }

    @Override
    public Integer batchInsert(List<EquipmentStopReason> list) {
        return equipmentStopReasonMapper.batchInsert(list);
    }

    @Override
    public Integer batchUpdate(List<EquipmentStopReason> list) {
        return equipmentStopReasonMapper.batchUpdate(list);
    }

    /**
     * 存在即更新
     * @return
     */
    @Override
    public Integer upsert(EquipmentStopReason equipmentStopReason) {
        return equipmentStopReasonMapper.upsert(equipmentStopReason);
    }

    /**
     * 存在即更新，可选择具体属性
     * @return
     */
    @Override
    public Integer upsertSelective(EquipmentStopReason equipmentStopReason) {
        return equipmentStopReasonMapper.upsertSelective(equipmentStopReason);
    }

    @Override
    public List<EquipmentStopReason> query(EquipmentStopReason equipmentStopReason) {
        return equipmentStopReasonMapper.query(equipmentStopReason);
    }

    @Override
    public Long queryTotal() {
        return equipmentStopReasonMapper.queryTotal();
    }

    @Override
    public Integer deleteBatch(List<Long> list) {
        return equipmentStopReasonMapper.deleteBatch(list);
    }

}
