package com.lego.perception.user.service.impl;


import com.alibaba.fastjson.JSON;
import com.lego.perception.user.constant.InitConst;
import com.lego.perception.user.constant.LoginConst;
import com.lego.perception.user.model.User;
import com.lego.perception.user.service.SsoLoginService;
import com.ym.sso.supervisor.common.bean.SsoLogin;
import com.ym.sso.supervisor.common.bean.SsoTicket;
import com.ym.sso.supervisor.common.constant.ClientResultEnum;
import com.ym.sso.supervisor.common.constant.LoginResultEnum;
import com.ym.sso.supervisor.common.constant.TicketResultEnum;
import com.ym.sso.supervisor.common.util.SsoHttpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 登录服务
 *
 * @author 张江波
 * @version 2019-10-02 10:35
 */
@SuppressWarnings("AlibabaAvoidNewDateGetTime")
@Slf4j
@Service
public class SsoLoginSerImpl implements SsoLoginService {
    /**
     * 初始化内存加载
     */
    private InitConst initConst = new InitConst();

    /**
     * 检查门票是否存在
     *
     * @param ssoTicket       输入参数
     * @param ssoSupServerUrl 单点登录服务的访问路径
     * @param sessionId       session的id
     * @return 门票是否存在 true 是 false 否
     */
    @Override
    public SsoTicket checkTicket(SsoTicket ssoTicket, String ssoSupServerUrl, String sessionId) {
        log.debug("checkTicket:ssoTicket={},ssoSupServerUrl={},sessionId={}", ssoTicket,
                ssoSupServerUrl, sessionId);
        if (ssoTicket == null) {
            ssoTicket = new SsoTicket();
            ssoTicket.setResult(ClientResultEnum.LOCAL_MISS_PARAM.getNo());
            ssoTicket.setMessage(ClientResultEnum.LOCAL_MISS_PARAM.getMessage());
            return ssoTicket;
        }
        if (ssoTicket.getIdNumber() == null || ssoTicket.getTicket() == null) {
            ssoTicket.setResult(ClientResultEnum.LOCAL_MISS_PARAM.getNo());
            ssoTicket.setMessage(ClientResultEnum.LOCAL_MISS_PARAM.getMessage());
            return ssoTicket;
        }
        if (ssoSupServerUrl.endsWith("/")) {
            ssoSupServerUrl = ssoSupServerUrl.substring(0, ssoSupServerUrl.indexOf("/"));
        }
        String json = SsoHttpClient.doPost(ssoSupServerUrl + "/000000/sso/ticket/check.do",
                JSON.toJSONString(ssoTicket));
        if (json == null || json.trim().length() < 3) {
            ssoTicket.setResult(ClientResultEnum.LOCAL_TICKET_ERROR.getNo());
            ssoTicket.setMessage(ClientResultEnum.LOCAL_TICKET_ERROR.getMessage());
            return ssoTicket;
        }
        return JSON.parseObject(json, SsoTicket.class);
    }

    /**
     * 登录方法
     *
     * @param session         HttpSession
     * @param ssoTicket       输入参数
     * @param ssoSupServerUrl 单点登录服务的访问路径
     */
    @Override
    public SsoTicket login(HttpSession session, SsoTicket ssoTicket, String ssoSupServerUrl) {
        log.debug("login:ssoTicket={},ssoSupServerUrl={}", ssoTicket, ssoSupServerUrl);
        User user = new User();
        user.setUserName("王富贵");
        user.setIdNumber(ssoTicket.getIdNumber());
        session.setAttribute(LoginConst.SESSION_USER_KEY, user);
        Map<String, HttpSession> sessionMap = initConst.getSessionMap();
        if (sessionMap == null) {
            sessionMap = new HashMap<>();
        }
        sessionMap.put(session.getId(), session);
        initConst.setSessionMap(sessionMap);
        ssoTicket.setSessionId(session.getId());
        String json = SsoHttpClient.doPost(
                ssoSupServerUrl + "/000000/sso/ticket/receiveSessionId.do",
                JSON.toJSONString(ssoTicket));
        if (json != null && json.length() > 2) {
            ssoTicket = JSON.parseObject(json, SsoTicket.class);
            if (TicketResultEnum.RECEIVE_ID_SUCCESS.getNo().equals(ssoTicket.getResult())) {
                ssoTicket.setResult(ClientResultEnum.LOCAL_SUCCESS.getNo());
            } else {
                session.removeAttribute(LoginConst.SESSION_USER_KEY);
            }
        } else {
            ssoTicket.setResult(ClientResultEnum.LOCAL_SESSION_ERROR.getNo());
            ssoTicket.setMessage(ClientResultEnum.LOCAL_SESSION_ERROR.getMessage());
            session.removeAttribute(LoginConst.SESSION_USER_KEY);
        }
        return ssoTicket;
    }

