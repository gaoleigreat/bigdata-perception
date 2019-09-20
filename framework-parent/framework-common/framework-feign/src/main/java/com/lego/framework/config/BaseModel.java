package com.lego.framework.config;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseModel {

    @ApiModelProperty("id")
    @TableId
    private Long id;

    @ApiModelProperty("创建时间")
    private Date creationDate;

    @ApiModelProperty("创建人id")
    private Long createdBy;

    @ApiModelProperty("更新时间")
    private Date lastUpdateDate;

    @ApiModelProperty("更新人id")
    private Long lastUpdatedBy;

    @TableField(exist = false)
    private String lastUpdateUser;

    @TableField(exist = false)
    private String createUser;

    public void setCreateInfo() {
        Date currentDate = new Date();
        this.creationDate = currentDate;
        this.lastUpdateDate = currentDate;
    }

    public void setUpdateInfo() {
        this.lastUpdateDate = new Date();

    }
}
