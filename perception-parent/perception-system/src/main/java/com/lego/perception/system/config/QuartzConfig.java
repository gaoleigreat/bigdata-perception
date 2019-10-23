package com.lego.perception.system.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.quartz.simpl.SimpleJobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@Slf4j
public class QuartzConfig {

    @Autowired
    private Environment environment;


    @Bean("quartzDataSource")
    public DataSource quartzDataSource(Environment env) {
        DruidDataSource ds = new DruidDataSource();
        ds.setUrl(env.getProperty("define.schedule_job.datasource.url"));
        ds.setUsername(env.getProperty("define.schedule_job.datasource.username"));
        ds.setPassword(env.getProperty("define.schedule_job.datasource.password"));
        ds.setDriverClassName(env.getProperty("define.schedule_job.datasource.driver"));
        // 初始化时建立连接个数
        ds.setInitialSize(Integer.valueOf(env.getProperty("define.schedule_job.datasource.initsize")));
        // 连接池最大连接数
        ds.setMaxActive(Integer.valueOf(env.getProperty("define.schedule_job.datasource.maxActive")));
        // 最小连接池数
        ds.setMinIdle(Integer.valueOf(env.getProperty("define.schedule_job.datasource.minIdle")));
        // 获取连接时最大等待时间，单位：毫秒。配置了maxwait之后，缺省启用公平锁，并发效率会有所下降
        ds.setMaxWait(Long.valueOf(env.getProperty("define.schedule_job.datasource.maxWait")));
        // 是否缓存prepareStatement，也就是PScache，对支持游标的数据库性能提升巨大，如oracle，mysql5.5以下不支持游标
        ds.setPoolPreparedStatements(Boolean.valueOf(env.getProperty("define.schedule_job.datasource.poolPreparedStatements")));
        // 要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true。
        // 在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置大一些，比如说100
        ds.setMaxOpenPreparedStatements(
                Integer.valueOf(env.getProperty("define.schedule_job.datasource.maxOpenPreparedStatements")));
        // 用来检测连接是否有效的sql
        ds.setValidationQuery(env.getProperty("define.schedule_job.datasource.validationQuery"));
        // 申请连接时检测连接是否有效，会影响性能
        ds.setTestOnBorrow(Boolean.valueOf(env.getProperty("define.schedule_job.datasource.testOnBorrow")));
        // 归还连接时检测连接是否有效，会影响性能
        ds.setTestOnReturn(Boolean.valueOf(env.getProperty("define.schedule_job.datasource.testOnReturn")));
        // 测试空闲连接是否有效，不影响性能
        ds.setTestWhileIdle(Boolean.valueOf(env.getProperty("define.schedule_job.datasource.testWhileIdle")));
        // 1,Destroy线程检测连接的间隔时间 2,testWhileIdle的判断依据
        ds.setTimeBetweenEvictionRunsMillis(
                Long.valueOf(env.getProperty("define.schedule_job.datasource.timeBetweenEvictionRunsMillis")));
        try {
            // 通过别名的方式配置扩展插件，常用插件有
            // 监控统计用的：filter:stat
            // 日志用的：filter:log4j
            // 预防sql注入用的：filter:wall
            ds.setFilters(env.getProperty("define.schedule_job.datasource.filters"));
        } catch (SQLException e) {
            log.error("init datasource error |", e);
        }

        // 类型是List<com.alibaba.druid.filter.Filter>，如果同时配置了filters和proxyFilters，是组合关系，并非替换关系
        //ds.setProxyFilters();
        DruidConnectionProvider.setTemp(ds);
        return ds;
    }


    @Bean
    @ConditionalOnBean(name = {"quartzDataSource"})
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
