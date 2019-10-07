package com.lego.framework.equipment.feign;

import com.framework.common.consts.RespConsts;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/10/7 10:25
 * @desc :
 */
@FeignClient(value = "equipment-service", path = "/equipmentMaintenanceDoc", fallbackFactory = EquipmentMaintenanceDocClientFallbackFactory.class)
public interface EquipmentMaintenanceDocClient {

    @RequestMapping(value = "/upload", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
    RespVO upload(@RequestParam(value = "multipartFile") MultipartFile multipartFile);

}

@Component
@Slf4j
class EquipmentMaintenanceDocClientFallbackFactory implements FallbackFactory<EquipmentMaintenanceDocClient> {

    @Override
    public EquipmentMaintenanceDocClient create(Throwable throwable) {
        return multipartFile -> {
            log.error("fallback; reason was:", throwable);
            return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "equipment服务不可用");
        };
    }
}
