package com.lego.framework.template.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/3 11:49
 * @desc :
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tpl_business_template")
public class BusinessTemplate {
    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("表名")
    private String tableName;
    @ApiModelProperty("业务id")
    private Long businessId;
    @ApiModelProperty("系统id")
    private Long sysId;
    @ApiModelProperty("模板id")
    private Long templateId;
    @ApiModelProperty("模板类型(0-表单模板;1-数据模板)")
    private Integer templateType;
    @ApiModelProperty("数据源类型0-MySql;1-Mongo")
    private Integer sourceType;
    @ApiModelProperty("创建时间")
    private Date creationDate;
    @ApiModelProperty("创建人")
    private Long createdBy;
}
