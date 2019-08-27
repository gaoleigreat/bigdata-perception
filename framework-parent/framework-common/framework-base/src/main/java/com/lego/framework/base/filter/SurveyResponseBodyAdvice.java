package com.lego.framework.base.filter;

import com.alibaba.fastjson.JSONObject;
import com.framework.common.sdto.HeaderVo;
import com.lego.framework.base.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yanglf
 * @description
 * @since 2019/2/23
 **/
@ControllerAdvice
@Slf4j
public class SurveyResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        try {
            ServletServerHttpRequest ssReq = (ServletServerHttpRequest) request;
            ServletServerHttpResponse ssResp = (ServletServerHttpResponse) response;
            HttpServletRequest req = ssReq.getServletRequest();
            HttpServletResponse resp = ssResp.getServletResponse();
            String headers = HttpUtils.getHeaderVo(req);
            if (!StringUtils.isEmpty(headers)) {
                HeaderVo headerVo = JSONObject.parseObject(headers, HeaderVo.class);
                resp.setHeader("resp_time", System.currentTimeMillis() + "");
                resp.setHeader("token", headerVo.getToken());
                resp.setHeader("req_time", headerVo.getTime());
                resp.setHeader("req_url", req.getRequestURI());
            }
        } catch (Exception e) {
            log.error("error:{}", e);
        }
        return body;
    }
}
