package com.lego.framework.template.model.entity;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class FormTemplateItem extends TemplateItem {

    private static final long serialVersionUID = 8231322939186855041L;

    @ApiModelProperty("数据项是否必填，1：必填，2：必填但是可以提交，3：不必填")
    private Integer isRequired;

    @ApiModelProperty("权限")
    private String permission;

    @ApiModelProperty("校验正则")
    private String validStr;

    @ApiModelProperty("是否搜索字段，0：是，1：否")
    private Integer isSearch;

    @ApiModelProperty("数据项字段")
    private String dataField;

    private String dataFieldName;

    private String enumName;

    @ApiModelProperty("行政区划url")
    private String regionUrl;

    @ApiModelProperty("子数据项")
    private List<FormTemplateItem> items;

    public Integer getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Integer isRequired) {
        this.isRequired = isRequired;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getValidStr() {
        return validStr;
    }

    public void setValidStr(String validStr) {
        this.validStr = validStr;
    }

    public Integer getIsSearch() {
        return isSearch;
    }

    public void setIsSearch(Integer isSearch) {
        this.isSearch = isSearch;
    }

    public List<FormTemplateItem> getItems() {
        return items;
    }

    public void setItems(List<FormTemplateItem> items) {
        this.items = items;
    }

    public String getDataField() {
        return dataField;
    }

    public void setDataField(String dataField) {
        this.dataField = dataField;
    }

    public String getRegionUrl() {
        return regionUrl;
    }

    public void setRegionUrl(String regionUrl) {
        this.regionUrl = regionUrl;
    }

    public String getDataFieldName() {
        return dataFieldName;
    }

    public void setDataFieldName(String dataFieldName) {
        this.dataFieldName = dataFieldName;
    }

    public String getEnumName() {
        return enumName;
    }

    public void setEnumName(String enumName) {
        this.enumName = enumName;
    }
}
