package com.lego.framework.sso;

import feign.Headers;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author yanglf
 * @description
 * @since 2019/8/31
 **/
@FeignClient(name = "sso", url = "http://www.baidu.com}", fallbackFactory = SsoClientFallbackFactory.class)
public interface SsoClient {

    /**
     * 访问  sso 服务
     *
     * @param cityCode
     * @return
     */
    @Headers({"User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36"})
    @RequestMapping(value = "/city/{cityCode}", method = RequestMethod.GET)
    String redirectSsoService(@PathVariable(value = "cityCode") String cityCode);

}

@Slf4j
@Component
class SsoClientFallbackFactory implements FallbackFactory<SsoClient> {

    @Override
    public SsoClient create(Throwable throwable) {
        log.error("fallback; reason was:{}", throwable);
        return cityCode -> null;
    }
}
