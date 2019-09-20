package com.lego.framework.data.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @author itar
 * @mail wuhandzy@gmail.com
 * @date 2019-09-19 12:36:38
 * @since jdk1.8
 */
@TableName(value = "datasharedtable")
public class Datasharedtable implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 共享时间戳
     */
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

    /**
     * 共享时间戳
     */
    public void setSharedtime(Date sharedtime) {
        this.sharedtime = sharedtime;
    }

    /**
     * 共享时间戳
     */
    public Date getSharedtime() {
        return sharedtime;
    }

    /**
     * 共享类型：文件夹类型、数据库类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 共享类型：文件夹类型、数据库类型
     */
    public String getType() {
        return type;
    }

    /**
     * 共享数据名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 共享数据名称
     */
    public String getName() {
        return name;
    }

    /**
     * 如果共享类型是HDFS类型，schema就是根目录下的一个文件夹； 如果是数据库类型，schema就是MYSQL里面一个database
     */
    public void setSchema(String schema) {
        this.schema = schema;
    }

    /**
     * 如果共享类型是HDFS类型，schema就是根目录下的一个文件夹； 如果是数据库类型，schema就是MYSQL里面一个database
     */
    public String getSchema() {
        return schema;
    }

    /**
     * 共享数据描述
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 共享数据描述
     */
    public String getDesc() {
        return desc;
    }

    /**
     * HDFS、MYSQL
     */
    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    /**
     * HDFS、MYSQL
     */
    public String getServerType() {
        return serverType;
    }

    /**
     *
     */
    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    /**
     *
     */
    public String getServerIp() {
        return serverIp;
    }

    /**
     *
     */
    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    /**
     *
     */
    public String getServerPort() {
        return serverPort;
    }

    /**
     * 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 密码
     */
    public void setPw(String pw) {
        this.pw = pw;
    }

    /**
     * 密码
     */
    public String getPw() {
        return pw;
    }
}
