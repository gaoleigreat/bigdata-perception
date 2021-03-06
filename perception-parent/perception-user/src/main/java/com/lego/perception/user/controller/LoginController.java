/*
package com.lego.perception.user.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.framework.common.consts.DictConstant;
import com.framework.common.consts.HttpConsts;
import com.framework.common.consts.RespConsts;
import com.framework.common.sdto.HeaderVo;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.framework.common.sdto.TokenVo;
import com.framework.common.utils.HttpUtils;
import com.lego.framework.auth.feign.AuthClient;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.base.utils.HeaderUtils;
import com.lego.framework.base.utils.SecurityUtils;
import com.lego.framework.event.log.LogSender;
import com.lego.framework.system.feign.UserClient;
import com.lego.framework.system.model.entity.User;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.UUID;

*/
/**
 * @author yanglf
 * @descript
 * @since 2018/12/17
 **//*

@Validated
@Controller
@RequestMapping(DictConstant.Path.USER)
@Api(value = "LoginController", description = "用户管理")
@Resource(value = "user", desc = "用户管理")
@Slf4j
public class LoginController {

    @Autowired
    private AuthClient authClient;


    @Autowired
    private UserClient userClient;

    @Autowired
    private LogSender logSender;


    @ApiOperation(value = "用户登录", httpMethod = "POST", notes = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", dataType = "String", required = true, paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String", required = true, paramType = "query"),
    })
    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public String login(HttpServletRequest request,
                        HttpServletResponse response,
                        @NotBlank(message = "用户名不能为空") @RequestParam String userName,
                        @NotBlank(message = "密码不能为空") @Size(min = 6, max = 32, message = "密码长度为6-23位") @RequestParam String password) throws IOException {
        // TODO 验证是否登录    是否携带注册令牌  sso_ticket
        String ssoTicket = request.getHeader("sso_ticket");
        if (StringUtils.isBlank(ssoTicket)) {
            // TODO 重定向到  SSO 服务(进行登录操作)
            return "redirect:http://baidu.com";
        }
        // TODO 调用sso服务校验注册令牌
        String ticketResult = ssoClient.redirectSsoService("101030100");
        log.info("校验 sso 票据:{}", ticketResult);
        // 创建本地会话
        Cookie cookie = new Cookie("PERCEPTION_TOKEN", UUID.randomUUID().toString().replace("-", ""));
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 2);
        response.addCookie(cookie);
        PrintWriter writer = response.getWriter();
        RespVO respVO = RespVOBuilder.success();
        writer.write(JSON.toJSONString(respVO));
        writer.flush();
        writer.close();
        return null;
    }


    @ApiOperation(value = "用户退出登录", httpMethod = "POST", notes = "用户退出登录")
    @ApiImplicitParams({

    })
    @RequestMapping(value = {"/logout"}, method = RequestMethod.POST)
    public String logout(HttpServletRequest request,
                         HttpServletResponse response,
                         @CookieValue(value = "PERCEPTION_TOKEN") String perceptionToken) throws IOException {
        HeaderVo headerVo = HeaderUtils.parseHeader(request);
        if (headerVo != null) {
            // TODO 验证 本地  cookie  perceptionToken
            String ssoTicket = request.getHeader("sso_ticket");
            if (!StringUtils.isBlank(ssoTicket)) {
                // 重定sso 服务  请求  全局注销
                return "redirect:http://baidu.com";
            }
            // 删除本地  token
            Cookie cookie = new Cookie("PERCEPTION_TOKEN", null);
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        PrintWriter writer = response.getWriter();
        RespVO respVO = RespVOBuilder.success();
        writer.write(JSONObject.toJSONString(respVO));
        writer.flush();
        writer.close();
        return null;
    }


    @ApiOperation(value = "模拟用户登录", httpMethod = "POST", notes = "模拟用户登录")
    @ApiImplicitParams({

    })
    @RequestMapping(value = {"/mock_login"}, method = RequestMethod.POST)
    @ResponseBody
    public RespVO mockLogin(@NotBlank(message = "用户名不能为空") @RequestParam String userName,
                            @NotBlank(message = "密码不能为空") @Size(min = 6, max = 32, message = "密码长度为6-23位") @RequestParam String password,
                            HttpServletRequest request) throws IOException {
        User user = new User();
        user.setPassword(SecurityUtils.encryptionWithMd5(password));
        user.setUsername(userName);
        RespVO<User> respVO = userClient.findUser(user);
        if (respVO.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
            return RespVOBuilder.failure();
        }
        User info = respVO.getInfo();
        if (info == null) {
            return RespVOBuilder.failure("用户不存在");
        }
        RespVO<TokenVo> tokenVo = authClient.generate(info, HttpConsts.DeviceType.DEVICE_WEB);
        if (tokenVo.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
            return RespVOBuilder.failure("登录失败");
        }
        logSender.sendLogEvent(HttpUtils.getClientIp(request),1L,"用户登录","user login","user-service","LOGIN","BUSINESS",new Date(),"admin",1L);
        return tokenVo;
    }


}
*/
