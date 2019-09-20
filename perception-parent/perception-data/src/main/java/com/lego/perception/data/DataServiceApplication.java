package com.lego.perception.data;

import com.lego.framework.event.log.LogSource;
import com.lego.framework.event.template.TemplateSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/3 18:01
 * @desc :
 */
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.lego")
@SpringBootApplication(scanBasePackages = {"com.lego", "com.framework"})
@EnableBinding(value = {LogSource.class, TemplateSource.class})
public class DataServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataServiceApplication.class, args);
    }

}
