package com.lego.framework.base.sdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author yanglf
 * @description
 * @since 2018/12/21
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenVo {
    /**
     * 用户名
     */
    private String userName;
    /**
     * userId
     */
    private String userId;
    /**
     * 登录 token
     */
    private String token;
    /**
     * 身份证号
     */
    private String cardId;
    /**
     * token 过期时间
     */
    private Date expireTime;
    /**
     * 当前登录设备
     */
    private String deviceType;
    /**
     * 上次登录时间
     */
    private Date lastLoginTime;
    /**
     * 角色
     */
    private String role;
    /**
     * 权限
     */
    private List<String> permissions;
}
