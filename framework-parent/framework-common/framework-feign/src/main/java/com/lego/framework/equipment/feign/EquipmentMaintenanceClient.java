package com.lego.framework.equipment.feign;

import com.framework.common.consts.RespConsts;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.equipment.model.entity.EquipmentMaintenance;
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
@FeignClient(value = "equipment-service", path = "/equipmentservice", fallbackFactory = EquipmentMaintenanceClientFallbackFactory.class)
public interface EquipmentMaintenanceClient {

    /**
     * 查询设备文档轨迹equipmentMaintenanceclient
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/select_by_id", method = RequestMethod.GET)
    RespVO<EquipmentMaintenance> selectByPrimaryKey(@RequestParam(value = "id") Long id);


    /**
     * 查询设备文档轨迹
     *
     * @param equipmentMaintenance
     * @return
     */
    @RequestMapping(value = "/query_list", method = RequestMethod.POST)
    RespVO<RespDataVO<EquipmentMaintenance>> queryByCondition(@RequestBody EquipmentMaintenance equipmentMaintenance);

}

@Component
@Slf4j
class EquipmentMaintenanceClientFallbackFactory implements FallbackFactory<EquipmentMaintenanceClient> {

    @Override
    public EquipmentMaintenanceClient create(Throwable throwable) {
        log.error("fallback; reason was:", throwable);
        return new EquipmentMaintenanceClient() {
            @Override
            public RespVO<EquipmentMaintenance> selectByPrimaryKey(Long id) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "equipment服务不可用");
            }

            @Override
            public RespVO<RespDataVO<EquipmentMaintenance>> queryByCondition(EquipmentMaintenance equipmentService) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "equipment服务不可用");
            }
        };
    }
}
