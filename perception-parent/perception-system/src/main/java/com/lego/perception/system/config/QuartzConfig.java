package com.lego.perception.system.config;
import org.quartz.simpl.SimpleJobFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class QuartzConfig {

    @Bean
    @ConditionalOnBean(name={"dataSource"})
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("quartz.properties"));

        propertiesFactoryBean.afterPropertiesSet();

        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        Properties properties = propertiesFactoryBean.getObject();

        factory.setOverwriteExistingJobs(true);
        factory.setAutoStartup(true);
        factory.setQuartzProperties(properties);
        factory.setJobFactory(new SimpleJobFactory());
        return factory;
    }
}
