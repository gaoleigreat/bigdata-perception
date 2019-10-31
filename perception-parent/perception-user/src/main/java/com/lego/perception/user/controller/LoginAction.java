package com.lego.perception.user.controller;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.user.model.vo.SsoLoginVo;
import com.lego.perception.user.service.SsoLoginService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import com.ym.sso.supervisor.common.bean.SsoLogin;
import com.ym.sso.supervisor.common.bean.SsoTicket;
import com.ym.sso.supervisor.common.constant.TicketResultEnum;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;


/**
 * 登录管理
 *
 * @author 张江波
 * @version 2019-09-24 16:15
 */
@RestController
@RequestMapping("/sso/login/")
@Api(value = "登录")
@Slf4j
public class LoginAction {
    /**
     * 单点登录服务器路径
     */
    @Value("${sso.server.supervisor.url}")
    private String ssoSupServerUrl;


    @Value("${sso.server.local.url}")
    private String ssoLocalUrl;


    @Value("${sso.server.local.index.url}")
    private String ssoLocalIndexUrl;

    /**
     * 本地路径
     */
    @Value("${server.servlet.session.cookie.name}")
    private String sessionKey;

    /**
     * 登录服务
     */
    private SsoLoginService ssoLoginService;

    public LoginAction(SsoLoginService ssoLoginService) {
        this.ssoLoginService = ssoLoginService;
    }

    /**
     * 单点登录代码
     *
     * @param request   HttpServletRequest
     * @param ssoTicket 输入参数
     * @return 登录的结果
     */
    @ApiOperation(value = "login", notes = "login", httpMethod = "GET")
    @GetMapping(value = "login")
    public void login(HttpServletRequest request,
                      HttpServletResponse response,
                      @ModelAttribute SsoTicket ssoTicket) {
        log.debug("login:ssoTicket={}", ssoTicket);
        String sessionId = request.getRequestedSessionId();
        // 验证登录后重定向回来的 票据 是否合法
        ssoTicket = ssoLoginService.checkTicket(ssoTicket, ssoSupServerUrl, sessionId);
        log.info("验证ticket:{}", ssoTicket);
        if (TicketResultEnum.SSO_SUCCESS.getNo().equals(ssoTicket.getResult())) {
            // 处理本地 session
            HttpSession session = request.getSession();
            ssoTicket.setSessionId(sessionId);
            ssoTicket.setSessionKey(sessionKey);
            ssoTicket = ssoLoginService.loginRedis(session, ssoTicket, ssoSupServerUrl);
            ssoTicket.setSsoClientUrl(ssoLocalUrl);
            ssoTicket.setSsoSupServerUrl(ssoSupServerUrl);
            Cookie cookie = new Cookie("sessionId", ssoTicket.getSessionId());
            response.addCookie(cookie);
            try {
                response.sendRedirect(ssoLocalIndexUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 检查session的是否存在
     *
     * @param request HttpServletRequest
     * @return session的是否存在的返回值
     */
    @ApiOperation(value = "checkSession", notes = "checkSession", httpMethod = "GET")
    @GetMapping(value = "checkSession")
    public RespVO<SsoLoginVo> checkSession(
            @CookieValue(required = false) String sessionId,
            HttpServletRequest request) {
        log.debug("checkSession");
        SsoLoginVo ssoLoginVo = ssoLoginService.checkRedisSession(sessionId);
        ssoLoginVo.setSsoSupServerUrl(ssoSupServerUrl);
        ssoLoginVo.setSsoClientUrl(ssoLocalUrl);
        return RespVOBuilder.success(ssoLoginVo);
    }

    /**
     * 登出接口
     *
     * @param request  HttpServletRequest
     * @param ssoLogin 输入参数
     * @return 登出的结果
     */
    @ApiOperation(value = "logout", notes = "logout", httpMethod = "POST")
    @PostMapping(value = "logout")
    public RespVO<SsoLogin> logout(HttpServletRequest request, @RequestBody SsoLogin ssoLogin) {
        log.debug("logout:ssoLogin={}", ssoLogin);
        String sessionId = request.getRequestedSessionId();
        SsoLogin logoutRedis = ssoLoginService.logoutRedis(ssoLogin, sessionId);
        return RespVOBuilder.success(logoutRedis);
    }

    /**
     * 获取登录数据
     *
     * @param request HttpServletRequest
     * @return 登录数据
     */
    @ApiOperation(value = "getLogParam", notes = "getLogParam", httpMethod = "GET")
    @GetMapping(value = "getLogParam")
    public RespVO<SsoLoginVo> getLogParam(HttpServletRequest request) {
        log.debug("getLogParam");
        String sessionId = request.getRequestedSessionId();
        SsoLoginVo ssoLogin = ssoLoginService.getLogParamRedis(sessionId);
        ssoLogin.setSsoClientUrl(ssoLocalUrl);
        ssoLogin.setSsoSupServerUrl(ssoSupServerUrl);
        return RespVOBuilder.success(ssoLogin);
    }
}