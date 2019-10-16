package com.lego.perception.user.service;


import com.lego.framework.user.model.vo.SsoLoginVo;
import com.ym.sso.supervisor.common.bean.SsoLogin;
import com.ym.sso.supervisor.common.bean.SsoTicket;

import javax.servlet.http.HttpSession;


/**
 * 登录服务
 *
 * @author 张江波
 * @version 2019-10-02 10:35
 */
public interface SsoLoginService {
    /**
     * 检查门票是否存在
     *
     * @param ssoTicket       输入参数
     * @param ssoSupServerUrl 单点登录服务的访问路径
     * @param sessionId       session的id
     * @return 门票是否存在 true 是 false 否
     */
    SsoTicket checkTicket(SsoTicket ssoTicket, String ssoSupServerUrl, String sessionId);

    /**
     * 登录方法
     *
     * @param session         HttpSession
     * @param ssoTicket       输入参数
     * @param ssoSupServerUrl 单点登录服务的访问路径
     */
    SsoTicket login(HttpSession session, SsoTicket ssoTicket, String ssoSupServerUrl);

    /**
     * 登录   Redis
     *
     * @param session
     * @param ssoTicket
     * @param ssoSupServerUrl
     * @return
     */
    SsoTicket loginRedis(HttpSession session, SsoTicket ssoTicket, String ssoSupServerUrl);

    /**
     * 注销的方法
     *
     * @param ssoLogin  输入的参数
     * @param sessionId session的id
     * @return 注销的结果
     */
    SsoLogin logout(SsoLogin ssoLogin, String sessionId);

    /**
     * 退出登录   Redis
     *
     * @param ssoLogin
     * @param sessionId
     * @return
     */
    SsoLogin logoutRedis(SsoLogin ssoLogin, String sessionId);

    /**
     * 检查用户是否登录
     *
     * @param sessionId session的id
     * @return 用户是否登录 true 是 false 否
     */
    SsoLogin checkSession(String sessionId);


    /**
     * 检查用户是否登录 Redis
     *
     * @param sessionId
     * @return
     */
    SsoLoginVo checkRedisSession(String sessionId);

    /**
     * 获取登录数据
     *
     * @param sessionId session的id
     * @return 登录数据
     */
    SsoLogin getLogParam(String sessionId);


    /**
     * 获取用户登录信息   Redis
     *
     * @param sessionId
     * @return
     */
    SsoLoginVo getLogParamRedis(String sessionId);

}
