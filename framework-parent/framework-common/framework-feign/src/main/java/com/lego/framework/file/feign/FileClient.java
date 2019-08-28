package com.lego.framework.file.feign;


import com.framework.common.consts.RespConsts;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.system.model.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author gaolei
 * @description
 * @since 2019/8/27
 **/
@FeignClient(value = "file-service", path = "/file/v1", fallback = FileClientFallback.class)
public interface FileClient {

    /**
     * @param req
     * @return
     */
    @RequestMapping(value = "/web/upload", method = RequestMethod.POST)
    RespVO<List<Map<String, Object>>> webUpload(HttpServletRequest req);


}

@Component
class FileClientFallback implements FileClient {


    @Override
    public RespVO webUpload(HttpServletRequest req) {
        return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "file-system服务不可用");
    }
}


