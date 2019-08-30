package com.lego.framework.template.model.entity;
import com.lego.framework.config.BaseModel;
import io.swagger.annotations.ApiModelProperty;

public class TemplateItem extends BaseModel {

    private static final long serialVersionUID = -4268123648236736403L;

    @ApiModelProperty("数据项父ID")
    private Long parentId;

    @ApiModelProperty("模板ID")
    private Long templateId;

    @ApiModelProperty("数据项名称")
    private String title;

    @ApiModelProperty("数据项字段")
    private String field;

    @ApiModelProperty("数据项类型")
    private Integer category;

    @ApiModelProperty("数据项默认值")
    private String defaultValue;

    @ApiModelProperty("数据项排序序号")
    private Integer sort;

    @ApiModelProperty("枚举id，当数据项类型未枚举时有效")
    private Long enumId;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("状态，1：启用，2：删除")
    private Integer status;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getEnumId() {
        return enumId;
    }

    public void setEnumId(Long enumId) {
        this.enumId = enumId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
