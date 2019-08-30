package com.lego.perception.template;

import com.lego.framework.event.log.LogSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * @author yanglf
 * @description
 * @since 2019/8/22
 **/
@EnableFeignClients(basePackages = "com.lego")
@EnableEurekaClient
@SpringBootApplication(scanBasePackages = {"com.lego", "com.framework"})
@EnableBinding(LogSource.class)
public class TemplateServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TemplateServiceApplication.class, args);
    }

}