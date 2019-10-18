package com.lego.framework.zuul.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yanglf
 * @description
 * @since 2019/3/6
 **/
@Component
@EnableSwagger2
public class SwaggerConfig {

    @Autowired
    private ZuulProperties properties;

    @Bean
    @Primary
    public SwaggerResourcesProvider swaggerResourcesProvider() {
        return () -> {
            List<SwaggerResource> resources = new ArrayList<>();
           /* properties.getRoutes().values()
                    .forEach(route -> resources
                            .add(createResource(
                                    route.getId(),route.getPath().replace("/**",""))));*/

            resources.add(createResource("权限服务", "auth-service"));
            resources.add(createResource("文件服务", "file-service"));
            resources.add(createResource("系统服务", "system-service"));
            resources.add(createResource("日志服务", "log-service"));
            resources.add(createResource("用户服务", "user-service"));
            resources.add(createResource("模板服务", "template-service"));
            resources.add(createResource("业务服务", "business-service"));
            resources.add(createResource("数据服务", "data-service"));
            resources.add(createResource("设备服务", "equipment-service"));
            resources.add(createResource("知识库管理", "knowledge-service"));
            resources.add(createResource("统计服务", "statistics-service"));

            return resources;
        };
    }

    private SwaggerResource createResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation("/" + location + "/v2/api-docs");
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }


}
