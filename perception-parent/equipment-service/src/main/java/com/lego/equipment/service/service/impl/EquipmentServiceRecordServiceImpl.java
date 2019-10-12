package com.lego.equipment.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.framework.common.consts.RespConsts;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespVO;
import com.framework.mybatis.utils.PageUtil;
import com.lego.equipment.service.mapper.EquipmentServiceRecordMapper;
import com.lego.equipment.service.service.IEquipmentServiceRecordService;
import com.lego.equipment.service.service.IEquipmentServiceRecordService;
import com.lego.framework.business.feign.BusinessClient;
import com.lego.framework.business.model.entity.Business;
import com.lego.framework.config.BaseModel;
import com.lego.framework.equipment.model.entity.EquipmentServiceRecord;
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
public class EquipmentServiceRecordServiceImpl implements IEquipmentServiceRecordService {
    /*<AUTOGEN--BEGIN>*/

    @Autowired
    public EquipmentServiceRecordMapper equipmentBusinessMapper;

    @Override
    public PagedResult<EquipmentServiceRecord> selectPaged(EquipmentServiceRecord equipmentServiceRecord, Page page) {
        PagedResult pagedResult = PageUtil.queryPaged(page, equipmentServiceRecord, equipmentBusinessMapper);

        return pagedResult;
    }

    @Override
    public EquipmentServiceRecord selectByPrimaryKey(Long id) {
        EquipmentServiceRecord equipmentServiceRecord = equipmentBusinessMapper.selectByPrimaryKey(id);

        return equipmentServiceRecord;
    }


    @Override
    public List<EquipmentServiceRecord> selectByEquipmentid(Long equipmentId) {
        QueryWrapper<EquipmentServiceRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("equipment_id", equipmentId);
        List<EquipmentServiceRecord> equipmentBusinesses = equipmentBusinessMapper.selectList(wrapper);
        return equipmentBusinesses;
    }

    @Override
    public Integer deleteByPrimaryKey(Long id) {
        return equipmentBusinessMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer insert(EquipmentServiceRecord equipmentServiceRecord) {
        return equipmentBusinessMapper.insert(equipmentServiceRecord);
    }

    @Override
    public Integer insertSelective(EquipmentServiceRecord equipmentServiceRecord) {
        return equipmentBusinessMapper.insertSelective(equipmentServiceRecord);
    }

    @Override
    public Integer insertSelectiveIgnore(EquipmentServiceRecord equipmentServiceRecord) {
        return equipmentBusinessMapper.insertSelectiveIgnore(equipmentServiceRecord);
    }

    @Override
    public Integer updateByPrimaryKeySelective(EquipmentServiceRecord equipmentServiceRecord) {
        return equipmentBusinessMapper.updateByPrimaryKeySelective(equipmentServiceRecord);
    }

    @Override
    public Integer updateByPrimaryKey(EquipmentServiceRecord equipmentServiceRecord) {
        return equipmentBusinessMapper.updateByPrimaryKey(equipmentServiceRecord);
    }

    @Override
    public Integer batchInsert(List<EquipmentServiceRecord> list) {
        return equipmentBusinessMapper.batchInsert(list);
    }

    @Override
    public Integer batchUpdate(List<EquipmentServiceRecord> list) {
        return equipmentBusinessMapper.batchUpdate(list);
    }

    /**
     * 存在即更新
     *
     * @return
     */
    @Override
    public Integer upsert(EquipmentServiceRecord equipmentServiceRecord) {
        return equipmentBusinessMapper.upsert(equipmentServiceRecord);
    }

    /**
     * 存在即更新，可选择具体属性
     *
     * @return
     */
    @Override
    public Integer upsertSelective(EquipmentServiceRecord equipmentServiceRecord) {
        return equipmentBusinessMapper.upsertSelective(equipmentServiceRecord);
    }

    @Override
    public List<EquipmentServiceRecord> query(EquipmentServiceRecord equipmentServiceRecord) {
        List<EquipmentServiceRecord> list = equipmentBusinessMapper.query(equipmentServiceRecord);
        return list;
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
