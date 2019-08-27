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

/**
 * @author gaolei
 * @description
 * @since 2019/8/27
 **/
@FeignClient(value = "file-service", path = "/user/v1", fallback = FileClientFallback.class)
public interface FileClient {

    /**
     * @param user
     * @return
     */
    @RequestMapping(value = "/findUserById", method = RequestMethod.POST)
    RespVO<User> findUserById(@RequestBody User user);


}

@Component
class FileClientFallback implements FileClient {

    @Override
    public RespVO<User> findUserById(User user) {
        return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "file-system服务不可用");
    }
}


