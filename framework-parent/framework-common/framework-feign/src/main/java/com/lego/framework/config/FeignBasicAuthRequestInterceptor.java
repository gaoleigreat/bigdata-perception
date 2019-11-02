package com.lego.framework.config;

import com.framework.common.consts.HttpConsts;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/10/25 12:57
 * @desc :
 */
@Slf4j
@Component
public class FeignBasicAuthRequestInterceptor implements RequestInterceptor {

    @Value("${spring.application.name}")
    private String serviceName;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("fromService", serviceName);
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        if (attributes == null) {
            return;
        }
        HttpServletRequest request = attributes.getRequest();
        log.debug("FeignBasicAuthRequestInterceptor get cookie--------------------");
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                String value = cookie.getValue();
                log.debug(name + "========================" + value);
                if (name.equals(HttpConsts.HEADER_TOKEN)) {
                    requestTemplate.header(HttpConsts.HEADER_TOKEN, value);
                }
            }
        }
        requestTemplate.header("Connection", "close");
    }
}
