package com.lego.framework.data.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/21 18:05
 * @desc :
 */
@Data
public class BaseData {

    @ApiModelProperty("共享时间")
    private Date sharedtime;
    /**
     * 共享类型：文件夹类型、数据库类型
     */
    @ApiModelProperty(" 共享类型：文件夹类型、数据库类型")
    private String type;
    /**
     * 共享数据名称
     */
    @ApiModelProperty("共享数据名称")
    private String name;
    /**
     * 如果共享类型是HDFS类型，schema就是根目录下的一个文件夹； 如果是数据库类型，schema就是MYSQL里面一个database
     */
    @TableField(value = "`schema`")
    private String schema;
    /**
     * 共享数据描述
     */
    @ApiModelProperty("共享数据描述")
    @TableField(value = "`desc`")
    private String desc;
    /**
     * HDFS、MYSQL
     */
    @ApiModelProperty("数据库类型(HDFS、MYSQL、MONGO)")
    private String serverType;
    /**
     *
     */
    @ApiModelProperty("数据源IP")
    private String serverIp;
    /**
     *
     */
    @ApiModelProperty("数据源端口")
    private String serverPort;
    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;
    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String pw;

    @ApiModelProperty("业务感知数据id")
    private Long fileId;
}
