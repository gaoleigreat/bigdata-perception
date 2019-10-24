package com.lego.perception.statistics.controller;

import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.base.utils.BigDecimalUtils;
import com.lego.framework.equipment.feign.EquipmentCostClient;
import com.lego.framework.equipment.model.entity.EquipmentCost;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
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


    @GetMapping("/profitSituation")
    public RespVO profitSituation(String equipmentCode) {
        EquipmentCost equipmentCost = new EquipmentCost();
        equipmentCost.setEquipmentCode(equipmentCode);
        RespVO<RespDataVO<EquipmentCost>> respDataVORespVO = equipmentCostClient.queryByCondition(equipmentCost);
        if (respDataVORespVO.getRetCode() == 1) {
            List<EquipmentCost> equipmentCostList = respDataVORespVO.getInfo().getList();
            BigDecimal totalQuantity = equipmentCostList.stream().filter(ec ->
                    Arrays.asList("1", "2", "3", "4", "5").contains(ec.getType())).map(EquipmentCost::getAmount).reduce(BigDecimal.ZERO, BigDecimalUtils::sum);

            return RespVOBuilder.success(totalQuantity);
        }
        return RespVOBuilder.failure();

    }


}