package com.lego.framework.user.feign;

import com.framework.common.consts.RespConsts;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.user.model.vo.SsoLoginVo;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/10/15 10:20
 * @desc :
 */
@FeignClient(value = "user-service", path = "/sso/login/", fallbackFactory = LoginClientFallbackFactory.class)
public interface LoginClient {


    /**
     * 验证 session
     *
     * @return
     */
    @GetMapping(value = "checkSession")
    RespVO<SsoLoginVo> checkSession();


}

@Slf4j
@Component
class LoginClientFallbackFactory implements FallbackFactory<LoginClient> {

    @Override
    public LoginClient create(Throwable throwable) {
        return () -> RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "user服务不可用");
    }

}
