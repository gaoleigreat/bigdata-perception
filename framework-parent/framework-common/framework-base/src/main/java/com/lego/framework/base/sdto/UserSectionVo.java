package com.lego.framework.base.sdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yanglf
 * @description
 * @since 2019/7/9
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSectionVo {
    // ID
    private String id;
    // 标段名称
    private String name;
    // code
    private String code;
    // 角色
    private String role;
}
