package com.lego.framework.system.model.entity;
import com.lego.framework.config.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRole extends BaseModel {
    @ApiModelProperty(value="用户ID 必填", required = false)
    private Long userId;
    @ApiModelProperty(value="角色ID 必填", required = false)
    private Long roleId;
    @ApiModelProperty(value="状态，1：启用，2：锁定 必填", required = false)
    private Integer status;
    private List<Long> userIds;
}
