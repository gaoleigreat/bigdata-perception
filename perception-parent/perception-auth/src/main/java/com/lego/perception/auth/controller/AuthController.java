package com.lego.perception.auth.controller;

import com.framework.common.consts.DictConstant;
import com.framework.common.sdto.*;
import com.lego.perception.auth.service.IAuthService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 * @author yanglf
 * @descript
 * @since 2018/12/20
 **/
@RestController
@RequestMapping(DictConstant.Path.AUTH)
@Api(value = "AuthController", description = "授权服务")
public class AuthController {

    @Autowired
    private IAuthService iAuthService;

/*
    @ApiOperation(value = "生成用户登录token", httpMethod = "POST", notes = "生成登录token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceType", value = "设备类型", dataType = "String", required = true, paramType = "query")
    })
    @RequestMapping(value = {"/generate"}, method = RequestMethod.POST)
    public RespVO<TokenVo> generate(@RequestBody User user,
                                    @RequestParam String deviceType
    ) {
        TokenVo tokenVo = iAuthService.generateUserToken(user, deviceType);
        if (tokenVo == null) {
            return RespVOBuilder.success(RespConsts.FAIL_RESULT_CODE, "生成token失败");
        }
        return RespVOBuilder.success(tokenVo);
    }*/


    /**
     * 登录成功  保存用户 token信息
     *
     * @param idNumber
     * @param sessionId
     * @return
     */
    @RequestMapping(value = "/saveUserToken", method = RequestMethod.POST)
    public RespVO saveUserToken(@RequestParam(value = "idNumber") String idNumber,
                                @RequestParam(value = "sessionId") String sessionId) {
        return iAuthService.saveUserToken(idNumber,sessionId);
    }


    @RequestMapping(value = "/getUserToken", method = RequestMethod.GET)
    public RespVO<CurrentVo> getUserToken(@RequestParam(value = "sessionId") String sessionId){
        return iAuthService.getUserToken(sessionId);
    }


    @RequestMapping(value = "/removeUserToken", method = RequestMethod.DELETE)
    public RespVO removeUserToken(@RequestParam(value = "sessionId") String sessionId){
        return iAuthService.removeUserToken(sessionId);
    }


 /*   @ApiOperation(value = "验证用户登录token", httpMethod = "GET", notes = "验证用户登录token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "用户登录Token", dataType = "String", required = true, paramType = "query"),
    })
    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    public RespVO<CurrentVo> parseUserToken(@RequestParam String token, @RequestParam String deviceType) {
        AuthVo authVo = iAuthService.verifyUserToken(token, deviceType);
        if (authVo == null) {
            return RespVOBuilder.failure();
        }
        Date expiration = authVo.getExpiration();
        if (expiration.compareTo(new Date()) <= 0) {
            return RespVOBuilder.failure();
        }
        return RespVOBuilder.success(authVo.getCurrentVo());
    }*/


   /* @ApiOperation(value = "刷新用户token", httpMethod = "POST", notes = "刷新token")
    @ApiImplicitParams({

    })
    @RequestMapping(value = {"/refresh"}, method = RequestMethod.POST)
    public RespVO<TokenVo> refresh(HttpServletRequest request) {
        String token = request.getHeader(HttpConsts.HEADER_TOKEN);
        String deviceType = request.getHeader(HttpConsts.DEVICE_TYPE);
        AuthVo authVo = iAuthService.verifyUserToken(token, deviceType);
        if (authVo == null) {
            ExceptionBuilder.sessionTimeoutException();
        }
        CurrentVo currentVo = authVo.getCurrentVo();
        if (currentVo == null) {
            ExceptionBuilder.sessionTimeoutException();
        }
        Long userId = currentVo.getUserId();
        String hasLogin = iAuthService.hasLogin(userId, deviceType);
        if (hasLogin != null) {
            // 删除之前的  token
            iAuthService.deleteUserToken(token, deviceType);
        }
        RespVO<User> userRespVO = userClient.findUser(User.builder().id(userId).build());
        if (userRespVO.getRetCode() == RespConsts.SUCCESS_RESULT_CODE) {
            User user = userRespVO.getInfo();
            if (user != null) {
                TokenVo tokenVo = iAuthService.generateUserToken(user, deviceType);
                if (tokenVo != null) {
                    return RespVOBuilder.success(tokenVo);
                }
            }
        }
        return RespVOBuilder.failure();
    }


    @ApiOperation(value = "删除用户token", httpMethod = "POST", notes = "删除用户token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "当前token", dataType = "String", required = true, paramType = "query"),
            @ApiImplicitParam(name = "deviceType", value = "设备类型", dataType = "String", required = true, paramType = "query"),
    })
    @RequestMapping(value = {"/delete"}, method = RequestMethod.POST)
    public RespVO<TokenVo> delete(@RequestParam String token,
                                  @RequestParam String deviceType) {
        AuthVo authVo = iAuthService.verifyUserToken(token, deviceType);
        if (authVo != null) {
            // 删除 redis 里的token 信息
            iAuthService.deleteUserToken(token, deviceType);
        }
        return RespVOBuilder.success();
    }


    @ApiOperation(value = "获取登录用户的token信息", httpMethod = "GET", notes = "获取登录用户的token信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", dataType = "String", required = true, paramType = "query"),
            @ApiImplicitParam(name = "deviceType", value = "设备类型", dataType = "String", required = true, paramType = "query"),

    })
    @RequestMapping(value = {"/loginToken"}, method = RequestMethod.GET)
    public String loginToken(@RequestParam Long userId,
                             @RequestParam String deviceType) {
        return iAuthService.hasLogin(userId, deviceType);
    }


    @RequestMapping(value = "/getAuthVo", method = RequestMethod.POST)
    public CurrentVo getAuthVo(@RequestBody HeaderVo headerVo) {
        if (headerVo == null) {
            ExceptionBuilder.sessionTimeoutException();
        }
        String token = headerVo.getToken();
        AuthVo authVo = iAuthService.verifyUserToken(token, "2");
        if (authVo == null) {
            ExceptionBuilder.sessionTimeoutException();
        }
        return authVo.getCurrentVo();
    }


    @RequestMapping(value = "/setAuthVo", method = RequestMethod.POST)
    public RespVO setAuthVo(@RequestParam Long userId, String deviceType, String token) {
        RespVO<User> userRespVO = userClient.findUser(User.builder().id(userId).build());
        if (userRespVO.getRetCode() == RespConsts.SUCCESS_RESULT_CODE) {
            User user = userRespVO.getInfo();
            if (user != null) {
                Integer integer = iAuthService.setUserToken(user, deviceType, token);
                return integer > 0 ? RespVOBuilder.success() : RespVOBuilder.failure();
            }
        }
        return RespVOBuilder.failure();

    }


    @RequestMapping(value = "/generateServiceToken", method = RequestMethod.POST)
    public void generateServiceToken(String fromService,
                                     String toService
    ) {
        TokenVo tokenVo = iAuthService.generateServiceToken(fromService, toService);

    }*/


}
