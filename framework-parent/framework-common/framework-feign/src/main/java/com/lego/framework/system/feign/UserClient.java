package com.lego.framework.system.feign;

import com.framework.common.consts.RespConsts;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.system.model.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author yanglf
 * @description
 * @since 2019/8/27
 **/
@FeignClient(value = "system-service", path = "/user/v1", fallback = UserClientFallback.class)
public interface UserClient {

    /**
     * @param user
     * @return
     */
    @RequestMapping(value = "/findUserById", method = RequestMethod.POST)
    RespVO<User> findUserById(@RequestBody User user);


}

@Component
class UserClientFallback implements UserClient {

    @Override
    public RespVO<User> findUserById(User user) {
        return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "system服务不可用");
    }
}

