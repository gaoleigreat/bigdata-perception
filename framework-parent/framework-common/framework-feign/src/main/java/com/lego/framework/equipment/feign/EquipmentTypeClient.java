package com.lego.framework.equipment.feign;

import com.framework.common.consts.RespConsts;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.equipment.model.entity.EquipmentType;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
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
@FeignClient(value = "equipment-service", path = "/equipmentType", fallbackFactory = EquipmentTypeClientFallbackFactory.class)
public interface EquipmentTypeClient {

    /**
     * @param id
     * @return
     */
    @RequestMapping(value = "/select_by_id", method = RequestMethod.GET)
    RespVO<EquipmentType> selectByPrimaryKey(@RequestParam(value = "id") Long id);


}

@Component
@Slf4j
class EquipmentTypeClientFallbackFactory implements FallbackFactory<EquipmentTypeClient> {

    @Override
    public EquipmentTypeClient create(Throwable throwable) {
        log.error("fallback; reason was:", throwable);
        return id -> RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "equipment服务不可用");
    }
}
