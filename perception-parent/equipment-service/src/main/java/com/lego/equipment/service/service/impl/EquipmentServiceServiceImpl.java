package com.lego.equipment.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.framework.common.consts.RespConsts;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespVO;
import com.framework.mybatis.tool.WhereEntityTool;
import com.framework.mybatis.utils.PageUtil;
import com.lego.equipment.service.mapper.EquipmentServiceMapper;
import com.lego.equipment.service.mapper.EquipmentServiceRecordMapper;
import com.lego.equipment.service.service.IEquipmentServiceService;
import com.lego.framework.business.feign.CrudClient;
import com.lego.framework.equipment.model.entity.EquipmentService;
import com.lego.framework.equipment.model.entity.EquipmentServiceRecord;
import com.lego.framework.system.feign.ProjectClient;
import com.lego.framework.system.model.entity.Project;
import com.lego.framework.template.feign.TemplateFeignClient;
import com.lego.framework.template.model.entity.FormTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

@Service
public class EquipmentServiceServiceImpl implements IEquipmentServiceService {
    /*<AUTOGEN--BEGIN>*/

    @Autowired
    public EquipmentServiceMapper equipmentServiceMapper;

    @Autowired
    public EquipmentServiceRecordMapper equipmentServiceRecordMapper;

    @Autowired
    private TemplateFeignClient templateFeignClient;

    @Autowired
    private CrudClient crudClient;

    @Autowired
    private ProjectClient projectClient;


    @Override
    public PagedResult<EquipmentService> selectPaged(EquipmentService equipmentService, Page page) {
        QueryWrapper wrapper = new QueryWrapper();
        WhereEntityTool.invoke(wrapper);
        IPage<EquipmentService> iPage = PageUtil.page2IPage(page);
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<EquipmentService> queryList = equipmentServiceMapper.queryList(iPage,wrapper);
        List<EquipmentService> records = queryList.getRecords();
        if (!CollectionUtils.isEmpty(records)) {
            for (EquipmentService record : records) {
                Integer templateType = record.getTemplateType();
                RespVO<FormTemplate> respVO = templateFeignClient.findByDataType(templateType);
                if (respVO.getRetCode() != RespConsts.SUCCESS_RESULT_CODE || respVO.getInfo()==null) {
                    continue;
                }
                FormTemplate formTemplate = respVO.getInfo();
                String equipmentCode = record.getEquipmentCode();
                RespVO<Map<String, Object>> dataByCode = crudClient.queryBusinessDataByCode(formTemplate.getTemplateCode(), equipmentCode);
                if (dataByCode.getRetCode() == RespConsts.SUCCESS_RESULT_CODE
                        && dataByCode.getInfo() != null && dataByCode.getInfo().containsKey("name")) {
                    record.setEquipmentName(dataByCode.getInfo().get("name").toString());
                }

                RespVO<Project> projectRespVO = projectClient.selectByPrimaryKey(record.getProjectId());
                if (projectRespVO.getRetCode() == RespConsts.SUCCESS_RESULT_CODE && projectRespVO.getInfo() != null) {
                    record.setProjectName(projectRespVO.getInfo().getName());
                }
            }
        }
        return PageUtil.iPage2Result(queryList);
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
    public Integer insert(EquipmentService equipmentService) {
        int insert = equipmentServiceMapper.insert(equipmentService);
        if (insert > 0) {
            EquipmentServiceRecord equipmentServiceRecord = new EquipmentServiceRecord();
            BeanUtils.copyProperties(equipmentService, equipmentServiceRecord);
            equipmentServiceRecord.setEquipmentServiceId(equipmentService.getId());
            equipmentServiceRecordMapper.insert(equipmentServiceRecord);
        }
        return insert;
    }

    @Override
    public Integer insertSelective(EquipmentService equipmentService) {
        int insert = equipmentServiceMapper.insertSelective(equipmentService);
        if (insert > 0) {
            EquipmentServiceRecord equipmentServiceRecord = new EquipmentServiceRecord();
            BeanUtils.copyProperties(equipmentService, equipmentServiceRecord);
            equipmentServiceRecord.setEquipmentServiceId(equipmentService.getId());
            equipmentServiceRecordMapper.insert(equipmentServiceRecord);
        }
        return insert;
    }

    @Override
    public Integer insertSelectiveIgnore(EquipmentService tplEquipmentService) {
        return equipmentServiceMapper.insertSelectiveIgnore(tplEquipmentService);
    }

    @Override
    public Integer updateByPrimaryKeySelective(EquipmentService equipmentService) {

        int update = equipmentServiceMapper.updateByPrimaryKeySelective(equipmentService);
        if (update > 0) {
            EquipmentServiceRecord equipmentServiceRecord = new EquipmentServiceRecord();
            BeanUtils.copyProperties(equipmentService, equipmentServiceRecord);
            equipmentServiceRecord.setEquipmentServiceId(equipmentService.getId());
            equipmentServiceRecordMapper.insert(equipmentServiceRecord);
        }
        return update;
    }

    @Override
    public Integer updateByPrimaryKey(EquipmentService equipmentService) {
        int update = equipmentServiceMapper.updateByPrimaryKey(equipmentService);
        if (update > 0) {
            EquipmentServiceRecord equipmentServiceRecord = new EquipmentServiceRecord();
            BeanUtils.copyProperties(equipmentService, equipmentServiceRecord);
            equipmentServiceRecord.setEquipmentServiceId(equipmentService.getId());
            equipmentServiceRecordMapper.insert(equipmentServiceRecord);
        }
        return update;
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
