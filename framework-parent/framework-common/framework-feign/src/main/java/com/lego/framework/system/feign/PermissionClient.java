package com.lego.framework.system.feign;

import com.framework.common.consts.RespConsts;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.system.model.entity.Permission;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author yanglf
 * @description
 * @since 2019/8/23
 **/
@FeignClient(value = "system-service", path = "/permission/v1", fallback = PermissionClientFallback.class)
public interface PermissionClient {


    /**
     * 添加权限点
     *
     * @param scope
     * @param permissions
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    RespVO save(@RequestParam(value = "scope") String scope, @RequestBody List<Permission> permissions);

}

@Component
class PermissionClientFallback implements PermissionClient {

    @Override
    public RespVO save(String scope, List<Permission> permissions) {
        return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "system服务不可用");
    }
}
