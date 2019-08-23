package com.lego.framework.system.model;

import com.lego.framework.config.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePermission extends BaseModel {

    @ApiModelProperty(value="角色名称ID 必填", required = false)
    private Long roleId;

    @ApiModelProperty(value="权限点ID 必填", required = false)
    private Long permissionId;

}
