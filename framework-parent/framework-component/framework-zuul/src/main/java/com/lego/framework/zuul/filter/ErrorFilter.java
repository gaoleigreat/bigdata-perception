package com.lego.framework.zuul.filter;

import com.alibaba.fastjson.JSONObject;
import com.framework.common.sdto.RespVOBuilder;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.ERROR_TYPE;
/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/11/2 17:08
 * @desc :
 */
@Slf4j
@Component
public class ErrorFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return ERROR_TYPE;
    }

    @Override
    public int filterOrder() {
        return -1;
    }

    @Override
    public boolean shouldFilter() {
        return RequestContext.getCurrentContext().containsKey("throwable");
    }

    @Override
    public Object run() throws ZuulException {
        try {
            RequestContext requestContext = RequestContext.getCurrentContext();
            Object e = requestContext.get("throwable");
            if (e instanceof ZuulException) {
                ZuulException zuulException = (ZuulException) e;
                // 删除该异常信息,不然在下一个过滤器中还会被执行处理
                requestContext.remove("throwable");
                // 响应给客户端信息
                HttpServletResponse response = requestContext.getResponse();
                response.setHeader("Content-type", "application/json;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                PrintWriter pw = response.getWriter();
                pw.write(JSONObject.toJSONString(RespVOBuilder.failure()));
                pw.close();
            }
        } catch (Exception ex) {
            log.error("Exception filtering in custom error filter", ex);
            ReflectionUtils.rethrowRuntimeException(ex);
        }
        return null;
    }
}
