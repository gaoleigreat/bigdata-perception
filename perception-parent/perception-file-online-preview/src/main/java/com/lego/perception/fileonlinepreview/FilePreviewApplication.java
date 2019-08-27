package com.lego.perception.fileonlinepreview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Properties;

@SpringBootApplication
@EnableScheduling
@ComponentScan(value = "com.lego.perception.fileonlinepreview.*")
@EnableDiscoveryClient
@EnableEurekaClient

public class FilePreviewApplication {
	public static void main(String[] args) {
        Properties properties = System.getProperties();
        System.out.println(properties.get("user.dir"));
        SpringApplication.run(FilePreviewApplication.class, args);
	}
}
