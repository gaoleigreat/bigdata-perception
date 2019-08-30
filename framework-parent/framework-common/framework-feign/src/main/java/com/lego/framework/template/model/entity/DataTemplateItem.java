package com.lego.framework.template.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "模板项")
public class DataTemplateItem extends TemplateItem {

    private static final long serialVersionUID = -5330339159054173751L;

    @ApiModelProperty("子数据项")
    private List<DataTemplateItem> items;

    @ApiModelProperty("字段来源，1：自有，2：省平台，3：国家平台，4：省平台+国平台")
    private String source;

    @ApiModelProperty("国家平台字段")
    private String gField;

    @ApiModelProperty("省平台字段")
    private String sField;

    private String enumName;;

    @ApiModelProperty("字段绝对路径")
    private String absoluteField;

    @ApiModelProperty("行政区划请求路径")
    private String regionUrl;

    private Integer isSearch;

    private List<Long> ids;

    public List<DataTemplateItem> getItems() {
        return items;
    }

    public void setItems(List<DataTemplateItem> items) {
        this.items = items;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getgField() {
        return gField;
    }

    public void setgField(String gField) {
        this.gField = gField;
    }

    public String getsField() {
        return sField;
    }

    public void setsField(String sField) {
        this.sField = sField;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public String getAbsoluteField() {
        return absoluteField;
    }

    public void setAbsoluteField(String absoluteField) {
        this.absoluteField = absoluteField;
    }

    public String getRegionUrl() {
        return regionUrl;
    }

    public void setRegionUrl(String regionUrl) {
        this.regionUrl = regionUrl;
    }

    public String getEnumName() {
        return enumName;
    }

    public void setEnumName(String enumName) {
        this.enumName = enumName;
    }

    public Integer getIsSearch() {
        return isSearch;
    }

    public void setIsSearch(Integer isSearch) {
        this.isSearch = isSearch;
    }
}
