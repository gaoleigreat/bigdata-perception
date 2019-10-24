package com.lego.framework.equipment.feign;

import com.framework.common.consts.RespConsts;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.equipment.model.entity.EquipmentCost;
import com.lego.framework.equipment.model.entity.EquipmentDocTrace;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/10/6 14:36
 * @desc :
 */
@FeignClient(value = "equipment-service", path = "/equipmentcost", fallbackFactory = EquipmentCostClientFallbackFactory.class)
public interface EquipmentCostClient {

    /**
     * 查询设备文档轨迹
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/select_by_id", method = RequestMethod.GET)
    RespVO<EquipmentCost> selectByPrimaryKey(@RequestParam(value = "id") Long id);


    /**
     * 查询设备文档轨迹
     *
     * @param equipmentCost
     * @return
     */
    @RequestMapping(value = "/query_list", method = RequestMethod.POST)
    RespVO<RespDataVO<EquipmentCost>> queryByCondition(@RequestBody EquipmentCost equipmentCost);

}

@Component
@Slf4j
class EquipmentCostClientFallbackFactory implements FallbackFactory<EquipmentCostClient> {

    @Override
    public EquipmentCostClient create(Throwable throwable) {
        log.error("fallback; reason was:", throwable);
        return new EquipmentCostClient() {
            @Override
            public RespVO<EquipmentCost> selectByPrimaryKey(Long id) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "equipment服务不可用");
            }

            @Override
            public RespVO<RespDataVO<EquipmentCost>> queryByCondition(EquipmentCost equipmentCost) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "equipment服务不可用");
            }
        };
    }
}
