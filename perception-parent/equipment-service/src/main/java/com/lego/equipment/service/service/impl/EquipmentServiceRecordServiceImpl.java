package com.lego.equipment.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.mybatis.tool.WhereEntityTool;
import com.framework.mybatis.utils.PageUtil;
import com.lego.equipment.service.mapper.EquipmentServiceRecordMapper;
import com.lego.equipment.service.service.IEquipmentServiceRecordService;
import com.lego.framework.equipment.model.entity.EquipmentServiceRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-09-24 10:07:49
 * @since jdk 1.8
 */
@Service
public class EquipmentServiceRecordServiceImpl implements IEquipmentServiceRecordService {
    /*<AUTOGEN--BEGIN>*/

    @Autowired
    public EquipmentServiceRecordMapper equipmentServiceRecordMapper;

    @Override
    public PagedResult<EquipmentServiceRecord> selectPaged(EquipmentServiceRecord equipmentServiceRecord, Page page) {
        IPage<EquipmentServiceRecord> iPage = PageUtil.page2IPage(page);
        QueryWrapper<EquipmentServiceRecord> wrapper = new QueryWrapper<>();
        WhereEntityTool.excute(equipmentServiceRecord, wrapper);
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<EquipmentServiceRecord> recordPage = equipmentServiceRecordMapper.queryList(iPage, wrapper);
        return PageUtil.iPage2Result(recordPage);
    }

    @Override
    public EquipmentServiceRecord selectByPrimaryKey(Long id) {
        return equipmentServiceRecordMapper.selectByPrimaryKey(id);
    }


    @Override
    public List<EquipmentServiceRecord> selectByEquipmentid(Long equipmentId) {
        QueryWrapper<EquipmentServiceRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("equipment_id", equipmentId);
        return equipmentServiceRecordMapper.selectList(wrapper);
    }

    @Override
    public Integer deleteByPrimaryKey(Long id) {
        return equipmentServiceRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer insert(EquipmentServiceRecord equipmentServiceRecord) {
        return equipmentServiceRecordMapper.insert(equipmentServiceRecord);
    }

    @Override
    public Integer insertSelective(EquipmentServiceRecord equipmentServiceRecord) {
        return equipmentServiceRecordMapper.insertSelective(equipmentServiceRecord);
    }

    @Override
    public Integer insertSelectiveIgnore(EquipmentServiceRecord equipmentServiceRecord) {
        return equipmentServiceRecordMapper.insertSelectiveIgnore(equipmentServiceRecord);
    }

    @Override
    public Integer updateByPrimaryKeySelective(EquipmentServiceRecord equipmentServiceRecord) {
        return equipmentServiceRecordMapper.updateByPrimaryKeySelective(equipmentServiceRecord);
    }

    @Override
    public Integer updateByPrimaryKey(EquipmentServiceRecord equipmentServiceRecord) {
        return equipmentServiceRecordMapper.updateByPrimaryKey(equipmentServiceRecord);
    }

    @Override
    public Integer update(EquipmentServiceRecord equipmentServiceRecord) {
        QueryWrapper<EquipmentServiceRecord> wrapper = new QueryWrapper<>();
        return equipmentServiceRecordMapper.update(equipmentServiceRecord, wrapper);
    }

    @Override
    public Integer batchInsert(List<EquipmentServiceRecord> list) {
        return equipmentServiceRecordMapper.batchInsert(list);
    }

    @Override
    public Integer batchUpdate(List<EquipmentServiceRecord> list) {
        return equipmentServiceRecordMapper.batchUpdate(list);
    }

    /**
     * 存在即更新
     *
     * @return
     */
    @Override
    public Integer upsert(EquipmentServiceRecord equipmentServiceRecord) {
        return equipmentServiceRecordMapper.upsert(equipmentServiceRecord);
    }

    /**
     * 存在即更新，可选择具体属性
     *
     * @return
     */
    @Override
    public Integer upsertSelective(EquipmentServiceRecord equipmentServiceRecord) {
        return equipmentServiceRecordMapper.upsertSelective(equipmentServiceRecord);
    }

    @Override
    public List<EquipmentServiceRecord> query(EquipmentServiceRecord equipmentServiceRecord) {
        return equipmentServiceRecordMapper.query(equipmentServiceRecord);
    }

    @Override
    public Long queryTotal() {
        return equipmentServiceRecordMapper.queryTotal();
    }

    @Override
    public Integer deleteBatch(List<Long> list) {
        return equipmentServiceRecordMapper.deleteBatch(list);
    }

}
