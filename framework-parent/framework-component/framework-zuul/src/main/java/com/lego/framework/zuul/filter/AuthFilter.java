package com.lego.framework.zuul.filter;

import com.framework.common.consts.HttpConsts;
import com.framework.common.consts.RespConsts;
import com.framework.common.sdto.CurrentVo;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.auth.feign.AuthClient;
import com.lego.framework.base.utils.HttpUtils;
import com.lego.framework.user.feign.LoginClient;
import com.lego.framework.user.model.vo.SsoLoginVo;
import com.lego.framework.zuul.predicate.RibbonVersionHolder;
import com.lego.framework.zuul.utils.JwtPatternUrl;
import com.lego.framework.zuul.utils.RouteUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * @author yanglf
 * @description
 * @since 2019/7/3
 **/
@Component
public class AuthFilter extends ZuulFilter {

    private Logger logger = LoggerFactory.getLogger("access");


    @Autowired
    private JwtPatternUrl jwtPatternUrl;

    @Value("${server.servlet.context-path}")
    private String contextPath;


    @Value("${session.domain}")
    private String authDomain;

    @Autowired
    private AuthClient authClient;

    @Autowired
    private LoginClient loginClient;


    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest req = ctx.getRequest();
        HttpServletResponse res = ctx.getResponse();
        StringBuilder sb = new StringBuilder();
        //web 跨域 上线注释
        res.addHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
        res.addHeader("Access-Control-Allow-Credentials", "true");
        try {
            String uri = req.getRequestURI();
            String remoteIp = HttpUtils.getClientIp(req);
            String localIp = req.getLocalAddr();
            String pvId = HttpUtils.generatePVID(req, remoteIp, localIp);
            long timeMillis = System.currentTimeMillis();
            String traceInfo = timeMillis + "-" + remoteIp + "-" + uri + "-" + authDomain;

            sb.append(timeMillis).append("\t").append("ACCESS").append("\t").append(pvId).append("\t")
                    .append(localIp).append("\t").append(remoteIp).append("\t").append(uri).append("\t");
            logger.info(sb.toString());

            // url 是否需要 token 认证
            Boolean isIgnore = isIgnore(uri);
            if (isIgnore) {
                setRequest(ctx, null, traceInfo);
                ctx.set("pvId", pvId);
                return null;
            }
            String userToken = req.getHeader(HttpConsts.HEADER_TOKEN);
            String deviceType = req.getHeader(HttpConsts.DEVICE_TYPE);
            String osType = req.getHeader(HttpConsts.OS_VERSION);
            if (!StringUtils.isEmpty(osType)) {
                RibbonVersionHolder.setContext(osType);
            }
            //  是否登录
            return checkLogin(ctx, pvId, traceInfo, userToken, deviceType);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 单点登录
     *
     * @param ctx
     * @param pvId
     * @param traceInfo
     * @param userToken
     * @param deviceType
     * @return
     */
    private Object checkSsoLogin(RequestContext ctx, String pvId, String traceInfo, String userToken, String deviceType) {
        if (!StringUtils.isEmpty(userToken) && !StringUtils.isEmpty(deviceType)) {
            RespVO<SsoLoginVo> respVO = loginClient.checkSession();
            if (respVO.getRetCode() == RespConsts.SUCCESS_RESULT_CODE) {
                SsoLoginVo ssoLoginVo = respVO.getInfo();
                if (ssoLoginVo != null && "check_session_success".equals(ssoLoginVo.getResult())) {
                    String idNumber = ssoLoginVo.getIdNumber();
                    // TODO 通过身份证号 获取用户信息
                    CurrentVo currentVo = new CurrentVo();
                    setRequest(ctx, currentVo, traceInfo);
                    ctx.set("pvId", pvId);
                    return null;
                }
            }
        }
        RespVO<SsoLoginVo> failure = RespVOBuilder.failure(RespConsts.FAIL_LOGIN_CODE, "登录失败");
        return RouteUtil.writeAndReturn(ctx, pvId, failure);
    }


    /**
     * 普通登录处理
     *
     * @param ctx
     * @param pvId
     * @param traceInfo
     * @param userToken
     * @param deviceType
     * @return
     */
    private Object checkLogin(RequestContext ctx, String pvId, String traceInfo, String userToken, String deviceType) {
        if (!StringUtils.isEmpty(userToken) && !StringUtils.isEmpty(deviceType)) {
            RespVO<CurrentVo> currentVoRespVO = authClient.parseUserToken(userToken, deviceType);
            if (currentVoRespVO.getRetCode() == RespConsts.SUCCESS_RESULT_CODE) {
                CurrentVo currentVo = currentVoRespVO.getInfo();
                if (currentVo != null) {
                    setRequest(ctx, currentVo, traceInfo);
                    ctx.set("pvId", pvId);
                    return null;
                }
            }
        }
        RespVO failure = RespVOBuilder.failure(RespConsts.FAIL_LOGIN_CODE, "登录失败");
        return RouteUtil.writeAndReturn(ctx, pvId, failure);
    }


    private Boolean isIgnore(String uri) {
        String s = uri.replaceAll(contextPath, "");
        List<String> urlPatterns = jwtPatternUrl.getUrlPatterns();
        if (CollectionUtils.isEmpty(urlPatterns)) {
            return false;
        }
        for (String reg : urlPatterns) {
            if (s.matches(reg)) {
                return true;
            }
        }
        return false;
    }


    private void setRequest(RequestContext ctx, CurrentVo currentVo, String traceInfo) {
        ctx.getZuulRequestHeaders().put("DOMAIN", authDomain);
        ctx.getZuulRequestHeaders().put("TRACE", traceInfo);
        ctx.setSendZuulResponse(true);
        ctx.setResponseStatusCode(200);
        if (currentVo != null) {
            ctx.addZuulRequestHeader(HttpConsts.USER_ID, currentVo.getUserId() + "");
            ctx.addZuulRequestHeader(HttpConsts.USER_NAME, currentVo.getUserName());
            try {
                ctx.addZuulRequestHeader("name", URLEncoder.encode(currentVo.getName(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                ctx.addZuulRequestHeader("name", currentVo.getName());
            }
        } else {
            // TODO 模拟 登录数据
            ctx.addZuulRequestHeader(HttpConsts.USER_ID, 1 + "");
            ctx.addZuulRequestHeader(HttpConsts.USER_NAME, "admin");
        }
    }


}
