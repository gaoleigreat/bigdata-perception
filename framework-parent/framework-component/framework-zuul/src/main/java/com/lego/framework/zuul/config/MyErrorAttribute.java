package com.lego.framework.zuul.config;

import com.framework.common.consts.RespConsts;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/19 18:14
 * @desc :
 */
@Component
public class MyErrorAttribute extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> result = super.getErrorAttributes(webRequest, includeStackTrace);
        result.put("code", RespConsts.FAIL_RESULT_CODE);
        return super.getErrorAttributes(webRequest, includeStackTrace);
    }
}
