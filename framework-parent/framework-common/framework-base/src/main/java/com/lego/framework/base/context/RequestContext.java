package com.lego.framework.base.context;

import com.framework.common.sdto.CurrentVo;
import com.lego.framework.base.service.IAuthCheckService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

/**
 * @author yanglf
 */
@Slf4j
public class RequestContext {

    private static ThreadLocal<CurrentVo> current = new ThreadLocal<CurrentVo>();

    private static IAuthCheckService authCheckService;


    public static void setCurrnet(CurrentVo cur) {
        current.set(cur);
    }

    public static CurrentVo getCurrent() {
        CurrentVo currentVo = current.get();
        try {
            if (currentVo != null && !currentVo.getIsInit() && null != currentVo.getToken()) {
                String token = currentVo.getToken();
                String deviceType = currentVo.getDeviceType();
                if (null == authCheckService) {
                    authCheckService = (IAuthCheckService) AppContext.getBean("authCheckServiceImpl");
                }
                CurrentVo data = authCheckService.getData(token, deviceType);
                if (data != null) {
                    currentVo.setIsInit(true);
                    data.setIsInit(true);
                    return data;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currentVo;
    }

    public static CurrentVo getBaseCurrent() {
        return current.get();
    }

    public static CurrentVo initCurrent() {
        current.remove();
        current.set(new CurrentVo());

        return current.get();
    }

    public static void remove() {
        current.remove();
    }

    public static ApplicationContext getApplicationContext() {

        return AppContext.getContext();
    }

    public static WebApplicationContext getWebApplicationContext() {

        return ContextLoader.getCurrentWebApplicationContext();
    }

    public static ServletContext getServletContext() {
        WebApplicationContext wac = getWebApplicationContext();
        if (null == wac) {
            return null;
        }
        return wac.getServletContext();
    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        authCheckService = (IAuthCheckService) AppContext.getContext().getBean("authCheckService");
//    }
}
