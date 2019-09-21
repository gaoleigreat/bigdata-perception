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
public class ShareData {
    @ApiModelProperty("共享时间")
    private Date sharedtime;
    /**
     * 共享类型：文件夹类型、数据库类型
     */
    private String type;
    /**
     * 共享数据名称
     */
    private String name;
    /**
     * 如果共享类型是HDFS类型，schema就是根目录下的一个文件夹； 如果是数据库类型，schema就是MYSQL里面一个database
     */
    @TableField(value = "`schema`")
    private String schema;
    /**
     * 共享数据描述
     */
    @TableField(value = "`desc`")
    private String desc;
    /**
     * HDFS、MYSQL
     */
    private String serverType;
    /**
     *
     */
    private String serverIp;
    /**
     *
     */
    private String serverPort;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String pw;
}
