package com.lego.perception.statistics;

import com.lego.framework.event.log.LogSource;
import com.lego.framework.event.template.TemplateSource;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author yanglf
 */
@EnableFeignClients(basePackages = "com.lego")
@EnableEurekaClient
@SpringBootApplication(scanBasePackages = {"com.lego", "com.framework"})
@EnableBinding({LogSource.class, TemplateSource.class})
public class StatisticsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StatisticsServiceApplication.class, args);
    }

}

