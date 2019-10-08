package com.lego.equipment.service.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.framework.excel.listener.ExcelReadListener;
import com.lego.equipment.service.service.IEquipmentMaintenanceDocService;
import com.lego.equipment.service.service.IEquipmentTypeService;
import com.lego.framework.base.exception.ExceptionBuilder;
import com.lego.framework.equipment.model.entity.EquipmentMaintenanceDoc;
import com.lego.framework.equipment.model.entity.EquipmentType;
import com.lego.framework.equipment.model.vo.EquipmentMaintenanceDocVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yanglf
 * @description
 * @since 2019/1/19
 **/
@Component
@Slf4j
public class DocExcelReadListener extends ExcelReadListener<EquipmentMaintenanceDocVo> {

    private List<EquipmentMaintenanceDoc> equipmentMaintenanceDocList = new ArrayList<>();

    @Autowired
    private IEquipmentMaintenanceDocService iEquipmentMaintenanceDocService;

    @Autowired
    private IEquipmentTypeService iEquipmentTypeService;


    private Long createBy;

    private Long fileId;

    @Override
    public void invoke(EquipmentMaintenanceDocVo equipmentMaintenanceDocVo, AnalysisContext analysisContext) {
        Integer currentRowNum = analysisContext.getCurrentRowNum();
        Integer totalCount = analysisContext.getTotalCount();
        log.info("read line :{},currentRowNum:{},totalCount:{}", equipmentMaintenanceDocVo, currentRowNum, totalCount);
        EquipmentMaintenanceDoc equipmentMaintenanceDoc = new EquipmentMaintenanceDoc();
        BeanUtils.copyProperties(equipmentMaintenanceDocVo, equipmentMaintenanceDoc);
        equipmentMaintenanceDocList.add(equipmentMaintenanceDoc);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if (CollectionUtils.isEmpty(equipmentMaintenanceDocList)) {
            return;
        }
        try {
            EquipmentMaintenanceDoc doc = equipmentMaintenanceDocList.get(0);
            String typeCode = doc.getTypeCode();
            EquipmentType equipmentType = new EquipmentType();
            equipmentType.setCode(typeCode);
            List<EquipmentType> typeList = iEquipmentTypeService.query(equipmentType);
            if (CollectionUtils.isEmpty(typeList)) {
                ExceptionBuilder.operateFailException("设备类型不存在");
            }
            Long typeId = typeList.get(0).getId();
            for (EquipmentMaintenanceDoc equipmentMaintenanceDoc : equipmentMaintenanceDocList) {
                Date currentDate = new Date();
                equipmentMaintenanceDoc.setCreationDate(currentDate);
                equipmentMaintenanceDoc.setType(getType(equipmentMaintenanceDoc.getTypeStr()));
                equipmentMaintenanceDoc.setEquipmentId(typeId);
                equipmentMaintenanceDoc.setFileId(fileId);
                equipmentMaintenanceDoc.setCreationBy(createBy);
            }
            iEquipmentMaintenanceDocService.batchInsert(equipmentMaintenanceDocList);
        } finally {
            equipmentMaintenanceDocList.clear();
        }
    }

    private Integer getType(String typeStr) {
        switch (typeStr) {
            case "初保":
                return 1;
            case "日保":
                return 2;
            case "周保":
                return 3;
            case "月保":
                return 4;
            default:
                return 0;
        }
    }


    public void setCreateByAndFileId(Long fileId, Long createBy) {
        this.createBy = createBy;
        this.fileId = fileId;

    }
}
