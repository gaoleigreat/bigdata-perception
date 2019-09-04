package com.lego.framework.system.model.entity;

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
 * @date : 2019/9/3 10:50
 * @desc :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "tpl_system")
public class TbSystem {
    @ApiModelProperty("系统id")
    private Long id;
    @ApiModelProperty("系统编码")
    private String code;
    @ApiModelProperty("系统名称")
    private String name;
    @ApiModelProperty("主机名")
    private String host;
    @ApiModelProperty("端口")
    private Integer port;
    @ApiModelProperty("描述")
    private String desc;
    @ApiModelProperty("创建时间")
    private Date creationDate;
    @ApiModelProperty("创建人")
    private Long createdBy;

}
