package com.lego.framework.zuul.utils;

import com.alibaba.fastjson.JSON;
import com.framework.common.sdto.RespVO;
import com.netflix.zuul.context.RequestContext;
import java.io.IOException;
/**
 * @author yanglf
 * @description
 * @since 2019/8/6
 **/
public class RouteUtil {


    /**
     * 阻止路由
     *
     * @param ctx
     * @param pvId
     * @param respVO
     * @return
     */
    public static Object writeAndReturn(RequestContext ctx, String pvId, RespVO respVO) {
        ctx.set("pvId", pvId);
        ctx.getResponse().setContentType("application/json;charset=utf-8");
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(200);
        ctx.setResponseBody(JSON.toJSONString(respVO));
        return null;
    }


    /**
     * 重定向
     *
     * @param ctx
     * @param pvId
     * @param redirectURL
     * @return
     */
    public static Object forward(RequestContext ctx, String pvId, String redirectURL) {
        try {
            ctx.addZuulRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
            ctx.set("pvId", pvId);
            ctx.setSendZuulResponse(false);
            ctx.getResponse().sendRedirect(redirectURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Object forward308(RequestContext ctx, String pvId, String redirectURL) {
        ctx.setResponseStatusCode(308);
        ctx.addZuulResponseHeader("location", redirectURL);
        ctx.addZuulRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
        ctx.set("pvId", pvId);
        ctx.setSendZuulResponse(false);
        return null;
    }

}