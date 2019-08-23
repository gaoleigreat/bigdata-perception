package com.lego.framework.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * @author yanglf
 * @description
 * @since 2019/7/3
 **/
@Component
@Slf4j
public class RequestParameterFilter extends ZuulFilter {


    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String method = request.getMethod();
        request.getParameterMap();
        if (method.equals(HttpMethod.GET.name())) {
            // 获取请求参数name
            try {
                setGetMethodParameter(ctx, request);
            } catch (Exception e) {
                log.error("handle request parameter error");
            }
        } else if (method.equals(HttpMethod.POST.name()) || method.equals(HttpMethod.PUT.name())) {
            try {
                setPostMethodParameter(ctx, request);
            } catch (Exception e) {
                log.error("handle request parameter error");
            }
        }
        return null;
    }

    private void setPostMethodParameter(RequestContext ctx, HttpServletRequest request) throws Exception {
        if (!ctx.isChunkedRequestBody()) {
            ServletInputStream inp = ctx.getRequest().getInputStream();
            InputStream in = request.getInputStream();
            String body;
            if (inp != null) {
                body = IOUtils.toString(inp, Charset.forName("UTF-8"));
                byte[] reqBodyBytes = body.trim().getBytes();
                ctx.setRequest(new HttpServletRequestWrapper(request) {

                    @Override
                    public ServletInputStream getInputStream() throws IOException {
                        return new ServletInputStreamWrapper(reqBodyBytes);
                    }

                    @Override
                    public int getContentLength() {
                        return reqBodyBytes.length;
                    }

                    @Override
                    public long getContentLengthLong() {
                        return reqBodyBytes.length;
                    }
                });
            }
        }
    }

    private void setGetMethodParameter(RequestContext ctx, HttpServletRequest request) throws Exception {
        Enumeration<String> parameterNames = request.getParameterNames();
        Map<String, List<String>> requestQueryParams = ctx.getRequestQueryParams();
        if (requestQueryParams == null) {
            requestQueryParams = new HashMap<>(16);
        }
        List<String> arrayList = new ArrayList<>();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            String parameterValue = request.getHeader(parameterName);
            log.info("request parameters:{}\n", parameterName + "=" + parameterValue);
            arrayList.add(parameterValue.trim() + "");
            requestQueryParams.put(parameterName, arrayList);
        }
        ctx.setRequestQueryParams(requestQueryParams);

    }
}
