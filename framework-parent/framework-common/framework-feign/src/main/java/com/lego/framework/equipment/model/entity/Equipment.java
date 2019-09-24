package com.lego.framework.equipment.model.entity;

import com.lego.framework.config.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/24 10:41
 * @desc :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Equipment extends BaseModel {
    private Long id;
    private String name;



}
