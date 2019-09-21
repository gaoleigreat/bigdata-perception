package com.lego.perception.data.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/21 10:57
 * @desc :
 */
@Component
@ConfigurationProperties(prefix = "define.share.database.hdfs")
@Data
public class HdfsProperties {
    private String schema;
    private String serverIp;
    private String serverPort;
    private String username;
    private String pw;
    private String serverType;
}
