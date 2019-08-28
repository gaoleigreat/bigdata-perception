package com.lego.framework.system.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author yanglf
 * @description
 * @since 2019/8/26
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu {
    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("项目角色ID")
    private Long projectRoleId;
    @ApiModelProperty("url")
    private String menuUrl;
    @ApiModelProperty("菜单名称")
    private String menuTitle;
    @ApiModelProperty("父级ID")
    private Long parentId;
    @ApiModelProperty("创建时间")
    private Date creationDate;
    @ApiModelProperty("创建人")
    private Long createdBy;

    public void setCreateInfo(Long userId) {
        this.creationDate = new Date();
        this.createdBy = userId;
    }
}