    /**
     * 注销的方法
     *
     * @param ssoLogin  输入的参数
     * @param sessionId session的id
     * @return 注销的结果
     */
    @Override
    public SsoLogin logout(SsoLogin ssoLogin, String sessionId) {
        log.debug("logout:ssoLogin={},sessionId={}", ssoLogin, sessionId);
        Long time = System.currentTimeMillis();
        if (ssoLogin == null) {
            ssoLogin = new SsoLogin();
        }
        if (sessionId == null) {
            ssoLogin.setResult(LoginResultEnum.LOGIN_OUT_MISS_PARAM.getNo());
            ssoLogin.setMessage(LoginResultEnum.LOGIN_OUT_MISS_PARAM.getMessage());
            ssoLogin.setTime(time);
            return ssoLogin;
        }
        Map<String, HttpSession> sessionMap = initConst.getSessionMap();
        if (sessionMap == null) {
            ssoLogin.setResult(LoginResultEnum.LOGIN_OUT_SUCCESS.getNo());
            ssoLogin.setMessage(LoginResultEnum.LOGIN_OUT_SUCCESS.getMessage());
            ssoLogin.setTime(time);
            return ssoLogin;
        }
        HttpSession session = sessionMap.get(sessionId);
        if (session == null) {
            ssoLogin.setResult(LoginResultEnum.LOGIN_OUT_SUCCESS.getNo());
            ssoLogin.setMessage(LoginResultEnum.LOGIN_OUT_SUCCESS.getMessage());
            ssoLogin.setTime(time);
            return ssoLogin;
        }
        Object userSession;
        try {
            userSession = session.getAttribute(LoginConst.SESSION_USER_KEY);
        } catch (IllegalStateException ex) {
            sessionMap.remove(sessionId);
            initConst.setSessionMap(sessionMap);
            ssoLogin.setResult(LoginResultEnum.LOGIN_OUT_SUCCESS.getNo());
            ssoLogin.setMessage(LoginResultEnum.LOGIN_OUT_SUCCESS.getMessage());
            ssoLogin.setTime(time);
            return ssoLogin;
        }
        if (userSession == null) {
            ssoLogin.setResult(LoginResultEnum.LOGIN_OUT_SUCCESS.getNo());
            ssoLogin.setMessage(LoginResultEnum.LOGIN_OUT_SUCCESS.getMessage());
            ssoLogin.setTime(time);
            return ssoLogin;
        }
        session.removeAttribute(LoginConst.SESSION_USER_KEY);
        ssoLogin.setResult(LoginResultEnum.LOGIN_OUT_SUCCESS.getNo());
        ssoLogin.setMessage(LoginResultEnum.LOGIN_OUT_SUCCESS.getMessage());
        ssoLogin.setTime(time);
        return ssoLogin;
    }

    /**
     * 检查用户是否登录
     *
     * @param sessionId session的id
     * @return 用户是否登录 true 是 false 否
     */
    @Override
    public SsoLogin checkSession(String sessionId) {
        log.debug("checkSession:sessionId={}", sessionId);
        Long time = System.currentTimeMillis();
        SsoLogin ssoLogin = new SsoLogin();
        if (sessionId == null) {
            ssoLogin.setResult(LoginResultEnum.CHECK_SESSION_MISS_PARAM.getNo());
            ssoLogin.setMessage(LoginResultEnum.CHECK_SESSION_MISS_PARAM.getMessage());
            ssoLogin.setTime(time);
            return ssoLogin;
        }
        Map<String, HttpSession> sessionMap = initConst.getSessionMap();
        if (sessionMap == null) {
            ssoLogin.setResult(LoginResultEnum.CHECK_SESSION_FAIL.getNo());
            ssoLogin.setMessage(LoginResultEnum.CHECK_SESSION_FAIL.getMessage());
            ssoLogin.setTime(time);
            return ssoLogin;
        }
        HttpSession session = sessionMap.get(sessionId);
        if (session == null) {
            ssoLogin.setResult(LoginResultEnum.CHECK_SESSION_FAIL.getNo());
            ssoLogin.setMessage(LoginResultEnum.CHECK_SESSION_FAIL.getMessage());
            ssoLogin.setTime(time);
            return ssoLogin;
        }
        Object userSession;
        try {
            userSession = session.getAttribute(LoginConst.SESSION_USER_KEY);
        } catch (IllegalStateException ex) {
            sessionMap.remove(sessionId);
            initConst.setSessionMap(sessionMap);
            ssoLogin.setResult(LoginResultEnum.CHECK_SESSION_FAIL.getNo());
            ssoLogin.setMessage(LoginResultEnum.CHECK_SESSION_FAIL.getMessage());
            ssoLogin.setTime(time);
            return ssoLogin;
        }
        if (userSession == null) {
            ssoLogin.setResult(LoginResultEnum.CHECK_SESSION_FAIL.getNo());
            ssoLogin.setMessage(LoginResultEnum.CHECK_SESSION_FAIL.getMessage());
            ssoLogin.setTime(time);
            return ssoLogin;
        }
        ssoLogin.setResult(LoginResultEnum.CHECK_SESSION_SUCCESS.getNo());
        ssoLogin.setMessage(LoginResultEnum.CHECK_SESSION_SUCCESS.getMessage());
        ssoLogin.setTime(time);
        return ssoLogin;
    }

    /**
     * 获取登录数据
     *
     * @param sessionId session的id
     * @return 登录数据
     */
    @Override
    public SsoLogin getLogParam(String sessionId) {
        log.debug("getLogParam:sessionId={}", sessionId);
        SsoLogin ssoLogin = checkSession(sessionId);
        if (LoginResultEnum.CHECK_SESSION_SUCCESS.getNo().equals(ssoLogin.getResult())) {
            HttpSession session = initConst.getSessionMap().get(sessionId);
            User user = (User) session.getAttribute(LoginConst.SESSION_USER_KEY);
            ssoLogin.setIdNumber(user.getIdNumber());
            ssoLogin.setSessionId(sessionId);
        }
        return ssoLogin;
    }
}
