package com.lego.perception.user;

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
public class UserServiceApplication extends SpringBootServletInitializer implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }


    /**
     * 打war包的配置
     *
     * @param application
     *            参数
     * @return 覆盖configure(SpringApplicationBuilder)方法
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(Application.class);
    }

    /**
     * 1、 extends WebMvcConfigurationSupport 2、重写下面方法; setUseSuffixPatternMatch :
     * 设置是否是后缀模式匹配，如“/user”是否匹配/user.*，默认真即匹配； setUseTrailingSlashMatch :
     * 设置是否自动后缀路径模式匹配，如“/user”是否匹配“/user/”，默认真即匹配；
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer)
    {
        configurer.setUseSuffixPatternMatch(true).setUseTrailingSlashMatch(true);
    }

}

