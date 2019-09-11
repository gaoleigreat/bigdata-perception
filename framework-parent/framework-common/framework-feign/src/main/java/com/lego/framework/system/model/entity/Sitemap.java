package com.lego.framework.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lego.framework.config.BaseModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@TableName(value = "tpl_sitemap_t")
public class Sitemap extends BaseModel {

    @ApiModelProperty(value = "父ID 必填")
    private Long parentId;

    @ApiModelProperty(value = "子系统编码")
    private String subSystem;

    @ApiModelProperty(value = "名称 必填")
    private String name;

    @ApiModelProperty(value = "点击url type=2时必填")
    private String url;

    @ApiModelProperty(value = "类型，1：目录，2：页面 必填")
    private Integer type;

    @ApiModelProperty(value = "权限")
    private String permission;

    @ApiModelProperty(value = "排序 必填")
    private Integer sort;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "应用范围")
    private String scope;

    @ApiModelProperty(value = "状态，1：启用，2：锁定 必填")
    private String status;

    @TableField(exist = false)
    private List<Sitemap> children;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Sitemap> getChildren() {
        return children;
    }

    public void setChildren(List<Sitemap> children) {
        this.children = children;
    }

    public String getSubSystem() {
        return subSystem;
    }

    public void setSubSystem(String subSystem) {
        this.subSystem = subSystem;
    }
}
