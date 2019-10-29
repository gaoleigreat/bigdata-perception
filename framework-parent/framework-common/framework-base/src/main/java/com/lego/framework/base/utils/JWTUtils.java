package com.lego.framework.base.utils;

import com.alibaba.fastjson.JSONObject;
import com.framework.common.sdto.AuthVo;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * @author yanglf
 * @description
 * @since 2019/1/24
 **/

@Slf4j
public class JWTUtils {

    private static String securityKey = "ijweuiwherewiurhw9890u9jhi";


    /**
     * 获取Token
     *
     * @param authVo
     * @return
     */

    public static String getToken(AuthVo authVo) {
        // 使用加密算法  HS256
        long nowMillis = System.currentTimeMillis();
        Date nowDate = new Date(nowMillis);

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(securityKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder jwtBuilder = Jwts.builder().setHeaderParam("type", "JWT")
                .claim("role", authVo.getCurrentVo().getProjectRoles())
                .claim("permissions", JSONObject.toJSONString(authVo.getCurrentVo().getPermissions()))
                .claim("userName", authVo.getCurrentVo().getUserName())
                .claim("userId", authVo.getCurrentVo().getUserId())
                // 设置 jwt 的签发者
                // 设置 接收 jwt 的名称
                //  设置  jwt 所面向的对象
                .signWith(signatureAlgorithm, signingKey);
        //添加Token过期时间
        long expiresSecond = 30;
        long expMillis = nowMillis + (expiresSecond * 1000);
        Date exp = new Date(expMillis);
        // 设置  jwt  的过期时间
        jwtBuilder.setExpiration(exp)
                // 如果当前时间在 nowDate 之前  token不生效
                .setNotBefore(nowDate);
        authVo.setExpiration(exp);
        authVo.setNotBefore(nowDate);
        //  头部(Header)、载荷(Payload)与签名(Signature)
        return jwtBuilder.compact();
    }


    /**
     * 检查Token是否合法
     *
     * @param token
     * @return JWTResult
     */

    public static void checkToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(securityKey)).parseClaimsJws(token).getBody();
            Long sub = claims.get("userId", Long.class);
            log.error("userId:" + sub);
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            log.error("token 失效");
        } catch (SignatureException e) {
            e.printStackTrace();
            log.error("token 解析失败");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("解析 token 异常");
        }
    }
}

