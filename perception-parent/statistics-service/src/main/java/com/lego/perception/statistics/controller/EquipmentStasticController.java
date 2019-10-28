package com.lego.perception.statistics.controller;

import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.base.utils.BigDecimalUtils;
import com.lego.framework.equipment.feign.EquipmentCostClient;
import com.lego.framework.equipment.feign.EquipmentMaintenanceClient;
import com.lego.framework.equipment.feign.EquipmentServiceClient;
import com.lego.framework.equipment.model.entity.EquipmentCost;
import com.lego.framework.equipment.model.entity.EquipmentMaintenance;
import com.lego.framework.equipment.model.entity.EquipmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @auther xiaodao
 * @date 2019/10/24 10:09
 */

@Api(value = "EquipmentStasticController", description = "单个备统计管理")
@RestController
@RequestMapping("/equipmentStasticController")
@Resource(value = "equipmentStasticController", desc = "设备统计管理")
public class EquipmentStasticController {

    @Autowired
    private EquipmentCostClient equipmentCostClient;
    @Autowired
    private EquipmentServiceClient equipmentServiceClient;

    @Autowired
    private EquipmentMaintenanceClient equipmentMaintenanceClient;


    @Operation(value = "find", desc = "查询")
    @ApiOperation("查询单个设备盈利状况")
    @GetMapping("/profitSituation")
    public RespVO profitSituation(String equipmentCode) {
        EquipmentCost equipmentCost = new EquipmentCost();
        equipmentCost.setEquipmentCode(equipmentCode);
        RespVO<RespDataVO<EquipmentCost>> respDataVORespVO = equipmentCostClient.queryByCondition(equipmentCost);
        if (respDataVORespVO.getRetCode() == 1) {
            List<EquipmentCost> equipmentCostList = respDataVORespVO.getInfo().getList();
            BigDecimal totalQuantity = equipmentCostList.stream().filter(ec ->
                    Arrays.asList("2").contains(ec.getType())).map(EquipmentCost::getAmount).reduce(BigDecimal.ZERO, BigDecimalUtils::sum);
            return RespVOBuilder.success(totalQuantity);
        }
        return RespVOBuilder.failure();

    }


    @Operation(value = "find", desc = "查询")
    @ApiOperation("查询单个设备支出状况")
    @GetMapping("/expenditure")
    public RespVO expenditure(String equipmentCode) {
        EquipmentCost equipmentCost = new EquipmentCost();
        equipmentCost.setEquipmentCode(equipmentCode);
        RespVO<RespDataVO<EquipmentCost>> respDataVORespVO = equipmentCostClient.queryByCondition(equipmentCost);
        if (respDataVORespVO.getRetCode() == 1) {
            List<EquipmentCost> equipmentCostList = respDataVORespVO.getInfo().getList();
            BigDecimal totalQuantity = equipmentCostList.stream().filter(ec ->
                    Arrays.asList("3", "4", "6").contains(ec.getType())).map(EquipmentCost::getAmount).reduce(BigDecimal.ZERO, BigDecimalUtils::sum);
            return RespVOBuilder.success(totalQuantity);
        }
        return RespVOBuilder.failure();

    }


    @Operation(value = "find", desc = "查询")
    @ApiOperation("查询单个设备维修次数")
    @GetMapping("/serviceTimes")
    public RespVO serviceTimes(String equipmentCode) {
        EquipmentService equipmentService = new EquipmentService();
        equipmentService.setEquipmentCode(equipmentCode);
        RespVO<RespDataVO<EquipmentService>> respDataVORespVO = equipmentServiceClient.queryByCondition(equipmentService);
        if (respDataVORespVO.getRetCode() == 1) {
            List<EquipmentService> equipmentServiceList = respDataVORespVO.getInfo().getList();
            long count = equipmentServiceList.stream().filter(ec ->
                    Arrays.asList(2, 3, 4).contains(ec.getStatus())).count();
            return RespVOBuilder.success(count);
        }
        return RespVOBuilder.failure();

    }


    @Operation(value = "find", desc = "查询")
    @ApiOperation("查询单个设备保养次数")
    @GetMapping("/maintenanceTimes")
    public RespVO maintenanceTimes(String equipmentCode) {
        EquipmentMaintenance equipmentMaintenance = new EquipmentMaintenance();
        equipmentMaintenance.setEquipmentCode(equipmentCode);
        RespVO<RespDataVO<EquipmentMaintenance>> respDataVORespVO = equipmentMaintenanceClient.queryByCondition(equipmentMaintenance);
        if (respDataVORespVO.getRetCode() == 1) {
            List<EquipmentMaintenance> equipmentMaintenanceList = respDataVORespVO.getInfo().getList();
            if (!CollectionUtils.isEmpty(equipmentMaintenanceList)) {
                return RespVOBuilder.success(equipmentMaintenanceList.size());
            } else {
                return RespVOBuilder.success(0);
            }

        }
        return RespVOBuilder.failure();

    }

    @Operation(value = "find", desc = "查询")
    @ApiOperation("查询单个所在项目盈利及支出")
    @GetMapping("/profitSituationAndExpenditure")
    public RespVO profitSituationAndExpenditure(String equipmentCode) {
        EquipmentCost equipmentCost = new EquipmentCost();
        equipmentCost.setEquipmentCode(equipmentCode);
        RespVO<RespDataVO<EquipmentCost>> respDataVORespVO = equipmentCostClient.queryByCondition(equipmentCost);
        if (respDataVORespVO.getRetCode() == 1) {
            List<EquipmentCost> equipmentCostList = respDataVORespVO.getInfo().getList();
            Map<Long, List<EquipmentCost>> listMap = equipmentCostList.stream().collect(Collectors.groupingBy(EquipmentCost::getProjectId));
            Set<Long> longSet = listMap.keySet();
            List<Map<String, Object>> resultList = new ArrayList<>();
            longSet.forEach(l -> {
                List<EquipmentCost> equipmentCosts = listMap.get(l);
                Map<String, Object> resltMap = new HashMap<>();
                BigDecimal totalProfit = equipmentCosts.stream().filter(ec ->
                        Arrays.asList("2").contains(ec.getType())).map(EquipmentCost::getAmount).reduce(BigDecimal.ZERO, BigDecimalUtils::sum);

                BigDecimal totalExpenditure = equipmentCosts.stream().filter(ec ->
                        Arrays.asList("3", "4", "6").contains(ec.getType())).map(EquipmentCost::getAmount).reduce(BigDecimal.ZERO, BigDecimalUtils::sum);


                resltMap.put("totalProfit", totalProfit);
                resltMap.put("totalExpenditure", totalExpenditure);
                resltMap.put("projectName", totalExpenditure);

                resultList.add(resltMap);
            });

            return RespVOBuilder.success(resultList);
        }
        return RespVOBuilder.failure();

    }


}