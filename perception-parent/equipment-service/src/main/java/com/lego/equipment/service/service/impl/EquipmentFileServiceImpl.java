package com.lego.equipment.service.service.impl;


import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.mybatis.utils.PageUtil;
import com.lego.equipment.service.mapper.EquipmentFileMapper;
import com.lego.equipment.service.service.IEquipmentFileService;
import com.lego.framework.equipment.model.entity.EquipmentBusiness;
import com.lego.framework.equipment.model.entity.EquipmentFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;




/**
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-10-08 08:42:13
 * @since jdk 1.8
 */
@Service("equipmentFileService")
public class EquipmentFileServiceImpl implements IEquipmentFileService {
    /*<AUTOGEN--BEGIN>*/

    @Autowired
    public EquipmentFileMapper equipmentFileMapper;


    @Override
    public PagedResult<EquipmentFile> selectPaged(EquipmentFile equipmentFile, Page page) {
        PagedResult pagedResult = PageUtil.queryPaged(page, equipmentFile, equipmentFileMapper);
        return pagedResult;
    }

    @Override
    public EquipmentFile selectByPrimaryKey(Long fileId) {
        return equipmentFileMapper.selectByPrimaryKey(fileId);
    }

    @Override
    public Integer deleteByPrimaryKey(Long fileId) {
        return equipmentFileMapper.deleteByPrimaryKey(fileId);
    }

    @Override
    public Integer insert(EquipmentFile equipmentFile) {
        return equipmentFileMapper.insert(equipmentFile);
    }

    @Override
    public Integer insertSelective(EquipmentFile equipmentFile) {
        return equipmentFileMapper.insertSelective(equipmentFile);
    }

    @Override
    public Integer insertSelectiveIgnore(EquipmentFile equipmentFile) {
        return equipmentFileMapper.insertSelectiveIgnore(equipmentFile);
    }

    @Override
    public Integer updateByPrimaryKeySelective(EquipmentFile equipmentFile) {
        return equipmentFileMapper.updateByPrimaryKeySelective(equipmentFile);
    }

    @Override
    public Integer updateByPrimaryKey(EquipmentFile equipmentFile) {
        return equipmentFileMapper.updateByPrimaryKey(equipmentFile);
    }

    @Override
    public Integer batchInsert(List<EquipmentFile> list) {
        return equipmentFileMapper.batchInsert(list);
    }

    @Override
    public Integer batchUpdate(List<EquipmentFile> list) {
        return equipmentFileMapper.batchUpdate(list);
    }

    /**
     * 存在即更新
     *
     * @return
     */
    @Override
    public Integer upsert(EquipmentFile equipmentFile) {
        return equipmentFileMapper.upsert(equipmentFile);
    }

    /**
     * 存在即更新，可选择具体属性
     *
     * @return
     */
    @Override
    public Integer upsertSelective(EquipmentFile equipmentFile) {
        return equipmentFileMapper.upsertSelective(equipmentFile);
    }

    @Override
    public List<EquipmentFile> query(EquipmentFile equipmentFile) {
        return equipmentFileMapper.query(equipmentFile);
    }

    @Override
    public Long queryTotal() {
        return equipmentFileMapper.queryTotal();
    }

    @Override
    public Integer deleteBatch(List<Long> list) {
        return equipmentFileMapper.deleteBatch(list);
    }


}
