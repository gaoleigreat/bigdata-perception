package com.lego.framework.zuul.config;

import com.lego.framework.zuul.predicate.GrayAwarePredicate;
import com.lego.framework.zuul.predicate.GrayAwareRule;
import com.netflix.loadbalancer.IRule;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author yanglf
 * @description
 * @since 2018/12/29
 **/
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {


    @Bean("grayAwareRule")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Profile(value = "local")
    public IRule grayAwareRule(StringRedisTemplate stringRedisTemplate) {
        return new GrayAwareRule(new GrayAwarePredicate(stringRedisTemplate));
    }


    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

}
