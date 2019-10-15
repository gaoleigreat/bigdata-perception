package com.lego.framework.auth.feign;

import com.framework.common.consts.DictConstant;
import com.framework.common.consts.RespConsts;
import com.framework.common.sdto.*;
import com.lego.framework.system.model.entity.User;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author yanglf
 * @descript
 * @since 2018/12/20
 **/
@FeignClient(value = DictConstant.Service.AUTH, path = DictConstant.Path.AUTH, fallbackFactory = AuthClientFallbackFactory.class)
public interface AuthClient {

    /**
     * 生成登录 token
     *
     * @param user 用户
     * @return
     */
    @RequestMapping(value = {"/generate"}, method = RequestMethod.POST)
    RespVO<TokenVo> generate(@RequestBody User user,
                             @RequestParam("deviceType") String deviceType
    );


    /**
     * 解析 用户 token
     *
     * @param token      user token
     * @param deviceType deviceType
     * @return user auth  vo
     */
    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    RespVO<CurrentVo> parseUserToken(@RequestParam("token") String token, @RequestParam("deviceType") String deviceType);


    /**
     * app端删除用户 token
     *
     * @param token
     * @param deviceType
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    RespVO<TokenVo> delete(@RequestParam("token") String token,
                           @RequestParam("deviceType") String deviceType);


    /**
     * 获取用户登录token
     *
     * @param userId
     * @param deviceType
     * @return
     */
    @RequestMapping(value = {"/loginToken"}, method = RequestMethod.GET)
    String loginToken(@RequestParam("userId") String userId,
                      @RequestParam("deviceType") String deviceType);


    /**
     * 根据header解析用户信息
     *
     * @param headerVo
     * @return
     */
    @RequestMapping(value = "/getAuthVo", method = RequestMethod.POST)
    CurrentVo getAuthVo(@RequestBody HeaderVo headerVo);


    /**
     * 设置    session信息
     *
     * @param userId
     * @param deviceType
     * @param token
     * @return
     */
    @RequestMapping(value = "/setAuthVo", method = RequestMethod.POST)
    RespVO setAuthVo(@RequestParam(value = "userId") String userId,
                     @RequestParam(value = "deviceType") String deviceType,
                     @RequestParam(value = "token") String token);

    /**
     * sso 保存 用户  token信息
     *
     * @param idNumber  用户身份证号
     * @param sessionId 当前 session id
     * @return
     */
    @RequestMapping(value = "/saveUserToken", method = RequestMethod.POST)
    RespVO saveUserToken(@RequestParam(value = "idNumber") String idNumber,
                         @RequestParam(value = "sessionId") String sessionId);

    /**
     * sso 获取 用户  token信息
     *
     * @param sessionId
     * @return
     */
    @RequestMapping(value = "/getUserToken", method = RequestMethod.GET)
    RespVO<CurrentVo> getUserToken(@RequestParam(value = "sessionId") String sessionId);

    /**
     * 删除用户  token
     *
     * @param sessionId
     * @return
     */
    @RequestMapping(value = "/removeUserToken", method = RequestMethod.DELETE)
    RespVO removeUserToken(@RequestParam(value = "sessionId") String sessionId);
}

@Slf4j
@Component
class AuthClientFallbackFactory implements FallbackFactory<AuthClient> {


    @Override
    public AuthClient create(Throwable throwable) {
        log.error("fallback; reason was:{}", throwable);
        return new AuthClient() {
            @Override
            public RespVO<TokenVo> generate(User user, String deviceType) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "auth服务不可用");
            }

            @Override
            public RespVO<CurrentVo> parseUserToken(String token, String deviceType) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "auth服务不可用");
            }

            @Override
            public RespVO<TokenVo> delete(String token, String deviceType) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "auth服务不可用");
            }

            @Override
            public String loginToken(String userId, String deviceType) {
                return null;
            }

            @Override
            public CurrentVo getAuthVo(HeaderVo headerVo) {
                return null;
            }

            @Override
            public RespVO setAuthVo(String userId, String deviceType, String token) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "auth服务不可用");
            }

            @Override
            public RespVO saveUserToken(String idNumber, String sessionId) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "auth服务不可用");
            }

            @Override
            public RespVO<CurrentVo> getUserToken(String sessionId) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "auth服务不可用");
            }

            @Override
            public RespVO removeUserToken(String sessionId) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "auth服务不可用");
            }
        };
    }
}

