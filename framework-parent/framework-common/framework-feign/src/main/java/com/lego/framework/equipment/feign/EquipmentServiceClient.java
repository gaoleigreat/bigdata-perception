package com.lego.framework.equipment.feign;

import com.framework.common.consts.RespConsts;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.equipment.model.entity.EquipmentCost;
import com.lego.framework.equipment.model.entity.EquipmentService;
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
@FeignClient(value = "equipment-service", path = "/equipmentservice", fallbackFactory = EquipmentCostClientFallbackFactory.class)
public interface EquipmentServiceClient {

    /**
     * 查询设备
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/select_by_id", method = RequestMethod.GET)
    RespVO<EquipmentService> selectByPrimaryKey(@RequestParam(value = "id") Long id);


    /**
     * 查询设备
     *
     * @param equipmentService
     * @return
     */
    @RequestMapping(value = "/query_list", method = RequestMethod.POST)
    RespVO<RespDataVO<EquipmentService>> queryByCondition(@RequestBody EquipmentService equipmentService);

}

@Component
@Slf4j
class EquipmentServiceClientFallbackFactory implements FallbackFactory<EquipmentServiceClient> {

    @Override
    public EquipmentServiceClient create(Throwable throwable) {
        log.error("fallback; reason was:", throwable);
        return new EquipmentServiceClient() {
            @Override
            public RespVO<EquipmentService> selectByPrimaryKey(Long id) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "equipment服务不可用");
            }

            @Override
            public RespVO<RespDataVO<EquipmentService>> queryByCondition(EquipmentService equipmentService) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "equipment服务不可用");
            }
        };
    }
}
