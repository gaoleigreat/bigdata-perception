package com.lego.framework.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author yanglf
 * @description
 * @since 2019/8/26
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tpl_user_role_project")
public class UserRoleProject {
    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("用户ID")
    private Long userId;
    @ApiModelProperty("角色ID")
    private Long roleId;
    @ApiModelProperty("项目ID")
    private Long projectId;
    @ApiModelProperty("'状态，1：启用，2：锁定'")
    private Integer status;
    @ApiModelProperty("创建时间")
    private Date creationDate;
    @ApiModelProperty("创建人")
    private Long createdBy;
    @ApiModelProperty("最后更新时间")
    private Date lastUpdateDate;
    @ApiModelProperty("最后更新人")
    private Long lastUpdatedBy;
    @TableField(exist = false)
    private List<Long> userIds;

    public void setCreateInfo() {
        Date currentTime = new Date();
        this.creationDate = currentTime;
        this.lastUpdateDate = currentTime;
    }
}
