package com.lego.framework.permission.init;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.system.feign.PermissionClient;
import com.lego.framework.system.model.entity.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class ResourcesInit implements ApplicationRunner {

    @Value("${spring.application.name}")
    private String serviceName;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private PermissionClient permissionClient;

    private void initUserPermission() {
        //1.扫描权限点
        List<Permission> permissions = getPermission();
        //2.权限点插入数据库
        permissionClient.save(serviceName, permissions);
    }


    private List<Permission> getPermission() {
        List<Permission> permissionList = new ArrayList<>();
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory();
        String basePath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "/com/lego/perception/**/controller/*Controller.class";

        try {

            org.springframework.core.io.Resource[] resources = resourcePatternResolver.getResources(basePath);
            TypeFilter typeFilter = new AnnotationTypeFilter(Resource.class);

            for (org.springframework.core.io.Resource resource : resources) {

                MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                if (typeFilter.match(metadataReader, metadataReaderFactory)) {
                    String rId = "";
                    String rName = "";
                    AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
                    Map<String, Object> classMap = annotationMetadata.getAnnotationAttributes(Resource.class.getName());
                    for (Map.Entry<String, Object> entry : classMap.entrySet()) {
                        if ("value".endsWith(entry.getKey())) {
                            rId = (String) entry.getValue();
                        }
                        if ("desc".endsWith(entry.getKey())) {
                            rName = (String) entry.getValue();
                        }
                    }

                    Set<MethodMetadata> set = annotationMetadata.getAnnotatedMethods(Operation.class.getName());
                    for (MethodMetadata methodMetadata : set) {
                        Permission permission = new Permission();
                        permission.setRId(rId);
                        permission.setRName(rName);
                        Map<String, Object> methodMap = methodMetadata.getAnnotationAttributes(Operation.class.getName());
                        for (Map.Entry<String, Object> entry : methodMap.entrySet()) {
                            if ("value".endsWith(entry.getKey())) {
                                permission.setPrId((String) entry.getValue());
                            }
                            if ("desc".endsWith(entry.getKey())) {
                                permission.setPrName((String) entry.getValue());
                            }
                        }
                        permissionList.add(permission);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return permissionList;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 初始化服务权限
        //initServicePermission();

        //初始化用户权限
        initUserPermission();
    }
}
