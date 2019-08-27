package com.lego.framework.system.model.entity;

import com.lego.framework.config.BaseModel;
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
public class Project {
    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("地址")
    private String address;
    @ApiModelProperty("开始时间")
    private Date startTime;
    @ApiModelProperty("结束时间")
    private Date endTime;
    @ApiModelProperty("负责人")
    private Long masterId;
    @ApiModelProperty("创建时间")
    private Date creationDate;
    @ApiModelProperty("创建人")
    private Long createdBy;
    @ApiModelProperty("最后更新时间")
    private Date lastUpdateDate;
    @ApiModelProperty("最后更新人")
    private Long lastUpdatedBy;
    @ApiModelProperty("是否删除(1-否;2-是)")
    private Integer deleteFlag;

    public void setCreateInfo() {
        Date currentTime = new Date();
        this.creationDate = currentTime;
        this.lastUpdateDate = currentTime;
    }

    public void setUpdateInfo() {
        this.lastUpdateDate = new Date();
    }
}
