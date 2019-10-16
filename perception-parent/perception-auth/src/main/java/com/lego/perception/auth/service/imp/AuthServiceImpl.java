package com.lego.perception.auth.service.imp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.framework.common.consts.RespConsts;
import com.framework.common.sdto.*;
import com.lego.framework.base.exception.ExceptionBuilder;
import com.lego.framework.system.feign.PermissionClient;
import com.lego.framework.system.feign.UserClient;
import com.lego.framework.system.model.entity.Permission;
import com.lego.framework.system.model.entity.User;
import com.lego.perception.auth.propery.JwtProperty;
import com.lego.perception.auth.service.IAuthService;
import com.lego.perception.auth.utils.JwtTokenUtil;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author yanglf
 * @descript
 * @since 2018/12/20
 **/
@Service
@Slf4j
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private JwtProperty jwtProperty;

    private String prefix = "survey:loginToken:";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PermissionClient permissionClient;

    @Autowired
    private UserClient userClient;


    @Override
    public TokenVo generateUserToken(User user, String deviceType) {
        if (user == null) {
            return null;
        }
        CurrentVo currentVo = generateCurrentVo(user, deviceType);
        AuthVo authVo = new AuthVo();
        authVo.setIssUer(jwtProperty.getClientId());
        authVo.setAudience(jwtProperty.getName());
        authVo.setSubject(deviceType);
        authVo.setCurrentVo(currentVo);
        authVo.setNotBefore(new Date());
        TokenVo tokenVo = jwtTokenUtil.generateToken(currentVo, deviceType);
        //  缓存  token 和  用户信息
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        long expiresSecond = tokenVo.getExpireTime().getTime() / 1000;
        String token = tokenVo.getToken();
        authVo.setToken(token);
        authVo.setExpiration(tokenVo.getExpireTime());
        ops.set(prefix + currentVo.getUserId() + ":" + deviceType, JSONObject.toJSONString(authVo), expiresSecond, TimeUnit.SECONDS);
        tokenVo.setUserName(user.getUsername());
        tokenVo.setCardId(user.getIdCardNO());
        //tokenVo.setRole(user.getRole());
        tokenVo.setUserId(user.getId());
        return tokenVo;
    }

    private CurrentVo generateCurrentVo(User user, String deviceType) {
        CurrentVo currentVo = new CurrentVo();
        currentVo.setDeviceType(deviceType);
        currentVo.setName(user.getRealName());
        findPermissions(currentVo, user);
        currentVo.setPhone(user.getPhone());
        currentVo.setIdCardNumber(user.getIdCardNO());
        return currentVo;
    }


    private void findPermissions(CurrentVo current, User user) {
        List<Permission> permissions = permissionClient.findUserPermissions(user.getId());
        Set<String> permissionSet = new HashSet<>();
        if (!CollectionUtils.isEmpty(permissions)) {
            for (Permission permission : permissions) {
                if (null == permission || org.apache.commons.lang.StringUtils.isEmpty(permission.getScope()) || org.apache.commons.lang.StringUtils.isEmpty(permission.getRId())
                        || org.apache.commons.lang.StringUtils.isEmpty(permission.getPrId())) {
                    continue;
                }
                permissionSet.add(permission.getScope() + "$" + permission.getRId() + "$" + permission.getPrId());
            }
        }
        current.setPermissions(permissionSet);
    }


    @Override
    public AuthVo verifyUserToken(String token, String deviceType) {
        try {
            if (StringUtils.isEmpty(token)) {
                return null;
            }
            TokenVo tokenVo = jwtTokenUtil.getTokenVoFromToken(token);
            if (tokenVo == null) {
                return null;
            }
            Long userId = tokenVo.getUserId();
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            Boolean hasValue = stringRedisTemplate.hasKey(prefix + userId + ":" + deviceType);
            if (hasValue == null || !hasValue) {
                return null;
            }
            String authStr = ops.get(prefix + userId + ":" + deviceType);
            return JSONObject.parseObject(authStr, AuthVo.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("token 解析异常");
            return null;
        }
    }

    @Override
    public Boolean deleteUserToken(String userToken, String deviceType) {
        //  注销用户 token
        TokenVo tokenVo = jwtTokenUtil.getTokenVoFromToken(userToken);
        if (tokenVo == null) {
            return null;
        }
        stringRedisTemplate.delete(userToken);
        Boolean hasAuth = stringRedisTemplate.hasKey(prefix + tokenVo.getUserId() + ":" + deviceType);
        if (hasAuth != null && hasAuth) {
            stringRedisTemplate.delete(prefix + tokenVo.getUserId() + ":" + deviceType);
        }
        return true;
    }

    @Override
    public String hasLogin(Long userId, String deviceType) {
        Boolean hasKey = stringRedisTemplate.hasKey(prefix + userId + ":" + deviceType);
        if (hasKey != null && hasKey) {
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            String authVoStr = ops.get(prefix + userId + ":" + deviceType);
            AuthVo authVo = JSONObject.parseObject(authVoStr, AuthVo.class);
            if (authVo != null) {
                return authVo.getToken();
            }
        }
        return null;
    }

    @Override
    public Integer setUserToken(User user, String deviceType, String token) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();

        String authStr = ops.get(prefix + user.getId() + ":" + deviceType);
        if (authStr == null) {
            return 0;
        }
        AuthVo authVo = JSONObject.parseObject(authStr, AuthVo.class);
        if (authVo == null) {
            return 0;
        }
        CurrentVo currentVo = generateCurrentVo(user, deviceType);
        authVo.setCurrentVo(currentVo);
        TokenVo tokenVo = jwtTokenUtil.getTokenVoFromToken(token);
        if (tokenVo == null || tokenVo.getExpireTime() == null) {
            return 0;
        }
        long expire = tokenVo.getExpireTime().getTime() / 1000;
        ops.set(prefix + user.getId() + ":" + deviceType, JSONObject.toJSONString(authVo), expire, TimeUnit.SECONDS);
        return 1;
    }

    @Override
    public TokenVo generateServiceToken(String fromService, String toService) {
        // 使用加密算法  HS256
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date nowDate = new Date(nowMillis);
        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtProperty.getBase64Secret());
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        JwtBuilder jwtBuilder = Jwts.builder().setHeaderParam("type", "JWT")
                .claim("fromService", fromService)
                .claim("toService", toService)
                // 设置 jwt 的签发者
                .setIssuer(jwtProperty.getClientId())
                // 设置 接收 jwt 的名称
                .setAudience(jwtProperty.getName())
                //  设置  jwt 所面向的对象
                .setSubject("service")
                .signWith(signatureAlgorithm, signingKey);
        Date date = new Date(nowMillis + 1000 * 10);
        jwtBuilder.setExpiration(date)
                // 如果当前时间在 nowDate 之前  token不生效
                .setNotBefore(nowDate);
        String compact = jwtBuilder.compact();
        TokenVo tokenVo = new TokenVo();
        tokenVo.setToken(compact);
        tokenVo.setExpireTime(date);
        return tokenVo;
    }

    @Override
    public Boolean verifyServiceToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(jwtProperty.getBase64Secret()))
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (ExpiredJwtException ex) {
            log.error("service  token  expire ");
        } catch (SignatureException | MalformedJwtException ex) {
            log.error(" service token invalid");
        }
        return null;
    }

    @Override
    public RespVO saveUserToken(String idNumber, String sessionId) {
        try {
            User user = new User();
            user.setIdCardNO(idNumber);
            RespVO<User> respVO = userClient.findUserById(user);
            if (respVO.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
                return RespVOBuilder.failure();
            }
            User info = respVO.getInfo();
            if (info == null) {
                return RespVOBuilder.failure("用户不存在");
            }
            CurrentVo currentVo = generateCurrentVo(info, "2");
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            long webExpires = jwtProperty.getWebExpires();
            ops.set(sessionId, JSONObject.toJSONString(currentVo), webExpires, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionBuilder.operateFailException("token保存失败");
        }
        return RespVOBuilder.success();
    }

    @Override
    public RespVO<CurrentVo> getUserToken(String sessionId) {
        CurrentVo currentVo = null;
        try {
            if (StringUtils.isEmpty(sessionId)) {
                return RespVOBuilder.failure();
            }
            Boolean aBoolean = stringRedisTemplate.hasKey(sessionId);
            if (aBoolean == null || !aBoolean) {
                return RespVOBuilder.failure();
            }
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            String session = ops.get(sessionId);
            currentVo = JSONObject.parseObject(session, CurrentVo.class);
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionBuilder.operateFailException("获取session失败");
        }
        return RespVOBuilder.success(currentVo);
    }

    @Override
    public RespVO removeUserToken(String sessionId) {
        try {
            if (StringUtils.isEmpty(sessionId)) {
                return RespVOBuilder.failure();
            }
            Boolean aBoolean = stringRedisTemplate.hasKey(sessionId);
            if (aBoolean == null || !aBoolean) {
                return RespVOBuilder.success();
            }
            stringRedisTemplate.delete(sessionId);
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionBuilder.operateFailException("获取session失败");
        }
        return RespVOBuilder.success();
    }


}
