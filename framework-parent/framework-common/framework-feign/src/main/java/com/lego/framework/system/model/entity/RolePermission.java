package com.lego.framework.system.model.entity;

import com.lego.framework.config.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePermission extends BaseModel {

    @ApiModelProperty(value = "项目角色ID 必填", required = false)
    private Long projectRoleId;

    @ApiModelProperty(value = "权限点ID 必填", required = false)
    private Long permissionId;

}
