package com.lego.perception.user.controller;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lego.framework.user.model.vo.SsoLoginVo;
import com.lego.perception.user.service.SsoLoginService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.server.Session;
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
@RequestMapping("/sso/login/*")
@Api(value = "登录")
@Slf4j
public class LoginAction {
    /**
     * 单点登录服务器路径
     */
    @Value("${sso.server.supervisor.url}")
    private String ssoSupServerUrl;

    /**
     * 本地路径
     */
    @Value("${sso.server.local.url}")
    private String localUrl;


    @Value("${sso.server.local.index.url}")
    private String localIndex;

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
    @GetMapping(value = "login")
    public void login(HttpServletRequest request,
                      HttpServletResponse response,
                      @ModelAttribute SsoTicket ssoTicket) throws Exception {
        log.debug("login:ssoTicket={}", ssoTicket);
        String sessionId = request.getRequestedSessionId();
        ssoTicket.setSsoSupServerUrl(ssoSupServerUrl);
        ssoTicket.setSessionId(sessionId);
        ssoTicket = ssoLoginService.checkTicket(ssoTicket);
        ssoTicket.setSsoSupServerUrl(ssoSupServerUrl);
        log.info("ssoTicket result:{}", ssoTicket.toString());
        if (TicketResultEnum.SSO_TICKET_SUCCESS.getNo().equals(ssoTicket.getResult())) {
            HttpSession session = request.getSession();
            ssoTicket.setSessionId(sessionId);
            ssoTicket.setSessionKey(sessionKey);
            ssoTicket = ssoLoginService.loginRedis(session, ssoTicket);
            // ssoTicket.getResult()
            Cookie cookie = new Cookie("sessionId", sessionId);
            cookie.setMaxAge(36000);
            response.addCookie(cookie);
            response.sendRedirect(localIndex);
        }
    }

    /**
     * 检查session的是否存在
     *
     * @param request HttpServletRequest
     * @return session的是否存在的返回值
     */
    @GetMapping(value = "checkSession")
    public SsoLoginVo checkSession(HttpServletRequest request,
                                   @CookieValue(required = false) String sessionId) {
        log.debug("checkSession");
        SsoLoginVo ssoLogin = ssoLoginService.checkRedisSession(sessionId);
        ssoLogin.setSsoSupServerUrl(ssoSupServerUrl);
        ssoLogin.setSsoClientUrl(localUrl);
        return ssoLogin;
    }

    /**
     * 登出接口
     *
     * @param request HttpServletRequest
     * @return 登出的结果
     */
    @GetMapping(value = "logout")
    public SsoLogin logout(HttpServletRequest request) {
        String sessionId = request.getRequestedSessionId();
        log.debug("logout:sessionId={}", sessionId);
        return ssoLoginService.logoutRedis(sessionId);
    }

    /**
     * 获取登录数据
     *
     * @param request HttpServletRequest
     * @return 登录数据
     */
    @GetMapping(value = "getLogParam")
    public SsoLogin getLogParam(HttpServletRequest request) {
        log.debug("getLogParam");
        String sessionId = request.getRequestedSessionId();
        SsoLogin ssoLogin = ssoLoginService.getLogParam(sessionId);
        ssoLogin.setSsoClientUrl(localUrl);
        ssoLogin.setSsoSupServerUrl(ssoSupServerUrl);
        return ssoLogin;
    }
}