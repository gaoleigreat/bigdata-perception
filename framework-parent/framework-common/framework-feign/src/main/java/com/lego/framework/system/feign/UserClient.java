package com.lego.framework.system.feign;

import com.framework.common.consts.RespConsts;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.system.model.entity.User;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
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
@FeignClient(value = "system-service", path = "/user/v1", fallbackFactory = UserClientFallbackFactory.class)
public interface UserClient {

    /**
     * @param user
     * @return
     */
    @RequestMapping(value = "/findUser", method = RequestMethod.POST)
    RespVO<User> findUser(@RequestBody User user);


}

@Slf4j
@Component
class UserClientFallbackFactory implements FallbackFactory<UserClient> {


    @Override
    public UserClient create(Throwable throwable) {
        log.error("fallback; reason was:{}", throwable);
        return user -> RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "system服务不可用");
    }
}


