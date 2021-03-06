package com.lego.framework.base.interceptor;
import com.framework.common.consts.RespConsts;
import com.framework.common.sdto.CurrentVo;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.base.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@Slf4j
public class SecurityInterceptor implements MethodInterceptor {

    @Value("${spring.application.name}")
    private String scope;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Class<?> c = invocation.getMethod().getDeclaringClass();
        Resource resource = c.getAnnotation(Resource.class);
        Operation operation = invocation.getMethod().getAnnotation(Operation.class);

        if (null != resource && null != operation) {
            CurrentVo currentVo = RequestContext.getCurrent();
            if(null != currentVo &&  !currentVo.isPermissionChecked()){
                String permission = scope + "$" + resource.value() + "$" + operation.value();
                if(null == currentVo.getPermissions() || !currentVo.getPermissions().contains(permission)){
                    Class returnType = invocation.getMethod().getReturnType();
                    if("java.util.Map".equals(returnType.getName())){
                        return RespVOBuilder.failure(RespConsts.FAIL_NOPRESSION_CODE,RespConsts.FAIL_NOPRESSION_MSG);
                    }else if("com.survey.lib.common.vo.RespVO".equals(returnType.getName())){
                        return RespVOBuilder.failure(RespConsts.FAIL_NOPRESSION_CODE,RespConsts.FAIL_NOPRESSION_MSG);
                    }else if("java.util.List".equals(returnType.getName())){
                        return Collections.EMPTY_LIST;
                    }
                    return null;
                }
                currentVo.setPermissionChecked(true);
            }
        }

        return invocation.proceed();
    }

}
