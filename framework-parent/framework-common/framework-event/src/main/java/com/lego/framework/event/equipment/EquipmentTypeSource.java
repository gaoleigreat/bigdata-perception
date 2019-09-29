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
public interface EquipmentTypeSource {

    String EQUIPMENT_TYPE_CREATE = "output_equipment_type_created";


    /**
     * 设备类型创建
     *
     * @return
     */
    @Output(EQUIPMENT_TYPE_CREATE)
    MessageChannel equipmentTypeCreate();
}
