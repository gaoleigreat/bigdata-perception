package com.lego.framework.data.feign;

import com.framework.common.consts.RespConsts;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/10/26 18:36
 * @desc :
 */
@FeignClient(value = "data-service", path = "/data", fallbackFactory = DataClientFallbackFactory.class)
public interface DataClient {


    /**
     * @param templateCode
     * @return
     */
    @RequestMapping(value = "/createTable", method = RequestMethod.POST)
    RespVO createDataTable(@RequestParam(value = "templateCode") String templateCode);
}

@Component
class DataClientFallbackFactory implements FallbackFactory<DataClient> {

    @Override
    public DataClient create(Throwable throwable) {
        return templateCode -> RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "数据服务不可用");
    }
}
