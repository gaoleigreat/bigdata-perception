package com.lego.framework.event.equipment;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Repository;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/29 9:46
 * @desc :
 */
@Repository
public interface EquipmentBusinessSink {

    String EQUIPMENT_BUSINESS_CREATE = "input_equipment_business_created";


    /**
     * 设备业务创建
     *
     * @return
     */
    @Input(EQUIPMENT_BUSINESS_CREATE)
    SubscribableChannel equipmentBusinessCreate();

}
