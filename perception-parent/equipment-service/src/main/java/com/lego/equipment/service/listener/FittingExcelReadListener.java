package com.lego.equipment.service.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.framework.excel.listener.ExcelReadListener;
import com.lego.equipment.service.service.IEquipmentMaintenanceDocService;
import com.lego.equipment.service.service.IEquipmentTypeService;
import com.lego.equipment.service.service.IFittingService;
import com.lego.framework.base.exception.ExceptionBuilder;
import com.lego.framework.equipment.model.entity.EquipmentMaintenanceDoc;
import com.lego.framework.equipment.model.entity.EquipmentType;
import com.lego.framework.equipment.model.entity.Fitting;
import com.lego.framework.equipment.model.vo.EquipmentMaintenanceDocVo;
import com.lego.framework.equipment.model.vo.FittingVo;
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
public class FittingExcelReadListener extends ExcelReadListener<FittingVo> {

    private List<Fitting> fittings = new ArrayList<>();

    @Autowired
    private IFittingService iFittingService;

    private String sys;

    private Long createBy;

    public void setSysAndCreateBy(String sys, Long createBy) {
        this.sys = sys;
        this.createBy = createBy;
    }

    @Override
    public void invoke(FittingVo fittingVo, AnalysisContext analysisContext) {
        Integer currentRowNum = analysisContext.getCurrentRowNum();
        Integer totalCount = analysisContext.getTotalCount();
        log.info("read line :{},currentRowNum:{},totalCount:{}", fittingVo, currentRowNum, totalCount);
        if (currentRowNum >0) {
            getFitting(fittingVo);
        }
    }

    private void getFitting(FittingVo fittingVo) {
        Fitting fitting = new Fitting();
        Date currentDate = new Date();
        fitting.setCreationDate(currentDate);
        fitting.setLastUpdateDate(currentDate);
        fitting.setSys(sys);
        fitting.setCreatedBy(createBy);
        fitting.setCenterMaterialsNum(fittingVo.getCenterMaterialsNum());
        fitting.setCount(Integer.valueOf(fittingVo.getCount()));
        fitting.setFactoryMaterialNum(fittingVo.getFactoryMaterialNum());
        fitting.setMaterialDesc(fittingVo.getMaterialDesc());
        fitting.setMaterialName(fittingVo.getMaterialName());
        fitting.setRemark(fittingVo.getRemark());
        fitting.setUnit(fittingVo.getUnit());
        fittings.add(fitting);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if (CollectionUtils.isEmpty(fittings)) {
            return;
        }
        try {
            iFittingService.batchInsert(fittings);
        } finally {
            fittings.clear();
        }
    }
}
