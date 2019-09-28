package com.lego.framework.zuul.filter;

import java.io.IOException;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/28 10:39
 * @desc :
 */
@Slf4j
@Component
public class RequestLogFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 3;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        try {
            RequestContext ctx = RequestContext.getCurrentContext();
            HttpServletRequest request = ctx.getRequest();
            ServletInputStream inputStream = request.getInputStream();
            String requestBody = StreamUtils.copyToString(inputStream, Charset.forName("UTF-8"));
            if (!StringUtils.isEmpty(requestBody)) {
                log.info("requestBody:{}", requestBody);
            }
            ctx.setSendZuulResponse(true);
            ctx.setResponseStatusCode(200);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
