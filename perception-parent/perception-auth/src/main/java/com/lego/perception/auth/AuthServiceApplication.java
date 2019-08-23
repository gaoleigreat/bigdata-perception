package com.lego.perception.auth;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
/**
 * @author yanglf
 * @descript
 * @since 2018/12/20
 **/
@EnableConfigurationProperties
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.lego")
@SpringBootApplication(scanBasePackages = {"com.lego"})
public class AuthServiceApplication {

    public static void main(String []args){
        SpringApplication.run(AuthServiceApplication.class,args);
    }

}
