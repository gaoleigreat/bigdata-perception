package com.lego.perception.statistics.controller;

import com.framework.common.sdto.RespVO;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.equipment.feign.EquipmentCostClient;
import com.lego.framework.equipment.feign.EquipmentMaintenanceClient;
import com.lego.framework.equipment.feign.EquipmentServiceClient;
import com.lego.framework.equipment.model.entity.EquipmentType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther xiaodao
 * @date 2019/10/24 10:09
 */

@Api(value = "EquipmentTypeStasticController", description = "设备类型统计管理")
@RestController
@RequestMapping("/equipmentTypeStasticController")
@Resource(value = "EquipmentTypeStasticController", desc = "设备类型统计管理")
public class EquipmentTypeStasticController {

    @Autowired
    private EquipmentCostClient equipmentCostClient;
    @Autowired
    private EquipmentServiceClient equipmentServiceClient;

    @Autowired
    private EquipmentMaintenanceClient equipmentMaintenanceClient;


    @Operation(value = "find", desc = "查询")
    @ApiOperation("设备总数、发生故障数量、闲置数量")
    @GetMapping("/profitSituation")
    public RespVO profitSituation(String code) {
        EquipmentType equipmentType = new EquipmentType();
        equipmentType.setCode(code);
        return null;

    }


}