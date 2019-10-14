package com.lego.perception.file;

import com.lego.framework.event.log.LogSource;
import com.lego.framework.event.template.TemplateSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableFeignClients("com.lego")
@EnableEurekaClient
@SpringBootApplication(scanBasePackages = {"com.lego", "com.framework"})
@EnableBinding(value = {LogSource.class, TemplateSource.class})
public class FileServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(FileServerApplication.class, args);
    }
}
