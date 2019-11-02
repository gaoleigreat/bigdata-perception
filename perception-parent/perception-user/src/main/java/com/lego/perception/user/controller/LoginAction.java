package com.lego.perception.user.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.framework.common.consts.HttpConsts;
import com.framework.common.consts.RespConsts;
import com.framework.common.utils.UuidUtils;
import com.lego.framework.user.model.vo.SsoLoginVo;
import com.lego.perception.user.service.SsoLoginService;
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
     * @param ssoTicket 输入参数
     * @return 登录的结果
     */
    @GetMapping(value = "login")
    public void login(HttpServletResponse response,
                      @ModelAttribute SsoTicket ssoTicket) throws IOException {
        log.debug("login:ssoTicket={}", ssoTicket);
        String token = UuidUtils.generate16Uuid();
        ssoTicket.setSsoSupServerUrl(ssoSupServerUrl);
        ssoTicket.setSessionId(token);
        ssoTicket = ssoLoginService.checkTicket(ssoTicket);
        ssoTicket.setSsoSupServerUrl(ssoSupServerUrl);
        log.info("ssoTicket result:{}", ssoTicket.toString());
        if (TicketResultEnum.SSO_TICKET_SUCCESS.getNo().equals(ssoTicket.getResult())) {
            ssoTicket.setSessionId(token);
            ssoTicket.setSessionKey(sessionKey);
            log.info("start login redis~~~~~~~~~~~");
            SsoTicket loginRedis = ssoLoginService.loginRedis(token, ssoTicket);
            // ssoTicket.getResult()
            log.info("ssoTicket:{}------------", ssoTicket.toString());
            if (loginRedis != null) {
                log.info("test---------------------");
                Cookie cookie = new Cookie("token", token);
                cookie.setMaxAge(36000);
                cookie.setPath("/");
                cookie.setDomain(localUrl.replace("http://", ""));
                cookie.setHttpOnly(true);
                response.addCookie(cookie);
                response.sendRedirect(localUrl);
                return;
            }
        }
        log.info("ticket fail", ssoTicket.toString());
    }

    /**
     * 检查session的是否存在
     *
     * @return session的是否存在的返回值
     */
    @GetMapping(value = "checkSession")
    public SsoLoginVo checkSession(@RequestHeader(value = HttpConsts.HEADER_TOKEN, required = false) String token) {
        log.debug("checkSession:{}", token);
        SsoLoginVo ssoLogin = ssoLoginService.checkRedisSession(token);
        ssoLogin.setSsoSupServerUrl(ssoSupServerUrl);
        ssoLogin.setSsoClientUrl(localUrl);
        return ssoLogin;
    }

    /**
     * 登出接口
     *
     * @return 登出的结果
     */
    @GetMapping(value = "logout")
    public SsoLogin logout(@RequestHeader(required = false, value = HttpConsts.HEADER_TOKEN) String token) {
        log.debug("logout:sessionId={}", token);
        return ssoLoginService.logoutRedis(token);
    }

    /**
     * 获取登录数据
     *
     * @return 登录数据
     */
    @GetMapping(value = "getLogParam")
    public SsoLogin getLogParam(@RequestHeader(required = true, value = HttpConsts.HEADER_TOKEN) String token) {
        log.debug("getLogParam");
        SsoLogin ssoLogin = ssoLoginService.getLogParam(token);
        ssoLogin.setSsoClientUrl(localUrl);
        ssoLogin.setSsoSupServerUrl(ssoSupServerUrl);
        return ssoLogin;
    }
}