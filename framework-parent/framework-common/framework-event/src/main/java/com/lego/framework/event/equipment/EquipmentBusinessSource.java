package com.lego.framework.event.equipment;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Repository;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/29 9:53
 * @desc :
 */
@Repository
public interface EquipmentBusinessSource {

    String EQUIPMENT_BUSINESS_CREATE = "output_equipment_business_created";


    /**
     * 设备业务创建
     *
     * @return
     */
    @Output(EQUIPMENT_BUSINESS_CREATE)
    MessageChannel equipmentBusinessCreate();
}
