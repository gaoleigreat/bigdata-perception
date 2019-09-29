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
public interface EquipmentTypeSink {

    String EQUIPMENT_TYPE_CREATE = "input_equipment_type_created";


    /**
     * 设备类型创建
     *
     * @return
     */
    @Input(EQUIPMENT_TYPE_CREATE)
    SubscribableChannel equipmentTypeCreate();

}
