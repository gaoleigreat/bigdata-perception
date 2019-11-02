package com.lego.perception.user.service.impl;

import com.framework.common.consts.RespConsts;
import com.framework.common.sdto.CurrentVo;
import com.framework.common.sdto.RespVO;
import com.lego.framework.auth.feign.AuthClient;
import com.lego.framework.user.model.vo.SsoLoginVo;
import com.lego.perception.user.constant.InitConst;
import com.lego.perception.user.constant.LoginConst;
import com.lego.perception.user.model.User;
import com.lego.perception.user.service.SsoLoginService;
import com.ym.sso.supervisor.client.dao.SsoLoginDao;
import com.ym.sso.supervisor.client.dao.impl.SsoLoginDaoImpl;
import com.ym.sso.supervisor.common.bean.SsoLogin;
import com.ym.sso.supervisor.common.bean.SsoTicket;
import com.ym.sso.supervisor.common.constant.LoginResultEnum;
import com.ym.sso.supervisor.common.constant.TicketResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private AuthClient authClient;


    private SsoLoginDao ssoLoginDao = new SsoLoginDaoImpl();


    /**
     * 登录方法
     *
     * @param token
     * @param ssoTicket 输入参数
     */
    @Override
    public SsoTicket loginRedis(String token, SsoTicket ssoTicket) {
        log.debug("loginRedis Ticket:{},token:{}", ssoTicket, token);
        User user = new User();
        user.setUserName(LoginConst.SESSION_USER_NAME);
        user.setIdNumber(ssoTicket.getIdNumber());
        RespVO respVO = authClient.saveUserToken(ssoTicket.getIdNumber(), token);
        if (respVO.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
            log.error("save   user  token fail :{}", respVO);
            return null;
        }
        ssoTicket.setSessionId(token);
        SsoTicket resultTicket = ssoLoginDao.receiveSessionId(ssoTicket);
        log.info("loginRedis resultTicket:{}", resultTicket);
        if (!TicketResultEnum.RECEIVE_ID_SUCCESS.getNo().equals(resultTicket.getResult())) {
            log.error("receive session error:{}", resultTicket.toString());
            authClient.removeUserToken(token);
            return null;
        } else {
            log.info("receive:session success:{}", token);
        }
        return resultTicket;
    }


    @Override
    public SsoTicket checkTicket(SsoTicket ssoTicket) {
        log.debug("checkTicket:ssoTicket={}", ssoTicket);
        return ssoLoginDao.checkTicket(ssoTicket);
    }


    @Override
    public SsoTicket login(HttpSession session, SsoTicket ssoTicket) {
        log.debug("login:ssoTicket={}", ssoTicket);
        User user = new User();
        user.setUserName(LoginConst.SESSION_USER_NAME);
        user.setIdNumber(ssoTicket.getIdNumber());
        session.setAttribute(LoginConst.SESSION_USER_KEY, user);
        Map<String, HttpSession> sessionMap = initConst.getSessionMap();
        if (sessionMap == null) {
            sessionMap = new HashMap<>();
        }
        sessionMap.put(session.getId(), session);
        initConst.setSessionMap(sessionMap);
        ssoTicket.setSessionId(session.getId());
        SsoTicket resultTicket = ssoLoginDao.receiveSessionId(ssoTicket);
        if (!TicketResultEnum.RECEIVE_ID_SUCCESS.getNo().equals(resultTicket.getResult())) {
            sessionMap.remove(session.getId());
            session.removeAttribute(LoginConst.SESSION_USER_KEY);
        } else {
            log.info("login:session.getId()={},session={}", session.getId(),
                    initConst.getSessionMap().get(session.getId()).getAttribute(
                            LoginConst.SESSION_USER_KEY));
        }
        return resultTicket;
    }


    @Override
    public SsoLogin logoutRedis(String sessionId) {
        log.debug("logout:sessionId={}", sessionId);
        Long time = System.currentTimeMillis();
        SsoLogin ssoLogin = new SsoLogin();
        if (sessionId == null) {
            ssoLogin.setResult(LoginResultEnum.LOGIN_OUT_MISS_PARAM.getNo());
            ssoLogin.setMessage(LoginResultEnum.LOGIN_OUT_MISS_PARAM.getMessage());
            ssoLogin.setTime(time);
            return ssoLogin;
        }
        authClient.removeUserToken(sessionId);
        ssoLogin.setResult(LoginResultEnum.LOGIN_OUT_SUCCESS.getNo());
        ssoLogin.setMessage(LoginResultEnum.LOGIN_OUT_SUCCESS.getMessage());
        ssoLogin.setTime(time);
        return ssoLogin;
    }


    /**
     * 注销的方法
     *
     * @param sessionId session的id
     * @return 注销的结果
     */
    @Override
    public SsoLogin logout(String sessionId) {
        log.debug("logout:sessionId={}", sessionId);
        Long time = System.currentTimeMillis();
        SsoLogin ssoLogin = new SsoLogin();
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
            log.error("logout={}", ex.toString());
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
        if (sessionMap.get(session.getId()) != null) {
            log.info("logout:session.removeAttribute:sessionId={},session={}", sessionId,
                    sessionMap.get(session.getId()).getAttribute(LoginConst.SESSION_USER_KEY));
        }
        session.removeAttribute(LoginConst.SESSION_USER_KEY);
        ssoLogin.setResult(LoginResultEnum.LOGIN_OUT_SUCCESS.getNo());
        ssoLogin.setMessage(LoginResultEnum.LOGIN_OUT_SUCCESS.getMessage());
        ssoLogin.setTime(time);
        return ssoLogin;
    }

    @Override
    public SsoLoginVo checkRedisSession(String sessionId) {
        log.debug("checkSession:sessionId={}", sessionId);
        Long time = System.currentTimeMillis();
        SsoLoginVo ssoLoginVo = new SsoLoginVo();
        if (sessionId == null) {
            ssoLoginVo.setResult(LoginResultEnum.CHECK_SESSION_MISS_PARAM.getNo());
            ssoLoginVo.setMessage(LoginResultEnum.CHECK_SESSION_MISS_PARAM.getMessage());
            ssoLoginVo.setTime(time);
            return ssoLoginVo;
        }
        //验证本地 session
        RespVO<CurrentVo> currentVoRespVO = authClient.getUserToken(sessionId);
        if (currentVoRespVO.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
            ssoLoginVo.setResult(LoginResultEnum.CHECK_SESSION_FAIL.getNo());
            ssoLoginVo.setMessage(LoginResultEnum.CHECK_SESSION_FAIL.getMessage());
            ssoLoginVo.setTime(time);
            return ssoLoginVo;
        }
        CurrentVo info = currentVoRespVO.getInfo();
        if (info == null) {
            ssoLoginVo.setResult(LoginResultEnum.CHECK_SESSION_FAIL.getNo());
            ssoLoginVo.setMessage(LoginResultEnum.CHECK_SESSION_FAIL.getMessage());
            ssoLoginVo.setTime(time);
            return ssoLoginVo;
        }
        ssoLoginVo.setCurrentVo(info);
        ssoLoginVo.setIdNumber(info.getIdCardNumber());
        ssoLoginVo.setResult(LoginResultEnum.CHECK_SESSION_SUCCESS.getNo());
        ssoLoginVo.setMessage(LoginResultEnum.CHECK_SESSION_SUCCESS.getMessage());
        ssoLoginVo.setTime(time);
        return ssoLoginVo;
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
        //TODO  验证 session
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
            //TODO 获取登录信息
            HttpSession session = initConst.getSessionMap().get(sessionId);
            User user = (User) session.getAttribute(LoginConst.SESSION_USER_KEY);
            ssoLogin.setIdNumber(user.getIdNumber());
            ssoLogin.setSessionId(sessionId);
        }
        return ssoLogin;
    }


    @Override
    public SsoLoginVo getLogParamRedis(String sessionId) {
        log.debug("getLogParam:sessionId={}", sessionId);
        SsoLoginVo ssoLoginVo = checkRedisSession(sessionId);
        if (LoginResultEnum.CHECK_SESSION_SUCCESS.getNo().equals(ssoLoginVo.getResult())) {
            ssoLoginVo.setSessionId(sessionId);
        }
        return ssoLoginVo;
    }

}