package com.lego.equipment.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.framework.common.consts.RespConsts;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespVO;
import com.framework.mybatis.utils.PageUtil;
import com.lego.equipment.service.mapper.EquipmentBusinessMapper;
import com.lego.equipment.service.service.IEquipmentBusinessService;
import com.lego.framework.business.feign.BusinessClient;
import com.lego.framework.business.model.entity.Business;
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
public class EquipmentBusinessServiceImpl implements IEquipmentBusinessService {
    /*<AUTOGEN--BEGIN>*/

    @Autowired
    public EquipmentBusinessMapper equipmentBusinessMapper;

    @Autowired
    private BusinessClient businessClient;


    @Override
    public PagedResult<EquipmentBusiness> selectPaged(EquipmentBusiness equipmentBusiness, Page page) {
        PagedResult pagedResult = PageUtil.queryPaged(page, equipmentBusiness, equipmentBusinessMapper);
        if (!CollectionUtils.isEmpty(pagedResult.getResultList())) {
            List<EquipmentBusiness> resultList = pagedResult.getResultList();
            resultList.forEach(res -> setName(res));
        }
        return pagedResult;
    }

    @Override
    public EquipmentBusiness selectByPrimaryKey(Long id) {
        EquipmentBusiness equipmentBusiness = equipmentBusinessMapper.selectByPrimaryKey(id);
        setName(equipmentBusiness);
        return equipmentBusiness;
    }

    private void setName(EquipmentBusiness equipmentBusiness) {
        if (equipmentBusiness != null) {
            RespVO<Business> businessRespVO = businessClient.selectById(equipmentBusiness.getBusinessId());
            if (businessRespVO.getRetCode() == RespConsts.SUCCESS_RESULT_CODE) {
                Business info = businessRespVO.getInfo();
                if (info != null) {
                    equipmentBusiness.setName(info.getName());
                }
            }
        }
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
        List<EquipmentBusiness> list = equipmentBusinessMapper.query(equipmentBusiness);
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(l -> setName(l));
        }
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
