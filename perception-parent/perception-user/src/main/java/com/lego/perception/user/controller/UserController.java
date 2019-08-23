package com.lego.perception.user.controller;

import com.alibaba.fastjson.JSON;
import com.lego.framework.auth.feign.AuthClient;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.event.log.LogSender;
import com.survey.lib.common.consts.DictConstant;
import com.survey.lib.common.utils.HeaderUtils;
import com.survey.lib.common.utils.HttpUtils;
import com.survey.lib.common.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
import java.util.UUID;

/**
 * @author yanglf
 * @descript
 * @since 2018/12/17
 **/
@Validated
@Controller
@RequestMapping(DictConstant.Path.USER)
@Api(value = "UserController", description = "用户管理")
@Resource(value = "user", desc = "用户管理")
public class UserController {

    @Autowired
    private AuthClient authClient;


    @ApiOperation(value = "用户登录", httpMethod = "POST", notes = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", value = "用户名", dataType = "String", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pwd", value = "密码", dataType = "String", required = true, paramType = "query"),
    })
    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public String login(HttpServletRequest request,
                        HttpServletResponse response,
                        @NotBlank(message = "用户名不能为空") @RequestParam String user,
                        @NotBlank(message = "密码不能为空") @Size(min = 6, max = 32, message = "密码长度为6-23位") @RequestParam String pwd) throws IOException {
        // TODO 验证是否登录    是否携带注册令牌  sso_ticket
        String ssoTicket = request.getHeader("sso_ticket");
        if (StringUtils.isBlank(ssoTicket)) {
            // TODO 重定向到  SSO 服务
            return "redirect:http://baidu.com";
        }
        // TODO 校验注册令牌
        // 创建本地会话
        Cookie cookie = new Cookie("PERCEPTION_TOKEN", UUID.randomUUID().toString().replace("-", ""));
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
    public RespVO<TokenVo> logout(HttpServletRequest request) {
        HeaderVo headerVo = HeaderUtils.parseHeader(request);
        String userId = request.getHeader("userId");
        String token = headerVo.getToken();
        String deviceType = headerVo.getDeviceType();
        // TODO 验证 token
        Cookie[] cookies = request.getCookies();
        

        return authClient.delete(token, deviceType);
    }


}
