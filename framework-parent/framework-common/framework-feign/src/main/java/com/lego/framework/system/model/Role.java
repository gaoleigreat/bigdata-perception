package com.lego.framework.system.model;
import com.lego.framework.config.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role extends BaseModel {

    @ApiModelProperty(value="角色名称 必填", required = false)
    private String roleName;

    @ApiModelProperty(value="角色状态，1：启用，2：锁定 必填", required = false)
    private Integer status;

    private String scope;

    private List<Long> roleIds;
}
