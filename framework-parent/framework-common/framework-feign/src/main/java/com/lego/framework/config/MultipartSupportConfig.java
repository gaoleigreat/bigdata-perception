package com.lego.framework.config;

/**
 * @auther xiaodao
 * @date 2019/9/18 16:47
 */

import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class MultipartSupportConfig {

    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;

    @Bean
    public Encoder feignFormEncoder() {
        return new FeignSpringFormEncoder(new SpringEncoder(messageConverters));
    }


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        //先获取到converter列表
        List<HttpMessageConverter<?>> converters = builder.build().getMessageConverters();
        for(HttpMessageConverter<?> converter : converters){
            //因为我们只想要jsonConverter支持对text/html的解析
            if(converter instanceof MappingJackson2HttpMessageConverter){
                try{
                    //先将原先支持的MediaType列表拷出
                    List<MediaType> mediaTypeList = new ArrayList<>(converter.getSupportedMediaTypes());
                    //加入对text/html的支持
                    mediaTypeList.add(MediaType.TEXT_HTML);
                    mediaTypeList.add(MediaType.TEXT_PLAIN);
                    //将已经加入了text/html的MediaType支持列表设置为其支持的媒体类型列表
                    ((MappingJackson2HttpMessageConverter) converter).setSupportedMediaTypes(mediaTypeList);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return builder.build();
    }


   /* @Bean
    public Decoder feignDecoder() {
        return new ResponseEntityDecoder(new SpringDecoder(feignHttpMessageConverter()));
    }*/

    private ObjectFactory<HttpMessageConverters> feignHttpMessageConverter() {
        final HttpMessageConverters httpMessageConverters = new HttpMessageConverters(new WxMappingJackson2HttpMessageConverter());
        return () -> httpMessageConverters;
    }


    @Bean
    @Profile(value = {"dev"})
    public Logger.Level log() {
        return Logger.Level.FULL;
    }

    @Bean
    public FeignBasicAuthRequestInterceptor feignBasicAuthRequestInterceptor() {
        return new FeignBasicAuthRequestInterceptor();
    }


}


class FeignBasicAuthRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Connection", "close");
    }
}
