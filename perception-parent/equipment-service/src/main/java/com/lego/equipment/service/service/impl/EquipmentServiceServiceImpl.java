package com.lego.equipment.service.service.impl;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.mybatis.utils.PageUtil;
import com.lego.equipment.service.mapper.EquipmentServiceMapper;
import com.lego.equipment.service.service.IEquipmentServiceService;
import com.lego.framework.equipment.model.entity.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentServiceServiceImpl implements IEquipmentServiceService {
    /*<AUTOGEN--BEGIN>*/

    @Autowired
    public EquipmentServiceMapper equipmentServiceMapper;


    @Override
    public PagedResult<EquipmentService> selectPaged(EquipmentService equipmentService, Page page) {
        return PageUtil.queryPaged(page,equipmentService,equipmentServiceMapper);
    }

    @Override
    public EquipmentService selectByPrimaryKey(Long id) {
        return equipmentServiceMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer deleteByPrimaryKey(Long id) {
        return equipmentServiceMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer insert(EquipmentService tplEquipmentService) {
        return equipmentServiceMapper.insert(tplEquipmentService);
    }

    @Override
    public Integer insertSelective(EquipmentService tplEquipmentService) {
        return equipmentServiceMapper.insertSelective(tplEquipmentService);
    }

    @Override
    public Integer insertSelectiveIgnore(EquipmentService tplEquipmentService) {
        return equipmentServiceMapper.insertSelectiveIgnore(tplEquipmentService);
    }

    @Override
    public Integer updateByPrimaryKeySelective(EquipmentService tplEquipmentService) {
        return equipmentServiceMapper.updateByPrimaryKeySelective(tplEquipmentService);
    }

    @Override
    public Integer updateByPrimaryKey(EquipmentService tplEquipmentService) {
        return equipmentServiceMapper.updateByPrimaryKey(tplEquipmentService);
    }

    @Override
    public Integer batchInsert(List<EquipmentService> list) {
        return equipmentServiceMapper.batchInsert(list);
    }

    @Override
    public Integer batchUpdate(List<EquipmentService> list) {
        return equipmentServiceMapper.batchUpdate(list);
    }

    /**
     * 存在即更新
     *
     * @return
     */
    @Override
    public Integer upsert(EquipmentService tplEquipmentService) {
        return equipmentServiceMapper.upsert(tplEquipmentService);
    }

    /**
     * 存在即更新，可选择具体属性
     *
     * @return
     */
    @Override
    public Integer upsertSelective(EquipmentService tplEquipmentService) {
        return equipmentServiceMapper.upsertSelective(tplEquipmentService);
    }

    @Override
    public List<EquipmentService> query(EquipmentService tplEquipmentService) {
        return equipmentServiceMapper.query(tplEquipmentService);
    }

    @Override
    public Long queryTotal() {
        return equipmentServiceMapper.queryTotal();
    }

    @Override
    public Integer deleteBatch(List<Long> list) {
        return equipmentServiceMapper.deleteBatch(list);
    }

    /*<AUTOGEN--END>*/

}
