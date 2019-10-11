package com.lego.perception.user.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.framework.common.consts.DictConstant;
import com.lego.framework.base.annotation.Resource;
import com.lego.perception.user.service.SsoLoginService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ym.sso.supervisor.common.bean.SsoLogin;
import com.ym.sso.supervisor.common.bean.SsoTicket;
import com.ym.sso.supervisor.common.constant.TicketResultEnum;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;


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
    @Value("${sso.local.url}")
    private String localUrl;

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
    @ApiOperation(value = "login", notes = "login", httpMethod = "POST")
    @PostMapping(value = "login")
    public SsoTicket login(HttpServletRequest request, @RequestBody SsoTicket ssoTicket) {
        log.debug("login:ssoTicket={}", ssoTicket);
        String sessionId = request.getRequestedSessionId();
        ssoTicket = ssoLoginService.checkTicket(ssoTicket, ssoSupServerUrl, sessionId);
        if (TicketResultEnum.SSO_SUCCESS.getNo().equals(ssoTicket.getResult())) {
            HttpSession session = request.getSession();
            ssoTicket.setSessionId(sessionId);
            ssoTicket.setSessionKey(sessionKey);
            ssoTicket = ssoLoginService.login(session, ssoTicket, ssoSupServerUrl);
        }
        ssoTicket.setSsoClientUrl(localUrl);
        ssoTicket.setSsoSupServerUrl(ssoSupServerUrl);
        return ssoTicket;
    }

    /**
     * 检查session的是否存在
     *
     * @param request HttpServletRequest
     * @return session的是否存在的返回值
     */
    @ApiOperation(value = "checkSession", notes = "checkSession", httpMethod = "GET")
    @GetMapping(value = "checkSession")
    public SsoLogin checkSession(HttpServletRequest request) {
        log.debug("checkSession");
        String sessionId = request.getRequestedSessionId();
        SsoLogin ssoLogin = ssoLoginService.checkSession(sessionId);
        ssoLogin.setSsoSupServerUrl(ssoSupServerUrl);
        ssoLogin.setSsoClientUrl(localUrl);
        return ssoLogin;
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
    public SsoLogin logout(HttpServletRequest request, @RequestBody SsoLogin ssoLogin) {
        log.debug("logout:ssoLogin={}", ssoLogin);
        String sessionId = request.getRequestedSessionId();
        return ssoLoginService.logout(ssoLogin, sessionId);
    }

    /**
     * 获取登录数据
     *
     * @param request HttpServletRequest
     * @return 登录数据
     */
    @ApiOperation(value = "getLogParam", notes = "getLogParam", httpMethod = "GET")
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